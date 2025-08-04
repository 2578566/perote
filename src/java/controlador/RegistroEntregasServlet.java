package controlador;

import com.google.gson.Gson;
import jakarta.jms.Connection;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.util.List;
import java.util.Map;

import java.sql.DriverManager;
import java.sql.SQLException;
import util.ConexionBD;
import java.sql.PreparedStatement;

@WebServlet(name = "RegistroEntregasServlet", urlPatterns = {"/RegistroEntregasServlet"})
public class RegistroEntregasServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        List<Map<String, String>> entregas = gson.fromJson(reader, List.class);

        int guardadas = 0;
        String sql = "INSERT INTO registros_entrega (fecha, id_vehiculo, id_cliente, id_destino, id_producto, cantidad_litros) VALUES (?, ?, ?, ?, ?, ?)";

        try (java.sql.Connection con = ConexionBD.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            for (Map<String, String> entrega : entregas) {
         
                stmt.setDate(1, java.sql.Date.valueOf(entrega.get("fecha").toString()));
                stmt.setInt(2, Integer.parseInt(entrega.get("idVehiculo").toString()));
                stmt.setInt(3, Integer.parseInt(entrega.get("idCliente").toString()));
                stmt.setInt(4, Integer.parseInt(entrega.get("idDestino").toString()));
                stmt.setInt(5, Integer.parseInt(entrega.get("idProducto").toString()));
                stmt.setDouble(6, Double.parseDouble(entrega.get("cantidad").toString()));
                stmt.addBatch();
                guardadas++;
            }

            stmt.executeBatch();
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(500);
            response.getWriter().write("Error al guardar entregas.");
            return;
        }

        response.getWriter().write("Guardadas: " + guardadas + " entregas validadas.");

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
