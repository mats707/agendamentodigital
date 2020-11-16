/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.servico;

import api.restEmpresa;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.ServicoDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jdk.nashorn.internal.objects.NativeString;
import modelos.CategoriaServico;
import modelos.Funcionario;
import modelos.Servico;
import java.time.Duration;
import modelos.Empresa;
import util.Util;

/**
 *
 * @author alunocmc
 */
public class CadastrarAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ClassNotFoundException {

        ServicoDAO servicoDAO = new ServicoDAO();
        restEmpresa apiEmpresa = new restEmpresa();
        Empresa objEmpresa = apiEmpresa.buscar();

        request.setAttribute("pagina", "/pages/admin/servicos/cadastrar.jsp");

        String nome = Util.stringToUTF8(request.getParameter("nome"));
        String descricao = Util.stringToUTF8(request.getParameter("descricao"));
        String categoriaFinal = request.getParameter("categoriaFinal");
        String valor = request.getParameter("valor");
        String duracao = request.getParameter("duracao");
        String[] funcionariosString = request.getParameterValues("listaFuncionarios");

//        String[] funcionariosString = null;
//        if (funcionariosString != null) {
//            funcionariosString = listFuncionarios("\\,");
//        }
        String sqlState = "0";
        String funcaoMsg = "Carregando...";
        String funcaoStatus = "info";

        if (nome != null && descricao != null && categoriaFinal != null
                && valor != null && duracao != null && funcionariosString != null) {

            Long intervaloAgendamentoGeralServico = objEmpresa.getIntervaloAgendamentoGeralServico().toMinutes();
            Long duracaoLong = Long.parseLong(duracao);

            if (duracaoLong < intervaloAgendamentoGeralServico) {
                funcaoMsg = "Duração inválida!\\nA empresa permite agendamentos com " + intervaloAgendamentoGeralServico.toString() + " minutos no mínimo!";
                funcaoStatus = "error";
                request.setAttribute("funcaoMsg", funcaoMsg);
                request.setAttribute("funcaoStatus", funcaoStatus);
                return funcaoMsg;
            } else if (duracaoLong % intervaloAgendamentoGeralServico != 0) {
                funcaoMsg = "Duração inválida!\\nA empresa permite duração a cada " + intervaloAgendamentoGeralServico.toString() + " minutos!";
                funcaoStatus = "error";
                request.setAttribute("funcaoMsg", funcaoMsg);
                request.setAttribute("funcaoStatus", funcaoStatus);
                return funcaoMsg;
            }

            //Ajustes no formato dos campos
            valor = valor.replace(".", "").replace(",", ".");
            Duration tempo = Duration.ofHours(Integer.parseInt("00"));
            tempo = tempo.plusMinutes(Integer.parseInt(duracao));
            tempo = tempo.plusSeconds(Integer.parseInt("00"));

            ArrayList<Funcionario> funcionarios = new ArrayList<Funcionario>();

            for (String funcionario : funcionariosString) {
                Funcionario novoFuncionario = new Funcionario();
                novoFuncionario.setIdFuncionario(Integer.parseInt(funcionario));

                funcionarios.add(novoFuncionario);
            }

            CategoriaServico categoria = new CategoriaServico();
            categoria.setIdCategoriaServico(Integer.parseInt(categoriaFinal));

            Servico objServico = new Servico();
            objServico.setNome(nome);
            objServico.setDescricao(descricao);
            objServico.setCategoria(categoria);
            objServico.setValor(BigDecimal.valueOf(Double.parseDouble(valor)));
            objServico.setDuracao(tempo);
            objServico.setFuncionarios(funcionarios);

            sqlState = servicoDAO.cadastrar(objServico);
            if (sqlState == "0") {
                funcaoMsg = "Cadastrado com sucesso!";
                funcaoStatus = "success";
            } else {
                funcaoMsg = "Não foi possível cadastrar o serviço, tente novamente!";
                funcaoStatus = "error";
            }

            request.setAttribute("funcaoMsg", funcaoMsg);
            request.setAttribute("funcaoStatus", funcaoStatus);
            return funcaoMsg;
        }
        request.setAttribute("funcaoMsg", funcaoMsg);
        request.setAttribute("funcaoStatus", funcaoStatus);
        return funcaoMsg;
    }
}
