![Banner tasa de cambio  Degradado](https://github.com/user-attachments/assets/f5bdb4b3-bed5-4d8e-b4ba-967e8c2a6ca1)


<p align="center">
   <img src="http://img.shields.io/static/v1?label=STATUS&message=Por%20Mejorar&color=LIGHTBLUE&style=for-the-badge" />
   <img src="https://img.shields.io/badge/language-Java-007396?style=for-the-badge"/>
</p>


## Challenge: Conversor de Moneda
Este conversor de monedas permite realizar conversiones actualizadas en tiempo real
entre diferentes divisas. Utiliza la API de Open Exchange Rates, en su plan gratuito,
que proporciona actualizaciones cada hora.
Posee un menú que está diseñado para ser intuitivo y fácil de usar.
Además, el proyecto guarda el historial de conversiones en formato JSON,
lo que permite llevar un control de las conversiones realizadas, incluyendo la
tasa de cambio, la divisa base, el monto convertido, y la fecha y hora de cada
transacción.

## 🔧 Tecnologías Utilizadas

- **Lenguaje de programación**: Java ☕
- **Entorno de desarrollo**: IntelliJ IDEA 🖥️
- **Gson (de Google)**: Librería utilizada para convertir los datos JSON obtenidos de la API en objetos Java y viceversa.
- **Api Open Exchange Rates:** Para optener los tipos de cambios actualizados

- ## 🚀 Instalación

### 📋 Pre-requisitos 
1. Instala en tu ordenador: Java JDK 17 o superior.

* Puedes descargarlo desde aqui:

  ```
  https://www.oracle.com/ar/java/technologies/downloads/#java17
  
  ```
  
2. Tener acceso a Internet para realizar las solicitudes a la API **Open Exchange Rates**.
3. Abre tu IDE o editor de codigo favorito.
   * Aunque el proyecto fue desarrollado en **IntelliJ IDEA**, puedes utilizar cualquier IDE o entorno de desarrollo de tu preferencia (como Eclipse, NetBeans, Visual Studio Code, etc.).
   * Si prefieres IntelliJ IDEA, puedes descargarlo aquí.
   
    ```
    https://www.jetbrains.com/es-es/idea/
    
    ```

4. **Librería Gson**: Necesitas agregar la librería Gson para el manejo de JSON.
* Puedes descargarla desde aqui:

  ```
  https://mvnrepository.com/artifact/com.google.code.gson/gson/2.11.0
  
  ```

* Una vez estes ahi ve a `file` y descarga el archivo `jar (291 KB)`, agrégalo a tu proyecto en el IDE que estés usando.

> [!NOTE]
>  Esta es la forma en que instalé la biblioteca, aunque también se puede hacer utilizando gestores de dependencias como Maven u otros.

###  📁 📂 Clonar el repositorio    
1. Crea una carpeta en tu ordenador y luego clona este repositorio:
   
   ```
   https://github.com/Erika-Gimenez/Conversor-de-Moneda.git
   
   ```
2. Navega hasta la carpeta del proyecto.

    ```
    cd ConversorDeMoneda
    
    ```
4. Ejecuta la clase Principal que contiene el método main y sigue las intrucciones de la consola.

 ##  🔩 Funcionalidades
 
1. **Conversión de Monedas en Tiempo Real**: 
   - El conversor obtiene tasas de cambio actualizadas en tiempo real a través de la API de **Open Exchange Rates**,
     lo que garantiza que las conversiones sean precisas y reflejen los valores actuales del mercado.
     El plan gratuito de la API permite hasta 1000 solicitudes mensuales y se actualiza cada hora, con el dólar
     estadounidense (USD) como moneda base.

2. **Menú Interactivo e Intuitivo**: 
   - La aplicación presenta un menú claro y fácil de navegar, que permite al usuario seleccionar entre diferentes pares de divisas para realizar conversiones. Entre 
 las opciones de conversión, se incluyen:
     - Dólar estadounidense (USD) a Peso argentino (ARS) y viceversa.
     - Dólar estadounidense (USD) a Real brasileño (BRL) y viceversa.
     - Dólar estadounidense (USD) a Peso colombiano (COP) y viceversa.
     - Dólar estadounidense (USD) a Sol peruano (PEN) y viceversa.
   - El menú también ofrece la posibilidad de visualizar el historial de conversiones y salir del programa cuando se desee.

3. **Validación de Entrada**:
   - El conversor cuenta con un sistema de validación de entrada que se asegura de que el usuario ingrese
   únicamente valores numéricos válidos. De este modo, el sistema evita entradas incorrectas, como espacios en blanco,
   vacios o el uso de caracteres no numéricos cómo letras.
   
4. **Historial de Conversiones**: 
   - Cada vez que se realiza una conversión, los detalles de la operación (como el monto convertido, la tasa de cambio utilizada y la fecha y hora exactas de la 
   transacción) se almacenan en una lista dinamica.
   Si el usuario lo desea, puede consultarlo desde el menú del programa. Al visualizar el historial, muestra por consola el monto convertido junto con la fecha y hora de la conversión.
   Esto le permite al usuario llevar un registro de sus conversiones de manera sencilla, facilitando el seguimiento de las operaciones realizadas.

5. **Serialización de Datos**:
   - La aplicación guarda el historial de conversiones en un archivo JSON llamado ``tasa de cambio.json``, lo que permite almacenar y recuperar fácilmente la información.
   Este proceso asegura que los datos queden organizados y puedan ser utilizados en futuras consultas o revisiones.

* Ejemplo visual del archivo JSON llamado ``tasa de cambio.json``

![foto tasa de cambio json](https://github.com/user-attachments/assets/f141abf6-8dcd-41ac-8eeb-19c78044cabc)

6. **Soporte para Múltiples Divisas**:
   - La aplicación soporta la conversión entre varias divisas, como el Dólar estadounidense (USD), Peso argentino (ARS),
   Real brasileño (BRL), Peso colombiano (COP) y Sol peruano (PEN). Estas divisas están gestionadas mediante un `enum` , lo que asegura una mayor flexibilidad y control
   sobre adiciones de futuras monedas a medida que el proyecto lo requiera sin afectar el flujo principal del programa.
   
7. **Formato de Fecha y Hora en las Conversiones**:
   - Al realizar una conversión, la fecha y hora exactas de la transacción son almacenadas y mostradas, utilizando un formato claro y personalizado. Esto garantiza que el historial refleje no solo los montos y tasas de cambio, sino también cuándo se realizó cada operación.

8. **Manejo de Excepciones Personalizadas**:
   - El programa está diseñado para manejar errores de manera eficiente, evitando que el flujo normal del sistema se interrumpa. Se han implementado las siguientes excepciones personalizadas:

     - **ErrorEnValidacionDeEntradaException**: Se lanza cuando el usuario ingresa un valor no válido (por ejemplo, un número negativo o una cadena vacía).
     - **ErrorEnMontoInvalidoException**: Se lanza cuando el monto ingresado para la conversión no es válido, ya sea porque el monto ingresado por el usuario es muy pequeño y devuelve 0 o 0.0, o porque el monto ingresado es 0.
     - **ErrorEnPeticionNullEspacioEnBlancoException**: Se lanza si hay problemas al obtener datos de la API, como un resultado nulo o una cadena en blanco.

## 🛠️ Uso del Programa

1. Inicia el programa.

```
*****************************************************
                 CONVERSOR DE MONEDA
*****************************************************

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

Elige una opción válida: 

```
2. Luego de elegir una opción ingrese el monto.
3. Los detalles de la conversión se mostraran junto con la fecha y la hora actual.
4. El historial de cada conversión se guarda.
5. Puedes consutar el Historal de conversión en cualquier momento seleccionado opción `9`.
6. Para filanizar solo selecciona la opción `10`.

## 🏠 Estructura del Proyecto

* El proyecto está organizado en los siguientes paquetes:

1. **principal:** Contiene la clase `Principal` que maneja el flujo de la aplicación.
2. **molde:** Contiene el record `Moneda` que representa los datos de la base y la tasa de cambio, un enum `CodigoDeDivisa` que incluye los códigos `ISO` de las monedas soportadas,
la clase `ConversorDeMoneda` donde se gestina la logica de las conversiones, con verificacion y validaciones y por ultimo la clase `Presentacion` aqui se maneja la interacción con el usurio,
incluye la validación de las entradas, visualización del menu y el historial de conversión.  
4. **cliente:** Contiene la clase `TipoDeCambioApi` que maneja la solicitud HTTP a la API y la clase `Serializacion` para guardar la información del historial de conversiones en un archivo JSON.
5. **excepciones:** Contiene excepciones personalizadas como `ErrorEnPeticionNullException`, `ErrorEnMontoInvalidoException` y por ultimo `ErrorEnValidacionDeEntradaException`.

> [!NOTE]
>  Podría agregar un nuevo paquete llamado presentacion y nuevas clases para no tener toda la lógica en la clase actual de presentación.
>  Considerar tener una clase padre o una interfaz en el futuro paquete presentacion para mejorar la organización y reutilización del código.
>  Introducir más excepciones para validar los parámetros o atributos, mejorando la robustez del programa.
>  Experimentar con una interfaz gráfica en el futuro para mejorar la experiencia del usuario.

## 🙌🏽 Contribuciones 

¡Me encantaría tu ayuda para mejorar este proyecto! Puedes contribuir de varias maneras:
* Si encuentras algún error o problema.
* Si tienes ideas para nuevas funcionalidades o mejoras.
- Porfavor crea un issue para discutirlo. Me encanta recibir nuevas ideas.

## ✒️ Autores 

[<img src="https://github.com/user-attachments/assets/1e99f8e5-f229-4128-837a-554d2844c64c" width=115><br><sub>Gimenez Erika</sub>](https://github.com/Erika-Gimenez)

## 😊 Contacto 

* [Linkedin](https://www.linkedin.com/in/erika-gimenez/).
* [GitHub](https://github.com/Erika-Gimenez).
