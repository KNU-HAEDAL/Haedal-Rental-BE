package com.rental.haedal.repository;

import com.rental.haedal.domain.Member;
import com.rental.haedal.domain.Rental;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findAllByItem_Id(Long itemId);
    List<Rental> findByMember(Member member);
    List<Rental> findByItem_Id(Long itemId);
}
