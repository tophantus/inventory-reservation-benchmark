package com.tophantu.inventory.shop.adapter.inbound.rest;

import com.tophantu.inventory.shared.adapter.inbound.http.ApiResponse;
import com.tophantu.inventory.shop.application.command.dto.CreateShopCommand;
import com.tophantu.inventory.shop.application.command.dto.CreateShopResult;
import com.tophantu.inventory.shop.application.command.port.inbound.CreateShopUseCase;
import com.tophantu.inventory.shop.application.query.dto.GetShopByIdQuery;
import com.tophantu.inventory.shop.application.query.dto.ShopQueryResult;
import com.tophantu.inventory.shop.application.query.port.inbound.GetShopByIdUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/shops")
public class ShopController {

    private final CreateShopUseCase createShopUseCase;
    private final GetShopByIdUseCase getShopByIdUseCase;

    public ShopController(
            CreateShopUseCase createShopUseCase,
            GetShopByIdUseCase getShopByIdUseCase
    ) {
        this.createShopUseCase = createShopUseCase;
        this.getShopByIdUseCase = getShopByIdUseCase;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CreateShopResult>> createShop(
            @RequestBody CreateShopCommand command
    ) {
        CreateShopResult result = createShopUseCase.createShop(command);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(result));
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<ApiResponse<ShopQueryResult>> getShopById(@PathVariable Long shopId) {
        ShopQueryResult result = getShopByIdUseCase.getShopById(new GetShopByIdQuery(shopId));
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
