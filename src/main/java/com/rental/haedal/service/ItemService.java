package com.rental.haedal.service;

import com.rental.haedal.auth.AuthUtil;
import com.rental.haedal.domain.Item;
import com.rental.haedal.domain.Member;
import com.rental.haedal.domain.Rental;
import com.rental.haedal.domain.enums.ItemCategory;
import com.rental.haedal.domain.enums.ItemStatus;
import com.rental.haedal.dto.admin.req.AdminAddItemRequest;
import com.rental.haedal.dto.admin.req.AdminDeleteItemRequest;
import com.rental.haedal.dto.admin.req.AdminItemRequest;
import com.rental.haedal.dto.admin.res.AdminItemStatusChangeResponse;
import com.rental.haedal.dto.rental.req.ItemReturnRequest;
import com.rental.haedal.dto.rental.req.RentalRequest;
import com.rental.haedal.dto.rental.res.*;
import com.rental.haedal.repository.ItemRentalRepository;
import com.rental.haedal.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

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

    @Transactional
    public void deleteItem(AdminDeleteItemRequest request) {
        Item item = itemRepository.findById(request.itemId())
                .orElseThrow(() -> new IllegalArgumentException("해당 물품은 존재하지 않습니다."));

        itemRepository.delete(item);
    }

    public Page<ItemResponse> getItems(ItemCategory itemCategory, Pageable pageable) {
        Page<Item> items;
        if (itemCategory == null) {
            items = itemRepository.findAll(pageable);
        } else {
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

    public UserRentalResponse checkRentalItem() {
        Member member = authUtil.getCurrentUser();

        Integer penaltyCount = member.getPenaltyCount();

        List<ItemIndividualResponse> itemResponses = itemRentalRepository.findByMember(member)
                .stream()
                .map(rental -> new ItemIndividualResponse(
                        rental.getItem().getId(),
                        rental.getItem().getCategory(),
                        rental.getItem().getItemName(),
                        rental.getDueDate()))
                .toList();

        return new UserRentalResponse(itemResponses, penaltyCount);
    }

    @Transactional
    public ItemRentalResponse rentItem(RentalRequest request) {
        Item item = itemRepository.findById(request.itemId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid item id: " + request.itemId()));
        if (item.getStatus() != ItemStatus.RENTAL_AVAILABLE) {
            throw new IllegalArgumentException("Invalid item status: " + item.getStatus());
        }

        Member member = authUtil.getCurrentUser();
        Rental rental = Rental.builder()
                .member(member)
                .item(item)
                .rentalDate(request.rentalDate())
                .dueDate(request.rentalDate().plusDays(7))
                .build();
        itemRentalRepository.save(rental);

        changeItemStatus(item.getId(), ItemStatus.RENTING);

        return new ItemRentalResponse(rental.getId());
    }

    public AdminItemStatusChangeResponse changeItemStatus(AdminItemRequest request) {
        // 관리자용 강제 반납처리
        if (request.itemStatus() == ItemStatus.RENTAL_AVAILABLE) {
            Rental rental = findRentalByItemId(request.itemId());
            rental.setReturnDate(LocalDate.now());
        }

        changeItemStatus(request.itemId(), request.itemStatus());

        return new AdminItemStatusChangeResponse(request.itemId(), request.itemStatus());
    }


    private void changeItemStatus(Long itemId, ItemStatus status) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item id: " + itemId));
        item.setStatus(status);
        itemRepository.save(item);
    }

    @Transactional
    public ItemReturnResponse returnItem(ItemReturnRequest request) {
        Rental rental = findRentalByItemId(request.itemId());
        Member member = authUtil.getCurrentUser();
        if (rental.getMember().getId() != member.getId()) {
            throw new IllegalArgumentException("다른 회원이 대여한 item입니다.");
        }

        rental.setReturnDate(LocalDate.now());

        changeItemStatus(rental.getItem().getId(), ItemStatus.RENTAL_AVAILABLE);

        return new ItemReturnResponse(rental.getItem().getId());
    }

    private Rental findRentalByItemId(Long itemId) {
        List<Rental> rentals = itemRentalRepository.findAllByItem_Id(itemId);
        if (rentals.isEmpty()) {
            throw new IllegalArgumentException("대여내역이 없는 item id: " + itemId);
        }

        // 아직 반납되지 않은 대여(Rental.returnDate가 null)를 찾음
        return rentals.stream()
                .filter(r -> r.getReturnDate() == null)
                .max(Comparator.comparing(Rental::getRentalDate))
                .orElseThrow(() -> new IllegalArgumentException("해당 item의 반납되지 않은 대여 내역이 없습니다."));
    }
}
