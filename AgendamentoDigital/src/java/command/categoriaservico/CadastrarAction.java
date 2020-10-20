/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.categoriaservico;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.CategoriaServicoDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jdk.nashorn.internal.objects.NativeString;
import modelos.CategoriaServico;
import util.Util;

/**
 *
 * @author alunocmc
 */
public class CadastrarAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Gson objgson = new Gson();
        CategoriaServicoDAO categoriaServicoDAO = new CategoriaServicoDAO();

        request.setAttribute("pagina", "pages/admin/categoria/home.jsp");
//        request.setAttribute("pagina", "CadastrarCategoriaServico");
        String json = request.getParameter("lastService");
        String listCategoria = request.getParameter("categorias");
        String listDescricao = request.getParameter("descricoes");
        String[] categoriasString = null;
        String[] descricoesString = null;
        if (listCategoria != null && listDescricao != null) {
            categoriasString = listCategoria.split("\\,");
            descricoesString = listDescricao.split("\\,");
        }

        CategoriaServico lastService = objgson.fromJson(json, CategoriaServico.class);

        if (lastService == null) {
            lastService = new CategoriaServico(0, "DEFAULT", "", new CategoriaServico(),Boolean.TRUE);
        }
        String sqlState = "0";
        String funcaoMsg = "Carregando...";;
        String funcaoStatus = "info";

        if (categoriasString != null) {
            CategoriaServico newCategoria = new CategoriaServico();
            newCategoria.setNome(Util.stringToUTF8(categoriasString[0]));
            newCategoria.setDescricao(Util.stringToUTF8(descricoesString[0]));
            newCategoria.setCategoriaPai(lastService);
            newCategoria.setAtivo(Boolean.TRUE);

            CategoriaServico categoriaExistente = categoriaServicoDAO.buscarNome(newCategoria);
            if (categoriaExistente.getIdCategoriaServico() != null) {
                lastService = categoriaExistente;
                funcaoMsg = "Categoria já existente!";
                funcaoStatus = "warning";
            } else {
                sqlState = categoriaServicoDAO.cadastraCategoria(newCategoria);
                if (sqlState == "0") {
                    lastService = newCategoria;
                    funcaoMsg = "Cadastrado com sucesso!";
                    funcaoStatus = "success";
                }
            }
            
            if (sqlState == "0") {
                for (int i = 1; i < categoriasString.length; i++) {
                    CategoriaServico subCategoria = new CategoriaServico();
                    subCategoria.setNome(Util.stringToUTF8(categoriasString[i]));
                    subCategoria.setDescricao(Util.stringToUTF8(descricoesString[i]));
                    subCategoria.setCategoriaPai(lastService);
                    subCategoria.setAtivo(Boolean.TRUE);
                    categoriaExistente = categoriaServicoDAO.buscarNome(subCategoria);
                    if (categoriaExistente.getIdCategoriaServico() != null) {
                        lastService = categoriaExistente;
                    } else {
                        sqlState = categoriaServicoDAO.cadastraCategoria(subCategoria);
                        if (sqlState == "0") {
                            lastService = subCategoria;
                            funcaoMsg = "Cadastrado com sucesso!";
                            funcaoStatus = "success";
                        } else {
                            funcaoMsg = "Não foi possível cadastrar a categoria, tente novamente!";
                            funcaoStatus = "error";
                        }
                    }
                }
            } else {
                funcaoMsg = "Ocorreu um erro, tente novamente!";
                funcaoStatus = "error";
            }
        }
        request.setAttribute("funcaoMsg", funcaoMsg);
        request.setAttribute("funcaoStatus", funcaoStatus);
        return funcaoMsg;
    }
}
