package org.example.menaandfeena_finalproject.DTO.Out;

import lombok.AllArgsConstructor;
import lombok.Data;

// كلاس فرعي للكروت لتمثيل تفاصيل النشاط ببساطة
@Data
@AllArgsConstructor
public class ActivityCardDto {
    private String title;       // اسم النشاط (مثلاً: حفرة في الطريق أو بطولة البلوت)
    private String status;      // حالة النشاط (COMPLETED, CONFIRMED, JOINED)
    private String dateString;  // تاريخ التفاعل
}
