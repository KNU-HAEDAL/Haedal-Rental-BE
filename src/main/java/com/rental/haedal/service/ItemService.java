package com.rental.haedal.service;

import com.rental.haedal.domain.Item;
import com.rental.haedal.domain.enums.ItemCategory;
import com.rental.haedal.domain.enums.ItemStatus;
import com.rental.haedal.dto.admin.req.AdminAddItemRequest;
import com.rental.haedal.dto.rental.res.ItemResponse;
import com.rental.haedal.repository.ItemRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

    public List<ItemResponse> getItems(ItemCategory itemCategory) {
        List<Item> items;
        List<ItemResponse> itemResponses = new ArrayList<>();

        if (itemCategory == null) {
            items = itemRepository.findAll();
        }
        else {
            items = itemRepository.findAllByCategory(itemCategory);
        }

        for (Item item : items) {
            itemResponses.add(new ItemResponse(
                    item.getId(),
                    item.getItemName(),
                    item.getCategory(),
                    item.getStatus()
            ));
        }

        return itemResponses;
    }
}
