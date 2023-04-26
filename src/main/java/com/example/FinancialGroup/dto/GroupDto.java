package com.example.FinancialGroup.dto;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class GroupDto {

    private Long groupId;

    private Long price;

    private int numMembers;

    private String creator;

    private Date stDate;

    private Date endDate;

}
