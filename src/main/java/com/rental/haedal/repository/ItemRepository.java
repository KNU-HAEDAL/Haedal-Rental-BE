package com.rental.haedal.repository;

import com.rental.haedal.domain.Item;
import com.rental.haedal.domain.enums.ItemCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByCategory(ItemCategory itemCategory);

}
