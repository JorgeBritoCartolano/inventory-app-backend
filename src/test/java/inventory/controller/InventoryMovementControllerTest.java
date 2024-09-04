package inventory.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import inventory.dto.InventoryMovementCreateDTO;
import inventory.dto.InventoryMovementResponseDTO;
import inventory.service.InventoryMovementService;
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

class InventoryMovementControllerTest {

  private MockMvc mockMvc;

  @Mock private InventoryMovementService inventoryMovementService;

  @InjectMocks private InventoryMovementController inventoryMovementController;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    this.mockMvc = MockMvcBuilders.standaloneSetup(inventoryMovementController).build();
  }

  @Test
  public void testRegisterEntry() throws Exception {
    InventoryMovementResponseDTO responseDTO = new InventoryMovementResponseDTO();
    InventoryMovementCreateDTO createDTO = new InventoryMovementCreateDTO();

    when(inventoryMovementService.registerProductEntry(any(InventoryMovementCreateDTO.class)))
        .thenReturn(responseDTO);

    mockMvc
        .perform(
            post("/inventory-movements/entry")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createDTO)))
        .andExpect(status().isCreated())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(responseDTO)));
  }

  @Test
  public void testRegisterExit() throws Exception {
    InventoryMovementResponseDTO responseDTO = new InventoryMovementResponseDTO();

    when(inventoryMovementService.registerProductExit(any(InventoryMovementCreateDTO.class)))
        .thenReturn(responseDTO);

    InventoryMovementCreateDTO createDTO = new InventoryMovementCreateDTO();

    mockMvc
        .perform(
            post("/inventory-movements/exit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createDTO)))
        .andExpect(status().isCreated())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(responseDTO)));
  }

  @Test
  public void testGetAllMovements() throws Exception {
    InventoryMovementResponseDTO responseDTO = new InventoryMovementResponseDTO();
    List<InventoryMovementResponseDTO> movements = Collections.singletonList(responseDTO);
    when(inventoryMovementService.getInventoryMovements()).thenReturn(movements);

    mockMvc
        .perform(get("/inventory-movements").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(movements)));
  }

  @Test
  public void testGetMovementById() throws Exception {
    InventoryMovementResponseDTO responseDTO = new InventoryMovementResponseDTO();

    when(inventoryMovementService.getInventoryMovementById(1L)).thenReturn(responseDTO);

    mockMvc
        .perform(get("/inventory-movements/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(new ObjectMapper().writeValueAsString(responseDTO)));
  }
}
