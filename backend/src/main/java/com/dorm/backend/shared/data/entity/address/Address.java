package com.dorm.backend.shared.data.entity.address;

import com.dorm.backend.shared.data.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class Address extends BaseEntity {

    @ManyToOne
    private City city;

    //fields

    private String street;

    private String number;

    private String zipCode;
}
