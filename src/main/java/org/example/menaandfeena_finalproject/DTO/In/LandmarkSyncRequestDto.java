package org.example.menaandfeena_finalproject.DTO.In;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LandmarkSyncRequestDto {
    @NotNull(message = "خط العرض لا يمكن أن يكون فارغاً")
    private Double lat;

    @NotNull(message = "خط الطول لا يمكن أن يكون فارغاً")
    private Double lon;

    @NotNull(message = "نصف قطر البحث لا يمكن أن يكون فارغاً")
    private Integer radius;
}
