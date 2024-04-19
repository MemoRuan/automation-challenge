Feature: Basic info displayed

  Scenario:Verify the table with basic info is displayed
    Given the user opens the app
    When the application finish loading
    And the user enters "MemoRuan" in the input text field
    And the user clicks Go button
    Then the repo list is displayed in the results section
    And each row contains a Name column
    And each row contains a Description colum
    And the Description column with no value shows a - sign