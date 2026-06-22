package org.example.menaandfeena_finalproject.DTO.Out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileIssuesDTO {
    private UserBasicInfoDTO basicInfo;
    private Integer totalReports;
    private Integer openReports;
    private Integer completedReports;
    private List<UserIssueDTO> reports;
}