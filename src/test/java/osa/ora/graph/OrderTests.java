package osa.ora.graph;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import osa.ora.graph.data.OrdersDatafetcher;
import osa.ora.graph.data.beans.NewItem;
import osa.ora.graph.data.beans.NewOrder;
import osa.ora.graph.data.beans.Order;

@SpringBootTest
class OrderTests {

    @Test
    void contextLoads() {
    }
    /**
     * Method test get orders
     */
    @Test
    public void testGetOrders() {
        int AVAILABLE_ORDER=1001;
        int NOT_AVAILABLE_ORDER=2001;
        OrdersDatafetcher datafetchcer=new OrdersDatafetcher();
        Order[] allIOrders=datafetchcer.getAllOrders();
        assertNotNull(allIOrders);
        Order order=datafetchcer.getOrder(AVAILABLE_ORDER);
        assertNotNull(order);
        order=datafetchcer.getOrder(NOT_AVAILABLE_ORDER);
        assertNull(order);
    }
    /**
     * Method test submit new order
     */
    @Test
    public void testSubmitNewOrder() {
        int EMPTY_CATALOG_TYPE=16;
        int NOT_AVAILABLE_ORDER=3001;
        OrdersDatafetcher datafetchcer=new OrdersDatafetcher();
        NewItem newItem=new NewItem(30,"Nike Wrist Band",100,EMPTY_CATALOG_TYPE,"Wrist Band","Unisex",0,2);
        NewOrder newOrder=new NewOrder(NOT_AVAILABLE_ORDER,"23-12-2021",200,2,1,"Paid",new NewItem[]{newItem},"Giza, Egypt");       
        Order submitted=datafetchcer.submitNewOrder(newOrder);
        assertNotNull(submitted);
        submitted=datafetchcer.getOrder(NOT_AVAILABLE_ORDER);
        assertNotNull(submitted);
    }

}
