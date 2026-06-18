package org.example.menaandfeena_finalproject.DTO.Out;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CandidateResponseDto {
    private Integer candidateId;
    private String fullName;
    private String email;
    private String gender;
    private LocalDateTime appliedAt;
    private Integer totalVotes; // 👈 إجمالي الأصوات المحسوبة تلقائياً للمرشح
    private String status;
}
