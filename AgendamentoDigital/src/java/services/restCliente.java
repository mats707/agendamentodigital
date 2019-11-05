/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import static java.lang.System.out;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import modelos.*;
import dao.*;
import builder.cliente.*;
import com.thoughtworks.xstream.*;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.ws.rs.PathParam;

/**
 * REST Web Service
 *
 * @author mathm
 */
@Path("Cliente")
public class restCliente {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CadastrarCliente
     */
    public restCliente() {
    }

    /**
     * Retrieves representation of an instance of services.restCliente
     *
     * @return an instance of java.lang.String
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("Cadastrar")
    public String cadastrarCliente(
            @FormParam("inputUsername") String inputUsername,
            @FormParam("inputPassword") String inputPassword,
            @FormParam("inputName") String inputName,
            @FormParam("cmbtipodocumento") Integer cmbtipodocumento,
            @FormParam("inputNrdoc") String inputNrdoc,
            @FormParam("cmbtipocontato") Integer cmbtipocontato,
            @FormParam("inputNrcontato") String inputNrcontato,
            @FormParam("inputDataNasc") String inputDataNasc,
            @FormParam("cmbsexo") Integer cmbsexo,
            @FormParam("inputEmail") String inputEmail,
            @FormParam("cep") String cep,
            @FormParam("rua") String rua,
            @FormParam("numero") String numero,
            @FormParam("bairro") String bairro,
            @FormParam("cidade") String cidade,
            @FormParam("estado") String estado,
            @FormParam("complemento") String complemento,
            @FormParam("cmbestadocivil") Integer cmbestadocivil,
            @FormParam("filhos") String filhos,
            @FormParam("filhos_qtd") Integer filhos_qtd,
            @FormParam("cmbescolaridade") Integer cmbescolaridade,
            @FormParam("inputProfissao") String inputProfissao
    ) {
        String msg = "";

        Cliente cliente = ClienteBuilder.novoClienteBuilder()
                //Usuário
                .comUsuario(inputUsername, inputPassword)
                //Dados Pessoais
                .comNome(inputName)
                .nascidoEm(inputDataNasc)
                /*Construir*/
                .constroi();

        //Cadastrar Endreço
        UsuarioDAO daoUsuario = new UsuarioDAO();
        ClienteDAO daoCliente = new ClienteDAO();

        boolean saida = false;

        while (saida == false) {

            if (daoUsuario.cadastraNovoUsuario(cliente.getUsuario()).equals("0")) {
                msg += "Usuário cadastrado com sucesso!\n";
            } else {
                msg += "Usuário Inválido!\n";
                saida = true;
            }

            if (daoCliente.cadastrarPessoa(cliente)) {
                msg += "Pessoa cadastrado com sucesso!\n";
            } else {
                msg += "Pessoa Inválida!\n";
                saida = true;
            }

            if (daoCliente.cadastrarCliente(cliente)) {
                msg += "Cliente cadastrado com sucesso!\n";
            } else {
                msg += "Cliente Inválido!\n";
                saida = true;
            }

            saida = true;
        }

        return msg;
    }

    /**
     * Retrieves representation of an instance of services.restCliente
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Path("Listar")
    public String getCliente() throws SQLException, ClassNotFoundException {

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        ClienteDAO objDao = new ClienteDAO();

        ArrayList<Cliente> arr;
        arr = objDao.listar();

        return objgson.toJson(arr);
    }

    /**
     * Retrieves representation of an instance of services.restCliente
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML + ";charset=utf-8")
    @Path("ListarXML")
    public String getClienteXML() throws SQLException, ClassNotFoundException {

        ClienteDAO objDao = new ClienteDAO();

        ArrayList<Cliente> arr;

        arr = objDao.listar();

        XStream xstream = new XStream(new DomDriver());
        xstream.alias("Cliente", Cliente.class);
        xstream.alias("Pessoa", Pessoa.class);
        xstream.alias("Usuario", Usuario.class);

        String xml = xstream.toXML(arr);

        return xml;

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("BuscarId/{id}")
    public String buscarNomeId(@PathParam("id") Integer id) throws SQLException, ClassNotFoundException {

        Gson objgson = new GsonBuilder().setPrettyPrinting().create();

        ClienteDAO objDao = new ClienteDAO();

        Cliente cliente = new Cliente();
        cliente.setIdCliente(id);

        Cliente clienteEncontrado = objDao.buscarId(cliente);

        return objgson.toJson(clienteEncontrado);

    }
    
    @POST
    @Path("Atualizar")
    @Consumes(MediaType.APPLICATION_XML + ";charset=utf-8")
    @Produces(MediaType.TEXT_PLAIN)
    public String atualizarCliente(String content) throws SQLException, ClassNotFoundException, IOException {

        String msg = "Tente novamente!";
        
        try {

            XStream xstream = new XStream(new DomDriver());
            xstream.alias("Cliente", Cliente.class);
            xstream.alias("Pessoa", Pessoa.class);
            xstream.alias("Usuario", Usuario.class);
            xstream.registerConverter(
                    new com.thoughtworks.xstream.converters.basic.DateConverter("yyyy-MM-dd'T'HH:mm", new String[]{"dd/MM/yyyy HH:mm"}, new GregorianCalendar().getTimeZone()) {
                public boolean canConvert(Class type) {
                    return type.equals(Date.class) || type.equals(Timestamp.class);
                }

                public String toString(Object obj) {
                    return new SimpleDateFormat("yyyy-MM-dd HH:mm").format((Date) obj);
                }
            });

            Cliente cliente = (Cliente) xstream.fromXML(content);
            
            PessoaDAO dao = new PessoaDAO();
            if(dao.alterar(cliente)){
                msg = "Nome alterado com sucesso";
            } else {
                msg = "Não possível atualizar o nome!\nContate o administrador ou tente novamente.";
            }

            return msg;
            
        } catch (Exception ex) {
            return ex.toString();
        }
    }

}
