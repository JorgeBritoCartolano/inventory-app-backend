package inventory.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import inventory.service.ReportService;
import inventory.service.S3Service;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class ReportsControllerTest {

  private MockMvc mockMvc;

  @Mock private S3Service s3Service;

  @Mock private ReportService reportService;

  @InjectMocks private ReportsController reportsController;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(reportsController).build();
  }

  @Test
  public void testGenerateAndUploadInventoryReport_Success() throws Exception {
    File mockFile = new File("inventory-report.csv");
    when(reportService.generateInventoryReport()).thenReturn(mockFile);

    mockMvc
        .perform(get("/reports/inventory").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(content().string("Report uploaded to S3 with key: " + mockFile.getName()));

    verify(s3Service)
        .uploadFile(eq(mockFile), eq("inventory-backend-bucket"), eq(mockFile.getName()));
  }

  @Test
  public void testGenerateAndUploadInventoryReport_Failure() throws Exception {
    when(reportService.generateInventoryReport())
        .thenThrow(new IOException("Failed to generate report"));

    mockMvc
        .perform(get("/reports/inventory").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError())
        .andExpect(content().string("Failed to generate or upload report"));
  }

  @Test
  public void testGenerateAndUploadTransactionHistoryReport_Success() throws Exception {
    File mockFile = new File("transaction-history-report.pdf");
    when(reportService.generateTransactionHistoryReport()).thenReturn(mockFile);

    mockMvc
        .perform(get("/reports/transactions").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(content().string("Report uploaded to S3 with key: " + mockFile.getName()));

    verify(s3Service)
        .uploadFile(eq(mockFile), eq("inventory-backend-bucket"), eq(mockFile.getName()));
  }

  @Test
  public void testGenerateAndUploadTransactionHistoryReport_Failure() throws Exception {
    when(reportService.generateTransactionHistoryReport())
        .thenThrow(new IOException("Failed to generate report"));

    mockMvc
        .perform(get("/reports/transactions").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError())
        .andExpect(content().string("Failed to generate or upload report"));
  }
}
