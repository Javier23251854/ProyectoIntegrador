package com.proyectointegrador.controlador;

import com.google.gson.Gson;
import com.proyectointegrador.dao.UsuarioDAO;
import com.proyectointegrador.modelo.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/api/cambiarClave")
public class CambiarClaveControlador extends HttpServlet {

    private final UsuarioDAO dao = new UsuarioDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);

        Map<String, Object> resp = new HashMap<>();

        if (session == null) {
            response.setStatus(401);
            resp.put("status", "error");
            resp.put("message", "Debe iniciar sesión");
        } else {

            Usuario usuario =
                (Usuario) session.getAttribute("usuarioLogueado");

            String nuevaClave =
                request.getParameter("nuevaClave");

            boolean ok =
                dao.cambiarClave(
                    usuario.getIdUsuario(),
                    nuevaClave
                );

            resp.put("status", ok ? "success" : "error");
            resp.put("message",
                ok ? "Contraseña actualizada"
                   : "No se pudo actualizar");
        }

        try (PrintWriter out = response.getWriter()) {
            out.print(gson.toJson(resp));
        }
    }
}