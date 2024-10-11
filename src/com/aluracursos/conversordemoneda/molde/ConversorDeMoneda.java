package com.aluracursos.conversordemoneda.molde;

import com.aluracursos.conversordemoneda.excepciones.ErrorEnMontoInvalidoException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ConversorDeMoneda {

    public BigDecimal calculoConversion(String montoOrigenStr, String montoDestinoStr){

        BigDecimal montoOrigen = new BigDecimal(montoOrigenStr);
        BigDecimal montoDestino = new BigDecimal(montoDestinoStr);
        BigDecimal monto ;

        if (montoOrigen.compareTo(BigDecimal.ZERO) <= 0){
            throw new ErrorEnMontoInvalidoException("ingrese un monto superior a 0.");
        }
        try{
          monto= montoOrigen.multiply(montoDestino).setScale(2, RoundingMode.HALF_UP);
        }catch (ArithmeticException e) {
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
