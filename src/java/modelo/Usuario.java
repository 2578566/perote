package modelo;

import java.sql.Connection;
import java.sql.SQLException;
import util.ConexionBD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Usuario {

    private int id;
    private String nombre;
    private String usuario;
    private String password;

    public Usuario() {
    }

    // Constructor con todos los campos excepto el ID auto increment
    public Usuario(String nombreCompleto, String usuario, String password) {
        this.nombre = nombreCompleto;
        this.usuario = usuario;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombre;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombre = nombreCompleto;

    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Usuario buscarUsuario(String usuario, String password) {
        Usuario user = null;
        String sql = "SELECT * FROM login WHERE username = ? AND password = ?";

        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new Usuario();
                user.setId(rs.getInt("id_usuario"));
                user.setUsuario(rs.getString("username"));
                user.setNombreCompleto(rs.getString("nombre"));

            }

        } catch (SQLException e) {
            e.printStackTrace(); // para depuración
        }

        return user;
    }

}
