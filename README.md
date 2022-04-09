# Simple Java SpringBoot GraphQL Example

This Project provides basic query/mutations operation for Catalog/Orders objects showing how easily you can use GraphQL to retrive/update the required data only. The project uses JSON files to load initial catalog items and orders to demostrate these operatons.   

GraphQL is efficient in reducing the over/under-fetching of the data which improve the performance by reducing the number of calls required to load the data, it might be slower then normal REST APIs but by fixing this over/under fetching the overall system performance become better. It also reduces the number of API varaiants required and provides some flexibility to the consumers for dynamically changing APIs. 

![og-image](https://user-images.githubusercontent.com/18471537/162588891-85f0d961-4833-446b-b4f4-6da5ca1abb92.png)


## How to Use GraphQL within your SpringBoot App
- First we need to import the required depedencies in the maven POM file as you can see in the POM file we are using Netflix Graphql library.
```
  <groupId>com.netflix.graphql.dgs</groupId>
  <artifactId>graphql-dgs-platform-dependencies</artifactId>
  ....
```
- Add inside the resources/schema folder "schema.graphqls" and define your objects as following:
```
// This is for all your queries i.e. data fetching using the Query section
type Query {
    getCatalog(catalogId: Int): Catalog
    getAllItems: [Item]
    getItem(itemId: Int): Item
    getAllOrders: [Order]
    getOrder(orderId: Int) : Order
}
//This is for all your data inserts/updates/delete using Mutation section
type Mutation {
    addToStock(input: NewItem) : Item
    deleteFromStock(input: Int): Boolean
    submitNewOrder(input: NewOrder) : Order
}
//This is a custom type used for data fetching and refer to other object "Item" using "type" keyword
type Catalog {
    catalogId: Int
    catalogType: String
    items: [Item]
}
type Item {
    itemNo: Int
    type: Int
    ....
}
//This define a type used to insert/update/delete data using "input" keyword
input NewItem {
    itemNo: Int
    type: Int
    .....
}
```
- Implement the data manipulations for example:
```
//1. annotate your class with "@DgsComponent"
  @DgsComponent
  public class CatalogDatafetcher {

//2. This is a query to fetch one item in the catalog using input argument and annotated with "@DgsQuery"
    @DgsQuery
    public Item getItem(@InputArgument int itemId){
    
//3. This is used to insert new item, it uses the Mutation inout types as input arguments and annotated with "@DgsMutation"
    @DgsMutation
    public Item addToStock(@InputArgument (value="input") NewItem newItem){
```
To run it locally using Maven:   

```
mvn test // to run the unit tests
mvn clean test spring-boot:run // to build, test and run the application
mvn package //to build the jar file of this SpringBoot app
//or package then run
mvn package
java -jar target/graph-0.0.1-SNAPSHOT.jar
//or run a specifc listening port
java -jar -Dserver.port=8083 target/graph-0.0.1-SNAPSHOT.jar
```
To deploy the application directly into Openshift using s2i you can use the following (using OpenShift "OC" client:
```
oc new-app --name=graphql java~https://github.com/osa-ora/simple_java_graphql
oc expose svc/graphql
```
To build & run as a Docker image (if you have Docker available locally)
```
//use the Dockerfile
docker build -t spring/graph:1.0 .
docker run -p 8080:8080 spring/graph:1.0
//or run a specific port e.g. 8083
docker run -p 8083:8083 -e PORT=8083 spring/graph:1.0
```
The application enables query for the full or partial data using graphql capabilities
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



