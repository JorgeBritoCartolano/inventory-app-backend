package inventory.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import inventory.dto.MovementTypeEnum;
import inventory.model.InventoryMovement;
import inventory.model.Product;
import inventory.repository.InventoryMovementRepository;
import inventory.repository.ProductRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ReportServiceImplTest {

  @Mock private ProductRepository productRepository;

  @Mock private InventoryMovementRepository inventoryMovementRepository;

  @InjectMocks private ReportServiceImpl reportService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testGenerateInventoryReport_Success() throws IOException {
    Product product = new Product();
    product.setId(1L);
    product.setName("Product A");
    product.setStock(100);
    product.setCategory("Category A");
    product.setPrice(10.0);

    when(productRepository.findAll()).thenReturn(Collections.singletonList(product));

    File report = reportService.generateInventoryReport();

    assertTrue(report.exists());
    List<String> lines = Files.readAllLines(report.toPath());

    assertEquals(2, lines.size()); // header + 1 product
    assertEquals("Product ID, Name, Stock, Category, Price", lines.get(0));
    assertEquals("1, Product A, 100, Category A, 10.00", lines.get(1));

    // Cleanup
    report.delete();
  }

  @Test
  public void testGenerateTransactionHistoryReport_Success() throws IOException {
    Product product = new Product();
    product.setId(1L);
    InventoryMovement movement = new InventoryMovement();
    movement.setId(1L);
    movement.setProduct(product);
    movement.setMovementType(MovementTypeEnum.IN);
    movement.setQuantity(10);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
    movement.setCreatedAt(LocalDateTime.parse("2024-09-03 11:59:06.667175", formatter));

    when(inventoryMovementRepository.findAll()).thenReturn(Collections.singletonList(movement));

    File report = reportService.generateTransactionHistoryReport();

    assertTrue(report.exists());
    List<String> lines = Files.readAllLines(report.toPath());

    assertEquals(2, lines.size()); // header + 1 movement
    assertEquals("Movement ID, Product ID, Movement Type, Quantity, Date", lines.get(0));
    assertEquals("1, 1, IN, 10, 2024-09-03T11:59:06.667175", lines.get(1));

    // Cleanup
    report.delete();
  }
}
