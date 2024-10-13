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

    public void tituloDeBienvenida(){
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

    public String mostrarResultadoDeLaConvercion(String montoOrigenStr, String codigoOrigenStr, BigDecimal monto, boolean esInversa) {

        //con la clase LocalDateTime obtenemos la fecha y hora actual y la guardamos en la variable fechaActual
        LocalDateTime fechaActual = LocalDateTime.now();
        String mensaje;
        //llamamos a nuestro enum y traemos la constante USD como un string tal cual esta inicializada en el enum con ayuda del metod name
        String llaveDestino = CodigoDeDivisa.USD.name();

        try {
           //la clase DateTimeFormatter y su metodo ofPattern nos permite perzonalizar un formato de fecha y hora
           DateTimeFormatter formatoDeFechaPersonalizada = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
           //utilizamos el metodo .format de la clase string para formater al fecha actual con fortomato de fecha y lo guardamos en la variable fecha formateada
           String fechaFormateada = fechaActual.format(formatoDeFechaPersonalizada);

           //utilizamos una operacion ternaria para saber que msj va a mostrar si de conversion o conversion inversa con el monto ingresado por el usuario los codigos iso el resultado de monto y la fecha y hora
           mensaje = esInversa ?
                   "\nConversion: " + montoOrigenStr + " " + codigoOrigenStr + " ===> " + monto + " " + llaveDestino + "\nFecha y hora: " + fechaFormateada
                   : "\nConversion: " + montoOrigenStr + " " + llaveDestino + " ===> " + monto + " " + codigoOrigenStr + "\nFecha y hora: " + fechaFormateada ;
           return mensaje;

       }catch (DateTimeException e){

           throw new RuntimeException("Error: al formatear la fecha y la hora " + e.getMessage());
       }

    }

    public void validacionDeEntradaEntero(String entrada) throws ErrorEnValidacionDeEntradaException {

        //verifica si la entrada contiene espacio en blanco o esta vacia si es asi lanza o tira una exception
        if (entrada.trim().isBlank()) {
            throw new ErrorEnValidacionDeEntradaException("no se ha ingresado ningún valor.");
        }

        //verifica la entrada negando la condicion si ingresa cualquier caracter que no sea numero entero les lanza una error
        if (!entrada.matches("\\d+")) {
            throw new ErrorEnValidacionDeEntradaException("ingresa solo números enteros positivos.");
        }
    }

    public void validacionDeEntradaDecimales(String entrada) throws ErrorEnValidacionDeEntradaException {

        //verifica si la entrada contiene espacio en blanco o esta vacia si es asi lanza o tira una exception
        if (entrada.trim().isBlank()) {
            throw new ErrorEnValidacionDeEntradaException("no se ha ingresado ningún valor.");
        }

        //verifica la entrada negando la condicion si ingresa cualquier caracter que no sea numero positivo o  un numero decimal o coma lanza una exception
        if (!entrada.matches("\\d+(\\.\\d+)?")) {
            throw new ErrorEnValidacionDeEntradaException("ingresa solo números positivos. Si es un número decimal, utiliza '.' en lugar de ','");
        }
    }

    public void manejoDeCasesConversionAndConversionInversa(String codigoMonedaOrigenStr, boolean esInversa) {

        BigDecimal monto;
        String resultado;
        String montoDestinoStr;
        String montoOrigenStr;

        try {
            System.out.println("Ingrese el monto que desea convertir");
            montoOrigenStr = leer.nextLine();
            validacionDeEntradaDecimales(montoOrigenStr);
            //pido el valor por medio de la clave en este caso "codigo iso"  que se me pasa por parametro  "codigoMonedaOrigenStr" y lo almaceno en una variable primitiva double
            double rate = moneda.rates().get(codigoMonedaOrigenStr);
            //convierto la variable rate a string haciendo un casting
            montoDestinoStr = String.valueOf(rate);
            //utilizo la varible "conversorDeMoneda" del objeto "ConversorDeMoneda" y utilizo un condicional ternario para saber que metodo asignarle a la variable "monto" del objeto "BigDecimal" calculoConversion o calculoConversionInversa
            monto = esInversa ? conversorDeMoneda.calculoConversionInversa(montoOrigenStr, montoDestinoStr) : conversorDeMoneda.calculoConversion(montoOrigenStr, montoDestinoStr);
            //llamo al metodo mostrarResultadoDeLaConversion que devuelve un mensaje modificado para monstrar el monto el codigo iso mas la fecha y hora de la conversion
            resultado = mostrarResultadoDeLaConvercion(montoOrigenStr, codigoMonedaOrigenStr, monto, esInversa);
            System.out.println(resultado);
            //en atributo tenemos un List que anida un Map
            //creamos una segundo objeto temporal de la clase Moneda para guardar la base y la tasa de cambio  de cada conversion
            Moneda monedaGuardaTasaDeCambio = new Moneda(moneda.base(), Map.of(codigoMonedaOrigenStr, rate));
            //luego creamos un map con los objetos  clave moneda y valor String
            Map<Moneda, String> conversion = new HashMap<>();
            //guardamos el objeto temporal de moneda y el resultado en el map
            conversion.put(monedaGuardaTasaDeCambio, resultado);
            //guardamos la variable de map en un arrayalist
            historialDeConversion.add(conversion);
            //creamos un objeto de la clase Serializacion
            Serializacion generador = new Serializacion();
            //serializamos nuestro arrays con todos los detalles de la base, la tasa de cambio, la conversion y su fecha y hora
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
        tituloDeBienvenida();

        while (true) {

            menu();
            System.out.print("Elige una opción válida: ");
            try {
                opcionString = leer.nextLine().trim();// recibimos la opcion ingrsada por el usuario
                validacionDeEntradaEntero(opcionString);
                opc = Integer.parseInt(opcionString);// casteamos la opcion "la convertimos en un int"

                //manejamos cada opcion del menu usando lambdas
                switch (opc) {
                    case 1 -> manejoDeCasesConversionAndConversionInversa(CodigoDeDivisa.ARS.name(), false);

                    case 2 -> manejoDeCasesConversionAndConversionInversa(CodigoDeDivisa.ARS.name(), true);

                    case 3 -> manejoDeCasesConversionAndConversionInversa(CodigoDeDivisa.BRL.name(), false);

                    case 4 -> manejoDeCasesConversionAndConversionInversa(CodigoDeDivisa.BRL.name(), true);

                    case 5 -> manejoDeCasesConversionAndConversionInversa(CodigoDeDivisa.COP.name(), false);

                    case 6 -> manejoDeCasesConversionAndConversionInversa(CodigoDeDivisa.COP.name(), true);

                    case 7 -> manejoDeCasesConversionAndConversionInversa(CodigoDeDivisa.PEN.name(), false);

                    case 8 -> manejoDeCasesConversionAndConversionInversa(CodigoDeDivisa.PEN.name(), true);

                    case 9 -> mostrarHistorial();

                    case 10 -> {
                        System.out.println("Ha finalizado el programa, gracias por usar nuestro conversor de monedas");
                        return;
                    }
                    default -> System.out.println("El número ingresado no es válido, inténtelo nuevamente");
                }

            } catch (ErrorEnValidacionDeEntradaException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (ArithmeticException e) {
                System.out.println(e.getMessage());
            } catch (Exception e){
                System.out.println("Ocurrio un error inesperado " + e.getMessage());
            }
        }


    }


}
