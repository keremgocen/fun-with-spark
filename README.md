# fun-with-spark
Java Spark Rest API example with Retrofit/Lombok and OkHttp as request cache

### Dependencies

- [Java 8 SDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

### Compile/Run using Maven on OS X

#### Install Maven via brew

> brew install maven

#### Compile and generate executable JAR with dependencies

> mvn package

> java -jar target/app-1.0-SNAPSHOT-jar-with-dependencies.jar

#### Usage

```username``` Generated users will be listed in console when you run the app, you can just use any of the listed names

> curl localhost:8000/api/recent-purchases/:username

### Compile/Run using Docker

#### Pull

> docker pull keremgocen/fun-with-spark


#### Run

> docker run -d -p 4567:8000 keremgocen/fws


#### Usage

> curl localhost:4567/api/recent_purchases/:username


#### Build
(only if you changed something and want to get a new image)

> docker build -t <tag> .


### Compile/Run via IntelliJ
- [IntelliJ Community Edition](https://www.jetbrains.com/idea/download/)
 - Tested on version 2016.2.12
- Go to "File->Open" and choose "app" folder in this repository to open the IntelliJ project
- Build & Run as a Maven project

 
#### Maven Dependencies

- [spark](http://sparkjava.com/)-core - Lightweight REST framefork
- slf4j-simple - Spark dependency
- lombok - Automated boilerplate getter/setters for objects
- retrofit - Used to consume daw-api
- converter-gson - Used by retrofit for serializing objects


### config.properties file

Settings below can be mofidied in ```config.properties``` file.

#### Target url for daw-api

>external.base.url = http://74.50.59.155:6000/api/

#### Port setting for this app

>internal.listen.port = 8000

#### Response cache eviction time (seconds)

>cachecontrol.maxage = 120

### Response Caching

Response caching is achieved through Retrofit/OkHttp client and by intercepting incoming responses from external daw-api. 

See [Caching in HTTP](https://www.w3.org/Protocols/rfc2616/rfc2616-sec13.html) for more info.

### API Reference

#### GET /api/recent_purchases/:username

- params:
  - username (string)

- response (json): max number of recents first
```
[
  {
    "id": 602508,
    "face": "t(-.-t)",
    "size": 36,
    "price": 3,
    "recent": [
      "Misael_Hilpert",
      "Kathleen.Upton22",
      "Novella94",
      "Frida_Welch39"
    ]
  },
  .
  .
]
```

