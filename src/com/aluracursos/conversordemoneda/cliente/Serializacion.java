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
        // Crear una instancia de Gson con ciertas configuraciones:
        // disableHtmlEscaping evita que Gson reemplace caracteres como '<', '>', '&' por sus equivalentes HTML
        // setPrettyPrinting formatea el JSON con saltos de línea y sangrías para que sea más fácil de leer
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

        // Crear un FileWriter para escribir en el archivo llamado "tasa de cambio.json"
        // Este archivo se creará o se sobrescribirá en caso de que ya exista
        FileWriter escritura = new FileWriter("tasa de cambio.json");

        // Convertir la lista 'historialDeConversion' (que contiene mapas de Moneda a String) a un formato JSON
        // usando Gson para escribirlo en el archivo "tasa de cambio.json"
        escritura.write(gson.toJson(historialDeConversion));

        // Cerrar el FileWriter para liberar los recursos del sistema
        escritura.close();
    }

}
