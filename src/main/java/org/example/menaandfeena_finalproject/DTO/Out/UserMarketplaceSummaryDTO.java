package org.example.menaandfeena_finalproject.DTO.Out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMarketplaceSummaryDTO {

    private UserBasicInfoDTO basicInfo;
    private Integer myOrdersCount;

    private Integer productOrdersCount;

    private Integer myProductsCount;

    private Integer totalPurchasesAmount;

    private Integer totalSalesAmount;

    private List<UserMarketItemDTO> myProducts;
}