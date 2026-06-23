package org.example.menaandfeena_finalproject.DTO.Out;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitiativeParticipantOutDTO {
    private Integer participationId;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate joinedAt;
    private Integer userId;
    private String userFullName;
    private String userEmail;
    private String userPhone;
}
