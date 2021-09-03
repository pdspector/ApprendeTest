Feature: Blockchain Block API Endpoint
  
  Scenario: Verify Blockchain Hash is correct from /10 to /20 endpoints of Blockchain Api Website
    Given user is at https://api.blockcypher.com/v1/btc/main/blocks
    When user goes to the Website adding /"<Endpoint>" to the URL
    Then prev_block is equal to previous Hash
    
    Examples:
    | Endpoint |
    | 10 |
    | 11 |
    | 12 |
    | 13 |
    | 14 |
    | 15 |
    | 16 |
    | 17 |
    | 18 |
    | 19 |
    | 20 |