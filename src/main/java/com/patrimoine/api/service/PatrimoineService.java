package com.patrimoine.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patrimoine.api.model.Patrimoine;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

@Service
public class PatrimoineService {
    private static final String DATA_DIRECTORY = "data/patrimoines/";
    private final ObjectMapper objectMapper;

    public PatrimoineService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    public Patrimoine getPatrimoine(String id) throws IOException {
        Path filePath = Path.of(DATA_DIRECTORY + id + ".json");
        if (Files.exists(filePath)) {
            String json = Files.readString(filePath);
            return objectMapper.readValue(json, Patrimoine.class);
        } else {
            throw new IOException("Patrimoine non trouvé");
        }
    }

    public Patrimoine saveOrUpdatePatrimoine(String id, Patrimoine patrimoine) throws IOException {
        patrimoine.setId(id);
        patrimoine.setLastUpdate(LocalDateTime.now());
        String json = objectMapper.writeValueAsString(patrimoine);

        Path filePath = Path.of(DATA_DIRECTORY + id + ".json");
        Files.createDirectories(filePath.getParent());
        Files.writeString(filePath, json);

        return patrimoine;
    }
}
