/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.AgendamentoDAO;
import dao.ServicoDAO;
import dao.FuncionarioDAO;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import modelos.CategoriaServico;
import modelos.Funcionario;
import modelos.Servico;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelos.Agendamento;
import modelos.Cliente;
import modelos.StatusAgendamento;

/**
 *
 * @author mathm
 */
public class ValidarCodigo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //CadastrarServico();
        //ListarServicoApi();
        //ListarServicoApiPorCategoria();
        AgendarServico();
    }

    public static void AgendarServico() {

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();
        
        String idCliente = "1";
        String listaServico = "1";
        String listaFuncionarios = "2";
        String datahora = "20-05-2020 22:05";
        String categoriaFinal = "10";
        
        DateFormat formatter = new SimpleDateFormat("kk:mm");

        AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
//        String[] funcionariosString = null;
//        if (funcionariosString != null) {
//            funcionariosString = listFuncionarios("\\,");
//        }
        String sqlState = "0";

        if (idCliente != null && listaServico != null && listaFuncionarios != null
                && datahora != null) {

            //Ajustes no formato dos campos
            String data = datahora.split(" ")[0];
            String hora = datahora.split(" ")[1];
            System.out.println(data);
            System.out.println(hora);

            Funcionario objFuncionario = new Funcionario();
            objFuncionario.setIdFuncionario(Integer.parseInt(listaFuncionarios));

            Servico objServico = new Servico();
            objServico.setIdServico(Integer.parseInt(listaServico));

            Cliente objCliente = new Cliente();
            objCliente.setIdCliente(Integer.parseInt(idCliente));

            StatusAgendamento objStatus = StatusAgendamento.AGUARDANDOATENDIMENTO;

            Agendamento objAgendamento = new Agendamento();

            System.out.println(objgson.toJson(objCliente));
            objAgendamento.setCliente(objCliente);
            System.out.println(objgson.toJson(objFuncionario));
            objAgendamento.setFuncionario(objFuncionario);
            System.out.println(objgson.toJson(objServico));
            objAgendamento.setServico(objServico);
            System.out.println(objgson.toJson(objStatus));
            objAgendamento.setStatus(objStatus);
            Date dataAgendamento = null;
            Time horaAgendamento = null;

            try {
                dataAgendamento = new SimpleDateFormat("dd-MM-yyyy").parse(data);
            } catch (ParseException ex) {
                Logger.getLogger(ValidarCodigo.class.getName()).log(Level.SEVERE, null, ex);
            }
            objAgendamento.setDataAgendamento(dataAgendamento);

            try {
                horaAgendamento = new java.sql.Time(formatter.parse(hora).getTime());
            } catch (ParseException ex) {
                Logger.getLogger(ValidarCodigo.class.getName()).log(Level.SEVERE, null, ex);
            }
            objAgendamento.setHoraAgendamento(horaAgendamento);

            System.out.println(objgson.toJson(objAgendamento));

            sqlState = agendamentoDAO.cadastrar(objAgendamento);
            if (sqlState == "0") {
                System.out.println("Cadastrado com sucesso!");
            } else {
                System.out.println("Error - " + sqlState);
                System.out.println("Não foi possível cadastrar a categoria, tente novamente!");
            }
        }
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
