package com.aluracursos.conversordemoneda.molde;

import com.aluracursos.conversordemoneda.cliente.Serializacion;
import com.aluracursos.conversordemoneda.cliente.TipoDeCambioApi;

import java.math.BigDecimal;
import java.nio.channels.ScatteringByteChannel;
import java.util.*;

public class Presentacion {


    private final TipoDeCambioApi tipoDeCambio;
    private final Moneda moneda;
    private final Scanner leer;
    private final ConversorDeMoneda conversorDeMoneda;
    List<Map<Moneda, String>> historialDeConversion;

    public Presentacion() {
        this.leer = new Scanner(System.in);
        this.tipoDeCambio = new TipoDeCambioApi();
        this.moneda = tipoDeCambio.peticionTasaDeCambio();
        this.conversorDeMoneda = new ConversorDeMoneda();
        this.historialDeConversion = new ArrayList<>();
    }
   public void bievenida(){
        String bienvenida = """
                *****************************************************
                                 CONVERSOR DE MONEDA
                *****************************************************
                """;
       System.out.print(bienvenida);
   }

    public void menu(){

        String menu = """
                
                1) DÓLAR ===> PESO ARGENTINO
                2) PESO ARGENTINO ===> DÓLAR
                3) DÓLAR ===> REAL BRASILEÑO
                4) REAL BRASILEÑO ===> DÓLAR
                5) DÓLAR ===> PESO COLOMBIANO
                6) PESO COLOMBIANO ===> DÓLAR
                7) DÓLAR ===> SOL PERUANO
                8) SOL PERUANO ===> DÓLAR
                9) Historial de conversion
                10) SALIR
                """;
        System.out.println(menu);

    }

   public String mostrarResultadoDeLaConvercion(String montoOrigenStr, String montoDestinoStr, BigDecimal monto, boolean esInversa){

        String mensaje;
        String llaveOrigen = "";
        String llaveDestino = "";
        double valorUsd = 1;
        double valorDestino = Double.parseDouble(montoDestinoStr);

        for (Map.Entry<String, Double> entry : moneda.rates().entrySet()) {

            if (entry.getValue() == valorDestino) {
                llaveOrigen = entry.getKey();

            }
            if (entry.getValue() == valorUsd) {
                llaveDestino = entry.getKey();

            }
        }

        if (esInversa){
            mensaje = montoOrigenStr + " " + llaveOrigen + " ===> " + monto + " " + llaveDestino;
            }else {
            mensaje = montoOrigenStr + " " + llaveDestino + " ===> " + monto + " " + llaveOrigen;
        }

        return mensaje;
   }

   public void manejoDeCasesConversionAndConversionInversa(String monedaOrigenStr, boolean esInversa){

       BigDecimal monto;
       String resultado;
       String montoDestinoStr;
       String montoOrigenStr;

       try{
       System.out.println("Ingrese el monto que desea convertir");
       montoOrigenStr = leer.nextLine();
       double rate = moneda.rates().get(monedaOrigenStr); // estoy pidiendo el valor por medio de la llave en este caso ARS y lo guardo en un double
       montoDestinoStr = String.valueOf(rate); // llamo a rate y luego lo convierto en un String
       monto = esInversa ? conversorDeMoneda.calculoConvercionInversa(montoOrigenStr, montoDestinoStr):conversorDeMoneda.calculoConvercion(montoOrigenStr, montoDestinoStr);
       resultado = mostrarResultadoDeLaConvercion(montoOrigenStr, montoDestinoStr, monto,esInversa);
       System.out.println(resultado);
       Moneda monedaGuardaTasaDeCambio = new Moneda(moneda.base(),Map.of(monedaOrigenStr, rate));
       Map<Moneda, String> conversion = new HashMap<>();
       conversion.put(monedaGuardaTasaDeCambio, resultado);
       historialDeConversion.add(conversion);
       Serializacion generador = new Serializacion();
       generador.guardarJson(historialDeConversion);

       } catch (Exception e) {
           throw new RuntimeException(e);
       }


   }

   public void mostrarHistorial(){
       for (Map<Moneda, String> conversion : historialDeConversion) {
           for (Map.Entry<Moneda, String> entry : conversion.entrySet()) {
               System.out.println("Conversión: " + entry.getValue());
           }
       }
   }

   public void logica(){

        int opc;
        bievenida();

        while (true) {
            menu();
            System.out.print("Elige una opción válida: ");
            opc = Integer.parseInt(leer.nextLine());

            switch (opc) {
                case 1:
                    manejoDeCasesConversionAndConversionInversa( "ARS", false);

                    break;
                case 2:
                    manejoDeCasesConversionAndConversionInversa( "ARS", true);

                    break;
                case 3:
                    manejoDeCasesConversionAndConversionInversa( "BRL", false);

                    break;
                case 4:
                    manejoDeCasesConversionAndConversionInversa( "BRL", true);

                    break;
                case 5:
                    manejoDeCasesConversionAndConversionInversa( "COP", false);

                    break;
                case 6:
                    manejoDeCasesConversionAndConversionInversa( "COP", true);

                    break;
                case 7:
                    manejoDeCasesConversionAndConversionInversa( "PEN", false);

                    break;
                case 8:
                    manejoDeCasesConversionAndConversionInversa( "PEN", true);

                    break;
                case 9:
                    mostrarHistorial();

                    break;
                case 10:
                    return;

                default:
                    System.out.println("El numero ingresado no es valido, intentelo nuevamente");
            }



        }
   }



}
