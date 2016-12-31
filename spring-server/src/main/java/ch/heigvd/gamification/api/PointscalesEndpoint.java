/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.api;

import ch.heigvd.gamification.api.dto.PointScale;
import ch.heigvd.gamification.dao.ApplicationRepository;
import ch.heigvd.gamification.dao.RegisteredPointScaleRepository;
import ch.heigvd.gamification.model.Application;
import ch.heigvd.gamification.model.RegisteredPointScale;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Lucie
 */
@RestController
public class PointscalesEndpoint implements PointscalesApi {

   private final ApplicationRepository applicationRepository;
   private final RegisteredPointScaleRepository registeredPointScalesRepository;

   public PointscalesEndpoint(ApplicationRepository applicationsRepository, RegisteredPointScaleRepository registeredPointScaleRepository) {
      this.applicationRepository = applicationsRepository;
      this.registeredPointScalesRepository = registeredPointScaleRepository;
   }

   @Override
   public ResponseEntity createPointScale(@RequestHeader(value = "X-Gamification-Token") String xGamificationToken, @RequestBody PointScale pointScale) {
      String targetApplicationName = xGamificationToken;
      RegisteredPointScale regPointScale = new RegisteredPointScale();
      Application targetApplication = applicationRepository.findByName(targetApplicationName);
      if (targetApplication == null) {
         return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
      }
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
   public ResponseEntity<List<PointScale>> findAllPointScales(@RequestHeader(value = "X-Gamification-Token") String xGamificationToken) {
      List<PointScale> result = new ArrayList<>();
      String targetApplicationName = xGamificationToken;
      Application targetApplication = applicationRepository.findByName(targetApplicationName);
      if (targetApplication == null) {
         return null;
      }
      for (RegisteredPointScale regPointScale : registeredPointScalesRepository.findByApplication(targetApplication)) {
         PointScale ps = new PointScale();
         ps.setName(regPointScale.getName());
         ps.setDescription(regPointScale.getDescription());
         result.add(ps);
      }
      return ResponseEntity.ok(result);
   }

}
