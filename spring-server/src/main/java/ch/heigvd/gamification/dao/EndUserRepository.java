package ch.heigvd.gamification.dao;

import ch.heigvd.gamification.model.EndUser;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Olivier Liechti (olivier.liechti@heig-vd.ch)
 */
public interface EndUserRepository extends CrudRepository<EndUser, Long>{

  public EndUser findByApplicationNameAndIdInGamifiedApplication(String targetApplicationName, String targetEndUserId);
  
}
