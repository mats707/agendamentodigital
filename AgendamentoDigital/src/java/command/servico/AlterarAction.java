/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.servico;

import dao.ServicoDAO;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jdk.nashorn.internal.objects.NativeString;
import modelos.CategoriaServico;
import modelos.Funcionario;
import modelos.Servico;

/**
 *
 * @author alunocmc
 */
public class AlterarAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) {

        ServicoDAO servicoDAO = new ServicoDAO();

        request.setAttribute("pagina", "ListarServico");

        String idServico = request.getParameter("idServico");
        String nome = request.getParameter("editedNome");
        String descricao = request.getParameter("editedDescricao");
        String categoriaFinal = request.getParameter("categoriaFinal");
        String valor = request.getParameter("editedValor");
        String duracao = request.getParameter("editedDuracao");
        String[] funcionariosString = request.getParameterValues("listaFuncionarios");

//        String[] funcionariosString = null;
//        if (funcionariosString != null) {
//            funcionariosString = listFuncionarios("\\,");
//        }
        String sqlState = "0";
        String funcaoMsg = "Carregando...";
        String funcaoStatus = "info";

        if (idServico != null && nome != null && descricao != null && categoriaFinal != null
                && valor != null && duracao != null && funcionariosString != null) {

            //Ajustes no formato dos campos
            valor = valor.replace(",", ".");
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
            objServico.setIdServico(Integer.parseInt(idServico));
            objServico.setNome(nome);
            objServico.setDescricao(descricao);
            objServico.setCategoria(categoria);
            objServico.setValor(BigDecimal.valueOf(Double.parseDouble(valor)));
            objServico.setDuracao(tempo);
            objServico.setFuncionarios(funcionarios);

            sqlState = servicoDAO.alterar(objServico);
            if (sqlState == "0") {
                funcaoMsg = "Alterado com sucesso!";
                funcaoStatus = "success";
            } else {
                funcaoMsg = "Não foi possível alterar o serviço, tente novamente!";
                funcaoStatus = "error";
            }

            request.setAttribute("funcaoMsg", funcaoMsg);
            request.setAttribute("funcaoStatus", funcaoStatus);
            return funcaoMsg;
        }
        return funcaoMsg;

    }

}
