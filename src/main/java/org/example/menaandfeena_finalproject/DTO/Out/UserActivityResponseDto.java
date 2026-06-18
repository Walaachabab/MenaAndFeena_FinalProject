package org.example.menaandfeena_finalproject.DTO.Out;


import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class UserActivityResponseDto {
    private List<ActivityCardDto> reportedIssues;  // البلاغات التي رفعها وحالتها
    private List<ActivityCardDto> joinedEvents;    // الفعاليات التي سجل فيها وحالتها
    private List<ActivityCardDto> joinedInitiatives;// المبادرات التي انضم لها وحالتها
}

