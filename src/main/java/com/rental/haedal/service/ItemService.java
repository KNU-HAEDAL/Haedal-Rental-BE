package com.rental.haedal.service;

import com.rental.haedal.auth.AuthUtil;
import com.rental.haedal.domain.Item;
import com.rental.haedal.domain.Member;
import com.rental.haedal.domain.Rental;
import com.rental.haedal.domain.enums.ItemCategory;
import com.rental.haedal.domain.enums.ItemStatus;
import com.rental.haedal.dto.admin.req.AdminAddItemRequest;
import com.rental.haedal.dto.rental.req.RentalRequest;
import com.rental.haedal.dto.rental.res.ItemRentalResponse;
import com.rental.haedal.dto.rental.res.ItemResponse;
import com.rental.haedal.repository.ItemRentalRepository;
import com.rental.haedal.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemRentalRepository itemRentalRepository;
    private final AuthUtil authUtil;

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

    @Transactional
    public ItemRentalResponse rentItem(RentalRequest request) {
        Item item = itemRepository.findById(request.itemId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid item id: " + request.itemId()));
        if (item.getStatus() != ItemStatus.RENTAL_AVAILABLE) {
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            throw new IllegalArgumentException("Invalid item status: " + item.getStatus());
        }

        Member member = authUtil.getCurrentUser();
        Rental rental = Rental.builder()
                .member(member)
                .item(item)
                .rentalDate(request.rentalDate())
                .returnDate(request.rentalDate().plusDays(7))
                .build();
        itemRentalRepository.save(rental);

        changeItemStatus(item.getId(), ItemStatus.RENTING);

        return new ItemRentalResponse(rental.getId());
    }

    private void changeItemStatus(Long itemId, ItemStatus status) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item id: " + itemId));
        item.setStatus(status);
    }
}
