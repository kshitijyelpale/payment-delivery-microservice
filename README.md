# Challenge

## :computer: How to execute

_Description of how to run/execute your program..._

1. Just build the docker-compose by command `docker-compose build`, it creates my `delivery` microservice java container along with 
your provided containers.

2. Then just execute `docker-compose up`. And it should work. 

3. On my system, this does not work because my application trying to connect to Kafka for `localhost:9092`. I also mentioned docker 
name in the `KafkaConfig.java` and tried both internal and external ports.

4. But the GOOD NEWS is it worked on my local system by executing my application in IDE and it connected properly and consumed the 
messages. 

## :memo: Notes

_Some notes or explaination of your solution..._

1. To connect to Kafka server, I had to add an ebtry in my `hosts` file as `127.0.0.1  kafka-server`.

2. Have handled the application in `Async` mode to consider the scalaibility of the service using `ExcutorService`. 

3. To handle concurrency problem, added `Transactional` when storing data in `PostgreSQL`. For other parts exception handling would 
suffice.



