package inventory.responses;

public class ControllerResponses {

  public static final String INVENTORY_MOVEMENT_IN_RESPONSE =
      """
                            {
                                "id": 1,
                                "productId": 1,
                                "movementType": "IN",
                                "quantity": 50,
                                "createdAt": "2024-09-04T17:10:01.281354",
                                "description": "Restocked wireless mouse."
                            }
                         """;

  public static final String INVENTORY_MOVEMENT_OUT_RESPONSE =
      """
                               {
                                   "id": 2,
                                   "productId": 2,
                                   "movementType": "OUT",
                                   "quantity": 10,
                                   "createdAt": "2024-09-04T17:12:11.724496",
                                   "description": "Sold noise-cancelling headphones."
                               }
                         """;

  public static final String INVENTORY_MOVEMENT_LIST_RESPONSE =
      """
                           [
                               {
                                   "id": 1,
                                   "productId": 1,
                                   "movementType": "IN",
                                   "quantity": 50,
                                   "createdAt": "2024-09-04T17:10:01.281354",
                                   "description": "Restocked wireless mouse."
                               },
                               {
                                   "id": 2,
                                   "productId": 2,
                                   "movementType": "OUT",
                                   "quantity": 10,
                                   "createdAt": "2024-09-04T17:12:11.724496",
                                   "description": "Sold noise-cancelling headphones."
                               }
                           ]
                         """;

  public static final String PRODUCT_NOT_FOUND =
      """
                           {
                               "status": 404,
                               "message": "Product not found"
                           }
                         """;

  public static final String INSUFFICIENT_STOCK =
      """
                           {
                               "status": 422,
                               "message": "Insufficient stock"
                           }
                         """;

  public static final String NEGATIVE_QUANTITY =
      """
                        {
                            "status": 422,
                            "message": "Quantity must not be a negative number."
                        }
                         """;

  public static final String INVENTORY_MOVEMENT_NOT_FOUND =
      """
                           {
                               "status": 404,
                               "message": "Inventory movement not found"
                           }
                         """;

  public static final String PRODUCT_RESPONSE =
      """
                               {
                                       "id": 1,
                                       "name": "Wireless Mouse",
                                       "description": "A comfortable wireless mouse with ergonomic design.",
                                       "price": 25.99,
                                       "stock": 200,
                                       "category": "Electronics"
                               }
                             """;

  public static final String NEGATIVE_STOCK =
      """
                            {
                                "status": 422,
                                "message": "Stock must not be a negative number."
                            }
                             """;

  public static final String NEGATIVE_PRICE =
      """
                            {
                                "status": 422,
                                "message": "Price must not be a negative number."
                            }
                             """;

  public static final String PRODUCT_LIST =
      """
                            [
                                {
                                    "id": 1,
                                    "name": "Wireless Mouse",
                                    "description": "A comfortable wireless mouse with ergonomic design.",
                                    "price": 25.99,
                                    "stock": 200,
                                    "category": "Electronics"
                                },
                                {
                                    "id": 2,
                                    "name": "Noise-Cancelling Headphones",
                                    "description": "High-quality headphones with active noise cancellation.",
                                    "price": 199.99,
                                    "stock": 55,
                                    "category": "Audio"
                                }
                            ]
                             """;

  public static final String INVENTORY_REPORTS =
      "Report uploaded to S3 with key: inventory-report_202409051141000181074799622799207212.csv";

  public static final String TRANSACTIONS_REPORTS =
      "Report uploaded to S3 with key: transaction-history_2024090511442247511955863701256380429.csv";

  public static final String UPLOAD_ERROR = "Failed to generate or upload report";
}
