package com.rental.haedal.controller;

import com.rental.haedal.domain.enums.ItemCategory;
import com.rental.haedal.dto.rental.req.ItemReturnRequest;
import com.rental.haedal.dto.rental.req.RentalRequest;
import com.rental.haedal.dto.rental.res.ItemRentalResponse;
import com.rental.haedal.dto.rental.res.ItemResponse;
import com.rental.haedal.dto.rental.res.UserRentalResponse;
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
@RequestMapping("/api/rental")
@RequiredArgsConstructor
@Tag(name = "Rental", description = "Rental API")
public class RentalController {
    // 의존성 주입을 위해 띄움.

    @GetMapping("/itemList")
    @Operation(summary = "물품 조회", description = "대여가능한 전체 물품 목록을 조회합니다. 카테고리 타입에 따라 조회할 수 있습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    public ResponseEntity<List<ItemResponse>> getItemList(
            @RequestParam @Parameter(description = "아이템 카테고리") ItemCategory itemCategory
    ) {
        return ResponseEntity.ok().build();
    }


    @PostMapping("")
    @Operation(summary = "물품 대여", description = "물품 대여 장부를 작성하여 전송합니다. 대여한 itemId를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    public ResponseEntity<ItemRentalResponse> postRental(@RequestBody @Parameter RentalRequest request) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    @Operation(summary = "유저의 빌린 물품 확인", description = "Token 값에서 유저 ID를 추출하여 빌린 물품을 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    public ResponseEntity<UserRentalResponse> getUserItemList() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/return")
    @Operation(summary = "물품 반납", description = "물품을 반납하고 해당 itemId 값을 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    public ResponseEntity<ItemRentalResponse> returnRentalItem(@RequestBody @Parameter ItemReturnRequest request) {
        return ResponseEntity.ok().build();
    }
}
