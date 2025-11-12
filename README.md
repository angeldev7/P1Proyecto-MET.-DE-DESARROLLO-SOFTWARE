# Sistema Contable - Comercial El Mejor Vendedor S.A.

Este repositorio contiene el código fuente y la documentación del proyecto "Sistema Contable", desarrollado como parte de la materia de Metodologías de Desarrollo de Software. El sistema está diseñado para gestionar transacciones contables, usuarios y auditorías, utilizando una arquitectura de 3 capas y persistencia de datos en archivos JSON.

## Estructura del Repositorio

El proyecto se organiza siguiendo una estructura de carpetas estándar para facilitar la navegación y el mantenimiento:

```
/
├── docs/               # Artefactos y documentación (SRS, análisis, diseño, etc.)
├── src/                # Código fuente de la aplicación (Java)
├── test/               # Pruebas unitarias y de integración
├── scripts/            # Scripts de utilidad (si aplica)
├── usuarios.json       # Base de datos de usuarios
├── transacciones.json  # Base de datos de facturas y gastos
├── bitacora.json       # Log de auditoría de eventos
├── pom.xml             # Archivo de configuración de Maven
└── README.md           # Este archivo
```

---

## Entregables por Fase (Artefactos)

A continuación, se detalla la ubicación de cada uno de los entregables solicitados, organizados por fase del ciclo de vida del proyecto.

### 1) Requisitos

La Especificación de Requisitos de Software (SRS) define el propósito, alcance, funcionalidades y restricciones del sistema.

-   **Ubicación:** `docs/SRS_v1.0.md`
-   **Contenido:**
    -   Contexto, objetivo y alcance del proyecto.
    -   Lista priorizada de Requisitos Funcionales (RF) y No Funcionales (RNF) con criterios de aceptación.
    -   Supuestos, restricciones y funcionalidades fuera de alcance.
    -   Matriz de trazabilidad inicial.
    -   Glosario de términos y casos de uso principales.
    -   Plan de Gestión de Cambios y análisis de riesgos inicial.

### 2) Análisis

Esta fase detalla el modelo del dominio y el comportamiento esperado del sistema.

-   **Ubicación:** `docs/Analisis.md`
-   **Contenido:**
    -   Modelo lógico del dominio (Diagrama de Clases UML).
    -   Diagrama de Casos de Uso refinado y escenarios de interacción.
    -   Especificación de datos (esquemas de los archivos JSON).
    -   Criterios de aceptación detallados en formato BDD (Dado-Cuando-Entonces).

### 3) Diseño

Aquí se define la arquitectura de software, el diseño de la interfaz, la persistencia y la estrategia de pruebas.

-   **Ubicación:** `docs/Diseño.md`
-   **Contenido:**
    -   Diagrama de arquitectura (componentes y capas) y Decisiones de Diseño (ADR).
    -   Diseño de la persistencia de datos (estructura de archivos JSON).
    -   Diseño de la interfaz de usuario (wireframes conceptuales).
-   **Plan de Pruebas:**
    -   **Ubicación:** `docs/Plan_de_Pruebas.md`
    -   **Contenido:** Tipos de pruebas a realizar, cobertura por requisito y datos de prueba.

### 4) Implementación

Contiene el código fuente del proyecto y las guías para su compilación y ejecución.

-   **Código Fuente:** El código Java se encuentra en la carpeta `/src`.
-   **Indicaciones de Ejecución:**
    1.  **Prerrequisitos:**
        -   Java Development Kit (JDK) versión 11 o superior.
        -   Apache Maven.
    2.  **Compilación:**
        -   Abrir una terminal en la raíz del proyecto.
        -   Ejecutar `mvn clean install`.
    3.  **Ejecución:**
        -   Ejecutar la clase `Ejecutable.SistemaContable` desde un IDE como Eclipse o IntelliJ.
        -   Alternativamente, ejecutar desde la terminal con el comando adecuado post-compilación.

### 5) Pruebas y Verificación

Esta sección agrupa los casos de prueba, el código de las pruebas automatizadas y las evidencias de su ejecución.

-   **Casos de Prueba (Matriz):**
    -   **Ubicación:** `docs/Casos_de_Prueba.md`
-   **Código de Pruebas Automatizadas:**
    -   **Ubicación:** `/test`
    -   **Contenido:** Pruebas unitarias (6) y de integración (1) desarrolladas para validar el core del sistema.
-   **Evidencias de Ejecución:**
    -   **Ubicación:** `docs/evidencias/`
    -   **Contenido:** Capturas de pantalla y salidas de terminal que demuestran la correcta ejecución de las pruebas.

### 6) Despliegue y Entrega

Guías para la instalación, uso y operación del sistema.

-   **Guía de Despliegue y Operación:**
    -   **Ubicación:** `docs/Guias_Despliegue_Uso.md`
-   **Paquete Ejecutable:**
    -   El archivo `.jar` ejecutable se genera en la carpeta `/target` después de la compilación con Maven.
-   **Post-mortem y Lecciones Aprendidas:**
    -   **Ubicación:** `docs/Post_Mortem.md`
