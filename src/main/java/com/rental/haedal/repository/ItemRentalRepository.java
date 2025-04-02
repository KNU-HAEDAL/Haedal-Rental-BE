package com.rental.haedal.repository;

import com.rental.haedal.domain.Rental;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByItem_Id(Long itemId);
}
