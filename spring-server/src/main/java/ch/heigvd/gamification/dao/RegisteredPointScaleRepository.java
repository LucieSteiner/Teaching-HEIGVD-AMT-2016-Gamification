package ch.heigvd.gamification.dao;

import ch.heigvd.gamification.model.Application;
import ch.heigvd.gamification.model.RegisteredPointScale;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Lucie Steiner
 */
public interface RegisteredPointScaleRepository extends CrudRepository<RegisteredPointScale, Long>{
   
   public List<RegisteredPointScale> findByApplication(Application application);
}
