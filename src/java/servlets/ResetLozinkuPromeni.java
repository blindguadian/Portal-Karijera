/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;  

/**
 *
 * @author Korisnik
 */

public class ResetLozinkuPromeni extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            
            String kljuc = request.getParameter("kljuc");
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
                
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("(select korisnickoIme as korisnickoImestudent, vremeTrajanjaLinka from student where lozinkaResetLink='http://localhost:8080/Projekat_4/api/promena/" + kljuc + "') union all (select korisnickoIme as korisnickoImekompanija, vremeTrajanjaLinka from kompanija where lozinkaResetLink='http://localhost:8080/Projekat_4/api/promena/" + kljuc + "') union all (select korisnickoIme as korisnickoImeadministrator, vremeTrajanjaLinka from administrator where lozinkaResetLink='http://localhost:8080/Projekat_4/api/promena/" + kljuc + "')");
            DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
            LocalDateTime trenutnoVreme = LocalDateTime.now();
            
            
            while (rs.next()) {
                
                String vremeTrajanjaLinka = rs.getTimestamp("vremeTrajanjaLinka").toString();
                LocalDateTime vremeIstekaLinka = LocalDateTime.parse(vremeTrajanjaLinka, datePattern);
                
                if (vremeIstekaLinka.isAfter(trenutnoVreme)) {
                    String tabela = rs.getMetaData().getColumnName(1);
                    tabela = tabela.substring(13, tabela.length());
                    String korisnickoIme = rs.getString(1);
                    
                    HttpSession session = request.getSession();  
                    session.setAttribute("korisnickoIme",korisnickoIme);  
                    session.setAttribute("uloga",tabela);  
                    
                    String path = "/faces/resetLozinku.xhtml";
                    ServletContext sc = getServletConfig().getServletContext();
                    RequestDispatcher rd = sc.getRequestDispatcher(path);
                    rd.forward(request,response);
 

                }else{
                    String path = "/faces/resetLozinkuIstekaoLink.xhtml";
                    ServletContext sc = getServletConfig().getServletContext();
                    RequestDispatcher rd = sc.getRequestDispatcher(path);
                    rd.forward(request,response);
                }
        }
    } catch (SQLException ex) {
                    Logger.getLogger(ResetLozinkuPromeni.class.getName()).log(Level.SEVERE, null, ex);
                }
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
