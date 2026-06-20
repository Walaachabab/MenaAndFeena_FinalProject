package org.example.menaandfeena_finalproject.DTO.In;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Card details entered on our own payment page to pay for a specific order.
// The amount is NOT accepted from the client; it is read from the order on the server.
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPaymentRequestDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Card number cannot be blank")
    private String number;

    @NotBlank(message = "CVC cannot be blank")
    private String cvc;

    @NotBlank(message = "Month cannot be blank")
    private String month;

    @NotBlank(message = "Year cannot be blank")
    private String year;
}
