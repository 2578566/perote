package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.RegistroUsuario;
import modelo.Usuario;

@WebServlet("/Registro")
public class RegistroServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/registro.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String usuario = request.getParameter("usuario");
        String password = request.getParameter("password");
    

        Usuario nuevo = new Usuario();
        nuevo.setNombreCompleto(nombre);
        nuevo.setUsuario(usuario);
        nuevo.setPassword(password);

        boolean registrado = RegistroUsuario.registrar(nuevo);
        if (registrado) {
            System.out.println("Registro exitoso. Redirigiendo a login.jsp");
            request.setAttribute("success", "¡Registro exitoso! ahora puedes iniciar sesion."); // Set success message

            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Error al registrar. intente con otro usuario.");
            request.getRequestDispatcher("/WEB-INF/jsp/registro.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "";
    }// </editor-fold>

}
