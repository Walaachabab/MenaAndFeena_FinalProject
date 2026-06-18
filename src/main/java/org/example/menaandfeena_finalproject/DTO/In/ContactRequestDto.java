package org.example.menaandfeena_finalproject.DTO.In;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContactRequestDto {
    @NotBlank(message = "الاسم لا يمكن أن يكون فارغاً")
    private String name;

    @NotBlank(message = "البريد الإلكتروني لا يمكن أن يكون فارغاً")
    @Email(message = "صيغة البريد الإلكتروني غير صحيحة")
    private String email;

    @NotBlank(message = "نص الرسالة لا يمكن أن يكون فارغاً")
    private String message;
}
