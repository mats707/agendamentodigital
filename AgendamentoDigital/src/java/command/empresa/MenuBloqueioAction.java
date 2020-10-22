/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.empresa;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import dao.BloqueioAgendaDAO;
import dao.FuncionarioDAO;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelos.BloqueioAgenda;

/**
 *
 * @author Rafael Pereira
 */
public class MenuBloqueioAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("pagina", "pages/admin/empresa/menuBloqueio.jsp");
        Gson objgson = new GsonBuilder()
                // .registerTypeAdapter(BloqueioAgenda.class, new BloqueioAdapter())
                .setPrettyPrinting().create();

        ArrayList<BloqueioAgenda> arrBloqueio = new ArrayList<BloqueioAgenda>();

        BloqueioAgendaDAO bloqueioDAO = new BloqueioAgendaDAO();

        FuncionarioDAO objFuncionarioDao = new FuncionarioDAO();

        arrBloqueio = bloqueioDAO.listarBloqueio();

        for (BloqueioAgenda objBloqueio : arrBloqueio) {
            objFuncionarioDao.buscar(objBloqueio.getFuncionario());
        }
        
        return objgson.toJson(arrBloqueio);
    }

    private class BloqueioAdapter implements JsonSerializer<BloqueioAgenda> {

        public JsonElement serialize(BloqueioAgenda bloqueio, Type typeofsrc, JsonSerializationContext context) {

            JsonObject obj = new JsonObject();
            obj.add("idBloqueio", context.serialize(bloqueio.getIdBloquieio()));

            String horainicial = new SimpleDateFormat("kk:mm").format(bloqueio.getHoraInicial());
            obj.add("horaInicial", context.serialize(horainicial));

            obj.add("intervaloAgendamentoGeralServico", context.serialize(bloqueio.getDuracaoBloqueio().toMinutes()));

            obj.add("funcionario", context.serialize(bloqueio.getFuncionario()));
//            //Converte a String para array<Funcionario>
//            ArrayList<Integer> arrDia = new ArrayList<Integer>();
//            String[] diaSemanaString = request.getParameterValues("diasemana");
//
//            for (int i = 0; i < diaSemanaString.length; i++) {
//                arrDia.add(Integer.parseInt(diaSemanaString[i]));
//            }
//            objEmpresa.setDiaSemanaTrabalho(arrDia);
            return obj;

        }

    }

}
