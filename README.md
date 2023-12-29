# cacmer project
application base microservices: **addresses**, **customers**, **account**, **movement** and **risk-evaluation**
## modeling
![CACMER](https://github.com/placidenduwayo1/k8s-ingress-kafka-avro-cacm-back/assets/124048212/713a0dd8-a4a1-4c96-9d82-7a822dd3b2c7)

**risk-evaluation-service** is separated microservices. it permits a movement when followin conditions are fulfilled:
 - customer related to account is **active**
 - account type **current**
 - current account has **enough balance** or has enough **overdraft** to undergo movement



