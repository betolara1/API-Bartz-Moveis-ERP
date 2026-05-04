package bartzmoveis.apigetitem.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import bartzmoveis.apigetitem.service.ItemService;
import bartzmoveis.apigetitem.dto.ItemDTO;

@WebMvcTest(ItemController.class)
@AutoConfigureMockMvc(addFilters = false) // Desabilita o Spring Security
public class BartzErpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ItemService service;

    private ItemDTO mockItem;

    @BeforeEach
    void setUp() {
        mockItem = new ItemDTO("10.01", "Armario", "REF123");
    }

    @Test
    void listAll_ShouldReturnItems() throws Exception {
        when(service.listAll()).thenReturn(Arrays.asList(mockItem));

        mockMvc.perform(get("/itens"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].codeItem", is("10.01")));
    }

    @Test
    void listAll_WhenEmpty_ShouldReturn204() throws Exception {
        when(service.listAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/itens"))
                .andExpect(status().isNoContent());
    }

    @Test
    void searchByCode_WhenExists_ShouldReturn200() throws Exception {
        when(service.findByCode("10.01")).thenReturn(Arrays.asList(mockItem));

        mockMvc.perform(get("/itens/search").param("codigo", "10.01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].codeItem", is("10.01")));
    }

    @Test
    void searchByCode_WhenNotExists_ShouldReturn204() throws Exception {
        when(service.findByCode("99.99")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/itens/search").param("codigo", "99.99"))
                .andExpect(status().isNoContent());
    }

    @Test
    void searchByDescription_WhenExists_ShouldReturn200() throws Exception {
        when(service.findByDescription("Armario")).thenReturn(Arrays.asList(mockItem));

        mockMvc.perform(get("/itens/search").param("descricao", "Armario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].description", is("Armario")));
    }
}
