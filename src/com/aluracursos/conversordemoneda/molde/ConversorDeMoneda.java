package com.aluracursos.conversordemoneda.molde;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ConversorDeMoneda {

    public BigDecimal calculoConvercion(String montoOrigenStr, String montoDestinoStr){

        BigDecimal montoOrigen = new BigDecimal(montoOrigenStr);
        BigDecimal montoDestino = new BigDecimal(montoDestinoStr);

        if (montoOrigen.compareTo(BigDecimal.ZERO) < 0){
          System.out.println("Ingrese un monto superior a 0");
        }

            return montoOrigen.multiply(montoDestino).setScale(2 ,RoundingMode.HALF_UP);
    }

    public BigDecimal calculoConvercionInversa(String montoOrigenStr, String montoDestinoStr){

        BigDecimal montoOrigen = new BigDecimal(montoOrigenStr);
        BigDecimal montoDestino = new BigDecimal(montoDestinoStr);

        if (montoOrigen.compareTo(BigDecimal.ZERO) < 0){
            System.out.println("Ingrese un monto superior a 0");
        }

        return montoOrigen.divide(montoDestino ,2 ,RoundingMode.HALF_UP);
    }

}
