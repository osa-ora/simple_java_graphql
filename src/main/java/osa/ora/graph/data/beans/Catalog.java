
package osa.ora.graph.data.beans;

/**
 *
 * @author ooransa
 */
public class Catalog {
    private int catalogId;
    private String catalogType;
    private Item[] items;
    
    public Catalog(){
        
    }
    public Catalog(int catalogId,String catalogType,Item[] items){
        this.catalogId=catalogId;
        this.catalogType=catalogType;
        this.items=items;
    }

    /**
     * @return the catalogId
     */
    public int getCatalogId() {
        return catalogId;
    }

    /**
     * @param catalogId the catalogId to set
     */
    public void setCatalogId(int catalogId) {
        this.catalogId = catalogId;
    }

    /**
     * @return the catalogType
     */
    public String getCatalogType() {
        return catalogType;
    }

    /**
     * @param catalogType the catalogType to set
     */
    public void setCatalogType(String catalogType) {
        this.catalogType = catalogType;
    }

    /**
     * @return the items
     */
    public Item[] getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(Item[] items) {
        this.items = items;
    }
    
}
