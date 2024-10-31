@skip
Feature: Application Inquiry Via Details
    As a user
    I want to be able to send my bio details
    So that I can verify if I have an existing CCS application

    Scenario Outline: Existing Application
        Given the Details API
        When I enter details "<lastname>", "<firstname>", "<middlename>", "<gender>", and "<birthdate>"
        Then I should get <status> as Details API result

        Examples:
        | lastname           | firstname        | middlename        | gender | birthdate   | status |
        | sample lastname    | sample firstname | sample middlename | male   | 2018-01-01  | 200    |
        | bad lastname       | bad firstname    | bad middlename    | male   | 2018-01-01  | 404    |
