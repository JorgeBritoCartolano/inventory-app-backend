package inventory.controller;

import inventory.service.ReportService;
import inventory.service.S3Service;
import java.io.File;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportsController {

  @Autowired private ReportService reportService;

  @Autowired private S3Service s3Service;

  @GetMapping("/inventory")
  public ResponseEntity<String> generateAndUploadInventoryReport() {
    try {
      File report = reportService.generateInventoryReport();
      s3Service.uploadFile(report, "inventory-backend-bucket", report.getName());
      return ResponseEntity.status(HttpStatus.CREATED)
          .body("Report uploaded to S3 with key: " + report.getName());
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Failed to generate or upload report");
    }
  }

  @GetMapping("/transactions")
  public ResponseEntity<String> generateAndUploadTransactionHistoryReport() {
    try {
      File report = reportService.generateTransactionHistoryReport();
      s3Service.uploadFile(report, "inventory-backend-bucket", report.getName());
      return ResponseEntity.status(HttpStatus.CREATED)
          .body("Report uploaded to S3 with key: " + report.getName());
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Failed to generate or upload report");
    }
  }
}
