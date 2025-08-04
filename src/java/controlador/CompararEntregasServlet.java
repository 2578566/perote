package controlador;

import com.google.gson.Gson;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.SQLException;
import util.ConexionBD;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/CompararEntregasServlet")
public class CompararEntregasServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Gson gson = new Gson();
        BufferedReader reader = request.getReader();
        List<Map<String, String>> entregas = gson.fromJson(reader, List.class);
        List<Map<String, Object>> resultados = new ArrayList<>();

        for (Map<String, String> entrega : entregas) {
            try {

                String clienteStr = entrega.get("cliente");
                String destinoStr = entrega.get("destino");

                int idCliente = resolverIdcliente("cliente", clienteStr);
                int idDestino = resolverIddestino("destinos", destinoStr);

                boolean existe = existeTiempoEntrega(idCliente, idDestino);
                String nombreCliente = obtenerNombreCliente("cliente", idCliente);
                String nombreDestino = obtenerNombreDestino("destinos", idDestino);
                Double tiempoEntrega=obtenerTiempoEntrega(idCliente,idDestino, "tiempos_entrega");
                Map<String, Object> resultado = new HashMap<>();
                System.out.println(nombreCliente);
                resultado.put("cliente", nombreCliente);
                resultado.put("destino", nombreDestino);
                resultado.put("tiempo_entrega", tiempoEntrega);
                resultado.put("idcliente", idCliente);
                resultado.put("iddestino", idDestino);
                resultado.put("valido", existe);
                resultados.add(resultado);
            } catch (SQLException ex) {
                Logger.getLogger(CompararEntregasServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(resultados));

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private int resolverIdcliente(String tabla, String valor) throws SQLException {
        if (valor.matches("\\d+")) {
            return Integer.parseInt(valor); // Ya es un ID
        }

        String sql = "SELECT id_cliente FROM " + tabla + " WHERE nombre = ?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, valor.trim());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_cliente");
            } else {
                throw new SQLException("No se encontró en " + tabla + ": " + valor);
            }
        }
    }
    
     private int resolverIddestino(String tabla, String valor) throws SQLException {
        if (valor.matches("\\d+")) {
            return Integer.parseInt(valor); // Ya es un ID
        }

        String sql = "SELECT id_destino FROM " + tabla + " WHERE nombre = ?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, valor.trim());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_destino");
            } else {
                throw new SQLException("No se encontró en " + tabla + ": " + valor);
            }
        }
    }
    

    private boolean existeTiempoEntrega(int idCliente, int idDestino) throws SQLException {
        String sql = "SELECT COUNT(*) FROM tiempos_entrega WHERE id_cliente = ? AND id_destino = ?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            stmt.setInt(2, idDestino);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    private String obtenerNombreCliente(String tabla, int id) throws SQLException {
        String sql = "SELECT nombre FROM " + tabla + " WHERE id_cliente = ?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nombre");
            }
        }
        return "No encontrado";
    }

    private String obtenerNombreDestino(String tabla, int id) throws SQLException {
        String sql = "SELECT nombre FROM " + tabla + " WHERE id_destino = ?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nombre");
            }
        }
        return "No encontrado";
    }

    
    
    private double obtenerTiempoEntrega(int idCliente, int idDestino, String tabla) throws SQLException {
        String sql = "SELECT tiempo_horas FROM " + tabla + " WHERE id_cliente = ? AND id_destino= ?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
               stmt.setInt(2, idDestino);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("tiempo_horas");
            }
        }
        return 0.0;
    }
    
}
