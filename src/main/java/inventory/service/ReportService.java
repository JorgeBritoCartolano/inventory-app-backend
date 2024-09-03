package inventory.service;

import java.io.File;
import java.io.IOException;

public interface ReportService {

    File generateInventoryReport() throws IOException;
    File generateTransactionHistoryReport() throws IOException;
}
