package controlador;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import util.ConexionBD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import util.ConexionBD;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.sql.ResultSet;

@WebServlet("/ReporteEntregasLentasServlet")
public class ReporteEntregasLentasServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sql = "SELECT e.id_registro, e.fecha, c.nombre AS cliente, d.nombre AS destino, v.placas AS vehiculo, p.nombre AS producto, e.cantidad_litros, t.tiempo_horas FROM registros_entrega e JOIN cliente c ON e.id_cliente = c.id_cliente JOIN destinos d ON e.id_destino = d.id_destino JOIN vehiculos v ON e.id_vehiculo = v.id_vehiculo JOIN productos p ON e.id_producto = p.id_producto JOIN tiempos_entrega t ON e.id_cliente = t.id_cliente AND e.id_destino = t.id_destino ORDER BY t.tiempo_horas DESC LIMIT 20";

        List<Map<String, Object>> resultado = new ArrayList<>();

        try (Connection con = ConexionBD.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("id_registro", rs.getInt("id_registro"));
                fila.put("fecha", rs.getDate("fecha").toString());
                fila.put("cliente", rs.getString("cliente"));
                fila.put("destino", rs.getString("destino"));
                fila.put("vehiculo", rs.getString("vehiculo"));
                fila.put("producto", rs.getString("producto"));
                fila.put("cantidad", rs.getDouble("cantidad_litros"));
                fila.put("tiempo", rs.getDouble("tiempo_horas"));
                resultado.add(fila);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            response.getWriter().write("Error al consultar entregas.");
            return;
        }

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(new Gson().toJson(resultado));
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
