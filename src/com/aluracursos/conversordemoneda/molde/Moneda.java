package com.aluracursos.conversordemoneda.molde;

import java.util.Map;

public record Moneda(String base, Map<String, Double> rates) {
}
