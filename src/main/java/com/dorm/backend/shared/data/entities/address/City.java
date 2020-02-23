package com.dorm.backend.shared.data.entities.address;

import com.dorm.backend.shared.data.entities.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class City extends BaseEntity {

    private String name;
    private List<Address> addresses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "city")
    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return name;
    }
}
