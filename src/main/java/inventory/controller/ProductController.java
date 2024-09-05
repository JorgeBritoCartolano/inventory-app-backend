package inventory.controller;

import inventory.dto.InventoryMovementResponseDTO;
import inventory.dto.ProductCreateDTO;
import inventory.dto.ProductResponseDTO;
import inventory.exception.ErrorResponse;
import inventory.responses.ControllerResponses;
import inventory.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Product Operations", description = "Endpoints related to managing products")
@RequestMapping("/products")
public class ProductController {

  @Autowired private ProductService productService;

  @Operation(
      summary = "Create a new product",
      description = "Creates a new product in the inventory.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Created",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProductResponseDTO.class),
                    examples = @ExampleObject(value = ControllerResponses.PRODUCT_RESPONSE))),
        @ApiResponse(
            responseCode = "422",
            description = "Unprocessable entity",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = {
                      @ExampleObject(
                          name = "Negative stock",
                          value = ControllerResponses.NEGATIVE_STOCK),
                      @ExampleObject(
                          name = "Negative price",
                          value = ControllerResponses.NEGATIVE_PRICE)
                    }))
      })
  @PostMapping
  public ResponseEntity<ProductResponseDTO> createProduct(
      @RequestBody ProductCreateDTO productCreateDTO) {
    return new ResponseEntity<>(productService.createProduct(productCreateDTO), HttpStatus.CREATED);
  }

  @Operation(
      summary = "Update an existing product",
      description = "Updates the details of an existing product.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Ok",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProductResponseDTO.class),
                    examples = @ExampleObject(value = ControllerResponses.PRODUCT_RESPONSE))),
        @ApiResponse(
            responseCode = "404",
            description = "Not found",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(value = ControllerResponses.PRODUCT_NOT_FOUND))),
        @ApiResponse(
            responseCode = "422",
            description = "Unprocessable entity",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = {
                      @ExampleObject(
                          name = "Negative stock",
                          value = ControllerResponses.NEGATIVE_STOCK),
                      @ExampleObject(
                          name = "Negative price",
                          value = ControllerResponses.NEGATIVE_PRICE)
                    }))
      })
  @PutMapping("/{id}")
  public ResponseEntity<ProductResponseDTO> updateProduct(
      @PathVariable Long id, @RequestBody ProductCreateDTO productCreateDTO) {
    return new ResponseEntity<>(productService.updateProduct(id, productCreateDTO), HttpStatus.OK);
  }

  @Operation(summary = "Delete a product", description = "Deletes a product by its ID.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(
            responseCode = "404",
            description = "Not found",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(value = ControllerResponses.PRODUCT_NOT_FOUND)))
      })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Operation(
      summary = "Get all products",
      description = "Retrieves a list of all products in the inventory.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Ok",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = InventoryMovementResponseDTO.class),
                    examples = @ExampleObject(value = ControllerResponses.PRODUCT_LIST)))
      })
  @GetMapping
  public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
    return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
  }

  @Operation(
      summary = "Get a product by ID",
      description = "Retrieves the details of a specific product by its ID.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Ok",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = InventoryMovementResponseDTO.class),
                    examples = @ExampleObject(value = ControllerResponses.PRODUCT_RESPONSE))),
        @ApiResponse(
            responseCode = "404",
            description = "Not found",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(value = ControllerResponses.PRODUCT_NOT_FOUND)))
      })
  @GetMapping("/{id}")
  public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
    return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
  }
}
