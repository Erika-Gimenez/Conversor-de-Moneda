package com.aluracursos.conversordemoneda.cliente;

import com.aluracursos.conversordemoneda.excepciones.ErrorEnPeticionNullEspacioEnBlancoException;
import com.aluracursos.conversordemoneda.molde.Moneda;
import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class TipoDeCambioApi{

    public Moneda peticionTasaDeCambio (){
        URI direccion = URI.create("https://openexchangerates.org/api/latest.json?app_id=967642f2fdf6418b9f6061b0e4eb82af");

        HttpClient client= HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direccion)
                .build();

        try {
            HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

            String body = response.body();
            if (body == null || body.isBlank()){
                throw new ErrorEnPeticionNullEspacioEnBlancoException("El cuerpo de la respuesta está vacío o contiene datos nulos.");
            }

            Moneda moneda = new Gson().fromJson(body, Moneda.class);
            if (moneda.base() == null || moneda.rates() == null || moneda.base().isBlank() || moneda.rates().isEmpty()){
                throw new ErrorEnPeticionNullEspacioEnBlancoException("La base" + moneda.base() + "o tasa de cambio esta vacia o contiene datos nulos");
            }

            return moneda;

        }catch (ErrorEnPeticionNullEspacioEnBlancoException e){
            throw e;
        }catch (Exception e){
            throw new RuntimeException("Error inesperado al realizar la solicitud: ");
        }


    }

}
