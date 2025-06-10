Feature: Book API Verification

  Scenario: Validate Books API response
    Given I send a GET request to the Books API
    Then the status code should be 200
    And the response time should be less than 2000 milliseconds
    And the response size should be less than 10 KB
    And the response should contain atleast one book with type "fiction"

