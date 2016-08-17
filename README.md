# fun-with-spark
Java Spark Rest API example with Retrofit/Lombok and OkHttp as request cache

### Compile/Run via IntelliJ
#### Dependencies
- [Java 8 SDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [IntelliJ Community Edition](https://www.jetbrains.com/idea/download/)
 - Tested on version 2016.2.1
##### Maven Dependencies
(this should be handled via IntelliJ, automatically)
- spark-core - Lightweight REST framefork
- slf4j-simple - Spark dependency
- lombok - Automated boilerplate getter/setters for objects
- retrofit - Used to consume daw-api
- converter-gson - Used by retrofit for serializing objects

#### Usage
- Application's default port is 8000.
Can be changed by editing ```LISTEN_PORT``` in [Xdaw.java](https://github.com/keremgocen/fun-with-spark/blob/cache-dev/xdaw/src/main/java/Xdaw.java)

- DAW-API is assumed to be running on ```http://localhost:8080/api/```.
This behavior can be changed by modifying ```TARGET_URL_BASE``` constant in [ExtApi.java](https://github.com/keremgocen/fun-with-spark/blob/cache-dev/xdaw/src/main/java/ExtApi.java) file.

#### Response Caching
Response caching is achieved via OkHttp client and response headers from daw-api. Cache duration can be modified via "max-age=" parameter. 

>> "max-age=60" # evict responses after 60 seconds

See [Caching in HTTP](https://www.w3.org/Protocols/rfc2616/rfc2616-sec13.html) for more info.

### Complementary DAW-API
You can run it either locally(as assumed by default) or use the external version.

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
  {
    "id": 569294,
    "face": "◕‿↼",
    "size": 38,
    "price": 1078,
    "recent": [
      "Novella94",
      "Frida_Welch39"
    ]
  },
  {
    "id": 569294,
    "face": "◕‿↼",
    "size": 38,
    "price": 1078,
    "recent": [
      "Novella94",
      "Frida_Welch39"
    ]
  },
  {
    "id": 827648,
    "face": "ง ͠° ل͜ °)ง",
    "size": 12,
    "price": 281,
    "recent": [
      "Novella94"
    ]
  }
]
```