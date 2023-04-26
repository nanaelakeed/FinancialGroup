package com.example.FinancialGroup.dto;

import com.example.FinancialGroup.enums.StatusCode;
import com.example.FinancialGroup.enums.StatusMessage;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ApiResponseDto<T> {
    StatusMessage message;
    StatusCode code;
    Object responseData;
}
