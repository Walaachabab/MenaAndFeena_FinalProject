package org.example.menaandfeena_finalproject.DTO.Out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOrdersPageDTO {

    private UserBasicInfoDTO basicInfo;

    private Integer totalOrders;
    private Integer totalAmount;

    private List<UserOrderDTO> orders;
}