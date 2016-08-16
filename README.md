# fun-with-redis
Java Spark Rest API example with Retrofit/Lombok and Redis as request cache

##### Example usage

(compile/run the intellij idea project)

```
curl http://localhost:8080/api/recent_purchases/Misael_Hilpert
```

sample result:
```
[{
    "id": 602508,
    "face": "t(-.-t)",
    "size": 36,
    "price": 3,
    "recent": ["Misael_Hilpert", "Kathleen.Upton22", "Novella94", "Frida_Welch39"]
}, {
    "id": 89709,
    "face": "(ノ・∀・)ノ",
    "size": 34,
    "price": 1150,
    "recent": ["Misael_Hilpert", "Amir.Runolfsdottir", "Kathleen.Upton22", "Damian_Larkin17"]
}, {
    "id": 315940,
    "face": "(¬‿¬)",
    "size": 29,
    "price": 988,
    "recent": ["Misael_Hilpert", "Novella94", "Damian_Larkin17"]
}]
```