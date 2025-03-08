Feature: API Testing with Rest Assured

  Scenario: Verify GET request
    When Set a Method Type as "GET"
    Then Sent to "/books/" endpoint with "4" number
    And Call the API

  Scenario: User places an order successfully
    When the user has a valid order request body from "src/test/java/api/Payloads/test.json"
    When the user sends a "POST" request to "/orders"
    Then the response status code should be 201
    And the response should contain an "orderId"

  Scenario: User deletes an order successfully
    Given the user has an existing order
    When the user sends a "DELETE" request to "/orders/"
    Then the response status code should be 204

  Scenario: User gets the details of the order
    When the user sends a "GET" request to "/orders/"
    And the response status code should be 404
    Then the response should return an error with message "No order with id {orderId}"

@testJson
  Scenario: User places an order successfully
    When the user has a valid order request body
    When the user sends a "POST" request to "/orders"
    Then the response status code should be 201
    And the response should contain an "orderId"
