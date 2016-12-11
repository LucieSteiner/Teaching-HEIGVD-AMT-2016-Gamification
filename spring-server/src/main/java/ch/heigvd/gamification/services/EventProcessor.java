package ch.heigvd.gamification.services;

import ch.heigvd.gamification.api.dto.Event;
import ch.heigvd.gamification.dao.EndUserRepository;
import ch.heigvd.gamification.model.Application;
import ch.heigvd.gamification.model.EndUser;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Olivier Liechti
 */
@Service
public class EventProcessor {

  private final EndUserRepository endUsersRepository;

  public EventProcessor(EndUserRepository endUsersRepository) {
    this.endUsersRepository = endUsersRepository;
  }

  @Async
  @Transactional
  public void processEvent(Application application, Event event) {
    EndUser targetEndUser = endUsersRepository.findByApplicationNameAndIdInGamifiedApplication(application.getName(), event.getUserId());
    if (targetEndUser == null) {
      targetEndUser = new EndUser();
      targetEndUser.setApplication(application);
      targetEndUser.setIdInGamifiedApplication(event.getUserId());
      targetEndUser.setNumberOfEvents(1);
      endUsersRepository.save(targetEndUser);
    } else {
      targetEndUser.setNumberOfEvents(targetEndUser.getNumberOfEvents()+1);
    }

  }

}
