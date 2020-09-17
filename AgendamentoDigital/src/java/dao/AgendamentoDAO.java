package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.interfaces.IAgendamentoDAO;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.serial.SerialArray;
import modelos.Funcionario;
import modelos.Agendamento;
import modelos.Cliente;
import modelos.Servico;
import modelos.StatusAgendamento;
import util.ConectaBanco;

public class AgendamentoDAO implements IAgendamentoDAO {

    private static final String CADASTRAR = "INSERT INTO sistema.agendamento (id, dataAgendamento, horarioAgendamento, cliente, servico, funcionario, status) \n"
            + "VALUES (nextval('sistema.sqn_agendamento'),?::DATE,?::TIME,?,?,?,(select id from sistema.statusAgendamento where nome ilike ?));";
    private static final String ALTERAR = "UPDATE sistema.agendamento "
            + "SET dataAgendamento = ?, "
            + "horarioAgendamento = ?, "
            + "servico = ? "
            + "funcionario = ? "
            + "status = ? "
            + "WHERE id = ? AND cliente=?";
    private static final String BUSCAR_ID = "SELECT id, dataAgendamento::DATE, horarioAgendamento::TIME, cliente, servico, funcionario, status FROM sistema.agendamento WHERE id=? ORDER BY dataAgendamento, horarioAgendamento, cliente";
    private static final String LISTAR = "SELECT a.id, a.dataAgendamento::DATE, a.horarioAgendamento::TIME, a.cliente, a.servico, a.funcionario, s.nome as status FROM sistema.agendamento a INNER JOIN sistema.statusAgendamento s ON a.status = s.id ORDER BY dataAgendamento, horarioAgendamento, cliente";
    private static final String LISTAR_HORARIOS_OCUPADOS = "SELECT \n"
            + "to_char(a.horarioagendamento, 'HH:MI') as horarioagendamento \n"
            + ",EXTRACT(EPOCH FROM s.duracao)/60 as duracao_minutos \n"
            + "FROM sistema.agendamento a \n"
            + "LEFT JOIN sistema.servico s \n"
            + "ON s.id = a.servico \n"
            + "WHERE a.funcionario = ? AND a.dataagendamento = ?::DATE";
    private static final String LISTAR_CLIENTE = "SELECT a.id, a.dataAgendamento::DATE, a.horarioAgendamento::TIME, a.cliente, a.servico, a.funcionario, s.nome as status FROM sistema.agendamento a INNER JOIN sistema.statusAgendamento s ON a.status = s.id WHERE a.cliente=? ORDER BY dataAgendamento, horarioAgendamento";
    private static final String LISTAR_FUNCIONARIO = "SELECT id, dataAgendamento::DATE, horarioAgendamento::TIME, cliente, servico, funcionario, status FROM sistema.agendamento WHERE funcionario=? ORDER BY dataAgendamento, horarioAgendamento, cliente";
    private static final String LISTAR_STATUS = "SELECT id, dataAgendamento::DATE, horarioAgendamento::TIME, cliente, servico, funcionario, status FROM sistema.agendamento WHERE status=? ORDER BY dataAgendamento, horarioAgendamento, cliente";
    private static final String DELETAR = "DELETE FROM sistema.agendamento WHERE id = ?";

    private Connection conexao;

    Date date = new Date();
    SimpleDateFormat simpDate24 = new SimpleDateFormat("kk:mm:ss");

    @Override
    public String cadastrar(Agendamento agendamento) {

        conexao = ConectaBanco.getConexao();

        String sqlReturnCode = "0";

        PreparedStatement pstmt = null;
        try {
            pstmt = conexao.prepareStatement(CADASTRAR, Statement.RETURN_GENERATED_KEYS);
            pstmt.setDate(1, new java.sql.Date(agendamento.getDataAgendamento().getTime()));
            pstmt.setTime(2, agendamento.getHoraAgendamento());
            pstmt.setInt(3, agendamento.getCliente().getIdCliente());
            pstmt.setInt(4, agendamento.getServico().getIdServico());
            pstmt.setInt(5, agendamento.getFuncionario().getIdFuncionario());
            pstmt.setString(6, agendamento.getStatus().toString());

            pstmt.execute();

            final ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                agendamento.setIdAgendamento(Integer.parseInt(rs.getString("id")));
            }

            return sqlReturnCode;

        } catch (SQLException sqlErro) {

            sqlReturnCode = sqlErro.getSQLState();
            if (sqlReturnCode.equalsIgnoreCase("23505")) { //Significa que violou uma unique constraint
                return sqlErro.getMessage().split("\"")[1];
            } else {
                return sqlReturnCode;
            }
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    @Override
    public ArrayList<Agendamento> listar() {

        ArrayList<Agendamento> listaAgendamento = new ArrayList<>();
        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        try {

            //Conexao
            conexao = ConectaBanco.getConexao();
            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(LISTAR);
            //executa
            ResultSet rs = pstmt.executeQuery();
            System.out.println(pstmt.toString());
            while (rs.next()) {

                Agendamento novoAgendamento = new Agendamento();
                novoAgendamento.setIdAgendamento(rs.getInt("id"));
                novoAgendamento.setDataAgendamento(rs.getDate("dataAgendamento"));
                novoAgendamento.setHoraAgendamento(rs.getTime("horarioAgendamento"));
                novoAgendamento.setStatus(StatusAgendamento.valueOf(rs.getString("status")));

                Cliente objCliente = new Cliente();
                objCliente.setIdCliente(rs.getInt("cliente"));
                novoAgendamento.setCliente(objCliente);

                Servico objServico = new Servico();
                objServico.setIdServico(rs.getInt("servico"));
                novoAgendamento.setServico(objServico);

                Funcionario objFuncionario = new Funcionario();
                objFuncionario.setIdFuncionario(rs.getInt("funcionario"));
                novoAgendamento.setFuncionario(objFuncionario);

                System.out.println(objgson.toJson(novoAgendamento));
                //add na lista
                listaAgendamento.add(novoAgendamento);
            }
            return listaAgendamento;

        } catch (Exception ex) {
            System.out.println(ex);
            return listaAgendamento;
        } finally {
            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public  ArrayList<Map<String, String>> listarHorariosOcupados(Agendamento agendamento) {
        
        ArrayList<Map<String, String>> arrayHorariosOcupados = new ArrayList<>();

        try {

            //Conexao
            conexao = ConectaBanco.getConexao();
            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(LISTAR_HORARIOS_OCUPADOS);
            pstmt.setInt(1, agendamento.getFuncionario().getIdFuncionario());
            pstmt.setDate(2, new java.sql.Date(agendamento.getDataAgendamento().getTime()));

            //executa
            ResultSet rs = pstmt.executeQuery();
            System.out.println(pstmt.toString());
            while (rs.next()) {
                Map<String, String> arrHorariosOcupados = new HashMap<String, String>();
                arrHorariosOcupados.put("duracaoServico",rs.getString("duracao_minutos"));
                arrHorariosOcupados.put("hora",rs.getString("horarioAgendamento"));
                arrayHorariosOcupados.add(arrHorariosOcupados);
            }
            return arrayHorariosOcupados;

        } catch (Exception ex) {
            System.out.println(ex);
            return arrayHorariosOcupados;
        } finally {
            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ArrayList<Agendamento> listarCliente(Agendamento agendamento) {

        ArrayList<Agendamento> listaAgendamento = new ArrayList<>();
        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        try {
            //Conexao
            conexao = ConectaBanco.getConexao();

            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(LISTAR_CLIENTE);
            pstmt.setInt(1, agendamento.getCliente().getIdCliente());

            //executa
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Agendamento novoAgendamento = new Agendamento();
                novoAgendamento.setIdAgendamento(rs.getInt("id"));
                novoAgendamento.setDataAgendamento(rs.getDate("dataAgendamento"));
                novoAgendamento.setHoraAgendamento(rs.getTime("horarioAgendamento"));
                novoAgendamento.setStatus(StatusAgendamento.valueOf(rs.getString("status")));

                Cliente objCliente = new Cliente();
                objCliente.setIdCliente(rs.getInt("cliente"));
                novoAgendamento.setCliente(objCliente);

                Servico objServico = new Servico();
                objServico.setIdServico(rs.getInt("servico"));
                novoAgendamento.setServico(objServico);

                Funcionario objFuncionario = new Funcionario();
                objFuncionario.setIdFuncionario(rs.getInt("funcionario"));
                novoAgendamento.setFuncionario(objFuncionario);

                System.out.println(objgson.toJson(novoAgendamento));
                //add na lista
                listaAgendamento.add(novoAgendamento);
            }
            return listaAgendamento;

        } catch (Exception ex) {
            System.out.println(ex);
            return listaAgendamento;
        } finally {
            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

//    @Override
//    public ArrayList<Agendamento> listarPorCategoria(Agendamento agendamento) {
//        ArrayList<Agendamento> listaAgendamento = new ArrayList<>();
//        ArrayList<Funcionario> listaFuncionario = new ArrayList<>();
//        Integer[] funcionarios = null;
//        Array arrayFuncionarios = null;
//        ArrayList<Integer> listaCamposAdicionais = new ArrayList<>();
//        Integer[] camposAdicionais = null;
//        Array arrayCamposAdicionais = null;
//
//        try {
//
//            //Conexao
//            conexao = ConectaBanco.getConexao();
//            //cria comando SQL
//            PreparedStatement pstmt = conexao.prepareStatement(LISTAR_POR_CATEGORIA);
//            //Envia Parâmetros para a QUERY
//            System.out.println(agendamento.getCategoria().getIdCategoriaAgendamento());
//            pstmt.setInt(1, agendamento.getCategoria().getIdCategoriaAgendamento());
//            //executa
//            ResultSet rs = pstmt.executeQuery();
//            System.out.println(pstmt.toString());
//            while (rs.next()) {
//                Agendamento novoAgendamento = new Agendamento();
//                novoAgendamento.setIdAgendamento(rs.getInt("id"));
//                novoAgendamento.setNome(rs.getString("nome"));
//                novoAgendamento.setDescricao(rs.getString("descricao"));
//                novoAgendamento.setValor(parseBigDecimal(rs.getString("valor"), Locale.FRANCE));
//
//                // get the hours, minutes and seconds value and add it to the duration
//                String[] tempo = rs.getString("duracao").split(":");
//                Duration duracao = Duration.ofHours(Integer.parseInt(tempo[0]));
//                duracao = duracao.plusMinutes(Integer.parseInt(tempo[1]));
//                duracao = duracao.plusSeconds(Integer.parseInt(tempo[2]));
//                novoAgendamento.setDuracao(duracao);
//
//                try {
//                    if (rs.getArray("funcionarios") != null) {
//                        arrayFuncionarios = conexao.createArrayOf("INTEGER", (Object[]) rs.getArray("funcionarios").getArray());
//                    }
//                } catch (SQLException ex) {
//                    Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                arrayFuncionarios = rs.getArray("funcionarios");
//                funcionarios = (Integer[]) arrayFuncionarios.getArray();
//                for (int i = 0; i < funcionarios.length; i++) {
//                    Funcionario novoFuncionario = new Funcionario();
//                    novoFuncionario.setIdFuncionario(funcionarios[i]);
//                    listaFuncionario.add(novoFuncionario);
//                }
//                novoAgendamento.setFuncionarios(listaFuncionario);
//                try {
//                    if (rs.getArray("camposadicionais") != null) {
//                        arrayCamposAdicionais = conexao.createArrayOf("INTEGER", (Object[]) rs.getArray("camposadicionais").getArray());
//                    }
//                } catch (SQLException ex) {
//                    Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                arrayCamposAdicionais = rs.getArray("camposadicionais");
//                if (arrayCamposAdicionais != null) {
//                    camposAdicionais = (Integer[]) arrayCamposAdicionais.getArray();
//                    for (int i = 0; i < camposAdicionais.length; i++) {
//                        Integer campoAdicional = null;
//                        campoAdicional = camposAdicionais[i];
//                        listaCamposAdicionais.add(campoAdicional);
//                    }
//                }
//                novoAgendamento.setCamposadicionais(listaCamposAdicionais);
//                CategoriaAgendamento objCategoria = new CategoriaAgendamento();
//                objCategoria.setIdCategoriaAgendamento(rs.getInt("categoria"));
//                novoAgendamento.setCategoria(objCategoria);
//
//                //add na lista
//                listaAgendamento.add(novoAgendamento);
//            }
//            return listaAgendamento;
//
//        } catch (Exception ex) {
//            System.out.println(ex);
//            return listaAgendamento;
//        } finally {
//            try {
//                conexao.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
//
//
//    public static BigDecimal parseBigDecimal(final String amount, final Locale locale) throws ParseException {
//        final NumberFormat format = NumberFormat.getNumberInstance(locale);
//        if (format instanceof DecimalFormat) {
//            ((DecimalFormat) format).setParseBigDecimal(true);
//        }
//        return (BigDecimal) format.parse(amount.replaceAll("[^\\d.,]", ""));
//    }
//
//    @Override
//    public Agendamento buscaCompleta(Agendamento agendamento) {
//
//        try {
//
//            //Conexao
//            conexao = ConectaBanco.getConexao();
//
//            //cria comando SQL
//            PreparedStatement pstmt = conexao.prepareStatement(BUSCA_COMPLETA);
//
//            pstmt.setInt(1, agendamento.getIdAgendamento());
//            pstmt.setString(2, agendamento.getNome());
//            pstmt.setString(3, agendamento.getDescricao());
//            pstmt.setBigDecimal(4, agendamento.getValor());
//
//            agendamento.setIdAgendamento(null);
//            agendamento.setNome(null);
//            agendamento.setDescricao(null);
//            agendamento.setValor(null);
//
//            //executa
//            ResultSet rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                agendamento.setIdAgendamento(Integer.parseInt(rs.getString("id")));
//                agendamento.setNome(rs.getString("nome"));
//                agendamento.setDescricao(rs.getString("descricao"));
//                agendamento.setValor(parseBigDecimal(rs.getString("valor"), Locale.FRANCE));
//            }
//
//            return agendamento;
//
//        } catch (Exception ex) {
//
//            return agendamento;
//
//        } finally {
//
//            try {
//                conexao.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//        }
//    }
//
//    @Override
//    public String alterarAgendamento(Agendamento agendamento) {
//
//        String sqlReturnCode = "0";
//
//        PreparedStatement pstmt = null;
//        try {
//            conexao = ConectaBanco.getConexao();
//            pstmt = conexao.prepareStatement(ALTERA_SERVICO, Statement.RETURN_GENERATED_KEYS);
//            pstmt.setString(1, agendamento.getNome());
//            pstmt.setString(2, agendamento.getDescricao());
//            pstmt.setBigDecimal(3, agendamento.getValor());
//            pstmt.setInt(4, agendamento.getIdAgendamento());
//            pstmt.execute();
//
//            final ResultSet rs = pstmt.getGeneratedKeys();
//            if (rs.next()) {
//                agendamento.setIdAgendamento(rs.getInt("id"));
//            }
//
//            return sqlReturnCode;
//
//        } catch (SQLException sqlErro) {
//
//            sqlReturnCode = sqlErro.getSQLState();
//
//            return sqlReturnCode;
//
//        } finally {
//            if (conexao != null) {
//                try {
//                    conexao.close();
//                } catch (SQLException ex) {
//                    throw new RuntimeException(ex);
//                }
//            }
//        }
//    }
//
//    @Override
//    public boolean excluir(Agendamento agendamento) {
//
//        PreparedStatement pstmt = null;
//        try {
//            conexao = ConectaBanco.getConexao();
//            pstmt = conexao.prepareStatement(DELETAR);
//            pstmt.setInt(1, agendamento.getIdAgendamento());
//            pstmt.execute();
//
//            return true;
//
//        } catch (SQLException sqlErro) {
//
//            return false;
//
//        } finally {
//            if (conexao != null) {
//                try {
//                    conexao.close();
//                } catch (SQLException ex) {
//                    throw new RuntimeException(ex);
//                }
//            }
//        }
//    }
    @Override
    public Agendamento buscaCompleta(Agendamento agendamento) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String alterarAgendamento(Agendamento agendamento) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean excluir(Agendamento agendamento) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
};
