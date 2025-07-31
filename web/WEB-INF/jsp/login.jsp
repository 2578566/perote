
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Iniciar Sesión - Sistema de transporte y logistica</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pemex.css">
        <style>
            .success-message {
                color: green;
                font-weight: bold;
                margin-top: 10px;
            }
        </style>
    </head>
    <div class="contenedor">
        <h2>Iniciar Sesión</h2>

        <form action="login" method="post">
            <label for="usuario">Nombre de usuario:</label>
            <input type="text" name="usuario" id="usuario" required>

            <label for="password">Contraseña:</label>
            <input type="password" name="password" id="password" required>

            <input type="submit" value="Ingresar">
        </form>

        <p>¿No tienes cuenta? <a href="${pageContext.request.contextPath}/Registro">Regístrate aquí</a></p>

        <% String error = (String) request.getAttribute("error"); %>
        <% if (error != null) {%>
        <p class="error"><%= error%></p>
        <% } %>
        <% if (request.getAttribute("success") != null) {%>
        <p class="success-message"><%= request.getAttribute("success")%></p>
        <% }%>
    </div>
</body>
</html>