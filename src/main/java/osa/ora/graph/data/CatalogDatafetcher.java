
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
import osa.ora.graph.data.beans.Catalog;
import osa.ora.graph.data.beans.Item;
import osa.ora.graph.data.beans.NewItem;

/**
 *
 * @author ooransa
 */
@DgsComponent
public class CatalogDatafetcher {
    //all catalog items
    private static List<Item> items = new ArrayList();
    /**
     * load initial catalog items from catalog.json file as initial list for testing ...
     */
    @PostConstruct
    public void initialize() {
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("catalog.json");
            String itemsJson = new String(is.readAllBytes());
            //initialize the catalog here
            ObjectMapper objectMapper = new ObjectMapper();
            items = objectMapper.readValue(itemsJson, new TypeReference<List<Item>>(){});
        } catch (Exception ex) {
            System.out.println("Invalid JSON Parsing for initial catalog!!");
            ex.printStackTrace();
        }
    }
    /**
     * This method return the catalog items for specific catalog type
     * @param catalogId
     * @return 
     */
    @DgsQuery
    public Catalog getCatalog(@InputArgument int catalogId) {
        //catalogId of 0 means all catalog items
        if(catalogId == 0) {
            return new Catalog(0,"All Items",items.toArray(new Item[0]));
        }
        //get the required catalog id
        List<Item> catalogItems = new ArrayList();
        for(int i=0;i<items.size();i++){
            if(items.get(i).getType()==catalogId){
                catalogItems.add(items.get(i));
            }
        }
        //return the catalog 
        if(catalogItems.size()>0){
            return new Catalog(0,catalogItems.get(0).getTypeDesc(),catalogItems.toArray(new Item[0]));
        }else{
            return new Catalog(0,"No Item Found",null);
        }
    }
    /**
     * This method add new item to the catalog or update its price/stock value
     * @param newItem
     * @return 
     */
    @DgsMutation
    public Item addToStock(@InputArgument (value="input") NewItem newItem){
        if(newItem==null){
            System.out.println("New Item is null!!");
            return null;
        }
        //validate if the item already exist, update its price and stock
        //other elements can be also updated if required
        for(Item item:items){
            if(item.getItemNo()==newItem.getItemNo()) {
                System.out.println("Already exist, will append stock and update price!");
                item.setStock(item.getStock()+newItem.getStock());
                if(item.getStock()<0) item.setStock(0);
                item.setItemPrice(newItem.getItemPrice());
                return item;
            }
        }
        //add the new element to the items
        System.out.println("New Item will be Added to Stock!");
        Item addedItem=new Item(newItem.getItemNo(),newItem.getItemDesc(),newItem.getItemPrice(), newItem.getType()
            ,newItem.getTypeDesc(), newItem.getItemSize(),newItem.getStock(),0);
        items.add(addedItem);
        return addedItem; 
    }
    /**
     * This method delete item from the stock
     * @param itemId
     * @return 
     */
    @DgsMutation
    public boolean deleteFromStock(@InputArgument (value="input") int itemId){
       if(itemId<=0){
            System.out.println("Item id cannot be zero or minus!!");
            return false;
        }
        for(Item item:items){
            if(item.getItemNo()==itemId) {
                System.out.println("Item found with id="+itemId);
                items.remove(item);
                return true;
            }
        }
        return false;
    }
    /**
     * Method to retrieve specific item
     * @param itemId
     * @return 
     */
    @DgsQuery
    public Item getItem(@InputArgument int itemId){
        if(itemId<=0){
            System.out.println("Item id cannot be zero or minus!!");
            return null;
        }
        //validate if the item already exist, update its price and stock
        //other elements can be also updated if required
        for(Item item:items){
            if(item.getItemNo()==itemId) {
                System.out.println("Item found with id="+itemId);
                return item;
            }
        }
        return null;
    }
    /**
     * This method returns all catalog items
     * @return 
     */
    @DgsQuery
    public Item[] getAllItems() {
       return (items.toArray(new Item[0]));
    }

}
