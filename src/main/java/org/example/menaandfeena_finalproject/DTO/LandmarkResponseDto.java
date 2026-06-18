package org.example.menaandfeena_finalproject.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LandmarkResponseDto {

    private String name;
    private String type;
    private long distanceMeters;
}