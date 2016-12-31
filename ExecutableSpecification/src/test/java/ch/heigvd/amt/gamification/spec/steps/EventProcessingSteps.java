package ch.heigvd.amt.gamification.spec.steps;

import ch.heigvd.gamification.ApiException;
import ch.heigvd.gamification.api.DefaultApi;
import ch.heigvd.gamification.api.dto.Credentials;
import ch.heigvd.gamification.api.dto.Registration;
import ch.heigvd.gamification.api.dto.User;
import ch.heigvd.gamification.api.dto.Event;
import ch.heigvd.gamification.api.dto.Token;
import ch.heigvd.gamification.ApiResponse;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import static org.junit.Assert.*;

/**
 *
 * @author Olivier Liechti
 */
public class EventProcessingSteps {

  private static final Logger LOG = Logger.getLogger(EventProcessingSteps.class.getName());

  /*
   * The api stub generated by swagger codegen
   */
  private final DefaultApi api = new DefaultApi();

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

  /*
  * Keep track of the applications created during the scenarios execution
   */
   private final  Map<String, Registration> applications = new HashMap<>();

  /*
  * Keep track of the users created for each of the applications
   */
   private final  Map<String, Map<String, User>> applicationsUsers = new HashMap<>();

  /*
  * Keep track of the token obtained for each of the applications
   */
  private final Map<String, Token> applicationsTokens = new HashMap<>();

  @Given("^a token for a new gamified application (.*)$")
  public void a_token_for_a_new_gamified_application(String applicationReference) throws Throwable {
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

  @Given("^a user (.*) of the gamified application (.*)$")
  public void a_user_U_of_the_gamified_application_A(String userReference, String applicationReference) throws Throwable {
    User user = new User();
    String randomUserId = "user-" + (usersCounter++) + "-" + System.currentTimeMillis();
    user.setUserId(userReference);
    applicationsUsers.get(applicationReference).put(userReference, user);
  }

  @When("^the application (.*) POSTs (\\d+) payload(?:s?) for events associated to user (.*) on the /events endpoint$")
  public void the_application_A_POSTs_payload_s_for_events_associated_to_user_U_on_the_events_endpoint(String applicationReference, int numberOfEvents, String userReference) throws Throwable {
    for (int i = 0; i < numberOfEvents; i++) {
      Event event = new Event();
      event.setTimestamp(DateTime.now());
      event.setType("USER_ACTION");
      event.setUserId(applicationsUsers.get(applicationReference).get(userReference).getUserId());
      try {
        api.reportEvent(applicationsTokens.get(applicationReference).getApplicationName(), event);      
      } catch (ApiException e) {
        e.printStackTrace();
      }
    }
  }

  @When("^the application (.*) GETs user (.*) from the /users/ endpoint$")
  public void the_application_A_GETs_user_U_from_the_users_endpoint(String applicationReference, String userReference) throws Throwable {
    try {
      lastApiResponse = api.findUserByIdWithHttpInfo(applicationsTokens.get(applicationReference).getApplicationName(), applicationsUsers.get(applicationReference).get(userReference).getUserId());
    } catch (ApiException e) {
      lastApiResponse = null;
    }
  }

  @Then("^it receives a (\\d+) status code$")
  public void it_receives_a_status_code(int expectedStatusCode) throws Throwable {
    assertEquals(expectedStatusCode, lastApiResponse.getStatusCode());
  }

  @Then("^the payload in the response has a property numberOfEvents with a value of (\\d+)$")
  public void the_payload_in_the_response_has_a_property_numberOfEvents_with_a_value_of(int expectedNumberOfEvents) throws Throwable {
    User retrievedUserState = (User) lastApiResponse.getData();
    assertEquals(expectedNumberOfEvents, retrievedUserState.getNumberOfEvents().intValue());
  }

}
