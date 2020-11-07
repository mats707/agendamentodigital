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
import java.text.DateFormat;
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
            + "servico = ?, "
            + "funcionario = ?, "
            + "status = (select id from sistema.statusAgendamento where nome ilike ?) "
            + "WHERE id = ? AND cliente=?";
    private static final String ALTERAR_STATUS = "update sistema.agendamento "
            + " set status= (select id from sistema.statusAgendamento where nome ilike ?) "
            + " where cliente=? and dataAgendamento=?::DATE and horarioAgendamento=?::TIME;";
    private static final String BUSCAR_ID = "SELECT id, dataAgendamento::DATE, horarioAgendamento::TIME, cliente, servico, funcionario, status FROM sistema.agendamento WHERE id=? ORDER BY dataAgendamento, horarioAgendamento, cliente";
    private static final String LISTAR = "SELECT a.id, a.dataAgendamento::DATE, a.horarioAgendamento::TIME, a.cliente, a.servico, a.funcionario, s.nome as status FROM sistema.agendamento a INNER JOIN sistema.statusAgendamento s ON a.status = s.id ORDER BY dataAgendamento, horarioAgendamento, cliente";
    private static final String LISTAR_HORARIOS_OCUPADOS = ""
            + "select\n"
            + "	 tab_func.horarioAgendamento\n"
            + "	,tab_func.horarioFinalAgendamento\n"
            + "	,tab_func.cliente\n"
            + "	,tab_func.funcionario\n"
            + "from\n"
            + "(\n"
            + "	select\n"
            + "		 age.id as agendamento\n"
            + "		,age.dataAgendamento\n"
            + "		,age.horarioAgendamento\n"
            + "		,age.horarioAgendamento + ser.duracao as horarioFinalAgendamento\n"
            + "		,age.servico as servico\n"
            + "		,age.funcionario\n"
            + "		,age.cliente\n"
            + "	from sistema.agendamento age\n"
            + "	inner join sistema.servico ser\n"
            + "	on age.servico = ser.id\n"
            + "	where\n"
            + "		age.dataAgendamento = ?::DATE\n"
            + "		and age.funcionario = ?\n"
            + "		and age.status = 1\n"
            + ") as tab_func\n"
            + "inner join\n"
            + "(\n"
            + "	select\n"
            + "		t_servico.id,\n"
            + "		t1.horarioSolicitado,\n"
            + "		t1.horarioSolicitado + t_servico.duracao as horarioFinalSolicitado\n"
            + "	from (\n"
            + "		select\n"
            + "			? as servico\n"
            + "			,?::TIME as horarioSolicitado\n"
            + "	) t1\n"
            + "	inner join sistema.servico t_servico\n"
            + "	on t_servico.id = t1.servico\n"
            + ") as tab_param\n"
            + "on (tab_param.horarioSolicitado >= tab_func.horarioAgendamento::time\n"
            + "AND tab_param.horarioSolicitado < tab_func.horarioFinalAgendamento::time)\n"
            + "OR (tab_param.horarioFinalSolicitado > tab_func.horarioAgendamento::time\n"
            + "AND tab_param.horarioFinalSolicitado < tab_func.horarioFinalAgendamento::time)\n"
            + "OR  (tab_func.horarioAgendamento::time >= tab_param.horarioSolicitado\n"
            + "AND tab_func.horarioAgendamento::time < tab_param.horarioFinalSolicitado)\n"
            + "OR  (tab_func.horarioFinalAgendamento::time > tab_param.horarioSolicitado\n"
            + "AND tab_func.horarioFinalAgendamento::time < tab_param.horarioFinalSolicitado)\n"
            + "UNION\n"
            + "select\n"
            + "	tab_cli.horarioAgendamento\n"
            + "	,tab_cli.horarioFinalAgendamento\n"
            + "	,tab_cli.cliente\n"
            + "	,tab_cli.funcionario\n"
            + "from\n"
            + "(\n"
            + "	select\n"
            + "		 age.id as agendamento\n"
            + "		,age.dataAgendamento\n"
            + "		,age.horarioAgendamento\n"
            + "		,age.horarioAgendamento + ser.duracao as horarioFinalAgendamento\n"
            + "		,age.servico as servico\n"
            + "		,age.funcionario\n"
            + "		,age.cliente\n"
            + "	from sistema.agendamento age\n"
            + "	inner join sistema.servico ser\n"
            + "	on age.servico = ser.id\n"
            + "	where\n"
            + "		age.dataAgendamento = ?::DATE\n"
            + "		and age.cliente = ?\n"
            + "		and age.status = 1\n"
            + ") as tab_cli\n"
            + "inner join\n"
            + "(\n"
            + "	select\n"
            + "		t_servico.id,\n"
            + "		t1.horarioSolicitado,\n"
            + "		t1.horarioSolicitado + t_servico.duracao as horarioFinalSolicitado\n"
            + "	from (\n"
            + "		select\n"
            + "			? as servico\n"
            + "			,?::TIME as horarioSolicitado\n"
            + "	) t1\n"
            + "	inner join sistema.servico t_servico\n"
            + "	on t_servico.id = t1.servico\n"
            + ") as tab_param\n"
            + "on (tab_param.horarioSolicitado >= tab_cli.horarioAgendamento::time\n"
            + "AND tab_param.horarioSolicitado < tab_cli.horarioFinalAgendamento::time)\n"
            + "OR (tab_param.horarioFinalSolicitado > tab_cli.horarioAgendamento::time\n"
            + "AND tab_param.horarioFinalSolicitado < tab_cli.horarioFinalAgendamento::time)\n"
            + "OR  (tab_cli.horarioAgendamento::time >= tab_param.horarioSolicitado\n"
            + "AND tab_cli.horarioAgendamento::time < tab_param.horarioFinalSolicitado)\n"
            + "OR  (tab_cli.horarioFinalAgendamento::time > tab_param.horarioSolicitado\n"
            + "AND tab_cli.horarioFinalAgendamento::time < tab_param.horarioFinalSolicitado);";
    private static final String LISTAR_CLIENTE = "SELECT a.id, a.dataAgendamento::DATE, a.horarioAgendamento::TIME, a.cliente, a.servico, a.funcionario, s.nome as status FROM sistema.agendamento a INNER JOIN sistema.statusAgendamento s ON a.status = s.id WHERE a.cliente=? and s.nome=? ORDER BY dataAgendamento, horarioAgendamento";
    private static final String LISTAR_FUNCIONARIO = "SELECT id, dataAgendamento::DATE, horarioAgendamento::TIME, cliente, servico, funcionario, status FROM sistema.agendamento WHERE funcionario=? ORDER BY dataAgendamento, horarioAgendamento, cliente";
    private static final String LISTAR_STATUS = "SELECT id, dataAgendamento::DATE, horarioAgendamento::TIME, cliente, servico, funcionario, status FROM sistema.agendamento WHERE status=? ORDER BY dataAgendamento, horarioAgendamento, cliente";
    private static final String DELETAR = "DELETE FROM sistema.agendamento WHERE id = ?";
    private static final String BUSCAR_AGENDAMENTO = "select ag.id, ag.dataAgendamento::DATE, ag.horarioAgendamento::TIME, ag.cliente,ag.servico,ag.funcionario,s.nome as status \n"
            + "from sistema.agendamento as ag \n"
            + "INNER JOIN sistema.statusAgendamento s ON ag.status = s.id \n"
            + "where ag.dataAgendamento = ? and ag.horarioAgendamento =? and ag.cliente = ?;";
    private static final String VERIFICAR_STATUS_CLIENTE = "SELECT\n"
            + "ag.servico,\n"
            + "ag.funcionario,\n"
            + "s.nome as status\n"
            + "FROM sistema.agendamento ag\n"
            + "INNER JOIN sistema.statusAgendamento s ON ag.status = s.id\n"
            + "where\n"
            + "ag.dataAgendamento = ?::DATE and\n"
            + "ag.horarioAgendamento = ?::TIME and\n"
            + "ag.cliente = ? and\n"
            + "ag.servico = ? and\n"
            + "ag.funcionario = ?;";
    private static final String VERIFICAR_STATUS_FUNCIONARIO = "SELECT\n"
            + "ag.servico,\n"
            + "ag.cliente,\n"
            + "s.nome as status\n"
            + "FROM sistema.agendamento ag\n"
            + "INNER JOIN sistema.statusAgendamento s ON ag.status = s.id\n"
            + "where\n"
            + "ag.dataAgendamento = ?::DATE and\n"
            + "ag.horarioAgendamento = ?::TIME and\n"
            + "ag.funcionario = ? and\n"
            + "ag.servico = ? and\n"
            + "ag.cliente = ?;";

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
    public ArrayList<Map<String, String>> listarHorariosOcupados(Agendamento agendamento) {

        ArrayList<Map<String, String>> arrayHorariosOcupados = new ArrayList<>();

        try {

            //Conexao
            conexao = ConectaBanco.getConexao();
            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(LISTAR_HORARIOS_OCUPADOS);
            pstmt.setDate(1, new java.sql.Date(agendamento.getDataAgendamento().getTime()));
            pstmt.setInt(2, agendamento.getFuncionario().getIdFuncionario());
            pstmt.setInt(3, agendamento.getServico().getIdServico());
            pstmt.setTime(4, new java.sql.Time(agendamento.getHoraAgendamento().getTime()));
            pstmt.setDate(5, new java.sql.Date(agendamento.getDataAgendamento().getTime()));
            pstmt.setInt(6, agendamento.getCliente().getIdCliente());
            pstmt.setInt(7, agendamento.getServico().getIdServico());
            pstmt.setTime(8, new java.sql.Time(agendamento.getHoraAgendamento().getTime()));

            //executa
            ResultSet rs = pstmt.executeQuery();

            DateFormat formatter = new SimpleDateFormat("kk:mm");
            Time horarioAgendamento = null;
            Time horarioFinalAgendamento = null;

            while (rs.next()) {
                Map<String, String> arrHorariosOcupados = new HashMap<String, String>();
                arrHorariosOcupados.put("cliente", rs.getString("cliente"));
                arrHorariosOcupados.put("funcionario", rs.getString("funcionario"));
                //Parse horaAgendamento
                try {
                    horarioAgendamento = new java.sql.Time(formatter.parse(rs.getString("horarioAgendamento")).getTime());
                } catch (ParseException ex) {
                    Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    horarioFinalAgendamento = new java.sql.Time(formatter.parse(rs.getString("horarioFinalAgendamento")).getTime());
                } catch (ParseException ex) {
                    Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                arrHorariosOcupados.put("horarioAgendamento", horarioAgendamento.toString().substring(0, 5));
                arrHorariosOcupados.put("horarioFinalAgendamento", horarioFinalAgendamento.toString().substring(0, 5));
                arrayHorariosOcupados.add(arrHorariosOcupados);
//                [
//                    {
//                        "cliente": "X",
//                        "funcionario": "X",
//                        "horarioAgendamento": "kk:mm" ,
//                        "horarioFinalAgendamento": "kk:mm"
//                    },
//                    {
//                        "cliente": "X",
//                        "funcionario": "X",
//                        "horarioAgendamento": "kk:mm" ,
//                        "horarioFinalAgendamento": "kk:mm"
//                    }
//                ]                
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
            pstmt.setString(2, agendamento.getStatus().toString());

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
//        PreparedStatement pstmt;
//        try {
//            conexao = ConectaBanco.getConexao();
//            pstmt = conexao.prepareStatement(ALTERAR);
//            pstmt.setDate(1, new java.sql.Date(agendamento.getDataAgendamento().getTime()));
//            pstmt.setTime(2, agendamento.getHoraAgendamento());
//            pstmt.setInt(3, agendamento.getServico().getIdServico());
//            pstmt.setInt(4, agendamento.getFuncionario().getIdFuncionario());
//            pstmt.setString(5, agendamento.getStatus().toString());
//            pstmt.setInt(6, agendamento.getIdAgendamento());
//            pstmt.setInt(7, agendamento.getCliente().getIdCliente());
//            pstmt.executeUpdate();
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
    public Agendamento buscarCancelar(Agendamento agendamento) {
        try {

            //Conexao
            conexao = ConectaBanco.getConexao();

            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(BUSCAR_AGENDAMENTO);

            pstmt.setDate(1, new java.sql.Date(agendamento.getDataAgendamento().getTime()));
            pstmt.setTime(2, new java.sql.Time(agendamento.getHoraAgendamento().getTime()));
            pstmt.setInt(3, agendamento.getCliente().getIdCliente());

            //executa
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                agendamento.setIdAgendamento(rs.getInt("id"));
                agendamento.setDataAgendamento(rs.getDate("dataAgendamento"));
                agendamento.setHoraAgendamento(rs.getTime("horarioAgendamento"));

                Cliente objCliente = new Cliente();
                objCliente.setIdCliente(rs.getInt("cliente"));
                agendamento.setCliente(objCliente);

                Servico objServico = new Servico();
                objServico.setIdServico(rs.getInt("servico"));
                agendamento.setServico(objServico);

                Funcionario objFuncionario = new Funcionario();
                objFuncionario.setIdFuncionario(rs.getInt("funcionario"));
                agendamento.setFuncionario(objFuncionario);

                agendamento.setStatus(StatusAgendamento.valueOf(rs.getString("status")));

            }

            return agendamento;

        } catch (Exception ex) {

            return agendamento;

        } finally {

            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public String alterarAgendamento(Agendamento agendamento) {
        return null;

    }

    @Override
    public boolean excluir(Agendamento agendamento) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String alterarStatus(Agendamento agendamento) {
        String sqlReturnCode = "0";

        PreparedStatement pstmt;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(ALTERAR_STATUS);
            pstmt.setString(1, agendamento.getStatus().toString());
            pstmt.setInt(2, agendamento.getCliente().getIdCliente());
            pstmt.setDate(3, new java.sql.Date(agendamento.getDataAgendamento().getTime()));
            pstmt.setTime(4, agendamento.getHoraAgendamento());
            pstmt.executeUpdate();

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
    public Agendamento verificarStatusCliente(Agendamento objAgendamento) {

        Agendamento agendamentoVerificado = new Agendamento();

        PreparedStatement pstmt;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(VERIFICAR_STATUS_CLIENTE);
            pstmt.setDate(1, new java.sql.Date(objAgendamento.getDataAgendamento().getTime()));
            pstmt.setTime(2, new java.sql.Time(objAgendamento.getHoraAgendamento().getTime()));
            pstmt.setInt(3, objAgendamento.getCliente().getIdCliente());
            pstmt.setInt(4, objAgendamento.getServico().getIdServico());
            pstmt.setInt(5, objAgendamento.getFuncionario().getIdFuncionario());

            //executa
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Servico objServico = new Servico();
                objServico.setIdServico(rs.getInt("servico"));
                agendamentoVerificado.setServico(objServico);

                Funcionario objFuncionario = new Funcionario();
                objFuncionario.setIdFuncionario(rs.getInt("funcionario"));
                agendamentoVerificado.setFuncionario(objFuncionario);
                agendamentoVerificado.setStatus(StatusAgendamento.valueOf(rs.getString("status")));
            }

            return agendamentoVerificado;

        } catch (SQLException ex) {

            Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return agendamentoVerificado;

        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public Agendamento verificarStatusFuncionario(Agendamento objAgendamento) {

        Agendamento agendamentoVerificado = new Agendamento();

        PreparedStatement pstmt;
        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(VERIFICAR_STATUS_FUNCIONARIO);
            pstmt.setDate(1, new java.sql.Date(objAgendamento.getDataAgendamento().getTime()));
            pstmt.setTime(2, new java.sql.Time(objAgendamento.getHoraAgendamento().getTime()));
            pstmt.setInt(3, objAgendamento.getFuncionario().getIdFuncionario());
            pstmt.setInt(4, objAgendamento.getServico().getIdServico());
            pstmt.setInt(5, objAgendamento.getCliente().getIdCliente());

            //executa
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Servico objServico = new Servico();
                objServico.setIdServico(rs.getInt("servico"));
                agendamentoVerificado.setServico(objServico);

                Cliente objCliente = new Cliente();
                objCliente.setIdCliente(rs.getInt("cliente"));
                agendamentoVerificado.setCliente(objCliente);

                agendamentoVerificado.setStatus(StatusAgendamento.valueOf(rs.getString("status")));
            }

            return agendamentoVerificado;

        } catch (SQLException ex) {

            Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return agendamentoVerificado;

        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

};
