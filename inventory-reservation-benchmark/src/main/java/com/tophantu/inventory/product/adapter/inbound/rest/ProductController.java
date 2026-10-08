package com.tophantu.inventory.product.adapter.inbound.rest;

import com.tophantu.inventory.product.application.command.dto.CreateProductCommand;
import com.tophantu.inventory.product.application.command.dto.CreateProductResult;
import com.tophantu.inventory.product.application.command.port.inbound.CreateProductUseCase;
import com.tophantu.inventory.product.application.query.dto.GetProductByIdQuery;
import com.tophantu.inventory.product.application.query.dto.ProductQueryResult;
import com.tophantu.inventory.product.application.query.port.inbound.GetProductByIdUseCase;
import com.tophantu.inventory.shared.adapter.inbound.http.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/v1/products")
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;

    public ProductController(
            CreateProductUseCase createProductUseCase,
            GetProductByIdUseCase getProductByIdUseCase
    ) {
        this.createProductUseCase = createProductUseCase;
        this.getProductByIdUseCase = getProductByIdUseCase;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CreateProductResult>> createProduct(
            @Valid @RequestBody CreateProductCommand command
    ) {
        CreateProductResult result = createProductUseCase.createProduct(command);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(result));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductQueryResult>> getProductById(
            @PathVariable @Positive Long productId
    ) {
        ProductQueryResult result = getProductByIdUseCase.getProductById(new GetProductByIdQuery(productId));
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
