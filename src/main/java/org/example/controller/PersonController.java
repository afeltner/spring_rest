package org.example.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.example.dao.FakeDataStore;
import org.example.dao.Person;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@RestController
@SuppressWarnings("unused")
public class PersonController {

    @GetMapping("/people")
    @ResponseBody
    public List<Person> getPeople(HttpServletResponse response) {
        List<Person> people = FakeDataStore.getInstance().getPeople();
        if (Objects.nonNull(people) && !people.isEmpty()) {
            people.sort(Comparator.comparing(Person::name));
        } else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
        return people;
    }

    @GetMapping("/person/{name}")
    @ResponseBody
    public Person getPerson(@PathVariable String name, HttpServletResponse response) {
        Person person = FakeDataStore.getInstance().getPerson(name);
        if (Objects.isNull(person)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
        return person;
    }

    @PostMapping("/person")
    @ResponseBody
    public void addNewPerson(@RequestBody Person person, HttpServletResponse response) {
        FakeDataStore.getInstance().putPerson(new Person(person.name(), person.about(), person.birthYear()));
    }

    @DeleteMapping("/person/{name}")
    @ResponseBody
    public void deletePerson(@PathVariable String name, HttpServletResponse response) {
        FakeDataStore.getInstance().removePerson(name);
    }

}