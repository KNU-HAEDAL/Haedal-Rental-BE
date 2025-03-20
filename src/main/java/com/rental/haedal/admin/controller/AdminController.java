package com.rental.haedal.admin.controller;


import com.rental.haedal.admin.dto.req.AdminAddItemRequest;
import com.rental.haedal.admin.dto.req.AdminItemListRequest;
import com.rental.haedal.admin.dto.req.AdminItemRequest;
import com.rental.haedal.admin.dto.res.AdminItemDetailResponse;
import com.rental.haedal.admin.dto.res.AdminItemListResponse;
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
    // 의존성 주입을 위해 띄움.

    @PostMapping("/rentalCheck")
    @Operation(summary = "물품 상태 바꾸기", description = "관리자가 물품 상태를 바꿈.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    public ResponseEntity<Void> adminChangeItemStatus(@RequestBody @Parameter AdminItemRequest request) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/itemList")
    @Operation(summary = "페이지 물품 조회", description = "관리자가 페이지의 물품을 조회함.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    public ResponseEntity<List<AdminItemListResponse>> adminCheckItemList(@RequestBody @Parameter AdminItemListRequest request) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/item")
    @Operation(summary = "대여 물품 추가", description = "관리자가 물품을 추가함.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    public ResponseEntity<Void> adminAddItem(@RequestBody @Parameter AdminAddItemRequest request) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/item")
    @Operation(summary = "대여 물품 삭제", description = "관리자가 물품을 삭제함.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    public ResponseEntity<Void> adminDeleteItem(@RequestBody @Parameter AdminItemRequest request) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/itemDetail")
    @Operation(summary = "대여 물품 상세 조회", description = "관리자가 물품의 상세 정보를 확인함.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    public ResponseEntity<List<AdminItemDetailResponse>> adminItemDetail(@RequestBody @Parameter AdminItemRequest request) {
        return ResponseEntity.ok().build();
    }
}
