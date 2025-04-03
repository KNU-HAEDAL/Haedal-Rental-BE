package com.rental.haedal.controller;


import com.rental.haedal.domain.enums.ItemStatus;
import com.rental.haedal.dto.admin.req.AdminAddItemRequest;
import com.rental.haedal.dto.admin.req.AdminDeleteItemRequest;
import com.rental.haedal.dto.admin.req.AdminItemRequest;
import com.rental.haedal.dto.admin.res.*;
import com.rental.haedal.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    @Operation(summary = "동방 물품의 상태 바꾸기", description = "관리자가 물품 상태를 강제로 변경합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    public ResponseEntity<AdminItemStatusChangeResponse> adminChangeItemStatus(@RequestBody @Parameter AdminItemRequest request) {
        return ResponseEntity.ok(itemService.changeItemStatus(request));
    }

    @GetMapping("/itemList")
    @Operation(summary = "관리자용 상태별 물품리스트 조회", description = "관리자가 전체 물품을 조회합니다. itmeStatus에 따라 조회할 수 있습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    public ResponseEntity<Page<AdminItemResponse>> adminCheckItemList(
            @RequestParam(required = false) @Parameter(description = "아이템 상태") ItemStatus itemStatus,
            @RequestParam(defaultValue = "0") @Parameter(description = "페이지 번호") int page,
            @RequestParam(defaultValue = "10") @Parameter(description = "페이지 크기") int size
    ) {
        return ResponseEntity.ok(itemService.getAdminItems(itemStatus, PageRequest.of(page, size)));
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
    @Operation(summary = "대여 물품 삭제", description = "관리자가 물품을 삭제함. 삭제한 물품의 데이터베이스상의 id 값 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "해당 물품이 존재하지 않음")
    })
    public ResponseEntity<AdminItemDeleteResponse> adminDeleteItem(@RequestBody @Parameter AdminDeleteItemRequest request) {
        try {
            itemService.deleteItem(request);
            return ResponseEntity.ok(new AdminItemDeleteResponse("삭제에 성공했습니다."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new AdminItemDeleteResponse(e.getMessage()));
        }
    }

    @GetMapping("/itemDetail/{itemId}")
    @Operation(summary = "대여 물품 상세 조회", description = "관리자가 물품의 상세 정보를 확인함.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    public ResponseEntity<AdminItemDetailResponse> adminItemDetail(@PathVariable @Parameter Long itemId) {
        return ResponseEntity.ok(itemService.getItemDetail(itemId));
    }
}
