/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package command.cliente;

import command.usuario.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import command.cliente.ICommand;
import dao.ClienteDAO;
import dao.UsuarioDAO;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import modelos.Usuario;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author alunocmc
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class AlterarFotoPerfilAction implements ICommand {

    @Override
    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        request.setAttribute("pagina", "MinhaConta");

        InputStream inputStream = null; // input stream of the upload file
        // obtains the upload file part in this multipart request
        Part filePart = request.getPart("fotoPerfil");

        if (filePart != null) {
            //obtains input stream of the upload file
            //the InputStream will point to a stream that contains
            //the contents of the file
            inputStream = filePart.getInputStream();
        }

        Usuario objUsuario = new Usuario();

        objUsuario.setFotoPerfil(inputStream);

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        usuarioDAO.alterarFotoPerfil(objUsuario);
        
        usuarioDAO.buscarFotoPerfil(objUsuario);

        if (objUsuario.getFotoPerfil() != null) {
            request.setAttribute("msg", "File uploaded and saved into database");
        }

        return "";
    }

}
