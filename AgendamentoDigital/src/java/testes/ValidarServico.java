/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import dao.ServicoDAO;
import java.sql.Time;
import java.util.ArrayList;
import modelos.CategoriaServico;
import modelos.Funcionario;
import modelos.Servico;

/**
 *
 * @author mathm
 */
public class ValidarServico {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ServicoDAO servicoDAO = new ServicoDAO();

        String nome = "Serviço Teste";
        String descricao = "Descrição Teste";
        String categoriaFinal = "18";
        String valor = "25,90".replace(',','.');
        String tempo = "00:15:00";
        String listFuncionarios = "1,2,3";

        String[] funcionariosString = null;
        System.out.println(funcionariosString);
        funcionariosString = listFuncionarios.split("\\,");

        System.out.println(funcionariosString);

        String sqlState = "0";

        if (nome != null && descricao != null && categoriaFinal != null
                && valor != null && tempo != null && funcionariosString != null) {

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
            objServico.setValor(Double.parseDouble(valor));
            objServico.setDuracao(Time.valueOf(tempo));
            objServico.setFuncionarios(funcionarios);

            sqlState = servicoDAO.cadastrar(objServico);
        }
        System.out.println(sqlState);
    }

}
