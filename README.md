# cacmer project
application base microservices: **addresses**, **customers**, **account**, **movement** and **risk-evaluation**
## modeling
![CACMER](https://github.com/placidenduwayo1/k8s-ingress-kafka-avro-cacm-back/assets/124048212/f3f3b3f6-ae2e-4a71-9ecb-9ec055a54a34)


**risk-evaluation-service** is separated microservices. it permits a movement when followin conditions are fulfilled:
 - customer related to account is **active**
 - account type **current**
 - current account has **enough balance** or has enough **overdraft** to undergo movement



