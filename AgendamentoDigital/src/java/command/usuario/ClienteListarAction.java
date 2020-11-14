/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.usuario;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.UsuarioDAO;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.PerfilDeAcesso;
import modelos.Usuario;

/**
 *
 * @author alunocmc
 */
public class ClienteListarAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        request.setAttribute("pagina", "/pages/admin/usuarios/listarClientes.jsp");
        
        String funcaoMsg = (String) request.getAttribute("funcaoMsg");
        String funcaoStatus = (String) request.getAttribute("funcaoStatus");

        if (funcaoMsg == null || funcaoStatus == null) {
            funcaoMsg = "Carregando Usuários de Clientes";
            funcaoStatus = "info";
        }

        ArrayList<Usuario> arr = new ArrayList<Usuario>();
        
        PerfilDeAcesso perfil = PerfilDeAcesso.CLIENTECOMUM;

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = new Usuario(perfil);

        arr = usuarioDAO.listarPerfil(usuario);

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();
        
        request.setAttribute("pgperfil", "Clientes");
        request.setAttribute("funcaoMsg", funcaoMsg);
        request.setAttribute("funcaoStatus", funcaoStatus);
        
        return objgson.toJson(arr);

    }

}
