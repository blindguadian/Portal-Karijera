package controllers;

import beans.Korisnik;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class DodavanjeVesti {

    private String naslov, tekst, autor, arhiviranaVest, poruka, nivoVidljivosti, kategorija, metaVesti, nazivGrupeStudenata;
    private LocalDateTime datumPostavljanjaVesti;
    private String[] nivoiVidljivosti = {
        "Svi i gosti", "Studenti sa istog kursa", "Svi studenti", "Studenti koji su rezultat pretrage", "Formirana grupa studenata", "Svi korisnici"};

    public String getNazivGrupeStudenata() {
        return nazivGrupeStudenata;
    }

    public void setNazivGrupeStudenata(String nazivGrupeStudenata) {
        this.nazivGrupeStudenata = nazivGrupeStudenata;
    }

    public String getMetaVesti() {
        return metaVesti;
    }

    public void setMetaVesti(String metaVesti) {
        this.metaVesti = metaVesti;
    }
    
    public String getKategorija() {
        return kategorija;
    }

    public void setKategorija(String kategorija) {
        this.kategorija = kategorija;
    }

    public String[] getNivoiVidljivosti() {
        return nivoiVidljivosti;
    }

    public void setNivoiVidljivosti(String[] nivoiVidljivosti) {
        this.nivoiVidljivosti = nivoiVidljivosti;
    }
    public String getNivoVidljivosti() {
        return nivoVidljivosti;
    }

    public void setNivoVidljivosti(String nivoVidljivosti) {
        this.nivoVidljivosti = nivoVidljivosti;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }
    
    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getArhiviranaVest() {
        return arhiviranaVest;
    }

    public void setArhiviranaVest(String arhiviranaVest) {
        this.arhiviranaVest = arhiviranaVest;
    }

    public LocalDateTime getDatumPostavljanjaVesti() {
        return datumPostavljanjaVesti;
    }

    public void setDatumPostavljanjaVesti(LocalDateTime datumPostavljanjaVesti) {
        this.datumPostavljanjaVesti = datumPostavljanjaVesti;
    }

    public String objaviVest() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("select naslov from vesti");

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            if (korisnik.getUloga() != null) {
                while (rs.next()) {
                    if (rs.getString("naslov").equals(naslov)) {
                        poruka = "Vest sa ovim naslovom vec postoji";
                        
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
                        FacesContext.getCurrentInstance().addMessage("porukaDodavanjeVesti", message);
                        
                        return null;
                    }
                }
                ResultSet rs1 = stm.executeQuery("select k.idKorisnik, javnoIme, v.idVidljivost, kat.idKategorije from korisnik k, " + korisnik.getUloga() + " x, vidljivost v, kategorije kat where k.korisnickoIme='" + korisnik.getKorisnickoIme() + "' && nivoVidljivosti='" + nivoVidljivosti + "' && k.idKorisnik=x.idKorisnik && nazivKategorije='" + kategorija + "'");
                rs1.next();
                String idKorisnik = rs1.getString("idKorisnik");

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                String vremeUpisaVesti = LocalDateTime.now().format(datePattern);
                
                autor = rs1.getString("javnoIme");
                arhiviranaVest = "false";

                int idVidljivost = rs1.getInt("idVidljivost");
                int idKategorije = rs1.getInt("idKategorije");
                
                stm.executeUpdate("insert into ucestvuje_kreira values (" + idKorisnik + ", 'kreator', 'vest')");

                stm.executeUpdate("insert into vesti (naslovVesti, tekstVesti, datumPostavljanjaVesti, arhiviranaVest, idKorisnik, idVidljivost, idKategorije, metaVesti, autorVesti, idUcestvuje_kreira) values ('" + naslov + "', '" + tekst + "', '" + vremeUpisaVesti + "', '" + arhiviranaVest + "', " + idKorisnik + ", " + idVidljivost + ", " + idKategorije + ", '" + metaVesti + "', '" + autor + "', (SELECT MAX(idUcestvuje_kreira) FROM ucestvuje_kreira))");
                
                ResultSet rs2 = stm.executeQuery("select idVesti from vesti where naslovVesti='" + naslov + "' && datumPostavljanjaVesti='" + vremeUpisaVesti + "'");
                rs2.next();

                poruka = "Uspesno dodavanje vesti";
                
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
                FacesContext.getCurrentInstance().addMessage("porukaDodavanjeVesti", message);
                
                return "prikazVesti?idVesti=" + rs2.getInt("idVesti");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DodavanjeVesti.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
