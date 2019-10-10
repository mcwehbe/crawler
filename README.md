# Crawler
The crawler is limited to one domain
Starting with https://test.com/, it would crawl all pages within test.com, but not follow external links,
for example to the Facebook and Twitter accounts

### Breadth-first search (BFS)
The Crawler uses the (BFS) algorithm for traversing or searching tree or graph data structures.
It starts at the tree root, and explores all of the neighbor nodes at the present depth prior to moving on to the nodes
at the next depth level. 

### Requirements
    java 9
    gradle
    
### Install  

First you need to build the project by executing   
```bash
gradle build -x test
```
Execute this command to use the internal shell
```bash
java -jar build/libs/crawler-0.0.1-SNAPSHOT.jar
```

### shell
Given that Spring Shell will kick in and start the REPL by virtue of this dependency being present,
we need to **ignore the sample integration test** with @ignore annotation.
If we don’t do so, the integration test will create the Spring ApplicationContext and, depending on the build tool,
will stay stuck in the eval loop or crash with a NPE.

### shell command
you can use tab to see all available commands and options

**help** gives you an overview of all the commands
```bash
help
```
**crawl** by default --max-generation-number-to-visit is set to 2 and --url to http://test.com
the command will start crawling and gives you all the links with their children
```bash
crawl --max-generation-number-to-visit 2 --url http://test.com
```
* [Official spring shell documentation](https://projects.spring.io/spring-shell/)

### Concurrency  

To make the algorithm faster we are using a custom Executor that is defined by the ThreadPool we used the formula from
Brian Goetz in his book Java Concurrency in Practice to determine the number of threads:
Nthreads = NCPU * UCPU * (1 + W/C)

All the synchronous invocations to the different URLs are made with CompletableFutures
* [For more Information](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html)


### Cashing

To Optimize the speed if we execute the crawl command we are caching for a period of 600s 
We are using Caffeine caching that is a high performance, near optimal caching library based on Java 8

* [For more information](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-caching.html#boot-features-caching-provider-caffeine)

### Running all the tests from command line
```bash
gradle test
```
### Building from command line
```bash
gradle build
```
* [Official Gradle documentation](https://docs.gradle.org)

## JSOUP
For finding urls inside a web page we are using jsoup
* [Official JSOUP documentation](https://jsoup.org/)

### timeout
In order to optimize the performance a timeout has been added of **1 second**

## Wiremock for integration tests
Uses 2 folders in the resources __files and mappings

- **__files** folder contains body of the HTTP response
- **mappings** folder contains HTTP request

* [Official wiremock documentation](http://wiremock.org/)





