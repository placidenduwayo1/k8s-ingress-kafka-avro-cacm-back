# cacmer project
application base microservices: **addresses**, **customers**, **account**, **movement** and **risk-evaluation**
## modeling
![CACMER](https://github.com/placidenduwayo1/k8s-ingress-kafka-avro-cacmer-back/assets/124048212/99e16818-fac1-4c6b-9bcc-560ac7526361)


**risk-evaluator** is small separated microservices that evaluates customers and accounts risk against operations, this service need:
 - customer **status** (active / archive) and **risk** (low/high)
 - account type **current**/**saving**
 - account **balance**and **overdraft**
 - movement **amount**

# exposed endpoints by microservices

## gateway service
**[GET]** http://localhost:81  
all exposed endpoints by business microservices are  through the gateway :**http://localhost:81/endpoints**  

**risk-evaluation** is a background microservices, it has not endpoint

## business microservice address
**[GET]** http://localhost:81/bs-ms-address  
**[GET]** http://localhost:81/bs-ms-address/addresses  
**[GET]**/**[DELETE]** http://localhost:81/bs-ms-address/addresses/id/{value}  
**[POST]**/**[PUT]** http://localhost:81/bs-ms-address/addresses  
*payload*  
```
{
    "num":int,
    "street":string,
    "pb":int,
    "city":string,
    "country":string
}
```
## business microservice customer
**[GET]** http://localhost:81/bs-ms-customer  
**[GET]** http://localhost:81/bs-ms-customer/customers  
**[GET]** http://localhost:81/bs-ms-customer/customers/name/{lastname}  
**[GET]** http://localhost:81/bs-ms-customer/customers/addresses/id/{amountid}  
**[GET]** http://localhost:81/bs-ms-customer/customers/addresses/city/{city}  
**[GET]**/**[DELETE]** http://localhost:81/bs-ms-customer/customers/id/{id}  
**[POST]**/**[PUT]** http://localhost:81/bs-ms-customer/customers  
*payload*
```
{
    "firstname":string,
    "lastname":string,
    "risk":string,
    "status":string,
    "country":string,
    "address-id":string
}
```
## business microservice account
**[GET]** http://localhost:81/bs-ms-account    
**[GET]** http://localhost:81/bs-ms-account/accounts  
**[GET]**/**[DELETE]** http://localhost:81/bs-ms-account/accounts/id/{id}  
**[GET]** http://localhost:81/bs-ms-account/accounts/customers/id/{id}  
**[GET]** http://localhost:81/bs-ms-account/accounts/customers/name/{lastname}  
**[POST]**/**[PUT]** http://localhost:81/bs-ms-customer/customers  
*payload*
```
{
    "type":string,
    "balance":double,
    "overdraft":double,
    "customerId":string
}
```
## business microservice movement

**[GET]** http://localhost:81/bs-ms-mvt  
**[GET]** http://localhost:81/bs-ms-mvt/movements  
**[GET]**/**[DELETE]** http://localhost:81/bs-ms-mvt/movements/id/{id}  
**[GET]** http://localhost:81/bs-ms-mvt/movements/customers/name/{lastname}  
**[POST]**/**[PUT]** http://localhost:81/bs-ms-mvt/movements  
*paylaod* 
```
{
    "sens":string,
    "amount": double,
    "account-id": string
}
```


