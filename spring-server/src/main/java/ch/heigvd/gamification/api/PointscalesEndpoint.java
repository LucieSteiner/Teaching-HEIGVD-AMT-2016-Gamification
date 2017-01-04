package ch.heigvd.gamification.api;

import ch.heigvd.gamification.api.dto.PointScale;
import ch.heigvd.gamification.api.helpers.ApplicationFilter;
import ch.heigvd.gamification.dao.ApplicationRepository;
import ch.heigvd.gamification.dao.RegisteredPointScaleRepository;
import ch.heigvd.gamification.model.Application;
import ch.heigvd.gamification.model.RegisteredPointScale;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Lucie Steiner
 */
@RestController
public class PointscalesEndpoint implements PointscalesApi {
   @Autowired 
   private HttpServletRequest request;
   private final RegisteredPointScaleRepository registeredPointScalesRepository;

   public PointscalesEndpoint(RegisteredPointScaleRepository registeredPointScaleRepository) {
      this.registeredPointScalesRepository = registeredPointScaleRepository;
   }

   @Override
   @ApplicationFilter
   public ResponseEntity createPointScale(@RequestHeader(value = "X-Gamification-Token") String xGamificationToken, @RequestBody PointScale pointScale) {
      
      RegisteredPointScale regPointScale = new RegisteredPointScale();
      Application targetApplication = (Application)request.getAttribute("targetApplication");
      regPointScale.setApplication(targetApplication);
      regPointScale.setName(pointScale.getName());
      regPointScale.setDescription(pointScale.getDescription());

      try {
         registeredPointScalesRepository.save(regPointScale);
         return ResponseEntity.status(HttpStatus.CREATED).build();
      } catch (DataIntegrityViolationException e) {
         System.out.println(e.getMessage());
         System.out.println(e.getClass());
         return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
      }
   }

   @Override
   @ApplicationFilter
   public ResponseEntity<List<PointScale>> findAllPointScales(@RequestHeader(value = "X-Gamification-Token") String xGamificationToken) {
      List<PointScale> result = new ArrayList<>();
      Application targetApplication = (Application)request.getAttribute("targetApplication");
      for (RegisteredPointScale regPointScale : registeredPointScalesRepository.findByApplication(targetApplication)) {
         PointScale ps = new PointScale();
         ps.setName(regPointScale.getName());
         ps.setDescription(regPointScale.getDescription());
         result.add(ps);
      }
      return ResponseEntity.ok(result);
   }

}
