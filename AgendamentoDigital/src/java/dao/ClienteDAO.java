/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import builder.cliente.ClienteBuilder;
import dao.interfaces.IClienteDAO;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelos.PerfilDeAcesso;
import modelos.Cliente;
import util.ConectaBanco;

/**
 *
 * @author mathm
 */
public class ClienteDAO implements IClienteDAO {

    private static final String SELECT_ALL = "SELECT\n"
            + "	c.id as id_cliente,\n"
            + "	p.id as id_pessoa,\n"
            + "	u.id as id_usuario,\n"
            + "	pa.id as id_perfil_acesso,\n"
            + "	e.id as id_endereco,\n"
            + "	s.id as id_sexo,\n"
            + "	ec.id as id_estado_civil,\n"
            + "	pr.id as id_profissao,\n"
            + "	es.id as id_escolaridade,\n"
            + "	p.nome,\n"
            + "	p.datanascimento as data_nascimento,\n"
            + "	case when p.qtdfilhos > 0 then 'sim' else 'nao' end tem_filhos,\n"
            + "	case when p.qtdfilhos = 0 then null end quantidade_filhos,\n"
            + "	u.login as usuario,\n"
            + "	'null' as senha_usuario,\n"
            + "	pa.nome as perfil_usuario,\n"
            + "	e.cep,\n"
            + "	e.rua,\n"
            + "	e.numero,\n"
            + "	e.bairro,\n"
            + "	e.cidade,\n"
            + "	e.estado,\n"
            + "	s.nome as sexo,\n"
            + "	ec.nome as estado_civil,\n"
            + "	pr.nome as profissao,\n"
            + "	pr.inicial as inicial_profissao,\n"
            + "	es.nome as escolaridade\n"
            + "FROM cliente c\n"
            + "INNER JOIN pessoa p\n"
            + "ON p.id = c.pessoa\n"
            + "INNER JOIN usuario u\n"
            + "ON u.id = c.usuario\n"
            + "INNER JOIN perfilacesso pa\n"
            + "ON pa.id = u.perfil\n"
            + "INNER JOIN endereco e\n"
            + "ON e.id = p.endereco\n"
            + "INNER JOIN sexo s\n"
            + "ON s.id = p.sexo\n"
            + "INNER JOIN estadocivil ec\n"
            + "ON ec.id = p.estadocivil\n"
            + "INNER JOIN profissao pr\n"
            + "ON pr.id = p.profissao\n"
            + "INNER JOIN escolaridade es\n"
            + "ON es.id = p.escolaridade;";
    private static final String SELECT_NOME = "SELECT\n"
            + "	c.id as id_cliente,\n"
            + "	p.id as id_pessoa,\n"
            + "	u.id as id_usuario,\n"
            + "	pa.id as id_perfil_acesso,\n"
            + "	e.id as id_endereco,\n"
            + "	s.id as id_sexo,\n"
            + "	ec.id as id_estado_civil,\n"
            + "	pr.id as id_profissao,\n"
            + "	es.id as id_escolaridade,\n"
            + "	p.nome,\n"
            + "	p.datanascimento as data_nascimento,\n"
            + "	case when p.qtdfilhos > 0 then 'sim' else 'nao' end tem_filhos,\n"
            + "	case when p.qtdfilhos = 0 then null end quantidade_filhos,\n"
            + "	u.login as usuario,\n"
            + "	'null' as senha_usuario,\n"
            + "	pa.nome as perfil_usuario,\n"
            + "	e.cep,\n"
            + "	e.rua,\n"
            + "	e.numero,\n"
            + "	e.bairro,\n"
            + "	e.cidade,\n"
            + "	e.estado,\n"
            + "	s.nome as sexo,\n"
            + "	ec.nome as estado_civil,\n"
            + "	pr.nome as profissao,\n"
            + "	pr.inicial as inicial_profissao,\n"
            + "	es.nome as escolaridade\n"
            + "FROM cliente c\n"
            + "INNER JOIN pessoa p\n"
            + "ON p.id = c.pessoa\n"
            + "INNER JOIN usuario u\n"
            + "ON u.id = c.usuario\n"
            + "INNER JOIN perfilacesso pa\n"
            + "ON pa.id = u.perfil\n"
            + "INNER JOIN endereco e\n"
            + "ON e.id = p.endereco\n"
            + "INNER JOIN sexo s\n"
            + "ON s.id = p.sexo\n"
            + "INNER JOIN estadocivil ec\n"
            + "ON ec.id = p.estadocivil\n"
            + "INNER JOIN profissao pr\n"
            + "ON pr.id = p.profissao\n"
            + "INNER JOIN escolaridade es\n"
            + "ON es.id = p.escolaridade\n"
            + "WHERE p.nome = ?;";
    private static final String SELECT_ID = "SELECT\n"
            + "	c.id as id_cliente,\n"
            + "	p.id as id_pessoa,\n"
            + "	u.id as id_usuario,\n"
            + "	pa.id as id_perfil_acesso,\n"
            + "	e.id as id_endereco,\n"
            + "	s.id as id_sexo,\n"
            + "	ec.id as id_estado_civil,\n"
            + "	pr.id as id_profissao,\n"
            + "	es.id as id_escolaridade,\n"
            + "	p.nome,\n"
            + "	p.datanascimento as data_nascimento,\n"
            + "	case when p.qtdfilhos > 0 then 'sim' else 'nao' end tem_filhos,\n"
            + "	case when p.qtdfilhos = 0 then null end quantidade_filhos,\n"
            + "	u.login as usuario,\n"
            + "	'null' as senha_usuario,\n"
            + "	pa.nome as perfil_usuario,\n"
            + "	e.cep,\n"
            + "	e.rua,\n"
            + "	e.numero,\n"
            + "	e.bairro,\n"
            + "	e.cidade,\n"
            + "	e.estado,\n"
            + "	s.nome as sexo,\n"
            + "	ec.nome as estado_civil,\n"
            + "	pr.nome as profissao,\n"
            + "	pr.inicial as inicial_profissao,\n"
            + "	es.nome as escolaridade\n"
            + "FROM cliente c\n"
            + "INNER JOIN pessoa p\n"
            + "ON p.id = c.pessoa\n"
            + "INNER JOIN usuario u\n"
            + "ON u.id = c.usuario\n"
            + "INNER JOIN perfilacesso pa\n"
            + "ON pa.id = u.perfil\n"
            + "INNER JOIN endereco e\n"
            + "ON e.id = p.endereco\n"
            + "INNER JOIN sexo s\n"
            + "ON s.id = p.sexo\n"
            + "INNER JOIN estadocivil ec\n"
            + "ON ec.id = p.estadocivil\n"
            + "INNER JOIN profissao pr\n"
            + "ON pr.id = p.profissao\n"
            + "INNER JOIN escolaridade es\n"
            + "ON es.id = p.escolaridade\n"
            + "WHERE c.id = ?;";
    private static final String INSERT_CLIENTE = "INSERT INTO cliente (id, pessoa, usuario) VALUES (nextval('sqn_cliente'),?,?)";
    private static final String INSERT_PESSOA
            = "INSERT INTO pessoa (id, nome, dataNascimento, endereco, sexo, estadoCivil, qtdFilhos, profissao, escolaridade)\n"
            + "  VALUES (NEXTVAL('sqn_pessoa'),\n"
            + "		?,\n"
            + "		?,\n"
            + "		(SELECT id FROM endereco WHERE id = ?),\n"
            + "		(SELECT id FROM sexo WHERE id = ?),\n"
            + "		(SELECT id FROM estadoCivil WHERE id = ?),\n"
            + "		?,\n"
            + "		(SELECT id FROM profissao WHERE id = ?),\n"
            + "		(SELECT id FROM escolaridade WHERE id = ?)\n"
            + "  );";

    private Connection conexao;

    public boolean cadastrarCliente(Cliente cliente) {

        try {

            conexao = ConectaBanco.getConexao();

            PreparedStatement pstmt = conexao.prepareStatement(INSERT_CLIENTE, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, cliente.getIdPessoa());
            pstmt.setInt(2, cliente.getUsuario().getIdUsuario());

            pstmt.execute();

            final ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                cliente.setIdCliente(rs.getInt("id"));
            }

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

    public boolean cadastrarPessoa(Cliente cliente) {

        try {
            java.sql.Date dataNascSQL = new java.sql.Date(cliente.getDataNascimento().getTime());

            conexao = ConectaBanco.getConexao();

            PreparedStatement pstmt = conexao.prepareStatement(INSERT_PESSOA, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, cliente.getNome());
            pstmt.setDate(2, dataNascSQL);

            pstmt.execute();

            final ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                cliente.setIdPessoa(rs.getInt("id"));
            }

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
    public ArrayList<Cliente> listar() {

        //cria uma array de Obj Tipo
        ArrayList<Cliente> listaCliente = new ArrayList<Cliente>();

        try {

            //Conexao
            conexao = ConectaBanco.getConexao();

            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(SELECT_ALL);

            //executa
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                //a cada loop
                Cliente novoCliente = ClienteBuilder.novoClienteBuilder()
                        //Usuário
                        .comUsuario(rs.getString("usuario"), rs.getString("senha_usuario"))
                        //Dados Pessoais
                        .comNome(rs.getString("nome"))
                        .nascidoEm(rs.getString("data_nascimento"))
                        /*Construir*/
                        .constroi();

                novoCliente.getUsuario().setIdUsuario(rs.getInt("id_usuario"));
                novoCliente.setIdCliente(rs.getInt("id_cliente"));
                novoCliente.setIdPessoa(rs.getInt("id_pessoa"));

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
    public ArrayList<Cliente> listarNome(Cliente cliente) {

        //cria uma array de Obj Tipo
        ArrayList<Cliente> listaCliente = new ArrayList<Cliente>();

        try {

            //Conexao
            conexao = ConectaBanco.getConexao();

            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(SELECT_NOME);

            pstmt.setString(1, cliente.getNome() + "%");

            //executa
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                //a cada loop
                Cliente novoCliente = ClienteBuilder.novoClienteBuilder()
                        //Usuário
                        .comUsuario(rs.getString("usuario"), rs.getString("senha_usuario"))
                        //Dados Pessoais
                        .comNome(rs.getString("nome"))
                        .nascidoEm(rs.getString("data_nascimento"))
                        /*Construir*/
                        .constroi();

                novoCliente.getUsuario().setIdUsuario(rs.getInt("id_usuario"));
                novoCliente.setIdCliente(rs.getInt("id_cliente"));
                novoCliente.setIdPessoa(rs.getInt("id_pessoa"));

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
    public Cliente buscarId(Cliente cliente) {

        try {

            //Conexao
            conexao = ConectaBanco.getConexao();

            //cria comando SQL
            PreparedStatement pstmt = conexao.prepareStatement(SELECT_ID);

            int id = cliente.getIdCliente();
            pstmt.setInt(1, id);

            //executa
            ResultSet rs = pstmt.executeQuery();

            cliente = new Cliente();

            while (rs.next()) {
                //a cada loop
                Cliente clienteEncontrado = ClienteBuilder.novoClienteBuilder()
                        //Usuário
                        .comUsuario(rs.getString("usuario"), rs.getString("senha_usuario"))
                        //Dados Pessoais
                        .comNome(rs.getString("nome"))
                        .nascidoEm(rs.getString("data_nascimento"))
                        /*Construir*/
                        .constroi();

                clienteEncontrado.getUsuario().setIdUsuario(rs.getInt("id_usuario"));
                clienteEncontrado.setIdCliente(rs.getInt("id_cliente"));
                clienteEncontrado.setIdPessoa(rs.getInt("id_pessoa"));
                cliente = clienteEncontrado;
            }
            
            return cliente;
     
        } catch (Exception ex) {

            return cliente;
            
        } finally {

            try {
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
