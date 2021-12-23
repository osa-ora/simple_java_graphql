package osa.ora.graph;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import osa.ora.graph.data.CatalogDatafetcher;
import osa.ora.graph.data.beans.Item;
import static org.junit.jupiter.api.Assertions.*;
import osa.ora.graph.data.beans.Catalog;
import osa.ora.graph.data.beans.NewItem;

@SpringBootTest
class CatalogTests {

    @Test
    void contextLoads() {
    }
    /**
     * Method test get catalog items
     */
    @Test
    public void testGetCatalog() {
        int EMPTY_CATALOG_TYPE=15;
        CatalogDatafetcher datafetchcer=new CatalogDatafetcher();
        Item[] allItems=datafetchcer.getAllItems();
        assertNotNull(allItems);
        Catalog catalog=datafetchcer.getCatalog(1);
        assertNotNull(catalog);
        catalog=datafetchcer.getCatalog(EMPTY_CATALOG_TYPE);
        assertNull(catalog.getItems());
    }
    /**
     * Method test add new item to the catalog
     */
    @Test
    public void testAddCatalogItem() {
        int EMPTY_CATALOG_TYPE=16;
        CatalogDatafetcher datafetchcer=new CatalogDatafetcher();
        NewItem newItem=new NewItem(30,"Nike Wrist Band",100,EMPTY_CATALOG_TYPE,"Wrist Band","Unisex",40,0);
        Item item=datafetchcer.addToStock(newItem);
        assertEquals(item.getItemNo(),newItem.getItemNo());
        Catalog catalog=datafetchcer.getCatalog(EMPTY_CATALOG_TYPE);
        assertNotNull(catalog);               
    }

}
