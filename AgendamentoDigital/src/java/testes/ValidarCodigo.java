/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.AgendamentoDAO;
import dao.CategoriaServicoDAO;
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
        //AlterarServico();
        //DeletarServico();
        ListarServico();
        //ListarServicoApiPorId();
        //ListarServicoApiPorCategoria();
        //AgendarServico();
        //ListarAgendamento();
    }

    public static void AgendarServico() {

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        String idCliente = "1";
        String listaServico = "1";
        String listaFuncionarios = "1";
        String datahora = "21-05-2020 20:00";
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
            } else if (sqlState.equalsIgnoreCase("unqagendamentocliente")) {
                System.out.println(sqlState);
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

    public static void AlterarServico() {

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();
        ServicoDAO servicoDAO = new ServicoDAO();

        String idServico = "1";
        String nome = "Corte de Cabelo Masculino";
        String descricao = "Descrição Teste";
        String categoriaFinal = "10";
        Double valor = Double.parseDouble("19,90".replace(',', '.'));
        String duracao = "30";
        String listFuncionarios = "1,2";

        String[] funcionariosString = null;
        funcionariosString = listFuncionarios.split("\\,");

        System.out.println(funcionariosString);

        String sqlState = "0";

        if (idServico != null && nome != null && descricao != null && categoriaFinal != null
                && valor != null && duracao != null && funcionariosString != null) {

            //Ajustes no formato dos campos
            Duration tempo = Duration.ofHours(Integer.parseInt("00"));
            tempo = tempo.plusMinutes(Integer.parseInt(duracao));
            tempo = tempo.plusSeconds(Integer.parseInt("00"));

            System.out.println(tempo);

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
            objServico.setValor(BigDecimal.valueOf(valor));
            objServico.setDuracao(tempo);
            objServico.setFuncionarios(funcionarios);

            System.out.println(objServico.getDuracao().toString());

            System.out.println(objgson.toJson(objServico));

            sqlState = servicoDAO.alterar(objServico);
        }
        System.out.println(sqlState);
    }

    public static void DeletarServico() {

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        Integer id = Integer.parseInt("1");
        String deletedNome = "Corte de Cabelo Masculino";
        String deletedDescricao = "Os melhores cortes de cabelo masculino";

        String sqlState = "0";
        String funcaoMsgDeleted = "";
        String funcaoStatusDeleted = "";

        if (id != null && deletedNome != null && deletedDescricao != null) {
            Servico servico = new Servico();
            servico.setIdServico(id);
            servico.setNome(deletedNome);
            servico.setDescricao(deletedDescricao);

            ServicoDAO servicoDAO = new ServicoDAO();
            Servico servicoSolicitado = servicoDAO.buscaCompleta(servico);

            System.out.println(objgson.toJson(servicoSolicitado));
            //Chamada da DAO para Cadastrar
            sqlState = servicoDAO.deletar(servicoSolicitado);
            System.out.println(sqlState);
            //Verifica o retorno da DAO (banco de dados)
            if (sqlState == "0") {
                funcaoMsgDeleted = "Deletado com sucesso!";
                funcaoStatusDeleted = "success";
            } else if (sqlState.equalsIgnoreCase("ERROR: update or delete on table \"servico\" violates foreign key constraint \"fkservico\" on table \"agendamento\"")) {
                funcaoMsgDeleted = "Você possui um agendamento com esse serviço! Delete o agendamento primeiro!";
                funcaoStatusDeleted = "error";
            } else {
                funcaoMsgDeleted = "Não foi possível deletar o serviço, tente novamente mais tarde!";
                funcaoStatusDeleted = "error";
            }
        } else {
            funcaoMsgDeleted = "Serviço inválido!";
            funcaoStatusDeleted = "error";
        }
        System.out.println(funcaoMsgDeleted);
    }

    public static void ListarServicoApiPorId() {

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        ServicoDAO objServicoDao = new ServicoDAO();
        FuncionarioDAO objFuncionarioDao = new FuncionarioDAO();
        CategoriaServicoDAO objCategoriaServicoDAO = new CategoriaServicoDAO();

        Servico objServico = new Servico();
        objServico.setIdServico(1);

        objServicoDao.buscar(objServico);

        if (objServico.getIdServico() != null) {
            for (Funcionario objFuncionario : objServico.getFuncionarios()) {
                objFuncionarioDao.listarCompletoId(objFuncionario);
            }
            objCategoriaServicoDAO.buscarId(objServico.getCategoria());
        }

        System.out.println(objgson.toJson(objServico));
    }

    public static void ListarServico() {

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        ServicoDAO objServicoDao = new ServicoDAO();

        ArrayList<Servico> arr = new ArrayList<Servico>();
        arr = objServicoDao.listar();

        FuncionarioDAO objFuncionarioDao = new FuncionarioDAO();
        CategoriaServicoDAO objCategoriaServicoDAO = new CategoriaServicoDAO();

        for (Servico objServico : arr) {
            if (objServico.getIdServico() != null) {
                for (Funcionario objFuncionario : objServico.getFuncionarios()) {
                    objFuncionarioDao.listarCompletoId(objFuncionario);
                }
                objCategoriaServicoDAO.buscarId(objServico.getCategoria());
            }
        }

        System.out.println(objgson.toJson(arr));
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

    public static void ListarAgendamento() {

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        AgendamentoDAO objAgendamentoDao = new AgendamentoDAO();
        FuncionarioDAO objFuncionarioDao = new FuncionarioDAO();

        ArrayList<Agendamento> arr = objAgendamentoDao.listar();

        for (Agendamento objAgendamento : arr) {
            objFuncionarioDao.listarCompletoId(objAgendamento.getFuncionario());
        }

        System.out.println(objgson.toJson(arr));
    }

}
