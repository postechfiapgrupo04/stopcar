package br.com.fiap.stopcar.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class PaymentDTO {

   private String id;
   private String type;
   private String value;
   private LocalDateTime date;
   public PaymentDTO() {}
}



