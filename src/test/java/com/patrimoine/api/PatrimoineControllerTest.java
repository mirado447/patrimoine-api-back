package com.patrimoine.api;

import com.patrimoine.api.controller.PatrimoineController;
import com.patrimoine.api.model.Patrimoine;
import com.patrimoine.api.service.PatrimoineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class PatrimoineControllerTest {

    @Mock
    private PatrimoineService patrimoineService;

    @InjectMocks
    private PatrimoineController patrimoineController;

    private Patrimoine patrimoine1;
    private Patrimoine patrimoine2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patrimoine1 = new Patrimoine("id_1", "possessor1", LocalDateTime.now());
        patrimoine2 = new Patrimoine("id_2", "possessor2", LocalDateTime.of(2023, 8, 15, 10, 30));
    }

    @Test
    void get_patrimoine_by_id_ok() throws IOException {
        when(patrimoineService.getPatrimoine("id_1")).thenReturn(patrimoine1);

        Patrimoine result = patrimoineController.getPatrimoine("id_1");

        assertEquals("possessor1", result.getPossessor());
        assertEquals(patrimoine1, result);
    }

    @Test
    void get_patrimoine_by_id_ko() throws IOException {
        when(patrimoineService.getPatrimoine("not_existing_id")).thenThrow(new IOException("Patrimoine non trouvé"));

        Exception exception = assertThrows(IOException.class, () -> {
            patrimoineController.getPatrimoine("not_existing_id");
        });

        assertEquals("Patrimoine non trouvé", exception.getMessage());
    }

    @Test
    void create_or_update_patrimoine_ok() throws IOException {
        when(patrimoineService.getPatrimoine("id_2")).thenReturn(patrimoine2);

        Patrimoine updatedPatrimoine2 = new Patrimoine("id_2", "possessor2_updated", LocalDateTime.now());
        when(patrimoineService.saveOrUpdatePatrimoine("id_2", updatedPatrimoine2)).thenReturn(updatedPatrimoine2);

        Patrimoine updated = patrimoineController.updateOrCreatePatrimoine("id_2", updatedPatrimoine2);

        assertEquals("possessor2_updated", updated.getPossessor());


        when(patrimoineService.getPatrimoine("id_3")).thenReturn(null);

        Patrimoine newPatrimoine = new Patrimoine("id_3", "possessor3", LocalDateTime.now());
        when(patrimoineService.saveOrUpdatePatrimoine("id_3", newPatrimoine)).thenReturn(newPatrimoine);

        Patrimoine created = patrimoineController.updateOrCreatePatrimoine("id_3", newPatrimoine);

        assertEquals("id_3", created.getId());
        assertEquals("possessor3", created.getPossessor());
    }
}
