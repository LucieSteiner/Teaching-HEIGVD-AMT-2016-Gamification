package ch.heigvd.gamification.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Lucie Steiner
 */
@Entity
public class RegisteredPointScale implements Serializable{
   
   @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  
  @ManyToOne
  private Application application;
   
  @Column(unique = true)
   private String name;
   
   private String description;
   
   public long getId(){
      return id;
   }
   public void setId(long id){
      this.id = id;
   }
   public Application getApplication(){
      return application;
   }
   public void setApplication(Application application){
      this.application = application;
   }
   public String getName(){
      return name;
   }
   public void setName(String name){
      this.name = name;
   }
   public String getDescription(){
      return description;
   }
   
   public void setDescription(String description){
      this.description = description;
   }
}
