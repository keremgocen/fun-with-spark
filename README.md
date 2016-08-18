# fun-with-spark
Java Spark Rest API example with Retrofit/Lombok and OkHttp as request cache

### Compile/Run via IntelliJ
#### Dependencies

- [Java 8 SDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [IntelliJ Community Edition](https://www.jetbrains.com/idea/download/)
 - Tested on version 2016.2.1
 
##### Maven Dependencies

(this should be handled via IntelliJ, automatically)
- [spark](http://sparkjava.com/)-core - Lightweight REST framefork
- slf4j-simple - Spark dependency
- lombok - Automated boilerplate getter/setters for objects
- retrofit - Used to consume daw-api
- converter-gson - Used by retrofit for serializing objects

#### config.properties file

Settings below can be mofidied in ```config.properties``` file.

##### Target url for daw-api
>external.base.url = http://74.50.59.155:6000/api/

##### Port setting for this app
>internal.listen.port = 8000

##### Response cache eviction time (seconds)
>cachecontrol.maxage = 120

#### Response Caching
Response caching is achieved through Retrofit/OkHttp client and by intercepting incoming responses from external daw-api. 

See [Caching in HTTP](https://www.w3.org/Protocols/rfc2616/rfc2616-sec13.html) for more info.

### Complementary DAW-API
You can run it either locally or use the external version(default).

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
### Usage (yes, I like low quality ffmpeg gifs)
![woho](/demo/xdaw.gif)
