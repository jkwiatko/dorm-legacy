package com.dorm.backend.shared.data.repos;

import com.dorm.backend.shared.data.entities.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
