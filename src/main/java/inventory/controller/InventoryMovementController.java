package inventory.controller;

import inventory.dto.InventoryMovementCreateDTO;
import inventory.dto.InventoryMovementResponseDTO;
import inventory.service.InventoryMovementService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory-movements")
public class InventoryMovementController {

  @Autowired private InventoryMovementService inventoryMovementService;

  @PostMapping("/entry")
  public ResponseEntity<InventoryMovementResponseDTO> registerEntry(
      @RequestBody InventoryMovementCreateDTO inventoryMovementCreateDTO) {
    return new ResponseEntity<>(
        inventoryMovementService.registerProductEntry(inventoryMovementCreateDTO),
        HttpStatus.CREATED);
  }

  @PostMapping("/exit")
  public ResponseEntity<InventoryMovementResponseDTO> registerExit(
      @RequestBody InventoryMovementCreateDTO inventoryMovementCreateDTO) {
    return new ResponseEntity<>(
        inventoryMovementService.registerProductExit(inventoryMovementCreateDTO),
        HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<InventoryMovementResponseDTO>> getAllMovements() {
    return new ResponseEntity<>(inventoryMovementService.getInventoryMovements(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<InventoryMovementResponseDTO> getMovementById(@PathVariable Long id) {
    return new ResponseEntity<>(
        inventoryMovementService.getInventoryMovementById(id), HttpStatus.OK);
  }
}
