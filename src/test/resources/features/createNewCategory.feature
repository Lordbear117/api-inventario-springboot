Feature: Creation of new category

    Scenario: A new category needs to be created
    Given a new name like "Laptop" for a category
    When the user use it in the save endpoint
    Then a new category should be created