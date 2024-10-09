package com.aluracursos.conversordemoneda.cliente;

import com.aluracursos.conversordemoneda.molde.Moneda;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Serializacion {

    public void guardarJson(List<Map<Moneda, String>> historialDeConversion) throws IOException {

        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        FileWriter escritura = new FileWriter("tasa de cambio.json");
        escritura.write(gson.toJson(historialDeConversion));
        escritura.close();
    }

}
