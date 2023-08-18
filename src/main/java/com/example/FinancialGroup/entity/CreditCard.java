package com.example.FinancialGroup.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "cridit_card")
@Setter
@Getter
@ToString
@NoArgsConstructor
public class CreditCard {
    @Id
    @Column(name = "cridit_number")
    private String creditNumber;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "cvv")
    private Long cvv;

    @Column(name = "expire_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date expireDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    public CreditCard(String creditNumber, String bankName, Long cvv, Date expireDate) {
        this.creditNumber = creditNumber;
        this.bankName = bankName;
        this.cvv = cvv;
        this.expireDate = expireDate;
    }
}
