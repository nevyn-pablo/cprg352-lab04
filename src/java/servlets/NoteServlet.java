/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;
import models.Note;
import java.io.*;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; 


/**
 *
 * @author 849961
 */
public class NoteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = getServletContext().getRealPath("/WEV-INF/note.txt");
        //reads the file
        BufferedReader read = new BufferedReader(new FileReader(new File(path)));
        Scanner in = new Scanner(read);
        
        String title = in.nextLine();
        String content = "";
        
        String edit = "abc";
        edit = request.getParameter("edit");
        
        if(edit == null){
            while(in.hasNext()){
                content = content + in.nextLine() + System.getProperty("line.separator") + "<br>+";
            }
            in.close();
            Note note = new Note(title, content);
            request.setAttribute("note", note);
            getServletContext().getRequestDispatcher("/WEB-INF/viewnote.jsp").forward(request, response);
        }else{
            while(in.hasNext()){
                content = content + in.nextLine() + System.getProperty("line.separator");
            }
            in.close();
            Note note = new Note(title, content);
            request.setAttribute("note", note);
            getServletContext().getRequestDispatcher("/WEB-INF/viewnote.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String title = request.getParameter("title");
        String content_forfile = request.getParameter("content");
        String content_forhtml = request.getParameter("content");
        String content = "";
       
        Scanner in = new Scanner(content_forhtml);
        
        Note note = new Note(title, content_forfile);
        request.setAttribute("note", note);
        String path = getServletContext().getRealPath("/WEB-INF/note.txt");
        
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(path, false)));
        pw.write(title);
        pw.write("\n");
        pw.write(content_forfile);
        pw.close();
        
        while(in.hasNext()){
            content = content + in.nextLine() + System.getProperty("line.separator") + "<br>";
        }
        in.close();
        note.setContent(content);
        getServletContext().getRequestDispatcher("/WEB-INF/viewnote.jsp").forward(request, response);
    }
}