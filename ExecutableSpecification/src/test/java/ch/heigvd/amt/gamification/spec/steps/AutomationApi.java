package ch.heigvd.amt.gamification.spec.steps;

import cucumber.api.java.en.Given;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import ch.heigvd.gamification.ApiException;
import ch.heigvd.gamification.api.DefaultApi;
import ch.heigvd.gamification.api.dto.Credentials;
import ch.heigvd.gamification.api.dto.Registration;
import ch.heigvd.gamification.api.dto.User;
import ch.heigvd.gamification.api.dto.Event;
import ch.heigvd.gamification.api.dto.Token;
import ch.heigvd.gamification.ApiResponse;

/**
 *
 * @author Lucie
 */
public interface AutomationApi {
   final DefaultApi defaultApi = new DefaultApi();
   //private PointScale payload;

   /*
    * Variables used to generate test data
    */
   final static String DUMMY_PASSWORD = "dummyPassword";
   int applicationsCounter = 1;
   int usersCounter = 1;

   /*
    * Variables used to share data between steps
    */
   ApiResponse lastApiResponse = null;
   int status = 0;

   /*
    * Keep track of the applications created during the scenarios execution
    */
   final Map<String, Registration> applications = new HashMap<>();

   /*
    * Keep track of the users created for each of the applications
    */
   final Map<String, Map<String, User>> applicationsUsers = new HashMap<>();

   /*
    * Keep track of the token obtained for each of the applications
    */
   final Map<String, Token> applicationsTokens = new HashMap<>();

   
}
