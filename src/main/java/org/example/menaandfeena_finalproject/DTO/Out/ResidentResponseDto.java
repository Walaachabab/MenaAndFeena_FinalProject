package org.example.menaandfeena_finalproject.DTO.Out;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResidentResponseDto {
    private String fullName;
    private String email;
    private String phone;
    private String gender;
}
