
package osa.ora.graph.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.DgsMutation;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import osa.ora.graph.data.beans.Item;
import osa.ora.graph.data.beans.NewItem;
import osa.ora.graph.data.beans.NewOrder;
import osa.ora.graph.data.beans.Order;

/**
 *
 * @author ooransa
 */
@DgsComponent
public class OrdersDatafetcher {
    //all orders
    private static List<Order> orders = new ArrayList();
    /**
     * load initial orders from orders.json file as initial list for testing ...
     */
    @PostConstruct
    public void initialize() {
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("orders.json");
            String itemsJson = new String(is.readAllBytes());
            //initialize the orders here
            ObjectMapper objectMapper = new ObjectMapper();
            orders = objectMapper.readValue(itemsJson, new TypeReference<List<Order>>(){});
        } catch (Exception ex) {
            System.out.println("Invalid JSON Parsing for initial Orders!!");
            ex.printStackTrace();
        }
    }
    /**
     * This method return specific order using its id
     * @param orderId
     * @return 
     */
    @DgsQuery
    public Order getOrder(@InputArgument int orderId) {
        //return order that matches the orderId
        for(int i=0;i<orders.size();i++){
            if(orders.get(i).getOrderId()==orderId){
                return orders.get(i);
            }
        }
        return null;
    }
    /**
     * This method add new item to the catalog or update its price/stock value
     * @param newItem
     * @return 
     */
    @DgsMutation
    public Order submitNewOrder(@InputArgument (value="input") NewOrder newOrder){
        //validate if null
        if(newOrder==null){
            System.out.println("New Order is null!!");
            return null;
        }
        //check if already exist
        for(Order order:orders){
            if(order.getOrderId()==newOrder.getOrderId()) {
                System.out.println("Already exist, will return it!");
                return order;
            }
        }
        //add the new order ..
        System.out.println("New Order will be Submiited!");
        Order submiitedOrder=new Order(newOrder.getOrderId(),newOrder.getOrderDate(), newOrder.getTotalPrice(),newOrder.getNumberOfItems(),
        newOrder.getStatus(),newOrder.getStatusDesc(),new Item[newOrder.getItems().length],newOrder.getDeliveryLocation());
        //add the new order items 
        for(int i=0;i<newOrder.getItems().length;i++){
            NewItem item=newOrder.getItems()[i];
            submiitedOrder.getItems()[i]=new Item(item.getItemNo(),item.getItemDesc(),item.getItemPrice(), item.getType()
            ,item.getTypeDesc(), item.getItemSize(),0,item.getPurchaseCount());
        }
        //int orderId,String orderDate,float totalPrice,int numberOfItems,
        //   int status,String statusDesc,Item[] items,String deliveryLocation
        orders.add(submiitedOrder);
        return submiitedOrder; 
    }
    /**
     * This method returns all catalog items
     * @return 
     */
    @DgsQuery
    public Order[] getAllOrders() {
       return (orders.toArray(new Order[0]));
    }

}
