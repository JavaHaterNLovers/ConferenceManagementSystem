package domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="payments")
public class Payment {
    @Id
    @GeneratedValue
    @Column
    private Integer id;
   
    @ManyToOne
    private User listener;
   
    @Column
    private Edition editionName;
   
     @Temporal(TemporalType.TIMESTAMP)
     @Column
     @CreationTimestamp
     private Calendar created;
     
     @Value("4111111111111111")
     @Size(min = 16, max=16)
     private int cardNumber;
   
     @Column
     private static final Integer suma = 30;
     
     
     public Payment(User listener,Edition editionName){
         super();
         this.id=null;
         this.listener=listener;
         this.editionName=editionName;
     }
     
     public Payment(){
     }
     
     public Integer getId(){
         return this.id;
     }
     
     public User getListener(){
         return this.listener;
     }
     
     public Edition getEdition(){
         return this.editionName;
     }
     
     public Calendar getCreated(){
         return this.created;
     }
     
     public Integer getSuma(){
         return this.suma;
     }
     
     public void setListener(User listener){
         this.listener=listener;
     }
     
     public void setEditionName(Edition editionName){
         this.editionName=editionName;
     }
     
     public void setCreated(Calendar created){
         this.created=created;
     } 
}
