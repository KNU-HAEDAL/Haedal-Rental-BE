package com.rental.haedal.service;

import com.rental.haedal.domain.Item;
import com.rental.haedal.domain.enums.ItemCategory;
import com.rental.haedal.domain.enums.ItemStatus;
import com.rental.haedal.dto.admin.req.AdminAddItemRequest;
import com.rental.haedal.dto.rental.res.ItemResponse;
import com.rental.haedal.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public Long createItem(AdminAddItemRequest request) {
        Item item = Item.builder()
                .itemName(request.itemName())
                .category(request.itemCategory())
                .status(ItemStatus.RENTAL_AVAILABLE)
                .build();
        itemRepository.save(item);

        return item.getId();
    }

    public Page<ItemResponse> getItems(ItemCategory itemCategory, Pageable pageable) {
        Page<Item> items;
        if (itemCategory == null) {
            items = itemRepository.findAll(pageable);
        }
        else {
            items = itemRepository.findAllByCategory(itemCategory, pageable);
        }

        Page<ItemResponse> response = items.map(item -> new ItemResponse(
                item.getId(),
                item.getItemName(),
                item.getCategory(),
                item.getStatus()
        ));

        return response;
    }
}
