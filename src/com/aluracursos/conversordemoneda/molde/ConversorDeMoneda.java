package com.aluracursos.conversordemoneda.molde;

import com.aluracursos.conversordemoneda.excepciones.ErrorEnMontoInvalidoException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ConversorDeMoneda {

    public BigDecimal calculoConversion(String montoOrigenStr, String montoDestinoStr){

        // Convierte el string de 'montoOrigenStr' y 'montoDestioStr' a un objeto BigDecimal para garantizar una precisión alta en las operaciones decimales financieras.
        BigDecimal montoOrigen = new BigDecimal(montoOrigenStr);
        BigDecimal montoDestino = new BigDecimal(montoDestinoStr);
        // Declara una variable BigDecimal para almacenar el monto de la operacion
        BigDecimal monto ;

        // Verifica si el monto de origen es menor o igual a cero
        if (montoOrigen.compareTo(BigDecimal.ZERO) <= 0){
            // Si el monto es menor o igual a 0 lanza una excepcion
            throw new ErrorEnMontoInvalidoException("ingrese un monto superior a 0.");
        }
        try{
            // Realiza la conversion multiplicando el monto de origen con el monto de destino
            // modifica el resultado a 2 decimales utilizando RoundingMode.HALF_UP para redondear de forma estándar
          monto= montoOrigen.multiply(montoDestino).setScale(2, RoundingMode.HALF_UP);

        }catch (ArithmeticException e) {

            // Si ocurre algun error en la operacion lanza una excepciom
            // y muestra el mensaje del error aritmetico
            throw new ErrorEnMontoInvalidoException("Error en la conversion: " + e.getMessage());
        }
        return monto;
    }

    public BigDecimal calculoConversionInversa(String montoOrigenStr, String montoDestinoStr){

        BigDecimal montoOrigen = new BigDecimal(montoOrigenStr);
        BigDecimal montoDestino = new BigDecimal(montoDestinoStr);
        BigDecimal monto ;

        if (montoOrigen.compareTo(BigDecimal.ZERO) <= 0){
            throw new ErrorEnMontoInvalidoException("ingrese un monto superior a 0.");
        }

        try {
            // Realiza la conversion dividiendo el monto de origen con el monto de destino
            // modifica el resultado a 2 decimales utilizando RoundingMode.HALF_UP para redondear 2 digitos despues de la coma
            monto = montoOrigen.divide(montoDestino, 2, RoundingMode.HALF_UP);

            if (monto.compareTo(BigDecimal.ZERO) == 0){
                throw new ErrorEnMontoInvalidoException("la conversion dio como resultado= 0 u 0.0, porfavor ingrese un monto mayor.");
            }

        }catch (ArithmeticException e){
            throw new ErrorEnMontoInvalidoException("Error en la conversion: " + e.getMessage());
        }
        return monto;
    }

}
