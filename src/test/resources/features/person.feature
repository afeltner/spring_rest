Feature: Person
  CRUD operations for a Person

  #Scenario will hit the GET /people endpoint to get a list of all Person objects
  Scenario: List all people
    When GET People
    Then I should get a response with status 200
    And I should get the following people
      | name   | about              | birthYear |
      | Aaron  | Aaron hates cats.  | 1970      |
      | Andrea | Andrea has 2 cats. | 1964      |
      | Zorro  | Zorro is a cat.    | 2024      |

  # Scenario will hit the GET /person/{name} endpoint to get one specific Person object
  Scenario: Get a person
    When GET a person Andrea
    Then I should get a response with status 200
    And I should get the following person
      | name   | about              | birthYear |
      | Andrea | Andrea has 2 cats. | 1964      |

  # Scenario will hit the POST /person endpoint to add a new Person
  # It will then hit the GET /person endpoint to verify that the Person was successfully added
  # It will then hit the DELETE /person endpoint to delete the Person
  # It will then hit the GET /people endpoint to verify that the list does not contain the deleted Person
  Scenario: Add a person and then delete the same person
    When POST a person
      | name  | about                | birthYear |
      | Elene | Elene likes puzzles. | 1936      |
    Then I should get a response with status 200
    When GET a person Elene
    Then I should get a response with status 200
    And I should get the following person
      | name  | about                | birthYear |
      | Elene | Elene likes puzzles. | 1936      |
    When DELETE a person Elene
    Then I should get a response with status 200
    When GET People
    Then I should get a response with status 200
    And I should get the following people
      | name   | about              | birthYear |
      | Aaron  | Aaron hates cats.  | 1970      |
      | Andrea | Andrea has 2 cats. | 1964      |
      | Zorro  | Zorro is a cat.    | 2024      |