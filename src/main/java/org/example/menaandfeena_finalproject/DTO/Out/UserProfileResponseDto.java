package org.example.menaandfeena_finalproject.DTO.Out;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserProfileResponseDto {
    private Integer id;
    private String fullName;
    private String email;
    private String phone;
    private String nationalId;
    private LocalDate birthDate;
    private Integer age;
    private String gender;
    private String status;
    private String neighborhoodName;
}
