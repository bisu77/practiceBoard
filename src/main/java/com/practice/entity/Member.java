package com.practice.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(unique = true, updatable = false, nullable = false)
    private String userId;
    @NotEmpty
    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column
    private Role role;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    @Builder
    public Member(@NotEmpty String userId, @NotEmpty String name, Address address, Role role){
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.role = role;
    }

    public void memberUpdate(String name, String street, String detailAddress, String zipcode){
        this.name = name;
        putAddress(street,detailAddress,zipcode);
    }

    public Member memberUpdate(String name){
        memberUpdate(name,null,null,null);
        return this;
    }

    private void putAddress(String street, String detailAddress, String zipcode){
        if(StringUtils.hasText(street))  this.address.setStreet(street);
        if(StringUtils.hasText(detailAddress))  this.address.setDetailAddress(detailAddress);
        if(StringUtils.hasText(zipcode))  this.address.setZipcode(zipcode);
    }

    private void updateRole(Role role){
        this.role = role;
    }
}
