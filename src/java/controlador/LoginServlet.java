package controlador;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Usuario;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String usuario = request.getParameter("usuario");
        String password = request.getParameter("password");

        Usuario usu = Usuario.buscarUsuario(usuario, password);

        if (usu != null) {
            request.getSession().setAttribute("usuario", usu);
            request.getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Usuario o contraseña incorrectos");
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
