package controllers;

import beans.Korisnik;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@ViewScoped
public class OstaviKomentar {

    private String tekstKomentara;

    public String getTekstKomentara() {
        return tekstKomentara;
    }

    public void setTekstKomentara(String tekstKomentara) {
        this.tekstKomentara = tekstKomentara;
    }
    
    public void ubaciKomentar() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            
            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");
            
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            
            int idDiskusija = Integer.parseInt(params.get("idDiskusija"));
            DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
            String datumKreiranjaKomentara = LocalDateTime.now().format(datePattern);
            
            ResultSet rs = stm.executeQuery("select idKorisnik from korisnik where korisnickoIme='" + korisnik.getKorisnickoIme() + "'");
            rs.next();
            
            int idKorisnik = rs.getInt("idKorisnik");
            stm.executeUpdate("insert into komentari (tekstKomentara, idDiskusija, idKorisnik, datumKreiranjaKomentara) values ('" + tekstKomentara + "', " + idDiskusija + ", " + idKorisnik + ", '" + datumKreiranjaKomentara + "')");
            
        } catch (SQLException ex) {
            Logger.getLogger(OstaviKomentar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
