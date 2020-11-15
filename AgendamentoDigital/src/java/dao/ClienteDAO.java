package dao;

import dao.interfaces.IClienteDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelos.Cliente;
import modelos.PerfilDeAcesso;
import modelos.Pessoa;
import modelos.Usuario;
import util.ConectaBanco;

public class ClienteDAO implements IClienteDAO {

    private static final String LISTAR = "SELECT id, pessoa FROM sistema.cliente ORDER BY id;";
    private static final String LISTAR_PESSOA = "SELECT c.id as idCli, p.id as idPessoa, p.nome, p.dataNascimento, u.id as idUsuario, u.email, u.celular, pf.nome as perfil, u.ativo FROM sistema.cliente c INNER JOIN sistema.pessoa p ON u.pessoa = p.id INNER JOIN sistema.usuario u ON p.usuario = u.id INNER JOIN sistema.perfilacesso pf ON u.perfil = pf.id ORDER BY u.id;";
    private static final String BUSCAR_USUARIO = "SELECT c.id as idCli, p.id as idPessoa, p.nome, p.dataNascimento, u.id as idUsuario, u.email, u.celular, pf.nome as perfil, u.ativo FROM sistema.cliente c INNER JOIN sistema.pessoa p ON c.pessoa = p.id INNER JOIN sistema.usuario u ON p.usuario = u.id INNER JOIN sistema.perfilacesso pf ON u.perfil = pf.id WHERE u.id = ? ORDER BY u.id;";
    private static final String BUSCAR = "SELECT id, pessoa FROM sistema.cliente WHERE id= ?;";
    private static final String CADASTRAR = "INSERT INTO sistema.cliente (id, pessoa) VALUES (NEXTVAL('sistema.sqn_cliente'),(SELECT id FROM sistema.pessoa WHERE id = ?));";
    private static final String DELETE = " FROM cliente WHERE id=?;";
    private static final String ALTERAR = "UPDATE sistema.pessoa SET nome=?, datanascimento=?::DATE WHERE id=?;";

    private Connection conexao;

    @Override
    public ArrayList<Cliente> listar() {

        //cria uma array de Obj Tipo
        ArrayList<Cliente> listaCliente = new ArrayList<Cliente>();

        try {

            //Conexao
            conexao = ConectaBanco.getConexao();

            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(LISTAR);

            //executa
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                //a cada loop
                Cliente novoCliente = new Cliente();
                novoCliente.setIdCliente(Integer.parseInt(rs.getString("id")));
                novoCliente.setIdPessoa(Integer.parseInt(rs.getString("pessoa")));

                //add na lista
                listaCliente.add(novoCliente);
            }
            return listaCliente;

        } catch (Exception ex) {

            return listaCliente;

        } finally {

            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public ArrayList<Cliente> listarCompleto() {

        //cria uma array de Obj Tipo
        ArrayList<Cliente> listaCliente = new ArrayList<Cliente>();

        try {

            //Conexao
            conexao = ConectaBanco.getConexao();

            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(LISTAR_PESSOA);

            //executa
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                //a cada loop
                Cliente novoCliente = new Cliente();
                novoCliente.setIdCliente(Integer.parseInt(rs.getString("idCli")));
                novoCliente.setIdPessoa(Integer.parseInt(rs.getString("idPessoa")));
                novoCliente.setNome(rs.getString("nome"));
                novoCliente.setDataNascimento(rs.getDate("dataNascimento"));

                Usuario usuario = new Usuario();
                usuario.setIdUsuario(Integer.parseInt(rs.getString("idUsuario")));
                usuario.setEmail(rs.getString("email"));
                usuario.setCelular(Long.parseLong(rs.getString("celular")));
                usuario.setPerfil(PerfilDeAcesso.valueOf(rs.getString("perfil")));

                novoCliente.setUsuario(usuario);

                //add na lista
                listaCliente.add(novoCliente);
            }
            return listaCliente;

        } catch (Exception ex) {

            return listaCliente;

        } finally {

            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public void buscarUsuario(Cliente cliente) {

        try {

            //Conexao
            conexao = ConectaBanco.getConexao();

            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(BUSCAR_USUARIO);

            //Passa ID como parametro
            pstmt.setInt(1, cliente.getUsuario().getIdUsuario());

            //executa
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                cliente.setIdCliente(Integer.parseInt(rs.getString("idCli")));
                cliente.setIdPessoa(Integer.parseInt(rs.getString("idPessoa")));
                cliente.setNome(rs.getString("nome"));
                cliente.setDataNascimento(rs.getDate("dataNascimento"));

                Usuario usuario = new Usuario();
                usuario.setIdUsuario(Integer.parseInt(rs.getString("idUsuario")));
                usuario.setEmail(rs.getString("email"));
                usuario.setCelular(Long.parseLong(rs.getString("celular")));
                usuario.setPerfil(PerfilDeAcesso.valueOf(rs.getString("perfil")));
                usuario.setAtivo(rs.getBoolean("ativo"));

                cliente.setUsuario(usuario);
            }

        } catch (Exception ex) {
            ex.getMessage();
        } finally {

            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public String alterar(Cliente cliente) {
        
        String sqlReturnCode = "0";
        
        String patternDataNascimento = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(patternDataNascimento);

        try {

            conexao = ConectaBanco.getConexao();

            PreparedStatement pstmt = conexao.prepareStatement(ALTERAR);

            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, df.format(cliente.getDataNascimento()));
            pstmt.setInt(3, cliente.getIdPessoa());

            pstmt.execute();

            return sqlReturnCode;

        } catch (SQLException sqlErro) {

            sqlReturnCode = sqlErro.getSQLState();

            return sqlReturnCode;

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
    public void buscar(Cliente cliente) {

        try {
            //Conexao
            conexao = ConectaBanco.getConexao();
            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(BUSCAR);

            pstmt.setInt(1, cliente.getIdCliente());
            //executa
            ResultSet rs = pstmt.executeQuery();

            // como a query ira retornar somente um registro, faremos o NEXT
            while (rs.next()) {
                cliente.setIdPessoa(rs.getInt("pessoa"));
            }
        } catch (Exception e) {

            //
        } finally {

            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public boolean excluir(Cliente cliente) {

        try {

            conexao = ConectaBanco.getConexao();

            PreparedStatement pstmt = conexao.prepareStatement(DELETE);

            pstmt.setInt(1, cliente.getIdCliente());

            pstmt.execute();

            return true;

        } catch (Exception ex) {

            return false;

        } finally {

            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public String cadastrar(Cliente cliente) {

        String sqlReturnCode = "0";

        PreparedStatement pstmt = null;

        try {
            conexao = ConectaBanco.getConexao();
            pstmt = conexao.prepareStatement(CADASTRAR, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, cliente.getIdPessoa());
            pstmt.execute();

            final ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                cliente.setIdCliente(rs.getInt("id"));
            }

            return sqlReturnCode;

        } catch (SQLException sqlErro) {

            sqlReturnCode = sqlErro.getSQLState();

            return sqlReturnCode;

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
};
