package inventory.controller;

import inventory.dto.InventoryMovementCreateDTO;
import inventory.dto.InventoryMovementResponseDTO;
import inventory.service.InventoryMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory-movement")
public class InventoryMovementController {

    @Autowired
    private InventoryMovementService inventoryMovementService;

    @PostMapping("/entry")
    public ResponseEntity<InventoryMovementResponseDTO> registerEntry(@RequestBody InventoryMovementCreateDTO inventoryMovementCreateDTO) {
        InventoryMovementResponseDTO inventoryMovementResponseDTO = inventoryMovementService.registerProductEntry(inventoryMovementCreateDTO);
        return new ResponseEntity<>(inventoryMovementResponseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/exit")
    public ResponseEntity<InventoryMovementResponseDTO> registerExit(@RequestBody InventoryMovementCreateDTO inventoryMovementCreateDTO) {
        InventoryMovementResponseDTO inventoryMovementResponseDTO = inventoryMovementService.registerProductExit(inventoryMovementCreateDTO);
        return new ResponseEntity<>(inventoryMovementResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<InventoryMovementResponseDTO>> getAllMovements() {
        List<InventoryMovementResponseDTO> inventoryMovementResponseDTO = inventoryMovementService.getInventoryMovements();
        return new ResponseEntity<>(inventoryMovementResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryMovementResponseDTO> getMovementById(@PathVariable Long id) {
        InventoryMovementResponseDTO inventoryMovementResponseDTO = inventoryMovementService.getInventoryMovementById(id);
        return new ResponseEntity<>(inventoryMovementResponseDTO, HttpStatus.OK);
    }
}
