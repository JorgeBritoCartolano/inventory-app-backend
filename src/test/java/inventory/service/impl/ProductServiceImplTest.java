package inventory.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import inventory.dto.ProductCreateDTO;
import inventory.dto.ProductResponseDTO;
import inventory.model.Product;
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

public class ProductServiceImplTest {

  @Mock private ProductRepository productRepository;

  @InjectMocks private ProductServiceImpl productService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testCreateProduct_Success() {
    ProductCreateDTO createDTO = new ProductCreateDTO();
    createDTO.setPrice(10.0);
    createDTO.setStock(100);

    Product mockProduct = new Product();
    mockProduct.setId(1L);
    mockProduct.setPrice(10.0);
    mockProduct.setStock(100);

    when(productRepository.save(any(Product.class))).thenReturn(mockProduct);

    ProductResponseDTO responseDTO = productService.createProduct(createDTO);

    verify(productRepository).save(any(Product.class));

    assertEquals(mockProduct.getId(), responseDTO.getId());
    assertEquals(mockProduct.getPrice(), responseDTO.getPrice());
    assertEquals(mockProduct.getStock(), responseDTO.getStock());
  }

  @Test
  public void testCreateProduct_NegativePrice() {
    ProductCreateDTO createDTO = new ProductCreateDTO();
    createDTO.setPrice(-10.0);
    createDTO.setStock(100);

    ResponseStatusException exception =
        assertThrows(ResponseStatusException.class, () -> productService.createProduct(createDTO));

    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, exception.getStatusCode());
    assertEquals("Price must not be a negative number.", exception.getReason());
  }

  @Test
  public void testCreateProduct_NegativeStock() {
    ProductCreateDTO createDTO = new ProductCreateDTO();
    createDTO.setPrice(10.0);
    createDTO.setStock(-100);

    ResponseStatusException exception =
        assertThrows(ResponseStatusException.class, () -> productService.createProduct(createDTO));

    assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, exception.getStatusCode());
    assertEquals("Stock must not be a negative number.", exception.getReason());
  }

  @Test
  public void testUpdateProduct_Success() {
    ProductCreateDTO updateDTO = new ProductCreateDTO();
    updateDTO.setPrice(20.0);
    updateDTO.setStock(50);

    Product existingProduct = new Product();
    existingProduct.setId(1L);

    when(productRepository.existsById(1L)).thenReturn(true);
    when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

    ProductResponseDTO responseDTO = productService.updateProduct(1L, updateDTO);

    verify(productRepository).save(any(Product.class));

    assertEquals(existingProduct.getId(), responseDTO.getId());
    assertEquals(updateDTO.getPrice(), responseDTO.getPrice());
    assertEquals(updateDTO.getStock(), responseDTO.getStock());
  }

  @Test
  public void testUpdateProduct_NotFound() {
    ProductCreateDTO updateDTO = new ProductCreateDTO();

    when(productRepository.existsById(1L)).thenReturn(false);

    ResponseStatusException exception =
        assertThrows(
            ResponseStatusException.class, () -> productService.updateProduct(1L, updateDTO));

    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    assertEquals("Product not found", exception.getReason());
  }

  @Test
  public void testDeleteProduct_Success() {
    when(productRepository.existsById(1L)).thenReturn(true);

    productService.deleteProduct(1L);

    verify(productRepository).deleteById(1L);
  }

  @Test
  public void testDeleteProduct_NotFound() {
    when(productRepository.existsById(1L)).thenReturn(false);

    ResponseStatusException exception =
        assertThrows(ResponseStatusException.class, () -> productService.deleteProduct(1L));

    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    assertEquals("Product not found", exception.getReason());
  }

  @Test
  public void testGetAllProducts() {
    Product product = new Product();
    product.setId(1L);
    product.setPrice(10.0);
    product.setStock(100);

    when(productRepository.findAll()).thenReturn(Collections.singletonList(product));

    List<ProductResponseDTO> products = productService.getAllProducts();

    assertEquals(1, products.size());
    assertEquals(product.getId(), products.get(0).getId());
    assertEquals(product.getPrice(), products.get(0).getPrice());
    assertEquals(product.getStock(), products.get(0).getStock());
  }

  @Test
  public void testGetProductById_Success() {
    Product product = new Product();
    product.setId(1L);
    product.setPrice(10.0);
    product.setStock(100);

    when(productRepository.findById(1L)).thenReturn(Optional.of(product));

    ProductResponseDTO responseDTO = productService.getProductById(1L);

    assertEquals(product.getId(), responseDTO.getId());
    assertEquals(product.getPrice(), responseDTO.getPrice());
    assertEquals(product.getStock(), responseDTO.getStock());
  }

  @Test
  public void testGetProductById_NotFound() {
    when(productRepository.findById(1L)).thenReturn(Optional.empty());

    ResponseStatusException exception =
        assertThrows(ResponseStatusException.class, () -> productService.getProductById(1L));

    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    assertEquals("Product not found", exception.getReason());
  }
}
