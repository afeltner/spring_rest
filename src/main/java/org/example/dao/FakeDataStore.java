package org.example.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FakeDataStore is a class to simulate a database without actually connecting to one.  It will instantiate several
 * entries which can then be modified.  This lives in memory for demonstration purposes.
 */
public class FakeDataStore {

    private static final FakeDataStore instance = new FakeDataStore();
    private final Map<String, Person> personMap = new HashMap<>();

    /**
     * FakeDataStore is a private constructor which will create in memory Person objects simulating entries in a
     * database.
     */
    private FakeDataStore() {
        //dummy data
        personMap.put("andrea", new Person("Andrea", "Andrea has 2 cats.", 1964));
        personMap.put("zorro", new Person("Zorro", "Zorro is a cat.", 2024));
        personMap.put("aaron", new Person("Aaron", "Aaron hates cats.", 1970));
    }

    /**
     * getInstance will allow callers to access in memory Person objects.
     *
     * @return FakeDataStore instance for the running service.
     */
    public static FakeDataStore getInstance() {
        return instance;
    }

    /**
     * getPerson will return one Person object if it exists in the personMap based on the key which is the normalized
     * name.
     *
     * @param name The name (and key) to the personMap you wish to obtain.
     * @return Person The Person object to be returned.
     */
    public Person getPerson(String name) {
        return personMap.get(name.toLowerCase());
    }

    /**
     * getPeople will return all Person entries in the in memory personMap.
     *
     * @return ArrayList<Person> The list of all people in this fake database class.
     */
    public List<Person> getPeople() {
        List<Person> people = new ArrayList<>();
        for (Map.Entry<String, Person> person : personMap.entrySet()) {
            people.add(person.getValue());
        }
        return people;
    }

    /**
     * putPerson will add a Person to the in memory personMap.
     *
     * @param person The Person object to be added.
     */
    public void putPerson(Person person) {
        personMap.put(person.name().toLowerCase(), person);
    }

    /**
     * removePerson will delete a Person to the in memory personMap.
     *
     * @param name The name of the Person to be deleted.
     */
    public void removePerson(String name) {
        personMap.remove(name.toLowerCase());
    }
}