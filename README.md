# Sistema de Validación de Entregas de Gasolina - PEMEX

## Resumen Ejecutivo

Este sistema permite **validar entregas de gasolina pegadas desde Excel**, compararlas con la base de datos `tiempos_entrega`, mostrar visualmente los resultados y generar reportes. Fue desarrollado usando **Java (Servlets + JSP)** como una solución para mejorar los procesos internos en PEMEX.

###  Problema Identificado

Actualmente, en PEMEX el proceso de registrar, validar y analizar entregas de gasolina se realiza de manera **manual y desorganizada**, lo que conlleva:

- Errores humanos y duplicidad de datos  
- Dificultad para auditar  
- Pérdida de tiempo en validaciones  
- Falta de análisis eficiente de rutas de entrega  

### Solución Propuesta

El sistema permite a los operadores:

- Ingresar entregas manualmente o pegarlas desde Excel  
- Validar automáticamente los datos contra catálogos oficiales  
- Comparar con los tiempos de entrega históricos  
- Visualizar los registros correctos (verde) e inválidos  
- Ordenar las rutas válidas por tiempo de entrega (horas)  
- Evitar duplicidad y errores, automatizando tareas repetitivas  

---

## Arquitectura del Sistema

El sistema utiliza una arquitectura **MVC (Modelo-Vista-Controlador)** para mantener la separación de lógica de negocio, presentación y acceso a datos.

### Tecnologías Utilizadas

- Java EE (Servlets + JSP)  
- HTML, CSS, JavaScript  
- MySQL  
- JUnit (pruebas automatizadas)  
- GitHub + GitHub Actions (CI/CD)

---

##  Requerimientos

- Java JDK 23 o superior  
- GlassFish 6.2.1  
- Servidor MySQL 8.0.17 o superior  
- JDBC Driver de MySQL  
- NetBeans IDE  
- Navegador web moderno

---

##  Instalación y Configuración

### 1. Clonar el proyecto

```bash
git clone https://github.com/tu-usuario/perote.git
```

### 2. Configurar el entorno en NetBeans

- Abrir el proyecto como aplicación web en NetBeans  
- Configurar GlassFish  
- Configurar conexión a base de datos en `Conexion.java`  
  - Usuario, contraseña y URL JDBC  
- Crear base de datos con el script `perote.sql`

### 3. Ejecutar localmente

- Iniciar el servidor GlassFish  
- Acceder desde: [http://localhost:8080/perote/login]

---

##  Pruebas

Para ejecutar pruebas manuales:

1. Iniciar GlassFish  
2. Ingresar al sistema  
3. Pegar datos desde Excel en la tabla  
4. Presionar el botón **"Comparar con tiempos"**

---

##  Uso del Sistema

### Para usuarios finales:

- Pegar entregas desde Excel en el área correspondiente  
- Presionar "Comparar con tiempos"  
- Validar resultados visuales (verde = válido)  
- Ordenar por tiempo de entrega

### Para administradores:

- Consultar o actualizar tabla `tiempos_entrega`  
- Agregar nuevos clientes, destinos o productos faltantes

---

##  Contribuciones

### Guía para contribuir:

1. Clona el repositorio  
2. Crea una nueva rama (`feature/nombre`)  
3. Realiza tus cambios  
4. Haz un pull request a la rama `develop`

### Estructura de ramas

- `master`: versión estable  
- `develop`: en desarrollo  

---

##  Roadmap

- [ ] Exportar entregas validadas a Excel o PDF  
- [ ] Filtros por fecha, cliente y destino  
- [ ] Dashboard con gráficas por vehículo y destino  
- [ ] Roles de usuario: Administrador, Supervisor, Chofer  
- [ ] Historial de entregas validadas con búsqueda y edición  
- [ ] Alertas para entregas fuera de estándar  
- [ ] Integración con GPS  
- [ ] API REST pública para consulta de entregas  

---

## 📄 Licencia

Este proyecto es de uso interno en PEMEX. Para más detalles, contactar al equipo de desarrollo.
