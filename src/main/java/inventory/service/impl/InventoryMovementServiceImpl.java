package inventory.service.impl;

import inventory.dto.InventoryMovementCreateDTO;
import inventory.dto.InventoryMovementResponseDTO;
import inventory.dto.MovementTypeEnum;
import inventory.mapper.InventoryMovementMapper;
import inventory.model.InventoryMovement;
import inventory.model.Product;
import inventory.repository.InventoryMovementRepository;
import inventory.repository.ProductRepository;
import inventory.service.InventoryMovementService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryMovementServiceImpl implements InventoryMovementService {

    @Autowired
    private InventoryMovementRepository inventoryMovementRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public InventoryMovementResponseDTO registerProductEntry(InventoryMovementCreateDTO inventoryMovement) {
    Product product =
        productRepository
            .findById(inventoryMovement.getProductId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        product.setStock(product.getStock() + inventoryMovement.getQuantity());
        productRepository.save(product);

        InventoryMovement movement = new InventoryMovement();
        movement.setProduct(product);
        movement.setMovementType(MovementTypeEnum.IN);
        movement.setQuantity(inventoryMovement.getQuantity());
        movement.setDescription(inventoryMovement.getDescription());
        movement.setCreatedAt(LocalDateTime.now());
        inventoryMovementRepository.save(movement);

        return InventoryMovementMapper.toDTO(movement);
    }

    @Transactional
    public InventoryMovementResponseDTO registerProductExit(InventoryMovementCreateDTO inventoryMovement) {
    Product product =
        productRepository
            .findById(inventoryMovement.getProductId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        if (product.getStock() < inventoryMovement.getQuantity()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Insufficient stock");
        }

        product.setStock(product.getStock() - inventoryMovement.getQuantity());
        productRepository.save(product);

        InventoryMovement movement = new InventoryMovement();
        movement.setProduct(product);
        movement.setMovementType(MovementTypeEnum.OUT);
        movement.setQuantity(inventoryMovement.getQuantity());
        movement.setDescription(inventoryMovement.getDescription());
        movement.setCreatedAt(LocalDateTime.now());
        inventoryMovementRepository.save(movement);

        return InventoryMovementMapper.toDTO(movement);
    }

    public List<InventoryMovementResponseDTO> getInventoryMovements() {
        return inventoryMovementRepository.findAll().stream()
                .map(InventoryMovementMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InventoryMovementResponseDTO getInventoryMovementById(Long id) {
        InventoryMovement inventoryMovement =
                inventoryMovementRepository
                        .findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory movement not found"));

        return InventoryMovementMapper.toDTO(inventoryMovement);
    }
}
