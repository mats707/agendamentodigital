/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.ServicoDAO;
import dao.FuncionarioDAO;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import modelos.CategoriaServico;
import modelos.Funcionario;
import modelos.Servico;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

/**
 *
 * @author mathm
 */
public class ValidarServico {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //CadastrarServico();
        //ListarServicoApi();
        ListarServicoApiPorCategoria();
    }

    public static void CadastrarServico() {
        ServicoDAO servicoDAO = new ServicoDAO();

        String nome = "Serviço Teste";
        String descricao = "Descrição Teste";
        String categoriaFinal = "18";
        Double valor = Double.parseDouble("25,90".replace(',', '.'));
        String duracao = "15";
        String listFuncionarios = "1,2,3";

        String[] funcionariosString = null;
        System.out.println(funcionariosString);
        funcionariosString = listFuncionarios.split("\\,");

        System.out.println(funcionariosString);

        String sqlState = "0";

        if (nome != null && descricao != null && categoriaFinal != null
                && valor != null && duracao != null && funcionariosString != null) {

            //Ajustes no formato dos campos
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
            objServico.setValor(BigDecimal.valueOf(valor));
            objServico.setDuracao(tempo);
            objServico.setFuncionarios(funcionarios);

            sqlState = servicoDAO.cadastrar(objServico);
        }
        System.out.println(sqlState);
    }

    public static void ListarServicoApiPorCategoria() {

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        Servico servico = new Servico();
        CategoriaServico categoria = new CategoriaServico();
        categoria.setIdCategoriaServico(10);
        servico.setCategoria(categoria);

        ServicoDAO objServicoDao = new ServicoDAO();
        FuncionarioDAO objFuncionarioDao = new FuncionarioDAO();

        ArrayList<Servico> arr = objServicoDao.listarPorCategoria(servico);

        for (Servico objServico : arr) {
            for (Funcionario objFuncionario : objServico.getFuncionarios()) {
                objFuncionarioDao.listarCompletoId(objFuncionario);
            }
        }

        System.out.println(objgson.toJson(arr));
    }
}
