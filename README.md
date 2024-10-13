# ADN HUMANUTANTE 🧬☣️

Este proyecto detecta si una secuencia de ADN corresponde a un **humano** o un **mutante** mediante el análisis de patrones genéticos. La secuancias de ADN se reciben en formato JSON, se valida y determina si contienen al menos **dos secuencias de cuatro letras iguales consecutivas** en cualquier dirección (horizontal, vertical o diagonal), lo que indicaría que es mutante.

Las secuencias válidas se almacenan en una base de datos **H2**, junto con un indicador de si son humanas o mutantes. Además, el proyecto proporciona estadísticas sobre el número de secuencias mutantes y humanas y su proporción, todo gestionado a través de una API REST.

## 🖥️ Instalación Local


1. Clonar o Descargar Proyecto: [Descargar aquí](https://github.com/matias-araya-02/Parcial_Mutantes_Prog_III/archive/refs/heads/main.zip)

2. Abrir el proyecto en IDE (intelliJ IDEA o el de su preferencia):

3. En caso de no tener H2 Database Engine, descargar e instalar: [Descargar H2 Database Engine](https://github.com/h2database/h2database/releases/download/version-2.3.232/h2-setup-2024-08-11.exe)

4. LEVANTAR H2 con este comando:
```
http://localhost:8080/h2-console/
```
5. Configurar H2 de la siguiente manera:
- `Driver Class:` org.h2.Driver
- `JDBC URL:` jdbc:h2:mem:testdb
- `User Name:` sa
- `Password:`

## 📂 Estructura del Proyecto

- 📄 `src/main/java/com/example/mutantes/MutantApplication.java`: Clase principal que inicia la aplicación Spring Boot.
- ⚙️ `src/main/java/com/example/mutantes/config/AppConfig.java`: Configuración de los beans necesarios para el funcionamiento de los servicios.
- 🔍 `src/main/java/com/example/mutantes/controller/MutantController.java`: Controlador que maneja las solicitudes HTTP para verificar el ADN y obtener estadísticas.
- 📊 `src/main/java/com/example/mutantes/service/MutantService.java`: Clase que contiene la lógica para detectar si una secuencia de ADN es mutante.
- 📈 `src/main/java/com/example/mutantes/service/StatisticsService.java`: Clase encargada de calcular y proporcionar estadísticas sobre los ADN analizados.
- 📦 `src/main/java/com/example/mutantes/model/`: Paquete que contiene las clases de modelo, como Dna, Payload y Stat, que representan los datos de entrada y salida.
- 🧪 `src/test/java/com/example/mutantes/`: Directorio que contiene las pruebas unitarias para validar la funcionalidad de la aplicación.

## 🔍 Algoritmo de Detección

La clase `MutantService` detecta si una secuencia de ADN pertenece a un mutante buscando patrones de cuatro bases genéticas consecutivas (A, T, C o G) en tres direcciones:

1. **Horizontal**: dentro de la misma fila.
2. **Vertical**: a lo largo de una columna.
3. **Diagonal**: en ambas diagonales de la matriz.

Un ADN es considerado mutante si contiene **más de una** secuencia repetitiva en cualquiera de estas direcciones. Si no se detectan suficientes secuencias, el ADN es clasificado como humano. Además, se validan las entradas para asegurar que la secuencia tenga el formato correcto antes de la verificación.

## ⚠️ Validaciones

Antes de realizar la detección, se llevan a cabo varias validaciones en la entrada:

- El array de ADN debe ser NxN (las mismas dimensiones en filas y columnas).
- El array de ADN debe tener un tamaño mínimo de 4x4.
- Cada fila del array de ADN solo puede contener los caracteres 'A', 'T', 'C' y 'G'.
- El array de ADN no puede ser null.
- El array de ADN no puede estar vacío.
- Cada fila del array de ADN no puede ser null.

## 🧰 Tecnologías Implementadas
- `H2` *(Base de datos en memoria para desarrollo ágil)*
- `Postman` *(Herramienta para validar y probar API)*
- `Java 17` *(Lenguaje de programación utilizado)*
- `JUnit` *(Framework para realizar pruebas unitarias)*
- `Swagger` *(Interfaz de documentación y pruebas de APIs)*
- `Spring Boot` *(Framework para construir aplicaciones Java de forma rápida)*
- `Render` *(Plataforma para desplegar aplicaciones en la nube)*
- `Gradle` *(Herramienta de automatización de construcción)*

## 🌐 Deploy Render
<p align="center">
  <img src="https://cdn.sanity.io/images/34ent8ly/production/ec37a3660704e1fa2b4246c9a01ab34e145194ad-824x824.png" alt="Postman Logo" width="150"/>
</p>


El proyecto está desplegado en [Render](https://render.com), una plataforma de hosting en la nube. Puedes acceder al API a través del siguiente enlace:

- [API de Mutantes](https://parcial-mutantes-prog-iii.onrender.com)

Este despliegue permite enviar solicitudes HTTP al endpoint de la API para detectar si una secuencia de ADN es mutante o no.

### 🟠 Consultas usando Postman

Aunque más adelante se explicarán en detalle las consultas con Postman, ya puedes realizar consultas a través de la API de detección de mutantes.

### Cómo hacer una solicitud:

1. **Método:** `POST`
2. **URL:** [https://parcial-mutantes-prog-iii.onrender.com/mutant](https://parcial-mutantes-prog-iii.onrender.com/mutant)
3. **Body:** El cuerpo de la solicitud debe ser enviado en formato JSON, con una secuencia de ADN como en el siguiente ejemplo:
4. **Send:** Haz click en `Send` para enviar la solicitud.

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
Puedes hacer una consulta a la API para obtener estadísticas sobre las secuencias de ADN que han sido analizadas.

### Cómo hacer una solicitud:
1. **Método:** `GET`
2. **URL:** [https://parcial-mutantes-prog-iii.onrender.com/mutant/stats](https://parcial-mutantes-prog-iii.onrender.com/mutant/stats)
3. **Send:** Haz click en `Send` para enviar la solicitud.
4. **Respuesta esperada:**
   - La API devolverá un JSON con estadísticas sobre cuántas secuencias de ADN mutantes y humanas han sido verificadas, así como la proporción entre ambas. Un ejemplo de respuesta es:

```json
{
    "countMutantDna": 1,
    "countHumanDna": 1,
    "ratio": 1.0
}
```

## 🟢 Swagger UI 
<p align="center">
  <img src="https://raw.githubusercontent.com/swagger-api/swagger.io/wordpress/images/assets/SWE-logo-clr.png" alt="Postman Logo" width="550"/>
</p>

Puedes interactuar directamente con la API sin necesidad de usar herramientas como Postman, utilizando la interfaz de Swagger.

### Cómo acceder:

1. **URL:** [https://parcial-mutantes-prog-iii.onrender.com/swagger-ui.html](https://parcial-mutantes-prog-iii.onrender.com/swagger-ui.html)

Swagger te proporciona una documentación interactiva de la API donde puedes realizar pruebas de las diferentes solicitudes (`GET`, `POST`, etc.) directamente desde el navegador. Solo necesitas ingresar los parámetros correspondientes, si aplica, y ejecutar la consulta.

Esto facilita el probar y entender las funcionalidades de la API sin configuraciones adicionales.


## 🟠 Pruebas en Postman 

<p align="center">
  <img src="https://upload.wikimedia.org/wikipedia/commons/c/c2/Postman_%28software%29.png" alt="Postman Logo" width="400"/>
</p>


Permite enviar un JSON con una secuencia de ADN y recibe como respuesta un estado: `200 OK` si se determina que la secuencia pertenece a un mutante, `403 Forbidden` si corresponde a un ser humano, y `400 Bad Request` si la secuencia de ADN no es válida. 

Además, permite obtener estadísticas `/stats` sobre las verificaciones realizadas. Estas estadísticas incluyen el número de secuencias mutantes, el número de secuencias humanas y la proporción entre ambas.

### 📝 Cómo realizar una consulta POST

#### Paso 1: Crear una nueva solicitud
- Haz clic en el botón `New` o en el icono de `"+"` para abrir una nueva pestaña de solicitud.

#### Paso 2: Seleccionar el tipo de solicitud
- Para verificar si una secuencia de ADN es mutante, selecciona `POST` del menú desplegable.

#### Paso 3: Ingresar la URL
- En el campo de URL, ingresa la dirección de tu API:

```
http://localhost:8080/mutant
```


#### Paso 4: Configurar el cuerpo de la solicitud
1. Haz clic en la pestaña `Body`.
2. Selecciona la opción `raw`.
3. Asegúrate de que el formato esté configurado en `JSON` (puedes seleccionar **JSON** desde el menú desplegable que aparece a la derecha).
4. Ingresa el JSON (ADN **MUTANTE** O **HUMANO**)
5. Haz clic en el botón `Send` para enviar la solicitud.

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
### 📝 Cómo realizar una consulta GET

#### Paso 1: Crear una nueva solicitud
- Haz clic en el botón `New` o en el icono de `"+"` para abrir una nueva pestaña de solicitud.
  
#### Paso 2: Seleccionar el tipo de solicitud
- Para obtener las estadísticas de ADN, selecciona `GET` del menú desplegable.

#### Paso 3: Ingresar la URL
- En el campo de URL, ingresa la dirección de tu API para obtener las estadísticas:
```
http://localhost:8080/mutant/stats
```
#### Paso 4: Enviar la solicitud
- Haz clic en el botón `Send` para enviar la solicitud.

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

La base de datos H2 sirve como almacenamiento para las secuencias de ADN analizadas. Permite realizar consultas sobre si un ADN es humano o mutante y acumular estadísticas de estos análisis.

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

- `testMutant1`: Verifica una secuencia de ADN mutante con secuencias repetitivas.
- `testMutant2`: Otro caso de ADN mutante.
- `testMutant3`: Casos extremos con bases nitrogenadas repetidas.
- `testNonMutant1`: Verifica ADN no mutante.
- `testNonMutant2`: Otro caso de ADN no mutante.
- `testMutant4`: Un caso complejo de ADN mutante.
- `testMutant5`: Un caso adicional de ADN mutante.

### ⚠️ Manejo de Errores

- `testEmptyDna`: Maneja ADN vacío.
- `testNullDna`: Maneja ADN nulo.
- `testAllNullDna`: Maneja todas las filas nulas.
- `testInvalidCharacters`: Maneja caracteres inválidos.
- `testNotNxNMatrix`: Maneja matriz no cuadrada.

### 🔍 Otros Casos de Prueba

- `testRows`: Secuencias repetitivas en filas.
- `testColumns`: Secuencias repetitivas en columnas.
- `testMainDiagonals`: Secuencias en diagonales principales.
- `testSecondaryLeftDiagonals`: Secuencias en diagonales secundarias izquierda.
- `testSecondaryRightDiagonals`: Secuencias en diagonales secundarias derecha.
- `testTertiaryLeftDiagonals`: Secuencias en diagonales terciarias izquierda.
- `testTertiaryRightDiagonals`: Secuencias en diagonales terciarias derecha.
- `testNonMutant`: Identificación correcta de ADN no mutante.




## 🔬 Pruebas unitarias
- **Mutante 1**☣️
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
- **Mutante 2**☣️
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
- **Mutante 3**☣️
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
- **Mutante 4**☣️
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
- **Mutante 5**☣️
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

- **Humano 1**🧬
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

- **Humano 2**🧬
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





