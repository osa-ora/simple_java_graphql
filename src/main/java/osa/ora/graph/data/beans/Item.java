
package osa.ora.graph.data.beans;

/**
 *
 * @author ooransa
 */
public class Item {
    private int itemNo;
    private int type;
    private String typeDesc;
    private String itemDesc;
    private float itemPrice;
    private String itemSize;
    private int stock;
    private int purchaseCount;
    
    public Item(){
        
    }
    public Item(int itemNo,String itemDesc,float itemPrice,int type,String typeDesc,String itemSize,int stock,int purchaseCount){
        this.itemDesc=itemDesc;
        this.itemNo=itemNo;
        this.itemPrice=itemPrice;
        this.type=type;
        this.typeDesc=typeDesc;
        this.itemSize=itemSize;
        this.stock=stock;
        this.purchaseCount=purchaseCount;
    }

    /**
     * @return the itemNo
     */
    public int getItemNo() {
        return itemNo;
    }

    /**
     * @param itemNo the itemNo to set
     */
    public void setItemNo(int itemNo) {
        this.itemNo = itemNo;
    }

    /**
     * @return the itemDesc
     */
    public String getItemDesc() {
        return itemDesc;
    }

    /**
     * @param itemDesc the itemDesc to set
     */
    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    /**
     * @return the itemPrice
     */
    public float getItemPrice() {
        return itemPrice;
    }

    /**
     * @param itemPrice the itemPrice to set
     */
    public void setItemPrice(float itemPrice) {
        this.itemPrice = itemPrice;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the typeDesc
     */
    public String getTypeDesc() {
        return typeDesc;
    }

    /**
     * @param typeDesc the typeDesc to set
     */
    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    /**
     * @return the itemSize
     */
    public String getItemSize() {
        return itemSize;
    }

    /**
     * @param itemSize the itemSize to set
     */
    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the purchaseCount
     */
    public int getPurchaseCount() {
        return purchaseCount;
    }

    /**
     * @param purchaseCount the purchaseCount to set
     */
    public void setPurchaseCount(int purchaseCount) {
        this.purchaseCount = purchaseCount;
    }
    
}
