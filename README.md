ibm-mq-spring-listener
----

Refer to [ibm-messaging/mq-jms-spring](https://github.com/ibm-messaging/mq-jms-spring)

# How to run

Start an IBM MQ instance with Docker on your laptop

```
$ docker run --env LICENSE=accept --env MQ_QMGR_NAME=QM1 \
             --publish 1414:1414 \
             --publish 9443:9443 \
             --detach \
             ibmcom/mq
```

The default attributes are

```
ibm.mq.queueManager=QM1
ibm.mq.channel=DEV.ADMIN.SVRCONN
ibm.mq.connName=localhost(1414)
ibm.mq.user=admin
ibm.mq.password=passw0rd
```

Build project with Gradle

```
$ ./gradlew build
```

Run the jar file directly

```
$ java -jar build/libs/spring-ibm-mq-listner.jar


    .   ____          _            __ _ _
   /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
  ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
   \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
    '  |____| .__|_| |_|_| |_\__, | / / / /
   =========|_|==============|___/=/_/_/_/
   :: Spring Boot ::        (v2.3.3.RELEASE)
  
  2020-08-21 09:58:36.250  INFO 18976 --- [           main] com.sourabhs.ibm.mq.spring.Application   : Starting Application on W1838853 with PID 18976 (C:\codeSpace\MQ\ibm-mq-spring-listener\build\classes\java\main started by SS040588 in C:\codeSpace\MQ\ibm-mq-spring-listener)
  2020-08-21 09:58:36.259  INFO 18976 --- [           main] com.sourabhs.ibm.mq.spring.Application   : No active profile set, falling back to default profiles: default
  2020-08-21 09:58:39.921  INFO 18976 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 1515 (http)
  2020-08-21 09:58:39.946  INFO 18976 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
  2020-08-21 09:58:39.947  INFO 18976 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.37]
  2020-08-21 09:58:40.256  INFO 18976 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
  2020-08-21 09:58:40.256  INFO 18976 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 3851 ms
  2020-08-21 09:58:41.543  INFO 18976 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
  2020-08-21 09:58:42.045  INFO 18976 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 1515 (http) with context path ''
  2020-08-21 09:58:42.876  INFO 18976 --- [           main] com.sourabhs.ibm.mq.spring.Application   : Started Application in 7.827 seconds (JVM running for 9.046)
  DEV.QUEUE.1 received ~Hello IBM MQ Started~
```

As you can see,

- The `main` method, sends message `Hello IBM MQ Started` to Queue `DEV.QUEUE.1`
- Class `Receiver` and `ReceiverSecond` consume messages
  - `Receiver` monitors Queue `DEV.QUEUE.1`
  - `ReceiverSecond` monitors Queue `DEV.QUEUE.2`

Do you test with IBM MQ web console https://localhost:9443/ibmmq/console

:smile:


