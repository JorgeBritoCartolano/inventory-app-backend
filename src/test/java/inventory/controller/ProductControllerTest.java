package inventory.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import inventory.dto.ProductCreateDTO;
import inventory.dto.ProductResponseDTO;
import inventory.service.ProductService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class ProductControllerTest {

  private MockMvc mockMvc;

  @Mock private ProductService productService;

  @InjectMocks private ProductController productController;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
  }

  @Test
  public void testCreateProduct() throws Exception {
    ProductResponseDTO responseDTO = new ProductResponseDTO();
    ProductCreateDTO createDTO = new ProductCreateDTO();

    when(productService.createProduct(any(ProductCreateDTO.class))).thenReturn(responseDTO);

    mockMvc
        .perform(
            post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createDTO)))
        .andExpect(status().isCreated())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(responseDTO)));
  }

  @Test
  public void testUpdateProduct() throws Exception {
    Long id = 1L;
    ProductResponseDTO responseDTO = new ProductResponseDTO();
    ProductCreateDTO createDTO = new ProductCreateDTO();

    when(productService.updateProduct(eq(id), any(ProductCreateDTO.class))).thenReturn(responseDTO);

    mockMvc
        .perform(
            put("/products/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createDTO)))
        .andExpect(status().isOk())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(responseDTO)));
  }

  @Test
  public void testDeleteProduct() throws Exception {
    Long id = 1L;

    mockMvc.perform(delete("/products/{id}", id)).andExpect(status().isNoContent());

    verify(productService).deleteProduct(id);
  }

  @Test
  public void testGetAllProducts() throws Exception {
    ProductResponseDTO products = new ProductResponseDTO();
    List<ProductResponseDTO> responseDTO = Collections.singletonList(products);

    when(productService.getAllProducts()).thenReturn(responseDTO);

    mockMvc
        .perform(get("/products").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(responseDTO)));
  }

  @Test
  public void testGetProductById() throws Exception {
    ProductResponseDTO responseDTO = new ProductResponseDTO();

    when(productService.getProductById(1L)).thenReturn(responseDTO);

    mockMvc
        .perform(get("/products/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(responseDTO)));
  }
}
