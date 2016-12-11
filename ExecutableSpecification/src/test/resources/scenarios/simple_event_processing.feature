Feature: Simple event processing

Background:
  Given a token for a new gamified application A1

Scenario: send the first event for a user of the gamified application
  Given a user U1 of the gamified application A1
  When the application A1 POSTs 1 payload for events associated to user U1 on the /events endpoint
  And the application A1 GETs user U1 from the /users/ endpoint
  Then it receives a 200 status code
  And the payload in the response has a property numberOfEvents with a value of 1

Scenario: send the first 2 events for a user of the gamified application
  Given a user U1 of the gamified application A1
  When the application A1 POSTs 2 payloads for events associated to user U1 on the /events endpoint
  And the application A1 GETs user U1 from the /users/ endpoint
  Then it receives a 200 status code
  And the payload in the response has a property numberOfEvents with a value of 2
