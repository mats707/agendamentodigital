/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.agenda;

import command.servico.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.ServicoDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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
public class AgendarAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServicoDAO servicoDAO = new ServicoDAO();

        int day = Calendar.DAY_OF_MONTH;
        int month = Calendar.MONTH;
        int year = Calendar.YEAR;
        String datahoje = year + "-" + month + "-" + day;
        String datamaxima = year + "-" + (month+1) + "-" + day;

        request.setAttribute("pagina", "pages/admin/agendamento/agendar.jsp");

        String nome = request.getParameter("nome");
        String descricao = request.getParameter("descricao");
        String categoriaFinal = request.getParameter("categoriaFinal");
        String valor = request.getParameter("valor");
        String tempo = request.getParameter("duracao");
        String[] funcionariosString = request.getParameterValues("listaFuncionarios");

//        String[] funcionariosString = null;
//        if (funcionariosString != null) {
//            funcionariosString = listFuncionarios("\\,");
//        }
        String sqlState = "0";
        String funcaoMsg = "Carregando...";
        String funcaoStatus = "info";

        if (nome != null && descricao != null && categoriaFinal != null
                && valor != null && tempo != null && funcionariosString != null) {

            //Ajustes no formato dos campos
            valor = valor.replace(",", ".");
            tempo = "00:" + tempo + ":00";

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
            objServico.setFuncionarios(funcionarios);

            System.out.println(objServico);

            sqlState = servicoDAO.cadastrar(objServico);
            if (sqlState == "0") {
                funcaoMsg = "Cadastrado com sucesso!";
                funcaoStatus = "success";
            } else {
                funcaoMsg = "Não foi possível cadastrar a categoria, tente novamente!";
                funcaoStatus = "error";
            }

            request.setAttribute("funcaoMsg", funcaoMsg);
            request.setAttribute("funcaoStatus", funcaoStatus);
            return funcaoMsg;
        }
        request.setAttribute("funcaoMsg", funcaoMsg);
        request.setAttribute("funcaoStatus", funcaoStatus);
        request.setAttribute("datahoje", datahoje);
        request.setAttribute("datamaxima", datamaxima);
        return funcaoMsg;
    }
}
