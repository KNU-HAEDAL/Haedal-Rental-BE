package com.rental.haedal.repository;

import com.rental.haedal.domain.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRentalRepository extends JpaRepository<Rental, Long> {
}
