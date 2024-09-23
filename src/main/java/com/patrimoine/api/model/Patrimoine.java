package com.patrimoine.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class Patrimoine {
    private String id;
    private String possessor;
    private LocalDateTime lastUpdate;
}
