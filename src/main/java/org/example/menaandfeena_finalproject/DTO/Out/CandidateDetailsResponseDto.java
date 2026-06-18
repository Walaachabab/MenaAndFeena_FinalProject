package org.example.menaandfeena_finalproject.DTO.Out;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class CandidateDetailsResponseDto {
    private Integer candidateId;
    private String fullName;
    private String neighborhoodName; // "حي النرجس"
    private String status;           // "مرشح معتمد"
    private String memberSince;      // "عضو في الحي منذ 2022"

    // العدادات الرقمية الأربعة في الفيقما
    private int organizedEventsCount;     // فعاليات نظمها (3)
    private int joinedInitiativesCount;   // مبادرات شارك/قادها (5)
    private int resolvedIssuesCount;      // بلاغات تمت متابعتها (12)
    private int initiativeParticipants;   // مشاركين في مبادراته (120)

    // قوائم الكروت للفعاليات والمبادرات
    private List<String> initiativesTitles; // ["حملة تشجير الحي", "تنظيف الحديقة"]
    private List<String> eventsTitles;      // ["بطولة كرة قدم", "يوم الطفل"]
}
