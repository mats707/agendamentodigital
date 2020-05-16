/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.servico;

import dao.ServicoDAO;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jdk.nashorn.internal.objects.NativeString;
import modelos.Servico;

/**
 *
 * @author alunocmc
 */
public class CategoriaAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) {

        String[] categorias = null;
        Servico lastService = new Servico();

        request.setAttribute("pagina", "pages/admin/cadastrarServico.jsp");

        String stringCategorias = request.getParameter("categorias");
        String result = "{}";

        //Gson objgson = new GsonBuilder().setPrettyPrinting().create();
        ServicoDAO servicoDAO = new ServicoDAO();

        categorias = stringCategorias.split("\\,");

        if (categorias != null) {
            Servico newCategoria = new Servico();
            newCategoria.setNome(categorias[0]);
            newCategoria.setServicoPai(lastService);
            String sqlState = servicoDAO.cadastraNovaCategoria(newCategoria);
            if (sqlState == "0") {
                lastService = newCategoria;
                for (int i = 1; i < categorias.length; i++) {
                    Servico subCategoria = new Servico();
                    subCategoria.setNome(categorias[i]);
                    subCategoria.setServicoPai(lastService);
                    sqlState = servicoDAO.cadastraNovoServico(subCategoria);
                    if (sqlState == "0") {
                        lastService = subCategoria;
                        result = "{\"id\":\"0\",\"descricao\":\"Cadastrado com Sucesso!\"";
                    } else {
                        result = "{\"id\":\"1\",\"descricao\":\"Categoria " + categorias[i] + " inválida\"";
                    }
                }
            } else {
                result = "{\"id\":\"1\",\"descricao\":\"Categoria " + categorias[0] + " inválida\"";
            }
        } else {
            result = "{\"id\":\"1\",\"descricao\":\"Categoria(s) inválida(s)\"";
        }

        return result;
    }
}
