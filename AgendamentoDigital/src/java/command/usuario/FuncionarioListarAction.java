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
public class FuncionarioListarAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        request.setAttribute("pagina", "/pages/admin/usuarios/listarFuncionarios.jsp");
        String funcaoMsg = (String) request.getAttribute("funcaoMsg");
        String funcaoStatus = (String) request.getAttribute("funcaoStatus");

        if (funcaoMsg == null || funcaoStatus == null) {
            funcaoMsg = "Carregando Usuários de Funcionários";
            funcaoStatus = "info";
        }

        ArrayList<Usuario> arrFuncionarioComum = new ArrayList<Usuario>();
        ArrayList<Usuario> arrFuncionarioAdmin = new ArrayList<Usuario>();
        ArrayList<Usuario> arr = new ArrayList<Usuario>();

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuarioFuncionarioComum = new Usuario(PerfilDeAcesso.FUNCIONARIOCOMUM);
        arrFuncionarioComum = usuarioDAO.listarPerfil(usuarioFuncionarioComum);
        
        Usuario usuarioFuncionarioAdmin = new Usuario(PerfilDeAcesso.FUNCIONARIOADMIN);
        arrFuncionarioAdmin = usuarioDAO.listarPerfil(usuarioFuncionarioAdmin);
        
        arr.addAll(arrFuncionarioComum);
        arr.addAll(arrFuncionarioAdmin);

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();
        request.setAttribute("pgperfil", "Funcionarios");
        request.setAttribute("funcaoMsg", funcaoMsg);
        request.setAttribute("funcaoStatus", funcaoStatus);
        
        return objgson.toJson(arr);

    }

}
