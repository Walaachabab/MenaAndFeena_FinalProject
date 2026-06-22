package org.example.menaandfeena_finalproject.DTO.Out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileInitiativesDTO {
    private UserBasicInfoDTO basicInfo;
    private List<UserInitiativeDTO> participatedInitiatives;
    private List<UserInitiativeDTO> createdInitiatives;
}