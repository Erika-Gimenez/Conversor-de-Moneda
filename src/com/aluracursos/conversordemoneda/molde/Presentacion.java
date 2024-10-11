package com.aluracursos.conversordemoneda.molde;

import com.aluracursos.conversordemoneda.cliente.Serializacion;
import com.aluracursos.conversordemoneda.cliente.TipoDeCambioApi;
import com.aluracursos.conversordemoneda.excepciones.ErrorEnMontoInvalidoException;
import com.aluracursos.conversordemoneda.excepciones.ErrorEnPeticionNullEspacioEnBlancoException;
import com.aluracursos.conversordemoneda.excepciones.ErrorEnValidacionDeEntradaException;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Presentacion {


    private final TipoDeCambioApi tipoDeCambio;
    private final Moneda moneda;
    private final Scanner leer;
    private final ConversorDeMoneda conversorDeMoneda;
    private List<Map<Moneda, String>> historialDeConversion;


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

    public String mostrarResultadoDeLaConvercion(String montoOrigenStr, String montoDestinoStr, BigDecimal monto, boolean esInversa) {

        LocalDateTime fechaActual = LocalDateTime.now();
        String mensaje;
        String llaveOrigen = "";
        String llaveDestino = "";

        try {
           DateTimeFormatter formatoDeFechaPersonalizada = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
           String fechaFormateada = fechaActual.format(formatoDeFechaPersonalizada);


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

           mensaje = esInversa ?
                   "\nConversion: " + montoOrigenStr + " " + llaveOrigen + " ===> " + monto + " " + llaveDestino + "\nFecha y hora: " + fechaFormateada
                   : "\nConversion: " + montoOrigenStr + " " + llaveDestino + " ===> " + monto + " " + llaveOrigen + "\nFecha y hora: " + fechaFormateada ;
           return mensaje;

       }catch (DateTimeException e){

           throw new RuntimeException("Error: al formatear la fecha y la hora.");
       }

    }

    public void validacionDeEntrada(String entrada) throws ErrorEnValidacionDeEntradaException {
        if (entrada.trim().isBlank()) {
            throw new ErrorEnValidacionDeEntradaException("no se ha ingresado ningún valor.");
        }
        if (!entrada.matches("\\d+")) {
            throw new ErrorEnValidacionDeEntradaException("ingresa solo números positivos sin puntos, comas o caracteres especiales.");
        }
    }

    public void manejoDeCasesConversionAndConversionInversa(String monedaOrigenStr, boolean esInversa) {

        BigDecimal monto;
        String resultado;
        String montoDestinoStr;
        String montoOrigenStr;

        try {
            System.out.println("Ingrese el monto que desea convertir");
            montoOrigenStr = leer.nextLine();
            validacionDeEntrada(montoOrigenStr);
            double rate = moneda.rates().get(monedaOrigenStr); // estoy pidiendo el valor por medio de la llave en este caso ARS y lo guardo en un double
            montoDestinoStr = String.valueOf(rate); // llamo a rate y luego lo convierto en un String
            monto = esInversa ? conversorDeMoneda.calculoConversionInversa(montoOrigenStr, montoDestinoStr) : conversorDeMoneda.calculoConversion(montoOrigenStr, montoDestinoStr);
            resultado = mostrarResultadoDeLaConvercion(montoOrigenStr, montoDestinoStr, monto, esInversa);
            System.out.println(resultado);
            Moneda monedaGuardaTasaDeCambio = new Moneda(moneda.base(), Map.of(monedaOrigenStr, rate));
            Map<Moneda, String> conversion = new HashMap<>();
            conversion.put(monedaGuardaTasaDeCambio, resultado);
            historialDeConversion.add(conversion);
            Serializacion generador = new Serializacion();
            generador.guardarJson(historialDeConversion);


        } catch (ErrorEnPeticionNullEspacioEnBlancoException e) {
            System.out.println("Error en la petición a la API: " + e.getMessage());
        } catch (ErrorEnValidacionDeEntradaException | ErrorEnMontoInvalidoException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error de entrada y salida: " + e.getMessage());
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }


    }

    public void mostrarHistorial() {
        int contador =1;
        String subtituloHistorial = """
                *****************************************************
                              HISTORIAL DE CONVERSIONES
                *****************************************************
                """;
        System.out.println(subtituloHistorial);

        for (Map<Moneda, String> conversion : historialDeConversion) {
            for (Map.Entry<Moneda, String> entry : conversion.entrySet()) {
                System.out.println(contador + ")" + entry.getValue());
                contador++;
            }
        }
    }

    public void logica() {
        String opcionString;
        int opc;
        bievenida();

        while (true) {

            menu();
            System.out.print("Elige una opción válida: ");
            try {
                opcionString = leer.nextLine().trim();
                validacionDeEntrada(opcionString);
                opc = Integer.parseInt(opcionString);

                switch (opc) {
                    case 1:
                        manejoDeCasesConversionAndConversionInversa("ARS", false);

                        break;
                    case 2:
                        manejoDeCasesConversionAndConversionInversa("ARS", true);

                        break;
                    case 3:
                        manejoDeCasesConversionAndConversionInversa("BRL", false);

                        break;
                    case 4:
                        manejoDeCasesConversionAndConversionInversa("BRL", true);

                        break;
                    case 5:
                        manejoDeCasesConversionAndConversionInversa("COP", false);

                        break;
                    case 6:
                        manejoDeCasesConversionAndConversionInversa("COP", true);

                        break;
                    case 7:
                        manejoDeCasesConversionAndConversionInversa("PEN", false);

                        break;
                    case 8:
                        manejoDeCasesConversionAndConversionInversa("PEN", true);

                        break;
                    case 9:
                        mostrarHistorial();

                        break;
                    case 10:
                        return;

                    default:
                        System.out.println("El numero ingresado no es valido, intentelo nuevamente");
                }

            } catch (ErrorEnValidacionDeEntradaException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (ArithmeticException e) {
                System.out.println(e.getMessage());
            }
        }


    }


}
