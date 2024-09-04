package inventory.service;

import inventory.dto.InventoryMovementCreateDTO;
import inventory.dto.InventoryMovementResponseDTO;
import java.util.List;

public interface InventoryMovementService {

  InventoryMovementResponseDTO registerProductEntry(InventoryMovementCreateDTO inventoryMovement);

  InventoryMovementResponseDTO registerProductExit(InventoryMovementCreateDTO inventoryMovement);

  List<InventoryMovementResponseDTO> getInventoryMovements();

  InventoryMovementResponseDTO getInventoryMovementById(Long id);
}
