package controllers;

import beans.Korisnik;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.time.LocalDate;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class Login {

    private String korisnickoIme, lozinka, poruka;
    private String ulogovan;

    LocalDate datumLogovanja = java.time.LocalDate.now();

    public String isUlogovan() {
        return ulogovan;
    }

    public void setUlogovan(String ulogovan) {
        this.ulogovan = ulogovan;
    }


    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String u) {
        this.korisnickoIme = u;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String password) {
        this.lozinka = password;
    }

    private Korisnik korisnik;

    public LocalDate getDatumLogovanja() {
        return datumLogovanja;
    }

    public void setDatumLogovanja(LocalDate datumLogovanja) {
        this.datumLogovanja = datumLogovanja;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public String login() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);

            ResultSet rs = stm.executeQuery("select k.korisnickoIme, k.lozinka, s.stanjeNaloga, s.javnoIme from korisnik k, student s where s.idKorisnik=k.idKorisnik");
            while (rs.next()) {
                if (rs.getString("korisnickoIme").equals(korisnickoIme) && rs.getString("lozinka").equals(lozinka)) {

                    if (rs.getString("stanjeNaloga").equals("na cekanju")) {
                        poruka = "Ovaj korisnicki nalog jos nije odobren";
                        FacesContext.getCurrentInstance().addMessage("porukaLogin", message);
                    } else {
                        String javnoIme = rs.getString("javnoIme");
                        stm.executeUpdate("update student s, korisnik k set datumLogovanja='" + datumLogovanja + "' where k.korisnickoIme='" + korisnickoIme + "' && k.idKorisnik=s.idKorisnik");
                        HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

                        korisnik = new Korisnik();
                        korisnik.setKorisnickoIme(korisnickoIme);
                        korisnik.setLozinka(lozinka);
                        korisnik.setUloga("student");
                        korisnik.setJavnoIme(javnoIme);
                        ulogovan = "true";
                        
                        sesija.setAttribute("ulogovan", ulogovan);
                        sesija.setAttribute("korisnik", korisnik);

                        return "faces/homepageStudent?faces-redirect=true";
                    }
                }
            }

            ResultSet rs1 = stm.executeQuery("select k.korisnickoIme, k.lozinka, ko.stanjeNaloga, ko.javnoIme from korisnik k, kompanija ko where ko.idKorisnik=k.idKorisnik");
            while (rs1.next()) {
                if (rs1.getString("korisnickoIme").equals(korisnickoIme) && rs1.getString("lozinka").equals(lozinka)) {
                    if (rs1.getString("stanjeNaloga").equals("na cekanju")) {
                        poruka = "Ovaj korisnicki nalog jos nije odobren";
                        FacesContext.getCurrentInstance().addMessage("porukaLogin", message);
                    } else {
                        String javnoIme = rs1.getString("javnoIme");

                        HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

                        korisnik = new Korisnik();
                        korisnik.setKorisnickoIme(korisnickoIme);
                        korisnik.setLozinka(lozinka);
                        korisnik.setUloga("kompanija");
                        korisnik.setJavnoIme(javnoIme);
                        ulogovan = "true";
                        
                        sesija.setAttribute("ulogovan", ulogovan);
                        sesija.setAttribute("korisnik", korisnik);

                        return "faces/homepageKompanija?faces-redirect=true";
                    }
                }
            }

            ResultSet rs2 = stm.executeQuery("select k.korisnickoIme, k.lozinka from korisnik k, administrator a where a.idKorisnik=k.idKorisnik");
            while (rs2.next()) {
                if (rs2.getString("korisnickoIme").equals(korisnickoIme) && rs2.getString("lozinka").equals(lozinka)) {

                    String javnoIme = rs2.getString("javnoIme");
                    HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

                    korisnik = new Korisnik();
                    korisnik.setKorisnickoIme(korisnickoIme);
                    korisnik.setLozinka(lozinka);
                    korisnik.setUloga("administrator");
                    korisnik.setJavnoIme(javnoIme);
                    ulogovan = "true";
                        
                    sesija.setAttribute("ulogovan", ulogovan);
                    sesija.setAttribute("korisnik", korisnik);

                    return "faces/homepageAdministrator?faces-redirect=true";
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        poruka = "Korisnik sa ovim nalogom ne postoji. Pokusajte ponovo.";

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
        FacesContext.getCurrentInstance().addMessage("porukaLogin", message);

        return null;
    }
}
