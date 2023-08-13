## Consuming a REST API

### Main Lessons

`RestTemplate` (class): Calls external endpoints & returns response in a given `domain-type`
`Domain-Type` (data class): Defines the properties to be extracted from the recieved reponse. (e.g. Json or String)

Mocking:
```
val service = mockk<ActualService>()
every { service.getDataFromDb("Expected Param") } returns "Expected Output"
```


Tutorials: 

__RestTemplate__  
- https://stonesoupprogramming.com/2018/04/14/consuming-rest-with-spring-and-kotlin/
- https://spring.io/guides/gs/consuming-rest/

__Testing__
- https://www.baeldung.com/kotlin/mockk