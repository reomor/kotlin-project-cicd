Feature: the version can be retrieved
  Scenario: client makes GET request to /hello
    When the client calls "hello" and status code is 200
    Then the client receives constant string