package inventory.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import inventory.model.Product;
import inventory.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ProductServiceImplTest {
/*
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenCreateProduct_shouldCreateProductSuccessfully() {
        Product product = new Product();
        when(productRepository.save(product)).thenReturn(product);

        Product createdProduct = productService.createProduct(product);
        assertNotNull(createdProduct);
        verify(productRepository).save(product);
    }

    @Test
    public void whenProductExists_shouldUpdateProductSuccessfully() {
        Long id = 1L;
        Product existingProduct = new Product();
        existingProduct.setId(id);
        when(productRepository.existsById(id)).thenReturn(true);
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);

        Product updatedProduct = productService.updateProduct(id, existingProduct);
        assertNotNull(updatedProduct);
        assertEquals(id, updatedProduct.getId());
        verify(productRepository).save(existingProduct);
    }

    @Test
    public void whenProductDoesNotExist_shouldThrowNotFoundException() {
        Long id = 1L;
        Product productToUpdate = new Product();
        productToUpdate.setId(id);

        when(productRepository.existsById(id)).thenReturn(false);

        assertThrows(ResponseStatusException.class,
                () -> productService.updateProduct(id, productToUpdate), "Expected ResponseStatusException to be thrown");

        verify(productRepository, never()).save(productToUpdate);
    }

    @Test
    public void whenDeleteProductExists_shouldDeleteProductSuccessfully() {
        Long id = 1L;
        when(productRepository.existsById(id)).thenReturn(true);

        productService.deleteProduct(id);
        verify(productRepository).deleteById(id);
    }

    @Test
    public void whenDeleteProductDoesNotExists_shouldThrowNotFoundException() {
        Long id = 1L;

        when(productRepository.existsById(id)).thenReturn(false);

        assertThrows(ResponseStatusException.class,
                () ->
            productService.deleteProduct(id), "Expected ResponseStatusException to be thrown");

        verify(productRepository, never()).deleteById(id);
    }

    @Test
    public void whenGetAllProducts_shouldReturnAllProducts() {
        Product product = new Product();
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<Product> products = productService.getAllProducts();
        assertNotNull(products);
        assertFalse(products.isEmpty());
    }

    @Test
    public void testGetProductById() {
        Long id = 1L;
        Product product = new Product();
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProductById(id);
        assertNotNull(foundProduct);
    }

    @Test
    public void whenGetProductByIdDoesNotExist_shouldThrowNotFoundException() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () ->
            productService.getProductById(anyLong()), "Expected ResponseStatusException to be thrown");
    }

 */
}

