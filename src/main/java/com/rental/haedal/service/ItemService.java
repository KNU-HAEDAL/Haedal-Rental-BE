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
import com.rental.haedal.dto.admin.res.AdminItemResponse;
import com.rental.haedal.dto.admin.res.AdminItemDetailResponse;
import com.rental.haedal.dto.admin.res.AdminItemStatusChangeResponse;
import com.rental.haedal.dto.rental.req.ItemReturnRequest;
import com.rental.haedal.dto.rental.req.RentalRequest;
import com.rental.haedal.dto.rental.res.*;
import com.rental.haedal.repository.ItemRentalRepository;
import com.rental.haedal.repository.ItemRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Base64;


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

    public AdminItemDetailResponse getItemDetail(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("not exist item id: "+ itemId));

        List<Rental> rentals = itemRentalRepository.findAllByItem_Id(itemId);

        if (rentals.isEmpty()) {   // 대여내역이 한번도 없는경우
            return AdminItemDetailResponse.builder()
                    .itemName(item.getItemName())
                    .itemCategory(item.getCategory())
                    .build();
        }
        else {
            // 가장 최근 대여내역 조회
            Rental recentRental = rentals.stream()
                    .max(Comparator.comparing(Rental::getRentalDate)).orElse(null);

            return AdminItemDetailResponse.builder()
                    .rentalMemberName(recentRental.getMember().getName())
                    .rentalMemberPhoneNumber(recentRental.getMember().getPhoneNumber())
                    .itemName(item.getItemName())
                    .itemCategory(item.getCategory())
                    .rentalDate(recentRental.getRentalDate())
                    .returnDateTime(recentRental.getReturnDateTime())
                    .lastPictureUrl(recentRental.getPictureUrl())
                    .build();
        }
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

    public Page<AdminItemResponse> getAdminItems(ItemStatus itemStatus, Pageable pageable) {
        Page<Item> items = (itemStatus == null)
                ? itemRepository.findAll(pageable)
                : itemRepository.findAllByStatus(itemStatus, pageable);

        return items.map(item -> {
            // item의 가장 최근 rental 찾기
            List<Rental> rentals = itemRentalRepository.findAllByItem_Id(item.getId());
            Rental latestRental = rentals.stream()
                    .max(Comparator.comparing(Rental::getRentalDate))
                    .orElse(null);

            AdminItemResponse.AdminItemResponseBuilder builder = AdminItemResponse.builder()
                    .itemId(item.getId())
                    .itemName(item.getItemName())
                    .itemCategory(item.getCategory())
                    .itemStatus(item.getStatus());

            if (latestRental != null) {
                builder.rentalDate(latestRental.getRentalDate())
                        .dueDate(latestRental.getDueDate())
                        .returnDateTime(latestRental.getReturnDateTime())
                        .rentalMemberName(latestRental.getMember().getName());
            }
            return builder.build();
        });
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
                        rental.getDueDate(),
                        rental.getItem().getStatus()))
                .sorted(Comparator.comparing(ItemIndividualResponse::dueDate))
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
            rental.setReturnDateTime(LocalDateTime.now());
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

        // Base64 이미지 디코딩 후 저장
        String encoded = request.base64Image();
        if (encoded != null && !encoded.isBlank()) {
            try {
                byte[] decodedImg = Base64.getDecoder().decode(encoded);
                String fileName = rental.getId().toString() + ".jpg";
                Path saveDir  = Paths.get("/home/ubuntu/app/images/returns");
//                Path saveDir  = Paths.get("src/main/resources/static/images/returns");  // 개발용
                Files.createDirectories(saveDir);
                Path filePath = saveDir.resolve(fileName);  // 디렉토리와 fileName을 합쳐 최종 파일경로 생성
                Files.write(filePath, decodedImg);

                // 웹에서 접근 가능한 URL 생성 및 저장
                String imageUrl = "/images/returns/" + rental.getId().toString();
                rental.setPictureUrl(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("반납 이미지 저장 중 오류가 발생했습니다.", e);
            }
        }

        // 기타 물품 반납 처리
        rental.setReturnDateTime(LocalDateTime.now());
        changeItemStatus(rental.getItem().getId(), ItemStatus.RENTAL_AVAILABLE);

        return new ItemReturnResponse(rental.getItem().getId());
    }

    private Rental findRentalByItemId(Long itemId) {
        List<Rental> rentals = itemRentalRepository.findAllByItem_Id(itemId);
        if (rentals.isEmpty()) {
            throw new IllegalArgumentException("대여내역이 없는 item id: " + itemId);
        }

        // 아직 반납되지 않은 대여(Rental.returnDateTime ==  null)를 찾음
        return rentals.stream()
                .filter(r -> r.getReturnDateTime() == null)
                .max(Comparator.comparing(Rental::getRentalDate))
                .orElseThrow(() -> new IllegalArgumentException("해당 item의 반납되지 않은 대여 내역이 없습니다."));
    }
}
