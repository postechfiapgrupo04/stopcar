package br.com.fiap.stopcar.factory;

import br.com.fiap.stopcar.application.dto.PaymentDTO;
import br.com.fiap.stopcar.domain.entities.Payments;
import br.com.fiap.stopcar.domain.enums.PaymentType;

public class PaymentsFactory {
    public static Payments buildPayments() {
        return Payments.builder()
                .paymentDate(ReservationsFactory.mockStartDate)
                .paymentType(PaymentType.CREDIT_CARD)
                .value(10D)
                .build();
    }

    public static PaymentDTO buildPaymentDTO() {
        return new PaymentDTO(
                buildPayments().getPaymentType(),
                buildPayments().getValue().toString(),
                buildPayments().getPaymentDate()
        );
    }
}
