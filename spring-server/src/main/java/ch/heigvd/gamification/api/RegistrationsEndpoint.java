package ch.heigvd.gamification.api;

import ch.heigvd.gamification.api.dto.Registration;
import ch.heigvd.gamification.api.dto.RegistrationSummary;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Olivier Liechti
 */
@RestController 
public class RegistrationsEndpoint implements RegistrationsApi {

  @Override
  public ResponseEntity<List<RegistrationSummary>> registrationsGet() {
    List<RegistrationSummary> result = new ArrayList<>();
    result.add(new RegistrationSummary().applicationName("StackOverflow"));
    result.add(new RegistrationSummary().applicationName("GAPS"));
    result.add(new RegistrationSummary().applicationName("TOto"));
    result.add(new RegistrationSummary().applicationName("Facebook"));
    result.add(new RegistrationSummary().applicationName("Twitter"));
    result.add(new RegistrationSummary().applicationName("MyApplication"));
    return ResponseEntity.ok().body(result);
  }

  @Override
  public ResponseEntity<Void> registrationsPost(@RequestBody Registration arg0) {
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }




}
