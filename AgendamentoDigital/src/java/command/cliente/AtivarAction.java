/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.cliente;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import command.usuario.*;
import command.cliente.ICommand;
import dao.UsuarioDAO;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.Cliente;
import modelos.PerfilDeAcesso;
import modelos.Usuario;

/**
 *
 * @author Rafael Pereira
 */
public class AtivarAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //Verifica Usuario logado
        //cria uma sessao para resgatar o usuario
        HttpSession sessaoUsuario = request.getSession();
        Usuario usuarioAutenticado = (Usuario) sessaoUsuario.getAttribute("usuarioAutenticado");
        if (usuarioAutenticado != null && usuarioAutenticado.getPerfil().equals(PerfilDeAcesso.FUNCIONARIOCOMUM)) {
            request.setAttribute("pagina", "/Funcionario/Cliente/Listar");
        } else {
            request.setAttribute("pagina", "HomeAction");
        }

        String cliente = request.getParameter("cliente");
        JsonParser parser = new JsonParser();
        JsonElement jsonCliente = parser.parse(cliente);

        String sqlState = "0";
        String funcaoMsg = "";
        String funcaoStatus = "";

        //Instanciando Usuário
        Cliente objCliente = new Cliente();
        if (usuarioAutenticado != null && usuarioAutenticado.getPerfil().equals(PerfilDeAcesso.CLIENTECOMUM)) {
            objCliente = (Cliente) sessaoUsuario.getAttribute("cliente");
        } else {
            try {
                Gson objgson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDateDeserializer()).create();
                objCliente = objgson.fromJson(jsonCliente, Cliente.class);
            } catch (Exception ex) {
                funcaoMsg = "Dados inválidos!\\n" + ex;
                funcaoStatus = "success";
            }
        }

        if (objCliente.getUsuario() != null) {

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            sqlState = usuarioDAO.ativar(objCliente.getUsuario());

            if ("0".equals(sqlState)) {
                funcaoMsg = "Ativado com sucesso!";
                funcaoStatus = "success";
            } else {
                funcaoMsg = "Não foi possível ativar o usuário, tente novamente mais tarde!";
                funcaoStatus = "error";
            }
        } else {
            funcaoMsg = "Usuário inválido!";
            funcaoStatus = "error";
        }

        request.setAttribute("funcaoMsg", funcaoMsg);
        request.setAttribute("funcaoStatus", funcaoStatus);
        return funcaoMsg;
    }

    public class JsonDateDeserializer implements JsonDeserializer<Date> {

        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            
            String s = json.getAsJsonPrimitive().getAsString();
            Date dataAgendamento = new Date();
            try {
                dataAgendamento = new SimpleDateFormat("MMM dd, yyyy").parse(s);
            } catch (ParseException ex) {
                Logger.getLogger(AtivarAction.class.getName()).log(Level.SEVERE, null, ex);
            }
            return dataAgendamento;
        }
    }
}
