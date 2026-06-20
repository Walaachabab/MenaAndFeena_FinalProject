package org.example.menaandfeena_finalproject.DTO.Out;

import lombok.AllArgsConstructor;
import lombok.Data;

// Minimal result of a Moyasar card charge: the Moyasar payment id, status, and optional 3DS URL.
@Data
@AllArgsConstructor
public class MoyasarChargeOutDTO {
    private String id;
    private String status;
    private Integer amount;
    private String transactionUrl;
}
