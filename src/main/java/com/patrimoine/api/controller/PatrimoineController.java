package com.patrimoine.api.controller;

import com.patrimoine.api.model.Patrimoine;
import com.patrimoine.api.service.PatrimoineService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class PatrimoineController {
    private final PatrimoineService patrimoineService;

    @GetMapping("patrimoines/{id}")
    public Patrimoine getPatrimoine(@PathVariable String id) throws IOException {
        return patrimoineService.getPatrimoine(id);
    }

    @PutMapping("patrimoines/{id}")
    public Patrimoine updateOrCreatePatrimoine(@PathVariable String id, @RequestBody Patrimoine patrimoine) throws IOException {
        return patrimoineService.saveOrUpdatePatrimoine(id, patrimoine);
    }

}
