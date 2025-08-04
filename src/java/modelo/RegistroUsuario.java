package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import util.ConexionBD;
import java.sql.PreparedStatement;

public class RegistroUsuario {

    public static boolean registrar(Usuario user) {
        String sql = "INSERT INTO login (nombre, username, password, rol) VALUES (?,?,?,?)";
        try (Connection con = ConexionBD.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getNombreCompleto());
            ps.setString(2, user.getUsuario());
            ps.setString(3, user.getPassword());
            ps.setString(4, "operador");
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
