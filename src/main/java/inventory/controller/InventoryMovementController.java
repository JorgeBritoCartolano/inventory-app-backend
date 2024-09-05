package inventory.controller;

import inventory.dto.InventoryMovementCreateDTO;
import inventory.dto.InventoryMovementResponseDTO;
import inventory.exception.ErrorResponse;
import inventory.responses.ControllerResponses;
import inventory.service.InventoryMovementService;
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
@Tag(
    name = "Inventory Movement Operations",
    description = "Endpoints related to managing product movements in the inventory")
@RequestMapping("/inventory-movements")
public class InventoryMovementController {

  @Autowired private InventoryMovementService inventoryMovementService;

  @Operation(
      summary = "Register a product entry",
      description = "Registers an entry of a product into the inventory system.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Created",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = InventoryMovementResponseDTO.class),
                    examples =
                        @ExampleObject(
                            value = ControllerResponses.INVENTORY_MOVEMENT_IN_RESPONSE))),
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
                    examples = @ExampleObject(value = ControllerResponses.NEGATIVE_QUANTITY)))
      })
  @PostMapping("/entry")
  public ResponseEntity<InventoryMovementResponseDTO> registerEntry(
      @RequestBody InventoryMovementCreateDTO inventoryMovementCreateDTO) {
    return new ResponseEntity<>(
        inventoryMovementService.registerProductEntry(inventoryMovementCreateDTO),
        HttpStatus.CREATED);
  }

  @Operation(
      summary = "Register a product exit",
      description = "Registers an exit of a product into the inventory system.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Created",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = InventoryMovementResponseDTO.class),
                    examples =
                        @ExampleObject(
                            value = ControllerResponses.INVENTORY_MOVEMENT_OUT_RESPONSE))),
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
                          name = "Negative quantity",
                          value = ControllerResponses.NEGATIVE_QUANTITY),
                      @ExampleObject(
                          name = "Insufficient stock",
                          value = ControllerResponses.INSUFFICIENT_STOCK)
                    }))
      })
  @PostMapping("/exit")
  public ResponseEntity<InventoryMovementResponseDTO> registerExit(
      @RequestBody InventoryMovementCreateDTO inventoryMovementCreateDTO) {
    return new ResponseEntity<>(
        inventoryMovementService.registerProductExit(inventoryMovementCreateDTO),
        HttpStatus.CREATED);
  }

  @Operation(
      summary = "Get all inventory movements",
      description = "Retrieves all inventory movements, including product entries and exits.")
  @ApiResponses(
      @ApiResponse(
          responseCode = "200",
          description = "Ok",
          content =
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = InventoryMovementResponseDTO.class),
                  examples =
                      @ExampleObject(
                          value = ControllerResponses.INVENTORY_MOVEMENT_LIST_RESPONSE))))
  @GetMapping
  public ResponseEntity<List<InventoryMovementResponseDTO>> getAllMovements() {
    return new ResponseEntity<>(inventoryMovementService.getInventoryMovements(), HttpStatus.OK);
  }

  @Operation(
      summary = "Get inventory movement by ID",
      description = "Retrieves a specific inventory movement by its unique ID.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Ok",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = InventoryMovementResponseDTO.class),
                    examples =
                        @ExampleObject(
                            value = ControllerResponses.INVENTORY_MOVEMENT_IN_RESPONSE))),
        @ApiResponse(
            responseCode = "404",
            description = "Not found",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples =
                        @ExampleObject(value = ControllerResponses.INVENTORY_MOVEMENT_NOT_FOUND)))
      })
  @GetMapping("/{id}")
  public ResponseEntity<InventoryMovementResponseDTO> getMovementById(@PathVariable Long id) {
    return new ResponseEntity<>(
        inventoryMovementService.getInventoryMovementById(id), HttpStatus.OK);
  }
}
