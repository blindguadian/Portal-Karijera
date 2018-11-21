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
public class DodavanjePreporuke {

    private String nazivPreporuke, tekstPreporuke, autorPreporuke, poruka, nivoVidljivosti, kurs, nazivGrupeStudenata, nazivFakulteta, nazivKompanije, metaPreporuke;
    private String[] nivoiVidljivosti = {
        "Studenti odredjenog kursa", "Svi studenti", "Studenti koji su rezultat pretrage", "Formirana grupa studenata", "Svi korisnici", "Diplomirani studenti odredjenog fakulteta", "Odredjena kompanija", "Sve kompanije"};
    private List<String> listaKorisnikaZaPreporuku;

    public String getMetaPreporuke() {
        return metaPreporuke;
    }

    public void setMetaPreporuke(String metaPreporuke) {
        this.metaPreporuke = metaPreporuke;
    }

    public String getAutorPreporuke() {
        return autorPreporuke;
    }

    public void setAutorPreporuke(String autorPreporuke) {
        this.autorPreporuke = autorPreporuke;
    }

    public String getNazivFakulteta() {
        return nazivFakulteta;
    }

    public void setNazivFakulteta(String nazivFakulteta) {
        this.nazivFakulteta = nazivFakulteta;
    }

    public String getNazivKompanije() {
        return nazivKompanije;
    }

    public void setNazivKompanije(String nazivKompanije) {
        this.nazivKompanije = nazivKompanije;
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

    public String getNazivPreporuke() {
        return nazivPreporuke;
    }

    public void setNazivPreporuke(String nazivPreporuke) {
        this.nazivPreporuke = nazivPreporuke;
    }

    public String getTekstPreporuke() {
        return tekstPreporuke;
    }

    public void setTekstPreporuke(String tekstPreporuke) {
        this.tekstPreporuke = tekstPreporuke;
    }

    public List<String> getListaKorisnikaZaPreporuku() {
        return listaKorisnikaZaPreporuku;
    }

    public void setListaKorisnikaZaOglas(List<String> listaKorisnikaZaPreporuku) {
        this.listaKorisnikaZaPreporuku = listaKorisnikaZaPreporuku;
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

    public String objaviPreporuku() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            if (korisnik.getUloga().equals("student")) {

                ResultSet rs1 = stm.executeQuery("select k.idKorisnik, javnoIme, v.idVidljivost from korisnik k, " + korisnik.getUloga() + " x, vidljivost v where k.korisnickoIme='" + korisnik.getKorisnickoIme() + "' && nivoVidljivosti='" + nivoVidljivosti + "' && k.idKorisnik=x.idKorisnik");
                rs1.next();

                String idKorisnik = rs1.getString("idKorisnik");

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                String datumPostavljanja = LocalDateTime.now().format(datePattern);

                autorPreporuke = rs1.getString("javnoIme");

                int idVidljivost = rs1.getInt("idVidljivost");

                stm.executeUpdate("insert into ucestvuje_kreira values (" + idKorisnik + ", 'postavlja', 'preporuka')");
                
                stm.executeUpdate("insert into Preporuke (nazivPreporuke, tekstPreporuke, datumKreiranjaPreporuke, autorPreporuke, idVidljivost, metaPreporuke, idUcestvuje_kreira) values ('" + nazivPreporuke + "', '" + tekstPreporuke + "', '" + datumPostavljanja + "', '" + autorPreporuke + "',  " + idVidljivost + ", '" + metaPreporuke + "', (SELECT MAX(idUcestvuje_kreira) FROM ucestvuje_kreira))");

                ResultSet rs2 = stm.executeQuery("select idPreporuke from Preporuke where nazivPreporuke='" + nazivPreporuke + "' && datumKreiranjaPreporuke='" + datumPostavljanja + "'");

                int idPreporuke = rs2.getInt("idPreporuke");

                switch (nivoVidljivosti) {
                    case "Studenti odredjenog kursa": {
                        ResultSet rs4 = stm.executeQuery("select k.idKorisnik from korisnik k, student s where s.idKorisnik=k.idKorisnik && kurs='" + kurs + "' && k.korisnickoIme!='" + korisnik.getKorisnickoIme() + "'");

                        ArrayList<Integer> nizKorisnika = new ArrayList<>();

                        while (rs4.next()) {
                            nizKorisnika.add(rs4.getInt("idKorisnik"));
                        }
                        for (int i = 0; i < nizKorisnika.size(); i++) {
                            stm.executeUpdate("insert into ucestvuje_kreira values (" + nizKorisnika.get(i) + ", 'ucestvuje', 'preporuka')");
                        }
                        break;
                    }
                    case "Svi studenti": {
                        ResultSet rs5 = stm.executeQuery("select k.idKorisnik from korisnik k, student s where k.idKorisnik=s.idKorisnik && k.korisnickoIme!='" + korisnik.getKorisnickoIme() + "'");

                        ArrayList<Integer> nizKorisnika = new ArrayList<>();

                        while (rs5.next()) {
                            nizKorisnika.add(rs5.getInt("idKorisnik"));
                        }
                        for (int i = 0; i < nizKorisnika.size(); i++) {
                            stm.executeUpdate("insert into ucestvuje_kreira values (" + nizKorisnika.get(i) + ", 'ucestvuje', 'preporuka')");
                        }
                        break;
                    }
                    case "Studenti koji su rezultat pretrage": {
                        ArrayList<Integer> idKorisnika3 = new ArrayList<>();

                            for (String javnoIme : listaKorisnikaZaPreporuku) {
                                ResultSet rs5 = stm.executeQuery("select idKorisnik from student where javnoIme='" + javnoIme + "'");
                                rs5.next();
                                idKorisnika3.add(rs5.getInt("idKorisnik"));
                            }

                        for (int i = 0; i < idKorisnika3.size(); i++) {
                            stm.executeUpdate("insert into ucestvuje_kreira values (" + idKorisnika3.get(i) + ", 'ucestvuje', 'preporuka')");
                        }
                        break;
                    }
                    case "Formirana grupa studenata": {
                        ResultSet rs7 = stm.executeQuery("select ug.idKorisnik from uGrupi ug, grupa g, korisnik k where g.idGrupa=ug.idGrupa && nazivGrupe='" + nazivGrupeStudenata + "' && k.korisnickoIme!='" + korisnik.getKorisnickoIme() + "' && k.idKorisnik=ug.idKorisnik");

                        ArrayList<Integer> nizKorisnika = new ArrayList<>();

                        while (rs7.next()) {
                            nizKorisnika.add(rs7.getInt("idKorisnik"));
                        }
                        for (int i = 0; i < nizKorisnika.size(); i++) {
                            stm.executeUpdate("insert into ucestvuje_kreira values (" + nizKorisnika.get(i) + ", 'ucestvuje', 'preporuka')");
                        }
                        break;
                    }
                    case "Svi korisnici": {
                        ResultSet rs8 = stm.executeQuery("select idKorisnik from korisnik where korisnickoIme!='" + korisnik.getKorisnickoIme() + "'");

                        ArrayList<Integer> nizKorisnika = new ArrayList<>();

                        while (rs8.next()) {
                            nizKorisnika.add(rs8.getInt("idKorisnik"));
                        }
                        for (int i = 0; i < nizKorisnika.size(); i++) {
                            stm.executeUpdate("insert into ucestvuje_kreira values (" + nizKorisnika.get(i) + ", 'ucestvuje', 'preporuka')");
                        }
                        break;
                    }
                    case "Diplomirani studenti odredjenog fakulteta": {
                        ResultSet rs = stm.executeQuery("select idSifFakulteta from sifFakulteta where nazivFakulteta='" + nazivFakulteta + "'");

                        int idFakulteta = rs.getInt("idSifFakulteta");

                        ResultSet rs8 = stm.executeQuery("select idKorisnik from korisnik k, sifFakulteta sf, zavrsio z, diploma d where k.korisnickoIme!='" + korisnik.getKorisnickoIme() + "' && k.idKorisnik=z.idKorisnik && z.idDiploma=d.idDiploma && d.idSifFakulteta=" + idFakulteta);

                        ArrayList<Integer> nizKorisnika = new ArrayList<>();

                        while (rs8.next()) {
                            nizKorisnika.add(rs8.getInt("idKorisnik"));
                        }
                        for (int i = 0; i < nizKorisnika.size(); i++) {
                            stm.executeUpdate("insert into ucestvuje_kreira values (" + nizKorisnika.get(i) + ", 'ucestvuje', 'preporuka')");
                        }
                        break;
                    }
                    case "Odredjena kompanija": {
                        ResultSet rs8 = stm.executeQuery("select idKorisnik from kompanija kom, sifKompanija sk where sk.nazivKompanije='" + nazivKompanije + "' && sk.idSifKompanija=kom.idSifKompanija && korisnickoIme!='" + korisnik.getKorisnickoIme() + "'");

                        ArrayList<Integer> nizKorisnika = new ArrayList<>();

                        while (rs8.next()) {
                            nizKorisnika.add(rs8.getInt("idKorisnik"));
                        }
                        for (int i = 0; i < nizKorisnika.size(); i++) {
                            stm.executeUpdate("insert into ucestvuje_kreira values (" + nizKorisnika.get(i) + ", 'ucestvuje', 'preporuka')");
                        }
                        break;
                    }
                    case "Sve kompanije": {
                        ResultSet rs8 = stm.executeQuery("select idKorisnik from kompanija where korisnickoIme!='" + korisnik.getKorisnickoIme() + "'");

                        ArrayList<Integer> nizKorisnika = new ArrayList<>();

                        while (rs8.next()) {
                            nizKorisnika.add(rs8.getInt("idKorisnik"));
                        }
                        for (int i = 0; i < nizKorisnika.size(); i++) {
                            stm.executeUpdate("insert into ucestvuje_kreira values (" + nizKorisnika.get(i) + ", 'ucestvuje', 'preporuka')");
                        }
                        break;
                    }
                }

                poruka = "Uspesno dodavanje preporuke";

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
                FacesContext.getCurrentInstance().addMessage("porukaDodavanjePreporuke", message);

                return "prikazPreporuke?idPreporuke=" + idPreporuke;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DodavanjeVesti.class.getName()).log(Level.SEVERE, null, ex);
        }
        poruka = "Neuspesno dodavanje preporuke. Pokusajte ponovo";

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
        FacesContext.getCurrentInstance().addMessage("porukaDodavanjePreporuke", message);
        return null;
    }
}
