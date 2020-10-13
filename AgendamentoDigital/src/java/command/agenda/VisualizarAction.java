/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.agenda;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.AgendamentoDAO;
import dao.FuncionarioDAO;
import dao.ServicoDAO;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.Agendamento;
import modelos.Usuario;
import modelos.Cliente;
import modelos.PerfilDeAcesso;

/**
 *
 * @author Rafael Pereira
 */
public class VisualizarAction implements ICommand {

    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        //Instanciando Cliente
        Cliente objCliente = new Cliente();
        
        //Verifica Usuario Cliente
        //cria uma sessao para resgatar o usuario
        HttpSession sessaoUsuario = request.getSession();
        Usuario usuarioAutenticado = (Usuario) sessaoUsuario.getAttribute("usuarioAutenticado");
        if (usuarioAutenticado != null && usuarioAutenticado.getPerfil().equals(PerfilDeAcesso.CLIENTECOMUM)) {
            objCliente = (Cliente) sessaoUsuario.getAttribute("cliente");
        }

        request.setAttribute("pagina", "pages/client/listar_agendamento.jsp");

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        AgendamentoDAO objAgendamentoDao = new AgendamentoDAO();
        FuncionarioDAO objFuncionarioDao = new FuncionarioDAO();
        ServicoDAO objServicoDao = new ServicoDAO();

        ArrayList<Agendamento> arr = objAgendamentoDao.listarCliente(new Agendamento(objCliente));

        for (Agendamento objAgendamento : arr) {
            objFuncionarioDao.buscar(objAgendamento.getFuncionario());
            objServicoDao.buscar_dados_basicos(objAgendamento.getServico());
        }

        return objgson.toJson(arr);

    }
}
