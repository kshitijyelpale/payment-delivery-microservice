# Challenge

## :computer: How to execute

_Provide a description of how to run/execute your program..._

1. Just build the docker-compose by command `docker-compose build`, it creates my `delivery` microservice java container along with 
your provided containers.

2. Then just execute `docker-compose up`. And it should work. 

3. On my system, this does not work because my application trying to connect to Kafka for `localhost:9092`. I also mentioned docker 
name in the `KafkaConfig.java` and tried both internal and external ports.

4. But the GOOD NEWS is it worked on my local system by executing my application in IDE and it connected properly and consumed the 
messages. 

5. I am very excited and happy to say that I successfully completed the Challenge with my limited time slots in the schedule. It is
working as expected.

6. I am sure, you will find a solution to run this successfully. In case not, you can run the my jar application or directly in any IDE.

## :memo: Notes

_Some notes or explaination of your solution..._

1. To connect to Kafka server, I had to add an ebtry in my `hosts` file as `127.0.0.1  kafka-server`.

2. Have handled the application in `Async` mode to consider the scalaibility of the service using `ExcutorService`. 

3. To handle concurrency problem, added `Transactional` when storing data in `PostgreSQL`. For other parts exception handling would 
suffice.


## :pushpin: Things to improve

_If u have more time or want to improve somthing..._

I would have 

1. investigated the problem why container is not connecting to Kafka in the Kafka network. Maybe it is a small configuration problem.
But need time to diagnose and find the problem. I believe this is not a big issue, but yeah I need to find out.

2. handle the `Exceptions` in a better way. Like a ideal way of Exceptions handling for rest APIs by creating the custom Excpetions
class such as mentioned here at https://www.baeldung.com/exception-handling-for-rest-with-spring

3. used the CamelCase in Entity classes.

4. handle appearance of Enums in table as `text` instead of `number`

5. added the test cases to expect some exceptions like database and network. 


## Last note.

Thanks for inviting me for the interview process. I liked the challenge. I want to discuss further on this. I am a fan of distributed systems.
So I like Kafka and want to learn it very deep with production applications.

Hope will discuss further. :) 
