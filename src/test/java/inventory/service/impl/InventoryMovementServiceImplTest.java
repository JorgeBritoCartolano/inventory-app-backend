package inventory.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import inventory.dto.InventoryMovementCreateDTO;
import inventory.dto.InventoryMovementResponseDTO;
import inventory.dto.MovementTypeEnum;
import inventory.model.InventoryMovement;
import inventory.model.Product;
import inventory.repository.InventoryMovementRepository;
import inventory.repository.ProductRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InventoryMovementServiceImplTest {

  @Mock private ProductRepository productRepository;

  @Mock private InventoryMovementRepository inventoryMovementRepository;

  @InjectMocks private InventoryMovementServiceImpl inventoryMovementService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testRegisterProductEntry_Success() {
    Product product = new Product();
    product.setId(1L);
    product.setStock(10);

    when(productRepository.findById(1L)).thenReturn(Optional.of(product));

    InventoryMovementCreateDTO createDTO = new InventoryMovementCreateDTO();
    createDTO.setProductId(1L);
    createDTO.setQuantity(5);
    createDTO.setDescription("Adding stock");

    InventoryMovementResponseDTO responseDTO =
        inventoryMovementService.registerProductEntry(createDTO);

    verify(productRepository).save(product);
    verify(inventoryMovementRepository).save(any(InventoryMovement.class));

    assertEquals(15, product.getStock());
    assertEquals(createDTO.getQuantity(), responseDTO.getQuantity());
    assertEquals(MovementTypeEnum.IN, responseDTO.getMovementType());
  }

  @Test
  public void testRegisterProductEntry_ProductNotFound() {
    when(productRepository.findById(1L)).thenReturn(Optional.empty());

    InventoryMovementCreateDTO createDTO = new InventoryMovementCreateDTO();
    createDTO.setProductId(1L);
    createDTO.setQuantity(5);

    ResponseStatusException exception =
        assertThrows(
            ResponseStatusException.class,
            () -> inventoryMovementService.registerProductEntry(createDTO));

    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    assertEquals("Product not found", exception.getReason());
  }

  @Test
  public void testRegisterProductEntry_NegativeQuantity() {
    Product product = new Product();
    product.setId(1L);
    product.setStock(10);

    when(productRepository.findById(1L)).thenReturn(Optional.of(product));

    InventoryMovementCreateDTO createDTO = new InventoryMovementCreateDTO();
    createDTO.setProductId(1L);
    createDTO.setQuantity(-5);

    ResponseStatusException exception =
        assertThrows(
            ResponseStatusException.class,
            () -> inventoryMovementService.registerProductEntry(createDTO));

    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, exception.getStatusCode());
    assertEquals("Quantity must not be a negative number.", exception.getReason());
  }

  @Test
  public void testRegisterProductExit_Success() {
    Product product = new Product();
    product.setId(1L);
    product.setStock(10);

    when(productRepository.findById(1L)).thenReturn(Optional.of(product));

    InventoryMovementCreateDTO createDTO = new InventoryMovementCreateDTO();
    createDTO.setProductId(1L);
    createDTO.setQuantity(5);
    createDTO.setDescription("Removing stock");

    InventoryMovementResponseDTO responseDTO =
        inventoryMovementService.registerProductExit(createDTO);

    verify(productRepository).save(product);
    verify(inventoryMovementRepository).save(any(InventoryMovement.class));

    assertEquals(5, product.getStock());
    assertEquals(createDTO.getQuantity(), responseDTO.getQuantity());
    assertEquals(MovementTypeEnum.OUT, responseDTO.getMovementType());
  }

  @Test
  public void testRegisterProductExit_InsufficientStock() {
    Product product = new Product();
    product.setId(1L);
    product.setStock(4);

    when(productRepository.findById(1L)).thenReturn(Optional.of(product));

    InventoryMovementCreateDTO createDTO = new InventoryMovementCreateDTO();
    createDTO.setProductId(1L);
    createDTO.setQuantity(5);

    ResponseStatusException exception =
        assertThrows(
            ResponseStatusException.class,
            () -> inventoryMovementService.registerProductExit(createDTO));

    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, exception.getStatusCode());
    assertEquals("Insufficient stock", exception.getReason());
  }

  @Test
  public void testGetInventoryMovements() {
    InventoryMovement movement = new InventoryMovement();
    movement.setId(1L);
    movement.setQuantity(5);
    movement.setMovementType(MovementTypeEnum.IN);

    when(inventoryMovementRepository.findAll()).thenReturn(Collections.singletonList(movement));

    List<InventoryMovementResponseDTO> movements = inventoryMovementService.getInventoryMovements();

    assertEquals(1, movements.size());
    assertEquals(5, movements.get(0).getQuantity());
    assertEquals(MovementTypeEnum.IN, movements.get(0).getMovementType());
  }

  @Test
  public void testGetInventoryMovementById_Success() {
    InventoryMovement movement = new InventoryMovement();
    movement.setId(1L);
    movement.setQuantity(5);
    movement.setMovementType(MovementTypeEnum.IN);

    when(inventoryMovementRepository.findById(1L)).thenReturn(Optional.of(movement));

    InventoryMovementResponseDTO responseDTO =
        inventoryMovementService.getInventoryMovementById(1L);

    assertEquals(5, responseDTO.getQuantity());
    assertEquals(MovementTypeEnum.IN, responseDTO.getMovementType());
  }

  @Test
  public void testGetInventoryMovementById_NotFound() {
    when(inventoryMovementRepository.findById(1L)).thenReturn(Optional.empty());

    ResponseStatusException exception =
        assertThrows(
            ResponseStatusException.class,
            () -> inventoryMovementService.getInventoryMovementById(1L));

    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    assertEquals("Inventory movement not found", exception.getReason());
  }
}
