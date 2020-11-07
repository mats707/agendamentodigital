/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.empresa;

import api.restEmpresa;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import dao.BloqueioAgendaDAO;
import dao.EmpresaDAO;
import dao.FuncionarioDAO;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelos.BloqueioAgenda;
import modelos.Empresa;

/**
 *
 * @author Rafael Pereira
 */
public class MenuBloqueioAction implements ICommand {

    EmpresaDAO objDao = new EmpresaDAO();
    Empresa Obj = new Empresa();

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("pagina", "pages/admin/empresa/menuBloqueio.jsp");
        objDao.buscar(Obj);
        String startTime = Obj.getHoraInicialTrabalho().toString();
        String maxTime = Obj.getHoraFinalTrabalho().toString();

        request.setAttribute("maxTime", maxTime);
        request.setAttribute("startTime", startTime);
        return null;
    }
}
