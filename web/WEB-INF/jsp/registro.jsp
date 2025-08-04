<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registro Usuarios</title>
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pemex.css">
       <style>
       
        input[type="submit"] {
            margin-top: 25px; 
        }
          .success-message {
            color: green;
            font-weight: bold;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<div class="contenedor">
    <h2>Registro de Usuario</h2>
    <form action="${pageContext.request.contextPath}/Registro" method="post">
        <label>Nombre completo:</label>
        <input type="text" name="nombre" required>

        <label>Nombre de usuario:</label>
        <input type="text" name="usuario" required>

        <label>Contraseña:</label>
        <input type="password" name="password" required>

      
        <tr></tr>
        <input type="submit" value="Registrarse">
    </form>
    <p><a href="${pageContext.request.contextPath}/login">Ya tengo cuenta</a></p>
</div>
</body>
</html>