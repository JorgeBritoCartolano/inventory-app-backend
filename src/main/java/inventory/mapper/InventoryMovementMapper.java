package inventory.mapper;

import inventory.dto.InventoryMovementCreateDTO;
import inventory.dto.InventoryMovementResponseDTO;
import inventory.model.InventoryMovement;
import org.modelmapper.ModelMapper;

public class InventoryMovementMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static InventoryMovementResponseDTO toDTO(InventoryMovement inventoryMovement) {
        return modelMapper.map(inventoryMovement, InventoryMovementResponseDTO.class);
    }

    public static InventoryMovement toEntity(InventoryMovementCreateDTO dto) {
        return modelMapper.map(dto, InventoryMovement.class);
    }
}
