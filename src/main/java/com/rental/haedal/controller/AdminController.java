package com.rental.haedal.controller;


import com.rental.haedal.dto.admin.req.AdminAddItemRequest;
import com.rental.haedal.dto.admin.req.AdminItemRequest;
import com.rental.haedal.dto.admin.res.AdminItemAddResponse;
import com.rental.haedal.dto.admin.res.AdminItemDetailResponse;
import com.rental.haedal.dto.admin.res.AdminItemEditResponse;
import com.rental.haedal.dto.admin.res.AdminItemListResponse;
import com.rental.haedal.dto.admin.res.AdminItemStatusChangeResponse;
import com.rental.haedal.domain.enums.ItemStatus;
import com.rental.haedal.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Admin API")
public class AdminController {
    private final ItemService itemService;

    @PutMapping("/changeItemStatus")
    @Operation(summary = "[구현 안됨] 동방 물품의 상태 바꾸기", description = "관리자가 물품 상태를 바꿈으로써 동아리 물품을 대여할 수 있도록 함.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    public ResponseEntity<AdminItemStatusChangeResponse> adminChangeItemStatus(@RequestBody @Parameter AdminItemRequest request) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/itemList")
    @Operation(summary = "[구현 안됨] 상태별 물품리스트 조회", description = "관리자가 페이지의 물품을 조회함.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    public ResponseEntity<List<AdminItemListResponse>> adminCheckItemList(@RequestParam @Parameter(description = "아이템 상태") ItemStatus itemStatus) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/item")
    @Operation(summary = "대여 물품 추가", description = "관리자가 물품을 추가함. 추가한 물품의 데이터베이스상의 id 값 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    public ResponseEntity<AdminItemAddResponse> adminAddItem(@RequestBody @Parameter AdminAddItemRequest request) {
        Long itemId = itemService.createItem(request);
        return ResponseEntity.ok(new AdminItemAddResponse(itemId));
    }

    @DeleteMapping("/item")
    @Operation(summary = "[구현 안됨] 대여 물품 삭제", description = "관리자가 물품을 삭제함. 삭제한 물품의 데이터베이스상의 id 값 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    public ResponseEntity<AdminItemEditResponse> adminDeleteItem(@RequestBody @Parameter AdminItemRequest request) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/itemDetail/{itemId}")
    @Operation(summary = "[구현 안됨] 대여 물품 상세 조회", description = "관리자가 물품의 상세 정보를 확인함.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    public ResponseEntity<List<AdminItemDetailResponse>> adminItemDetail(@PathVariable @Parameter Long itemId) {
        return ResponseEntity.ok().build();
    }
}
