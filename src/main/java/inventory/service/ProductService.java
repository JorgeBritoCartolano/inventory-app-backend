package inventory.service;

import inventory.dto.ProductCreateDTO;
import inventory.dto.ProductResponseDTO;
import inventory.model.Product;

import java.util.List;

public interface ProductService {
    ProductResponseDTO createProduct(ProductCreateDTO productCreateDTO);
    ProductResponseDTO updateProduct(Long id, ProductCreateDTO productCreateDTO);
    void deleteProduct(Long id);
    List<ProductResponseDTO> getAllProducts();
    ProductResponseDTO getProductById(Long id);
}
