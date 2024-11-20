package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.dao.Person;
import org.junit.Assert;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class StepDefinitions {

    private static final String BASE_URL = "http://localhost:8088";
    private static final String PEOPLE_URL = "/people";
    private static final String PERSON_URL = "/person";

    HttpClient client = HttpClient.newHttpClient();
    HttpResponse<String> response;

    /**
     * GetPeople will hit the GET /people endpoint to return a list of all Person's in the FakeDataStore.
     */
    @When("^GET People$")
    public void GetPeople() {
        HttpRequest request = HttpRequest.newBuilder(
                        URI.create(BASE_URL + PEOPLE_URL))
                .header("accept", "application/json")
                .GET()
                .build();
        try {
            response = client.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * GetPerson will hit the GET /person/{name} endpoint to return an individual Person from the FakeDataStore.
     */
    @When("^GET a person (.*)$")
    public void GetPerson(String name) {
        HttpRequest request = HttpRequest.newBuilder(
                        URI.create(BASE_URL + PERSON_URL + "/" + name))
                .header("accept", "application/json")
                .GET()
                .build();
        try {
            response = client.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * GetPerson will hit the POST /person endpoint to add a Person to the FakeDataStore.
     */
    @When("^POST a person")
    public void GetPerson(Person person) {
        ObjectMapper Obj = new ObjectMapper();
        try {
            String jsonStr = Obj.writeValueAsString(person);
            HttpRequest request = HttpRequest.newBuilder(
                            URI.create(BASE_URL + PERSON_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonStr))
                    .build();
            response = client.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * DeletePerson will hit the DELETE /person/{name} endpoint to remove an individual Person from the FakeDataStore.
     */
    @When("^DELETE a person (.*)$")
    public void DeletePerson(String name) {
        HttpRequest request = HttpRequest.newBuilder(
                        URI.create(BASE_URL + PERSON_URL + "/" + name))
                .header("accept", "application/json")
                .DELETE()
                .build();
        try {
            response = client.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * checkResponseStatus will verify that the last response return the expected status code.
     *
     * @param status The expected status code.
     */
    @Then("^I should get a response with status (\\d+)$")
    public void checkResponseStatus(int status) {
        assertEquals(status, response.statusCode());
    }

    /**
     * verifyListOfPeople will verify that the expected List of People equals the actual List returned in the response.
     *
     * @param expectedPeople The expected List of People objects from the person feature file.
     */
    @And("I should get the following people")
    public void verifyListOfPeople(List<Person> expectedPeople) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Person> actualPeople = mapper.readValue(response.body(),
                    new TypeReference<>() {
                    });
            Assert.assertEquals(expectedPeople, actualPeople);
        } catch (IOException e) {
            Assert.fail("verifyListOfPeople threw an exception while parsing: " + e.getMessage());
        }
    }

    /**
     * verifyPerson will verify that the expected Person object is the same as what was returned in the response.
     *
     * @param expectedPerson The expected Person object from the person feature file.
     */
    @And("I should get the following person")
    public void verifyPerson(Person expectedPerson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Person actualPerson = mapper.readValue(response.body(),
                    new TypeReference<>() {
                    });
            Assert.assertEquals(expectedPerson, actualPerson);
        } catch (IOException e) {
            Assert.fail("verifyPerson threw an exception while parsing: " + e.getMessage());
        }
    }

    /**
     * personEntryTransformer will change a DataTable from a feature file containing a Person's information will
     * be transformed into a Person object.
     *
     * @param row One row from the feature file representing a Person.
     * @return Person the newly created Person object.
     */
    @SuppressWarnings("unused")
    @DataTableType
    public Person personEntryTransformer(Map<String, String> row) {
        return new Person(
                row.get("name"),
                row.get("about"),
                Integer.parseInt(row.get("birthYear"))
        );
    }

}