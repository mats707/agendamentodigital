///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package command.cliente.MinhaConta;
//
//import command.usuario.*;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import command.cliente.ICommand;
//import dao.UsuarioDAO;
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.RequestDispatcher;
//import javax.servlet.annotation.MultipartConfig;
//import modelos.Usuario;
//import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.disk.DiskFileItemFactory;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;
//
///**
// *
// * @author alunocmc
// */
//public class AlterarFotoPerfilAction implements ICommand {
//
//    @Override
//    public String executar(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//        String uploadPath = getServletContext().getRealPath("") + File.separator + "/images";
//        File uploadDir = new File(uploadPath);
//        if (!uploadDir.exists()) {
//            uploadDir.mkdir();
//        }
//
//        filePath = "c:\\temp\\";
//
//        isMultipart = ServletFileUpload.isMultipartContent(request);
//        response.setContentType("text/html");
//
//        java.io.PrintWriter out = response.getWriter();
//
//        if (!isMultipart) {
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet upload</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<p>No file uploaded</p>");
//            out.println("</body>");
//            out.println("</html>");
//            return;
//        }
//        DiskFileItemFactory factory = new DiskFileItemFactory();
//
//        // maximum size that will be stored in memory
//        factory.setSizeThreshold(maxMemSize);
//
//        // Location to save data that is larger than maxMemSize.
//        factory.setRepository(new File("c:\\temp"));
//
//        // Create a new file upload handler
//        ServletFileUpload upload = new ServletFileUpload(factory);
//
//        // maximum file size to be uploaded.
//        upload.setSizeMax(maxFileSize);
//
//        try {
//            // Parse the request to get file items.
//            List fileItems = upload.parseRequest(request);
//
//            // Process the uploaded file items
//            Iterator i = fileItems.iterator();
//
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet upload</title>");
//            out.println("</head>");
//            out.println("<body>");
//
//            while (i.hasNext()) {
//                FileItem fi = (FileItem) i.next();
//                if (!fi.isFormField()) {
//                    // Get the uploaded file parameters
//                    String fieldName = fi.getFieldName();
//                    String fileName = fi.getName();
//                    String contentType = fi.getContentType();
//                    boolean isInMemory = fi.isInMemory();
//                    long sizeInBytes = fi.getSize();
//
//                    // Write the file
//                    if (fileName.lastIndexOf("\\") >= 0) {
//                        file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\")));
//                    } else {
//                        file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\") + 1));
//                    }
//                    fi.write(file);
//                    out.println("Uploaded Filename: " + fileName + "<br>");
//                }
//            }
//            out.println("</body>");
//            out.println("</html>");
//        } catch (Exception ex) {
//            System.out.println(ex);
//        }
//
//    }
//
//}
