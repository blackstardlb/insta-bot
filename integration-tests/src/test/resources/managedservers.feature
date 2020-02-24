Feature: Managed Servers
  Get Managed Servers for specified user

  Scenario: No servers if not authenticated
    Given User not authenticated
    When User asks for managed servers
    Then Unauthenticated Error
  Scenario: 3 servers if blackstardlb authenticated
    Given blackstardlb authenticated
    When User asks for managed servers
    Then 3 servers
