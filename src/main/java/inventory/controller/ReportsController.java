package inventory.controller;

import inventory.responses.ControllerResponses;
import inventory.service.ReportService;
import inventory.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.File;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Reports Management", description = "Endpoints for generating and uploading reports")
@RequestMapping("/reports")
public class ReportsController {

  @Autowired private ReportService reportService;

  @Autowired private S3Service s3Service;

  @Operation(
      summary = "Generate and upload inventory report",
      description = "Generates an inventory report and uploads it to an S3 bucket.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Created",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(value = ControllerResponses.INVENTORY_REPORTS))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(value = ControllerResponses.UPLOAD_ERROR)))
      })
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

  @Operation(
      summary = "Generate and upload transaction history report",
      description = "Generates a transaction history report and uploads it to an S3 bucket.")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Created",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(value = ControllerResponses.TRANSACTIONS_REPORTS))),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(value = ControllerResponses.UPLOAD_ERROR)))
      })
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
