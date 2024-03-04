package br.com.fiap.stopcar.factory;

import br.com.fiap.stopcar.application.dto.PaymentDTO;
import br.com.fiap.stopcar.domain.entities.Payments;
import br.com.fiap.stopcar.domain.enums.PaymentType;

public class PaymentsFactory {
    public static Payments buildPayments() {
        return Payments.builder()
                .date(ReservationsFactory.mockStartDate)
                .type(PaymentType.CREDIT_CARD)
                .value(10D)
                .build();
    }

    public static PaymentDTO buildPaymentDTO() {
        return new PaymentDTO(
                buildPayments().getType(),
                buildPayments().getValue().toString(),
                buildPayments().getDate()
        );
    }
}
