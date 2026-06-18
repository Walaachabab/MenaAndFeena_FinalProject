package org.example.menaandfeena_finalproject.DTO.Out;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class ElectionPageResponseDto {
    private long daysRemaining;    // 02 يوم
    private long hoursRemaining;   // 14 ساعة
    private long minutesRemaining; // 35 دقيقة
    private List<CandidateCardDto> candidates; // بطاقات المرشحين (محمد القحطاني، خالد المطيري...)
}
