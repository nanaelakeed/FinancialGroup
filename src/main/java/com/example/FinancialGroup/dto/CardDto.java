package com.example.FinancialGroup.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;


import java.util.Date;



@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder        //produce the code required build.builder
public class CardDto {
    private String creditNumber;

    private String bankName;

    private Long cvv;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date expireDate;
}
