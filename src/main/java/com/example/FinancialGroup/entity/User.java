package com.example.FinancialGroup.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "user")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "city")
    private String city;

    @Column(name = "salary")
    private Long salary;

    @JsonIgnore
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<CreditCard> creditCards;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "group_users",
    joinColumns = {@JoinColumn(name = "group_id")},
    inverseJoinColumns = {@JoinColumn(name = "user_id")})
    @ToString.Exclude
    private List<Group> groups;


}
