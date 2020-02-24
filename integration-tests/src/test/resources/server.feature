Feature: Server
  Get Discord Server

  Scenario: No server if not authenticated
    Given User not authenticated
    When User asks for server 626670741764440075
    Then Unauthenticated Error

  Scenario: No server if user not server manager
    Given blackstardlb authenticated
    When User asks for server 12345
    Then Unauthenticated Error

  Scenario: Server if user authenticated and manager
    Given blackstardlb authenticated
    When User asks for server 626670741764440075
    Then Server with id 626670741764440075
