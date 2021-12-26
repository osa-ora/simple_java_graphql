# Simple Java SpringBoot GraphQL Example

This Project provides basic query/mutations operation for Catalog/Orders showing how easy and simple to use GraphQL to retrive/update what is really required only. The project uses JSON files to load initial catalog items and orders to show these operatons.  

To run locally with Maven installed:   

```
mvn test // to run the unit tests
mvn clean test spring-boot:run // to build, test and run the application
mvn package //to build the jar file of this SpringBoot app

```
To deploy the application directly into Openshift using s2i you can use the folloing:
```
oc new-app --name=graphql java~https://github.com/osa-ora/simple_java_graphql
oc expose svc/graphql
```
The application enables query the full data or partial data using graphql capabilities
```
# to get full item details
query {
  getItem {
    itemNo
    type
    typeDesc
    itemDesc
    itemPrice
    itemSize
    stock
    purchaseCount
  } 
}
# to get full catalog details
query {
  getCatalog (catalogId: 1) {
    catalogId
    catalogType
    items {
      itemNo
      type
      typeDesc
      itemDesc
      itemPrice
      itemSize
      stock
      purchaseCount
    }
  }
}
# to get full order details
query {
  getOrder(orderId: 1001) {
    orderId
    orderDate
    totalPrice
    numberOfItems
    status
    statusDesc
    deliveryLocation
    items {
      itemNo
      type
      typeDesc
      itemDesc
      itemPrice
      itemSize
      stock
      purchaseCount
    }
  }
}
# Add item to stock using mutation
mutation {
    addToStock (input: { 
      itemNo: 60,
      type: 20,
      typeDesc:"Women T-shirt",
      itemDesc: "T-Shirt Polo Red XL",
      itemSize: "XL"
      stock: 10,
      itemPrice: 8.0
    }){
    itemNo
    stock
    itemPrice
  }
}
# Submit new order using mutation
mutation { 
  submitNewOrder(input: {
    orderId: 2003 
    orderDate: "21/12/2021",  
    totalPrice: 8,  
    deliveryLocation: "Cairo Egypt",  
    status: 1,
    statusDesc: "Submitted", 
    numberOfItems: 1  items: [
      {itemNo: 4, 
        type: 1, 
        typeDesc:"Men T-shirt", 
        itemDesc: "T-Shirt Avengers L", 
        itemSize: "L",
        stock: 12, 
        itemPrice: 8.0, 
        purchaseCount: 1}]  
      }
    ) {
    orderId
    orderDate
    totalPrice
    numberOfItems
    status
    statusDesc
    deliveryLocation
  } 
}
```
You can remove any fields that is not required from the caller prospective.

To test the application is running "Query Samples"
```
curl -X POST -H "Content-Type: application/json" --data '{"query":"{getAllOrders { orderId orderDate totalPrice }}"}' http://localhost:8080/graphql

curl -X POST -H "Content-Type: application/json" --data '{"query":"{getOrder (orderId: 1001) { orderId totalPrice }}"}' http://localhost:8080/graphql

curl -X POST -H "Content-Type: application/json" --data '{"query":"{getCatalog (catalogId: 1){ catalogId  catalogType  items {itemNo typeDesc  itemPrice}}}"}' http://localhost:8080/graphql
```
To test the application is running "Mutation Samples" i.e. Update/submit
Note: You just need to change the end point url "localhost:8080/graphql" and use Openshift route url instead or execute the query locally inside the container. 
```
curl -X POST -H "Content-Type: application/json" --data  '{"query":"mutation{  addToStock (input: {  itemNo: 22,  type: 16,  typeDesc:\"Head Band\",  itemDesc: \"Tennis Head Band\",  itemSize: \"One Size\", stock: 23, itemPrice: 5  }){ itemNo  itemSize  itemPrice  stock}}"}'  http://localhost:8080/graphql

curl -X POST -H "Content-Type: application/json" --data  '{"query":"{ getItem (itemId: 22) { itemNo itemDesc itemPrice stock} }"}'  http://localhost:8080/graphql

curl -X POST -H "Content-Type: application/json" --data  '{"query":"mutation { submitNewOrder(input: { orderId: 2003 orderDate: \"21/12/2021\"  totalPrice: 8  deliveryLocation: \"Cairo, Egypt\"  status: 1 statusDesc: \"Submitted\" numberOfItems: 1  items: [  {itemNo: 4, type: 1, typeDesc:\"Men T-shirt\", itemDesc: \"T-Shirt Avengers L\", itemSize: \"L\" stock: 12, itemPrice: 8.0, purchaseCount: 1}]  }) {  orderId  orderDate  totalPrice  deliveryLocation}}"}'  http://localhost:8080/graphql

curl -X POST -H "Content-Type: application/json" --data  '{"query":"mutation{ deleteFromStock (input: 22) }"}'  http://localhost:8080/graphql

curl -X POST -H "Content-Type: application/json" --data  '{"query":"{ getItem (itemId: 22) { itemNo itemDesc itemPrice stock} }"}'  http://localhost:8080/graphql

```


To understand the power of graphql you can open the graphql test interface which will guide you to execute different queries:

URL: /graphiql

You can then execute some queries and mutations there to test it.

<img width="1785" alt="Screen Shot 2021-12-23 at 12 16 12" src="https://user-images.githubusercontent.com/18471537/147225882-7520911e-df79-410f-9d65-94db2f68c0d6.png">

You can also see the documentation to the right which can help you to build and construct queries and mutations easily. 
Note that you can switch off this interface by modifying application.properties attribute:
```
dgs.graphql.graphiql.enabled=false
```



