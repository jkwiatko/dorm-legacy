package com.dorm.backend.shared.data.entity.address;

import com.dorm.backend.shared.data.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@Entity
public class City extends BaseEntity {

    @OneToMany(mappedBy = "city")
    private List<Address> addresses;

    //fields

    private String name;

    @Override
    public String toString() {
        return name;
    }
}
