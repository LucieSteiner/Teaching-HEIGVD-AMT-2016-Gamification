package ch.heigvd.gamification.api.helpers;

import ch.heigvd.gamification.dao.ApplicationRepository;
import ch.heigvd.gamification.model.Application;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * @author Lucie Steiner
 */
public class ApplicationFilterInterceptor extends HandlerInterceptorAdapter{
   
   ApplicationRepository applicationRepository;
   public ApplicationFilterInterceptor(ApplicationRepository applicationsRepository) {
      this.applicationRepository = applicationsRepository;
   }
   @Override
   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
      HandlerMethod handlerMethod = (HandlerMethod)handler;
      ApplicationFilter applicationFilter = handlerMethod.getMethodAnnotation(ApplicationFilter.class);
      String targetApplicationName = request.getHeader("X-Gamification-Token");
      Application targetApplication = applicationRepository.findByName(targetApplicationName);
      
      if (targetApplication == null) {
         return false;
      }
      request.setAttribute("targetApplication", targetApplication);
      return true;
   }
}
