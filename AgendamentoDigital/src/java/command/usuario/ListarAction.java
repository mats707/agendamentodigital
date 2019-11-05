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
import modelos.Usuario;

/**
 *
 * @author alunocmc
 */
public class ListarAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ArrayList<Usuario> arr = new ArrayList<Usuario>();

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        arr = usuarioDAO.listar();

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();
        
        return objgson.toJson(arr);

    }

}
