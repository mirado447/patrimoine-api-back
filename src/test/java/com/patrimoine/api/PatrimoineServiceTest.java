package com.patrimoine.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patrimoine.api.model.Patrimoine;
import com.patrimoine.api.service.PatrimoineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatrimoineServiceTest {

    private PatrimoineService patrimoineService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = Mockito.mock(ObjectMapper.class);
        patrimoineService = new PatrimoineService(objectMapper);
    }

    @Test
    void test_get_patrimoine_found() throws IOException {
        String id = "id_1";
        Patrimoine expectedPatrimoine = new Patrimoine(id, "John Doe", LocalDateTime.now());
        String json = "{\"possessor\":\"John Doe\",\"lastUpdate\":\"2024-09-23T12:00:00\"}";

        when(objectMapper.readValue(json, Patrimoine.class)).thenReturn(expectedPatrimoine);
        Path filePath = Path.of("data/patrimoines/" + id + ".json");
        Files.createDirectories(filePath.getParent());
        Files.writeString(filePath, json);

        Patrimoine patrimoine = patrimoineService.getPatrimoine(id);

        assertEquals(expectedPatrimoine.getPossessor(), patrimoine.getPossessor());
        Files.delete(filePath);
    }

    @Test
    void test_get_patrimoine_not_found() {
        String id = "non_existent_id";

        assertThrows(IOException.class, () -> patrimoineService.getPatrimoine(id));
    }

    @Test
    void test_save_or_update_patrimoine() throws IOException {
        String id = "id_2";
        Patrimoine patrimoine = new Patrimoine(id, "Jane Doe", LocalDateTime.now());
        String json = "{\"possessor\":\"Jane Doe\",\"lastUpdate\":\"2024-09-23T12:00:00\"}";

        when(objectMapper.writeValueAsString(patrimoine)).thenReturn(json);

        Patrimoine result = patrimoineService.saveOrUpdatePatrimoine(id, patrimoine);

        assertEquals(id, result.getId());
        assertEquals("Jane Doe", result.getPossessor());
    }
}
