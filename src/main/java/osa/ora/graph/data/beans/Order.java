
package osa.ora.graph.data.beans;

/**
 *
 * @author ooransa
 */
public class Order {
   private int orderId;
   private String orderDate;
   private float totalPrice;
   private int numberOfItems;
   private int status;
   private String statusDesc;
   private Item[] items;
   private String deliveryLocation;
   
   public Order(){
       
   }
   public Order(int orderId,String orderDate,float totalPrice,int numberOfItems,
           int status,String statusDesc,Item[] items,String deliveryLocation){
       this.orderId=orderId;
       this.orderDate=orderDate;
       this.totalPrice=totalPrice;
       this.numberOfItems=numberOfItems;
       this.status=status;
       this.statusDesc=statusDesc;
       this.items=items;
       this.deliveryLocation=deliveryLocation;
       
   }
    /**
     * @return the orderId
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * @return the orderDate
     */
    public String getOrderDate() {
        return orderDate;
    }

    /**
     * @param orderDate the orderDate to set
     */
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * @return the totalPrice
     */
    public float getTotalPrice() {
        return totalPrice;
    }

    /**
     * @param totalPrice the totalPrice to set
     */
    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * @return the numberOfItems
     */
    public int getNumberOfItems() {
        return numberOfItems;
    }

    /**
     * @param numberOfItems the numberOfItems to set
     */
    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the statusDesc
     */
    public String getStatusDesc() {
        return statusDesc;
    }

    /**
     * @param statusDesc the statusDesc to set
     */
    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
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

    /**
     * @return the deliveryLocation
     */
    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    /**
     * @param deliveryLocation the deliveryLocation to set
     */
    public void setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }
   
}
