package com.rental.haedal.repository;

import com.rental.haedal.domain.Member;
import com.rental.haedal.domain.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByMember(Member member);
}
