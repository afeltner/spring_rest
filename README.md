This is a sample gradle java project using Spring to handle incoming REST API calls.

Caution: Currently there is no validation on the input of the incoming request.  
This is just a sample of how to start a service with a Controller to handle the requests.

# Usage

```
Valid endpoints are:
 GET -  localhost:8088/people
 GET -  localhost:8088/person/{name}
 POST - localhost:8088/person
 ```

# Building

To build (outside of an IDE) execute the following command:

```
./gradlew build
```

To build (outside of an IDE) skipping all unit tests execute:

```
./gradlew build -x test
```