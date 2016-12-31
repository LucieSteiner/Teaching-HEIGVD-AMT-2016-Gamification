/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.gamification.spec.steps;

import ch.heigvd.gamification.ApiException;
import ch.heigvd.gamification.ApiResponse;
import ch.heigvd.gamification.api.DefaultApi;
import ch.heigvd.gamification.api.dto.Credentials;
import ch.heigvd.gamification.api.dto.PointScale;
import ch.heigvd.gamification.api.dto.Registration;
import ch.heigvd.gamification.api.dto.Token;
import ch.heigvd.gamification.api.dto.User;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import static org.junit.Assert.*;

/**
 *
 * @author Lucie
 */
public class PointScalesOperationsSteps {

   private final DefaultApi api = new DefaultApi();
   private PointScale payload;
   private static final Logger LOG = Logger.getLogger(EventProcessingSteps.class.getName());

   /*
    * Variables used to generate test data
    */
   final static String DUMMY_PASSWORD = "dummyPassword";
   int applicationsCounter = 1;
   int usersCounter = 1;

   /*
    * Variables used to share data between steps
    */
   private ApiResponse lastApiResponse = null;
   private int status;

   /*
    * Keep track of the applications created during the scenarios execution
    */
   private final Map<String, Registration> applications = new HashMap<>();

   /*
    * Keep track of the users created for each of the applications
    */
   private final Map<String, Map<String, User>> applicationsUsers = new HashMap<>();

   /*
    * Keep track of the token obtained for each of the applications
    */
   private final Map<String, Token> applicationsTokens = new HashMap<>();

   @Given("^a token for a gamified application (.*)$")
   public void a_token_for_a_gamified_application(String applicationReference) throws Throwable {
      String randomApplicationName = "app-name-" + (applicationsCounter++) + '-' + System.currentTimeMillis();
      Registration applicationRegistration = new Registration();
      applicationRegistration.setApplicationName(randomApplicationName);
      applicationRegistration.setPassword(DUMMY_PASSWORD);
      api.registrationsPost(applicationRegistration); // register the application

      Credentials credentials = new Credentials();
      credentials.setApplicationName(randomApplicationName);
      credentials.setPassword(DUMMY_PASSWORD);
      Token token = api.authenticateApplicationAndGetToken(credentials); // and immediately authenticate to get a token

      applications.put(applicationReference, applicationRegistration);
      applicationsUsers.put(applicationReference, new HashMap<>());
      applicationsTokens.put(applicationReference, token);
   }

   @When("^the application (.*) POSTs (\\d+) payload(?:s?) for pointscales on the /pointscales endpoint$")
   public void the_application_A_POSTs_payload_for_pointscales_on_the_pointscales_endpoint(String applicationReference, int numberOfPointScales) throws Throwable {
      for (int i = 0; i < numberOfPointScales; i++) {
         PointScale pointScale = new PointScale();
         pointScale.setName("Scale" + i + "_" + System.currentTimeMillis());
         pointScale.setDescription("Lorem Ipsum");
         try {
            lastApiResponse = api.createPointScaleWithHttpInfo(applicationsTokens.get(applicationReference).getApplicationName(), pointScale);
         } catch (ApiException e) {
            e.printStackTrace();
         }
      }
   }

   @Given("^a pointscale payload$")
   public void a_pointscale_payload() throws Throwable {
      payload = new PointScale();
      payload.setName("Scale_" + System.currentTimeMillis());
      payload.setDescription("Lorem Ipsum");

   }

   @When("^the application (.*) POSTs it on the /pointscales endpoint$")
   public void the_application_A_POSTs_it_on_the_pointscales_endpoint(String applicationReference) throws Throwable {
      String token = applicationsTokens.get(applicationReference).getApplicationName();
      try {
         lastApiResponse = api.createPointScaleWithHttpInfo(token, payload);
      } catch (ApiException e) {
         lastApiResponse = null;
         status = e.getCode();
      }
   }

   @When("^the application (.*) GETs all pointscales on the /pointscales endpoint$")
   public void the_application_A_GETs_all_pointscales_on_the_pointscales_endpoint(String applicationReference) throws Throwable {
      String token = applicationsTokens.get(applicationReference).getApplicationName();
      try {
         lastApiResponse = api.findAllPointScalesWithHttpInfo(token);
      } catch (ApiException e) {
         e.printStackTrace();
         lastApiResponse = null;
      }

   }

   @Then("^the payload in the response contains (\\d+) elements$")
   public void the_payload_in_the_response_contains_elements(int expectedNumberOfElements) throws Throwable {
      ArrayList elements = (ArrayList) lastApiResponse.getData();
      assertEquals(expectedNumberOfElements, elements.size());
   }

   @Then("^the application receives a (\\d+) status code$")
   public void the_application_receives_a_status_code(int expectedStatusCode) throws Throwable {
      int currentStatus;
      if (lastApiResponse == null) {
         currentStatus = status;

      } else {
         currentStatus = lastApiResponse.getStatusCode();
      }
      assertEquals(expectedStatusCode, currentStatus);
   }

}
