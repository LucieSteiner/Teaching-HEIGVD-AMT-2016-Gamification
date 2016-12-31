/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.gamification.dao;

import ch.heigvd.gamification.model.Application;
import ch.heigvd.gamification.model.RegisteredPointScale;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Lucie
 */
public interface RegisteredPointScaleRepository extends CrudRepository<RegisteredPointScale, Long>{
   
   public List<RegisteredPointScale> findByApplication(Application application);
}
