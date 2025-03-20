package com.rental.haedal.rental.controller;

import com.rental.haedal.rental.dto.req.ItemListRequest;
import com.rental.haedal.rental.dto.req.ItemReturnRequest;
import com.rental.haedal.rental.dto.req.RentalRequest;
import com.rental.haedal.rental.dto.res.ItemListResponse;
import com.rental.haedal.rental.dto.res.UserRentalResponse;
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

    @PostMapping("/itemList")
    @Operation(summary = "물품 조회", description = "대여가능한 전체 물품 목록을 조회합니다. 카테고리 타입에 따라 조회할 수 있습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    public ResponseEntity<List<ItemListResponse>> getItemList(
            @RequestBody @Parameter ItemListRequest request
    ) {
        return ResponseEntity.ok().build();
    }


    @PostMapping("")
    @Operation(summary = "물품 대여", description = "물품 대여 장부를 작성하여 전송합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    public ResponseEntity<Void> postRental(@RequestBody @Parameter RentalRequest request) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/rental/{usedId}")
    @Operation(summary = "유저의 빌린 물품 확인", description = "유저 ID로 빌린 물품을 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    public ResponseEntity<List<UserRentalResponse>> getUser(@Parameter(description = "사용자 ID", required = true, example = "1")
                                                            @PathVariable Long usedId) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/return")
    @Operation(summary = "물품 반납", description = "물품을 반납합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    public ResponseEntity<Void> returnRentalItem(@RequestBody @Parameter ItemReturnRequest request) {
        return ResponseEntity.ok().build();
    }
}
