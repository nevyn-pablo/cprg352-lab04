/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Model.Note;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
        String path = getServletContext().getRealPath("/WEB-INF/note.txt");
// to read files
BufferedReader read = new BufferedReader(new FileReader(new File(path)));
Scanner br=new Scanner(read);

// to write to a file
//PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(path, false))); 
            
        String title = br.nextLine();
        String content = "";
      
     String edit="abc";
     edit=request.getParameter("edit");
 
     if(edit==null ){ 
         
                   while(br.hasNext()){
        content=content+br.nextLine()+System.getProperty("line.separator")+"<br>";}
                br.close();
        Note note=new Note(title,content);    
           request.setAttribute("note",note);
     getServletContext().getRequestDispatcher("/WEB-INF/viewnote.jsp").forward(request,response);
     }
     else {
         while(br.hasNext()){
        content=content+br.nextLine()+System.getProperty("line.separator");}
                br.close();
          Note note=new Note(title,content); 
             request.setAttribute("note",note);
            getServletContext().getRequestDispatcher("/WEB-INF/editnote.jsp").forward(request,response);

    // }
    }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       

    
        String title=request.getParameter("title");
        String content_forfile=request.getParameter("content");
        String content_forhtml=request.getParameter("content");
        String content="";
           Scanner read =new Scanner(content_forhtml);
        
      Note  note=new Note(title,content_forfile);
            request.setAttribute("note", note);
           String path = getServletContext().getRealPath("/WEB-INF/note.txt");
           //System.out.println("real path is in post :  " + path);
           
           PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(path, false))); 
           pw.write(title);
           pw.write("\n");
           pw.write(content_forfile);
           pw.close();
           
              while(read.hasNext()){
        content=content+read.nextLine()+System.getProperty("line.separator")+"<br>";}
                read.close();
          // System.out.println("content in post is :  "+  content);
           note.setContent(content);

        getServletContext().getRequestDispatcher("/WEB-INF/viewnote.jsp").forward(request, response);
      
     
  
        
    }

}
