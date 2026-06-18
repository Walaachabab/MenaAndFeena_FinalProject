package org.example.menaandfeena_finalproject.DTO.Out;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NeighborhoodDashboardResponseDto {
    private String welcomeMessage;       // "مرحباً بك يا خالد في حي النرجس اليوم!"
    private String neighborhoodName;     // "النرجس"
    private String city;                 // "Riyadh"
    private Integer registeredResidents; // عدد جيرانه المسجلين في النظام بنفس الحي
    private Integer estimatedPopulation; // عدد سكان الحي التقديري الإجمالي
}
