Feature: Search using Go

  Scenario:Search by using Go button
    Given the user opens the app
    When the application finish loading
    And the user enters "MemoRuan" in the input text field
    And the user clicks Go button
    Then the repo list is displayed in the results section