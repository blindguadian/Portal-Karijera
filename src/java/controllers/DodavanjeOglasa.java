package controllers;

import beans.Korisnik;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class DodavanjeOglasa {

    private String naslovOglasa, tekstOglasa, autorOglasa, arhiviraniOglasi, poruka, nivoVidljivosti, kurs, nazivGrupeStudenata, metaOglasi;
    private int daniTrajanjaOglasa;
    private String[] nivoiVidljivosti = {
        "Studenti odredjenog kursa", "Svi studenti", "Studenti koji su rezultat pretrage", "Formirana grupa studenata", "Svi korisnici"};
    private List<String> listaKorisnikaZaOglas;

    public String getMetaOglasi() {
        return metaOglasi;
    }

    public void setMetaOglasi(String metaOglasi) {
        this.metaOglasi = metaOglasi;
    }

    public String getKurs() {
        return kurs;
    }

    public void setKurs(String kurs) {
        this.kurs = kurs;
    }

    public String getNazivGrupeStudenata() {
        return nazivGrupeStudenata;
    }

    public void setNazivGrupeStudenata(String nazivGrupeStudenata) {
        this.nazivGrupeStudenata = nazivGrupeStudenata;
    }

    public String getNaslovOglasa() {
        return naslovOglasa;
    }

    public void setNaslovOglasa(String naslovOglasa) {
        this.naslovOglasa = naslovOglasa;
    }

    public String getTekstOglasa() {
        return tekstOglasa;
    }

    public void setTekstOglasa(String tekstOglasa) {
        this.tekstOglasa = tekstOglasa;
    }

    public List<String> getListaKorisnikaZaOglas() {
        return listaKorisnikaZaOglas;
    }

    public void setListaKorisnikaZaOglas(List<String> listaKorisnikaZaOglas) {
        this.listaKorisnikaZaOglas = listaKorisnikaZaOglas;
    }

    public int getDaniTrajanjaOglasa() {
        return daniTrajanjaOglasa;
    }

    public void setDaniTrajanjaOglasa(int daniTrajanjaOglasa) {
        this.daniTrajanjaOglasa = daniTrajanjaOglasa;
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
        return naslovOglasa;
    }

    public void setNaslov(String naslov) {
        this.naslovOglasa = naslov;
    }

    public String getTekst() {
        return tekstOglasa;
    }

    public void setTekst(String tekst) {
        this.tekstOglasa = tekst;
    }

    public String getAutor() {
        return autorOglasa;
    }

    public void setAutor(String autor) {
        this.autorOglasa = autor;
    }

    public String getArhiviranaVest() {
        return arhiviraniOglasi;
    }

    public void setArhiviranaVest(String arhiviranaVest) {
        this.arhiviraniOglasi = arhiviranaVest;
    }

    public String objaviOglas() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("select naslov from Oglasi");

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            if (korisnik.getUloga().equals("kompanija")) {
                while (rs.next()) {
                    if (rs.getString("naslov").equals(naslovOglasa)) {
                        poruka = "Oglas sa ovim naslovom vec postoji";

                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
                        FacesContext.getCurrentInstance().addMessage("porukaDodavanjeOglasa", message);

                        return null;
                    }
                }
                ResultSet rs1 = stm.executeQuery("select k.idKorisnik, javnoIme, v.idVidljivost from korisnik k, " + korisnik.getUloga() + " x, vidljivost v where k.korisnickoIme='" + korisnik.getKorisnickoIme() + "' && nivoVidljivosti='" + nivoVidljivosti + "' && k.idKorisnik=x.idKorisnik");
                rs1.next();

                String idKorisnik = rs1.getString("idKorisnik");

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                String vremeUpisaOglasa = LocalDateTime.now().format(datePattern);
                String datumTrajanjaOglasa = LocalDateTime.now().plusDays(daniTrajanjaOglasa).format(datePattern);

                autorOglasa = rs1.getString("javnoIme");
                arhiviraniOglasi = "false";

                int idVidljivost = rs1.getInt("idVidljivost");

                stm.executeUpdate("insert into ucestvuje_kreira values (" + idKorisnik + ", 'postavlja', 'oglas')");
                
                stm.executeUpdate("insert into Oglasi (naslovOglasa, tekstOglasa, datumPostavljanja, autorOglasa, arhiviraniOglasi, idVidljivost, datumIsticanja, metaOglasi, idUcestvuje_kreira) values ('" + naslovOglasa + "', '" + tekstOglasa + "', '" + vremeUpisaOglasa + "', '" + autorOglasa + "', '" + arhiviraniOglasi + "', " + idVidljivost + ", '" + datumTrajanjaOglasa + "', '" + metaOglasi + "', (SELECT MAX(idUcestvuje_kreira) FROM ucestvuje_kreira))");

                ResultSet rs2 = stm.executeQuery("select idOglasi from Oglasi where naslovOglasa='" + naslovOglasa + "' && datumPostavljanja='" + vremeUpisaOglasa + "'");

                int idOglasa = rs2.getInt("idOglasi");
                
                
                switch (nivoVidljivosti) {
                    case "Studenti odredjenog kursa": {
                        ResultSet rs4 = stm.executeQuery("select k.idKorisnik from korisnik k, student s where s.idKorisnik=k.idKorisnik && kurs='" + kurs + "'");

                        ArrayList<Integer> nizKorisnika = new ArrayList<>();

                        while (rs4.next()) {
                            nizKorisnika.add(rs4.getInt("idKorisnik"));
                        }
                        for (int i = 0; i < nizKorisnika.size(); i++) {
                            stm.executeUpdate("insert into ucestvuje_kreira values (" + nizKorisnika.get(i) + ", 'ucestvuje', 'oglas')");
                        }
                        break;
                    }
                    case "Svi studenti": {
                        ResultSet rs5 = stm.executeQuery("select k.idKorisnik from korisnik k, student s where k.idKorisnik=s.idKorisnik");

                        ArrayList<Integer> nizKorisnika = new ArrayList<>();

                        while (rs5.next()) {
                            nizKorisnika.add(rs5.getInt("idKorisnik"));
                        }
                        for (int i = 0; i < nizKorisnika.size(); i++) {
                            stm.executeUpdate("insert into ucestvuje_kreira values (" + nizKorisnika.get(i) + ", 'ucestvuje', 'oglas')");
                        }
                        break;
                    }
                    case "Studenti koji su rezultat pretrage": {
                        ArrayList<Integer> idKorisnika3 = new ArrayList<>();

                            for (String javnoIme : listaKorisnikaZaOglas) {
                                ResultSet rs5 = stm.executeQuery("select idKorisnik from student where javnoIme='" + javnoIme + "'");
                                rs5.next();
                                idKorisnika3.add(rs5.getInt("idKorisnik"));
                            }


                        for (int i = 0; i < idKorisnika3.size(); i++) {
                            stm.executeUpdate("insert into ucestvuje_kreira values (" + idKorisnika3.get(i) + ", 'ucesnik', 'oglas')");
                        }
                        break;
                    }
                    case "Formirana grupa studenata": {
                        ResultSet rs7 = stm.executeQuery("select idKorisnik from uGrupi ug, grupa g where g.idGrupa=ug.idGrupa && nazivGrupe='" + nazivGrupeStudenata + "'");

                        ArrayList<Integer> nizKorisnika = new ArrayList<>();

                        while (rs7.next()) {
                            nizKorisnika.add(rs7.getInt("idKorisnik"));
                        }
                        for (int i = 0; i < nizKorisnika.size(); i++) {
                            stm.executeUpdate("insert into ucestvuje_kreira values (" + nizKorisnika.get(i) + ", 'ucestvuje', 'oglas')");
                        }
                        break;
                    }
                    case "Svi korisnici":
                        ResultSet rs8 = stm.executeQuery("select idKorisnik from korisnik");

                        ArrayList<Integer> nizKorisnika = new ArrayList<>();

                        while (rs8.next()) {
                            nizKorisnika.add(rs8.getInt("idKorisnik"));
                        }
                        for (int i = 0; i < nizKorisnika.size(); i++) {
                            stm.executeUpdate("insert into ucestvuje_kreira values (" + nizKorisnika.get(i) + ", 'ucestvuje', 'oglas')");
                        }
                        break;

                }
                poruka = "Uspesno dodavanje oglasa";

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
                FacesContext.getCurrentInstance().addMessage("porukaDodavanjeOglasa", message);

                return "prikazOglasa?idOglasi=" + idOglasa;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DodavanjeVesti.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
