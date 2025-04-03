package com.rental.haedal.repository;

import com.rental.haedal.domain.Item;
import com.rental.haedal.domain.enums.ItemCategory;
import com.rental.haedal.domain.enums.ItemStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findAllByCategory(ItemCategory itemCategory, Pageable pageable);
    Page<Item> findAllByStatus(ItemStatus itemStatus, Pageable pageable);

}
