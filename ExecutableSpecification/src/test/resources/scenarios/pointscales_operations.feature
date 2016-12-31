Feature: Pointscales Operations

Background:
  Given a token for a gamified application A1

Scenario: Create a pointscale
  When the application A1 POSTs 1 payload for pointscales on the /pointscales endpoint
  Then the application receives a 201 status code

Scenario: Check that it is not possible for an application to create two pointscales with the same name
  Given a pointscale payload
  When the application A1 POSTs it on the /pointscales endpoint
  And the application A1 POSTs it on the /pointscales endpoint
  Then the application receives a 422 status code
  
Scenario: Check that pointscales are linked to the right application
  Given a token for a gamified application A2
  When the application A1 POSTs 3 payloads for pointscales on the /pointscales endpoint
  And the application A2 POSTs 2 payloads for pointscales on the /pointscales endpoint
  And the application A1 GETs all pointscales on the /pointscales endpoint
  Then the application receives a 200 status code
  And the payload in the response contains 3 elements
  When the application A2 GETs all pointscales on the /pointscales endpoint
  Then the application receives a 200 status code
  And the payload in the response contains 2 elements