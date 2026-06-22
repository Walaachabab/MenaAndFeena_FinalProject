package org.example.menaandfeena_finalproject.DTO.Out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileEventsDTO {
    private UserBasicInfoDTO basicInfo;
    private List<UserEventDTO> participatedEvents;
    private List<UserEventDTO> createdEvents;
}