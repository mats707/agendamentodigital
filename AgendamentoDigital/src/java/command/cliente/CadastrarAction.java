///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package command.cliente;
//
//import command.cliente.*;
//import dao.ClienteDAO;
//import javax.servlet.RequestDispatcher;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import jdk.nashorn.internal.objects.NativeString;
//import modelos.PerfilDeAcesso;
//import modelos.Cliente;
//import util.geraHash;
//
///**
// *
// * @author alunocmc
// */
//public class CadastrarAction implements ICommand {
//
//    @Override
//    public String executar(HttpServletRequest request, HttpServletResponse response) {
//
//        request.setAttribute("pagina", "pages/admin/cadastrarCliente.jsp");
//
//        String email = request.getParameter("inputName");
//        String dataNascimento = request.getParameter("inputDataNasc");
//        String celular = request.getParameter("inputCelular");
//        String email = request.getParameter("inputEmail");
//        String senha = request.getParameter("senha");
//        String chksenha = request.getParameter("chksenha");
//
//        if (email != null && senha != null && chksenha != null && celular != null && perfil != null) {
//            Cliente cliente = new Cliente();
//            cliente.setEmail(email);
//            cliente.setSenha(geraHash.codificaBase64(senha));
//            cliente.setCelular(Long.parseLong(celular.replace("(", "").replace(")", "").replace("-", "").replace(" ", "")));
//            if (perfil.equalsIgnoreCase("administrador")) {
//                cliente.setPerfil(PerfilDeAcesso.FUNCIONARIOADMIN);
//            } else if (perfil.equalsIgnoreCase("comum")) {
//                cliente.setPerfil(PerfilDeAcesso.FUNCIONARIOCOMUM);
//            } else {
//                cliente.setPerfil(PerfilDeAcesso.CLIENTECOMUM);
//            }
//
//            if (geraHash.codificaBase64(chksenha).equals(cliente.getSenha())) {
//                ClienteDAO clienteDAO = new ClienteDAO();
//
//                String sqlState = clienteDAO.cadastraNovoCliente(cliente);
//
//                if (sqlState == "0") {
//                    request.setAttribute("colorMsg", "success");
//                    return "Cadastrado com sucesso!";
//                } else if ("23505".equals(sqlState)) {
//                    request.setAttribute("colorMsg", "danger");
//                    return "Tente outro email ou celular!";
//                } else {
//                    request.setAttribute("colorMsg", "danger");
//                    return "Não foi possível cadastrar o usuário, tente novamente!";
//                }
//            } else {
//                request.setAttribute("colorMsg", "warning");
//                return "Senhas diferente!";
//            }
//        } else {
//            request.setAttribute("colorMsg", "");
//            return "";
//        }
//
//    }
//
//}
