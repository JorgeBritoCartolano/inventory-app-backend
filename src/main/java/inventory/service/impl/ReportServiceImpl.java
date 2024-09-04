package inventory.service.impl;

import inventory.model.InventoryMovement;
import inventory.model.Product;
import inventory.repository.InventoryMovementRepository;
import inventory.repository.ProductRepository;
import inventory.service.ReportService;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

  @Autowired private ProductRepository productRepository;

  @Autowired private InventoryMovementRepository inventoryMovementRepository;

  public File generateInventoryReport() throws IOException {
    List<Product> products = productRepository.findAll();

    File reportFile =
        File.createTempFile(generateUniqueFileName("inventory/inventory-report"), ".csv");

    try (FileWriter writer = new FileWriter(reportFile)) {
      writer.write("Product ID, Name, Stock, Category, Price\n");
      for (Product product : products) {
        writer.write(
            String.format(
                Locale.US,
                "%d, %s, %d, %s, %.2f\n",
                product.getId(),
                product.getName(),
                product.getStock(),
                product.getCategory(),
                product.getPrice()));
      }
    }
    return reportFile;
  }

  public File generateTransactionHistoryReport() throws IOException {
    List<InventoryMovement> movements = inventoryMovementRepository.findAll();

    File reportFile =
        File.createTempFile(generateUniqueFileName("transactions/transaction-history"), ".csv");

    try (FileWriter writer = new FileWriter(reportFile)) {
      writer.write("Movement ID, Product ID, Quantity, Date\n");
      for (InventoryMovement movement : movements) {
        writer.write(
            String.format(
                "%d, %d, %d, %s\n",
                movement.getId(),
                movement.getProduct().getId(),
                movement.getQuantity(),
                movement.getCreatedAt().toString()));
      }
    }
    return reportFile;
  }

  private String generateUniqueFileName(String originalFileName) {
    String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

    int dotIndex = originalFileName.lastIndexOf('.');
    if (dotIndex > 0) {
      originalFileName = originalFileName.substring(0, dotIndex);
    }

    return originalFileName + "_" + timestamp;
  }
}
