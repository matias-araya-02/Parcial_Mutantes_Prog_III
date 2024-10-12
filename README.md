# ADN Humano o Mutante

Este proyecto es una aplicación Spring Boot que determina si una secuencia de ADN pertenece a un ser humano o a un mutante, utilizando un análisis de las secuencias de ADN en formato de matriz.

## 📂 Estructura del Proyecto

- 📄 `src/main/java/com/example/mutantes/MutantApplication.java`: Clase principal que inicia la aplicación Spring Boot.
- ⚙️ `src/main/java/com/example/mutantes/config/AppConfig.java`: Configuración de los beans necesarios para el funcionamiento de los servicios.
- 🔍 `src/main/java/com/example/mutantes/controller/MutantController.java`: Controlador que maneja las solicitudes HTTP para verificar el ADN y obtener estadísticas.
- 📊 `src/main/java/com/example/mutantes/service/MutantService.java`: Clase que contiene la lógica para detectar si una secuencia de ADN es mutante.
- 📈 `src/main/java/com/example/mutantes/service/StatisticsService.java`: Clase encargada de calcular y proporcionar estadísticas sobre los ADN analizados.
- 📦 `src/main/java/com/example/mutantes/model/`: Paquete que contiene las clases de modelo, como Dna, Payload y Stat, que representan los datos de entrada y salida.
- 🧪 `src/test/java/com/example/mutantes/`: Directorio que contiene las pruebas unitarias para validar la funcionalidad de la aplicación.

## 🧠 Lógica de Detección

La clase `MutantService` detecta si una secuencia de ADN pertenece a un mutante buscando patrones de cuatro bases genéticas consecutivas (A, T, C o G) en tres direcciones:

1. **Horizontal**: dentro de la misma fila.
2. **Vertical**: a lo largo de una columna.
3. **Diagonal**: en ambas diagonales de la matriz.

Un ADN es considerado mutante si contiene **más de una** secuencia repetitiva en cualquiera de estas direcciones. Si no se detectan suficientes secuencias, el ADN es clasificado como humano. Además, se validan las entradas para asegurar que la secuencia tenga el formato correcto antes de la verificación.

## ⚠️ Validaciones

Antes de realizar la detección, se llevan a cabo varias validaciones en la entrada:

- El array de ADN no puede ser null.
- El array de ADN no puede estar vacío.
- Cada fila del array de ADN no puede ser null.
- El array de ADN debe ser NxN (las mismas dimensiones en filas y columnas).
- Cada fila del array de ADN solo puede contener los caracteres 'A', 'T', 'C' y 'G'.

## 🛠️ Tecnologías utilizadas

- **Java 17** *(Desarrollo con IntelliJ IDEA)*
- **Gradle** *(Gestor de dependencias)*
- **Spring Boot** *(Framework backend)*
- **H2** *(Base de datos embebida)*
- **Postman** *(Cliente para pruebas de API)*
- **JUnit** *(Pruebas unitarias)*


## Pruebas Postman 

<p align="center">
  <img src="https://upload.wikimedia.org/wikipedia/commons/c/c2/Postman_%28software%29.png" alt="Postman Logo" width="400"/>
</p>


Permite enviar un JSON con una secuencia de ADN y recibe como respuesta un estado: `200 OK` si se determina que la secuencia pertenece a un mutante, `403 Forbidden` si corresponde a un ser humano, y `400 Bad Request` si la secuencia de ADN no es válida. 

Además, permite obtener estadísticas `/stats` sobre las verificaciones realizadas. Estas estadísticas incluyen el número de secuencias mutantes, el número de secuencias humanas y la proporción entre ambas.

### 📝 Cómo realizar una consulta POST en Postman

#### Paso 1: Abrir Postman
- Inicia la aplicación Postman en tu computadora.

#### Paso 2: Crear una nueva solicitud
- Haz clic en el botón **New** o en el icono de **"+"** para abrir una nueva pestaña de solicitud.

#### Paso 3: Seleccionar el tipo de solicitud
- Selecciona el tipo de solicitud HTTP que deseas realizar. Para verificar si una secuencia de ADN es mutante, selecciona **POST** del menú desplegable.

#### Paso 4: Ingresar la URL
- En el campo de URL, ingresa la dirección de tu API:

```
http://localhost:8080/mutant
```


#### Paso 5: Configurar el cuerpo de la solicitud
1. Haz clic en la pestaña **Body**.
2. Selecciona la opción **raw**.
3. Asegúrate de que el formato esté configurado en **JSON** (puedes seleccionar **JSON** desde el menú desplegable que aparece a la derecha).
4. Ingresa el JSON (ADN MUTANTE O HUMANO)
5. Haz clic en el botón **Send** para enviar la solicitud.

Ejemplo de ADN **MUTANTE**:
 ```json
{
    "dna": [
        "ATGCGA",
        "CAGTGC",
        "TTATGT",
        "AGAAGG",
        "CCCCTA",
        "TCACTG"
    ]
}
```


Response Status (ADN Mutante):
`200 OK`

Response Body:
```json
{
    "message": "Mutante 👽✌",
    "mutant": 1
}
```
#
Ejemplo de ADN **HUMANO**:
 ```json
{
    "dna": [
      "ATGGTG",
      "GTCTTA",
      "AATTGG",
      "ACTAGT",
      "GGATTC", 
      "AGGCAA"
    ]
}
```

Response Status (ADN Humano):
`403 Forbidden`

Response Body:
```json
{
    "message": "Humano 😉👍",
    "mutant": 0
}
```
#
Ejemplo de ADN **NO VALIDO** (Aplica para ADN null, vacio, caracteres invalidos, no sea NxN):
 ```json
{
    "dna": []
}
```

Response Status (ADN Humano):
`400 Bad Request`

Response Body:
```json
{
    "message": "El ADN proporcionado no es válido",
    "mutant": -1
}
```
#
### 📝 Cómo realizar una consulta GET en Postman

#### Paso 1: Crear una nueva solicitud
- Haz clic en el botón **New** o en el icono de **"+"** para abrir una nueva pestaña de solicitud.
  
#### Paso 2: Seleccionar el tipo de solicitud
- Selecciona el tipo de solicitud HTTP que deseas realizar. Para obtener las estadísticas de ADN, selecciona **GET** del menú desplegable.

#### Paso 3: Ingresar la URL
- En el campo de URL, ingresa la dirección de tu API para obtener las estadísticas:
```
http://localhost:8080/mutant/stats
```
#### Paso 4: Enviar la solicitud
- Haz clic en el botón **Send** para enviar la solicitud.

Response Body:
```json
{
    "countMutantDna": 1,
    "countHumanDna": 1,
    "ratio": 1.0
}
```

## 🗄️ Base de Datos H2

<p align="center">
  <img src="https://dbdb.io/media/logos/h2-logo.svg" alt="H2 Logo" width="150"/>
</p>

### Función de la Base de Datos H2 en el Proyecto

La base de datos H2 en tu proyecto sirve como almacenamiento para las secuencias de ADN analizadas. Permite realizar consultas sobre si un ADN es humano o mutante y acumular estadísticas de estos análisis.

### Estructura de la Tabla `Dna`

La tabla `Dna` tiene las siguientes columnas:

- **ID**: Identificador único de cada registro (clave primaria).
- **ACUMULAR**: Un contador que registra cuántas veces se ha analizado esta secuencia de ADN.
- **ADN**: Secuencia de ADN almacenada, que debe ser única en la tabla.
- **ES_MUTANTE**: Un entero que indica si la secuencia es mutante (1) o humana (0).

### Propósito de Cada Columna

- **ID**: Facilita la identificación de cada secuencia en la base de datos.
- **ACUMULAR**: Ayuda a llevar un registro del número de veces que se ha procesado la misma secuencia, lo que permite mejorar la eficiencia y la respuesta del servicio.
- **ADN**: Almacena la secuencia para permitir su posterior análisis y consultas.
- **ES_MUTANTE**: Permite clasificar rápidamente las secuencias analizadas y obtener estadísticas.


## Testing ✅

<p align="center">
  <img src="https://static.vecteezy.com/system/resources/thumbnails/011/858/556/small_2x/green-check-mark-icon-with-circle-tick-box-check-list-circle-frame-checkbox-symbol-sign-png.png" alt="Green Check Mark Icon" width="250" />
</p>

### ✅ Casos de Prueba

- `testMutant1`: Verifica que una secuencia de ADN con múltiples secuencias repetitivas sea detectada como mutante.
- `testMutant2`: Verifica otro caso de secuencia de ADN mutante.
- `testMutant3`: Verifica un caso extremo donde todas las filas contienen la misma base nitrogenada.
- `testNonMutant1`: Verifica que una secuencia de ADN sin suficientes secuencias repetitivas no sea detectada como mutante.
- `testNonMutant2`: Verifica otro caso de secuencia de ADN no mutante.
- `testMutant4`: Verifica un caso más complejo de secuencia de ADN mutante.
- `testMutant5`: Verifica un caso adicional de secuencia de ADN mutante.

### ⚠️ Manejo de Errores

- `testEmptyDna`: Verifica que se maneje correctamente una secuencia de ADN vacía.
- `testNullDna`: Verifica que se maneje correctamente una secuencia de ADN nula.
- `testAllNullDna`: Verifica que se maneje correctamente una secuencia de ADN con todos los valores nulos.
- `testInvalidCharacters`: Verifica que se manejen correctamente los caracteres inválidos en la secuencia de ADN.
- `testNotNxNMatrix`: Verifica que se maneje correctamente una secuencia de ADN que no es una matriz cuadrada.

### 🔍 Otros Casos de Prueba

- `testRows`: Verifica una secuencia de ADN donde se encuentran secuencias repetitivas en las filas.
- `testColumns`: Verifica una secuencia de ADN donde se encuentran secuencias repetitivas en las columnas.
- `testMainDiagonals`: Verifica una secuencia de ADN donde se encuentran secuencias repetitivas en las diagonales principales.
- `testSecondaryLeftDiagonals`: Verifica una secuencia de ADN donde se encuentran secuencias repetitivas en las diagonales secundarias izquierda.
- `testSecondaryRightDiagonals`: Verifica una secuencia de ADN donde se encuentran secuencias repetitivas en las diagonales secundarias derecha.
- `testTertiaryLeftDiagonals`: Verifica una secuencia de ADN donde se encuentran secuencias repetitivas en las diagonales terciarias izquierda.
- `testTertiaryRightDiagonals`: Verifica una secuencia de ADN donde se encuentran secuencias repetitivas en las diagonales terciarias derecha.
- `testNonMutant`: Verifica que una secuencia de ADN no mutante sea correctamente identificada.



## 🧬 Pruebas unitarias
- **Mutante 1**
```json
{
    "dna": [
      "AAAA",
      "CCCC",
      "TCAG",
      "GGTC"
    ]
}
```
- **Mutante 2**
```json
{
    "dna": [
      "TGAC",
      "AGCC",
      "TGAC",
      "GGTC"
    ]
}
```
- **Mutante 3**
```json
{
    "dna": [
      "AAAA",
      "AAAA",
      "AAAA",
      "AAAA"
    ]
}
```
- **Mutante 4**
```json
{
    "dna": [
      "TCGGGTGAT",
      "TGATCCTTT",
      "TACGAGTGA",
      "AAATGTACG",
      "ACGAGTGCT",
      "AGACACATG",
      "GAATTCCAA",
      "ACTACGACC",
      "TGAGTATCC"
    ]
}
```
- **Mutante 5**
```json
{
    "dna": [
      "TTTTTTTTT",
      "TTTTTTTTT",
      "TTTTTTTTT",
      "TTTTTTTTT",
      "CCGACCAGT",
      "GGCACTCCA",
      "AGGACACTA",
      "CAAAGGCAT",
      "GCAGTCCCC"
    ]
}
```

- **Humano 1**
```json
{
    "dna": [
      "AAAT",
      "AACC",
      "AAAC",
      "CGGG"
    ]
}
```

- **Humano 2**
```json
{
    "dna": [
      "TGAC",
      "ATCC",
      "TAAG",
      "GGTC"
    ]
}
```





