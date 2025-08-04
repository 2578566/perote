/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.modelo;

import static junit.framework.TestCase.assertEquals;
import modelo.Usuario;
import org.junit.Test;


public class UsuarioTest {
     @Test
    public void pruebaNombreUsuario() {
        Usuario u = new Usuario("Juan Pérez", "jperez", "1234");
        assertEquals("jperez", u.getUsuario());
    }
}
