package com.example.FinancialGroup.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "collect_group")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long groupId;

    @Column(name = "price")
    private Long price;

    @Column(name = "num_members")
    private int numMembers;

    @Column(name = "creator")
    private String creator;

    @Column(name = "st_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date stDate;

    @Column(name = "end_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endDate;

    @ManyToMany(mappedBy = "groups")
    @ToString.Exclude
    private List<User> users;

    public Group(Long price, int numMembers, String creator, Date stDate, Date endDate) {
        this.price = price;
        this.numMembers = numMembers;
        this.creator = creator;
        this.stDate = stDate;
        this.endDate = endDate;
    }


}
