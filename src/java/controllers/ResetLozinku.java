package controllers;

import java.util.Random;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.*;
import javax.mail.internet.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped

public class ResetLozinku {

    private String korisnickoIme, mail, lozinka, uloga;

    public String getUloga() {
        return uloga;
    }

    public void setUloga(String uloga) {
        this.uloga = uloga;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String randomKarakteri(int len) {
        char[] ch = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z'};

        char[] c = new char[len];
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            c[i] = ch[random.nextInt(ch.length)];
        }

        return new String(c);
    }

    String email = "nemanjat@yahoo.com",
            password = "blindguadian1",
            host = "smtp.mail.yahoo.com",
            port = "587";

//    String kljuc = randomKarakteri(8);
    public void posaljiMail() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("select korisnickoIme as korisnickoImeStudent, mail from student s, korisnik k where korisnickoIme='" + korisnickoIme + "' && k.idKorisnik=s.idKorisnik");
            
            while (rs.next()) {

                Properties props = System.getProperties();
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", host);
                props.put("mail.smtp.user", email);
                props.put("mail.smtp.password", password);
                props.put("mail.smtp.port", "587");
                props.put("mail.smtp.auth", "true");

                Session session = Session.getDefaultInstance(props);

                String tabela = rs.getMetaData().getColumnName(1);
                tabela = tabela.substring(13, tabela.length());

                LocalDateTime vremeSlanjaMejla = java.time.LocalDateTime.now();
                LocalDateTime vremeIstekaMejla = vremeSlanjaMejla.plusHours(4);

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
                String vremeTrajanjaLinka = vremeIstekaMejla.format(datePattern);
                
                String kljuc = randomKarakteri(9);

                MimeMessage msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress(email));
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(rs.getString("mail")));
                msg.setSubject("Resetovanje maila");
                msg.setContent("<h1 style=\"color:red;\">Promena sifre</h1><br/> Vasu novu lozinku mozete uneti na sledecem linku: <br/>"
                        + "http://localhost:8080/Projekat_4/ResetLozinkuPromeni?" + kljuc + "<br/>"
                        + "Pazite sta radite od sad, posmatramo vas.", "text/html");

                Transport transport = session.getTransport("smtp");
                transport.connect(host, email, password);
                transport.sendMessage(msg, msg.getAllRecipients());
                transport.close();
                
                stm.executeUpdate("update " + tabela + " set lozinkaResetLink='http://localhost:8080/Projekat_4/ResetLozinkuPromeni?" + kljuc + "', vremeTrajanjaLinka='" + vremeTrajanjaLinka + "'");
            }
            
            ResultSet rs2 = stm.executeQuery("select korisnickoIme as korisnickoImeKompanija, mail from kompanija kom, korisnik k where korisnickoIme='" + korisnickoIme + "' && k.idKorisnik=kom.idKorisnik");
            while (rs2.next()) {

                Properties props = System.getProperties();
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", host);
                props.put("mail.smtp.user", email);
                props.put("mail.smtp.password", password);
                props.put("mail.smtp.port", "587");
                props.put("mail.smtp.auth", "true");

                Session session = Session.getDefaultInstance(props);

                String tabela = rs.getMetaData().getColumnName(1);
                tabela = tabela.substring(13, tabela.length());

                LocalDateTime vremeSlanjaMejla = java.time.LocalDateTime.now();
                LocalDateTime vremeIstekaMejla = vremeSlanjaMejla.plusHours(4);

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
                String vremeTrajanjaLinka = vremeIstekaMejla.format(datePattern);
                
                String kljuc = randomKarakteri(9);

                MimeMessage msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress(email));
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(rs.getString("mail")));
                msg.setSubject("Resetovanje maila");
                msg.setContent("<h1 style=\"color:red;\">Promena sifre</h1><br/> Vasu novu lozinku mozete uneti na sledecem linku: <br/>"
                        + "http://localhost:8080/Projekat_4/ResetLozinkuPromeni?" + kljuc + "<br/>"
                        + "Pazite sta radite od sad, posmatramo vas.", "text/html");

                Transport transport = session.getTransport("smtp");
                transport.connect(host, email, password);
                transport.sendMessage(msg, msg.getAllRecipients());
                transport.close();
                
                stm.executeUpdate("update " + tabela + " set lozinkaResetLink='http://localhost:8080/Projekat_4/ResetLozinkuPromeni?" + kljuc + "', vremeTrajanjaLinka='" + vremeTrajanjaLinka + "'");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AddressException ex) {
            Logger.getLogger(ResetLozinku.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(ResetLozinku.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }
    
    
    public String resetujLozinku() {
        
        try {
            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            String korIme = (String) sesija.getAttribute("korisnickoIme");
            String ulogaSesija = (String) sesija.getAttribute("uloga");
//            ResetLozinkuPromena r = new ResetLozinkuPromena();
//            korisnickoIme = r.getResetPassword().getKorisnickoIme();
//            uloga = r.getResetPassword().getUloga();
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            
            stm.executeUpdate("update " + ulogaSesija + " set lozinka='" + lozinka + "' where korisnickoIme='" + korIme + "'");
                    
            return "potvrdaResetLozinke?faces-redirect=true";
            
        } catch (SQLException ex) {
            Logger.getLogger(ResetLozinku.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}
