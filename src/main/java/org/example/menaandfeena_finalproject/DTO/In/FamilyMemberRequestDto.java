package org.example.menaandfeena_finalproject.DTO.In;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class FamilyMemberRequestDto {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Age cannot be null")
    @Min(value = 0, message = "Age must be at least 0")
    @Max(value = 130, message = "Age must be realistic")
    private Integer age;

    @NotBlank(message = "Gender cannot be blank")
    @Pattern(regexp = "MALE|FEMALE", message = "Gender must be either MALE or FEMALE")
    private String gender;

    @NotBlank(message = "Relation cannot be blank")
    private String relation; // ابن، ابنة، زوجة

    @NotNull(message = "User ID cannot be null")
    private Integer userId; // مؤقتاً لربط التابع باليوزر (حتى نفعّل السيكيورتي لاحقاً)
}
