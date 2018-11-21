package controllers;

import beans.Korisnik;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public class KreiranjeGrupe {

    private String nazivGrupe, nivoVidljivosti, poruka, nazivFakulteta, nazivKompanije;
    private String[] nivoiVidljivosti = {
        "Svi i gosti", "Studenti sa istog kursa", "Svi studenti", "Studenti koji su rezultat pretrage", "Svi korisnici"};
    private List<String> listaKorisnikaZaGrupu;

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

    public List<String> getListaKorisnikaZaGrupu() {
        return listaKorisnikaZaGrupu;
    }

    public void setListaKorisnikaZaGrupu(List<String> listaKorisnikaZaGrupu) {
        this.listaKorisnikaZaGrupu = listaKorisnikaZaGrupu;
    }
    
    public String[] getNivoiVidljivosti() {
        return nivoiVidljivosti;
    }

    public void setNivoiVidljivosti(String[] nivoiVidljivosti) {
        this.nivoiVidljivosti = nivoiVidljivosti;
    }

    public String getNazivGrupe() {
        return nazivGrupe;
    }

    public void setNazivGrupe(String nazivGrupe) {
        this.nazivGrupe = nazivGrupe;
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

    public String kreirajGrupu() {

        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("select nazivGrupe from grupa");

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            if (korisnik.getUloga() != null) {
                while (rs.next()) {
                    if (rs.getString("nazivGrupe").equals(nazivGrupe)) {

                        poruka = "Grupa sa ovim nazivom vec postoji";

                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
                        FacesContext.getCurrentInstance().addMessage("porukaKreiranjeGrupe", message);

                        return null;
                    }
                }
                
                ResultSet rs1 = stm.executeQuery("select k.idKorisnik, javnoIme, v.idVidljivost from korisnik k, " + korisnik.getUloga() + " x, vidljivost v where k.korisnickoIme='" + korisnik.getKorisnickoIme() + "' && nivoVidljivosti='" + nivoVidljivosti + "'");
                rs1.next();
                
                int idVidljivost = rs1.getInt("idVidljivost");
                int idKorisnik = rs1.getInt("idKorisnik");
                
                stm.executeUpdate("insert into grupa (nazivGrupe, idVidljivost) values ('" + nazivGrupe + "', " + idVidljivost + ")");
                
                ResultSet rs2 = stm.executeQuery("select idGrupa from grupa where nazivGrupe='" + nazivGrupe + "'");
                rs2.next();
                
                int idGrupa = rs2.getInt("idGrupa");
                
                stm.executeUpdate("insert into uGrupi (idKorisnik, idGrupa, drugClan) values (" + idKorisnik + ", " + idGrupa + ", 'kreira')");
                
                switch (nivoVidljivosti) {
                    case "Svi i gosti":{
                        ResultSet rs3 = stm.executeQuery("select idKorisnik from korisnik where idKorisnik!=" + idKorisnik);
                        
                        ArrayList<Integer> nizKorisnika = new ArrayList<>();
                        
                        while (rs3.next()) {
                            nizKorisnika.add(rs3.getInt("idKorisnik"));
                            }
                        for (int i = 0; i < nizKorisnika.size(); i++) {
                            stm.executeUpdate("insert into uGrupi (idKorisnik, idGrupa, drugClan) values (" + nizKorisnika.get(i) + ", " + idGrupa + ", 'pozvan')");
                        }
                        break;
                    }
                    case "Studenti sa istog kursa":{
                        ResultSet rs4 = stm.executeQuery("select k.idKorisnik from korisnik k, student s where s.idKorisnik=k.idKorisnik && kurs=(select kurs from Student s, Korisnik k where korisnickoIme='" + korisnik.getKorisnickoIme() + "' && k.idKorisnik=s.idKorisnik) && s.idKorisnik!=" + idKorisnik);
                        
                        ArrayList<Integer> nizKorisnika = new ArrayList<>();
                        
                        while (rs4.next()) {
                            nizKorisnika.add(rs4.getInt("idKorisnik"));
                            }
                        for (int i = 0; i < nizKorisnika.size(); i++) {
                            stm.executeUpdate("insert into uGrupi (idKorisnik, idGrupa, drugClan) values (" + nizKorisnika.get(i) + ", " + idGrupa + ", 'pozvan')");
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
                            stm.executeUpdate("insert into uGrupi (idKorisnik, idGrupa, drugClan) values (" + nizKorisnika.get(i) + ", " + idGrupa + ", 'pozvan')");
                        }
                        break;
                    }
                    case "Studenti koji su rezultat pretrage": {
                        ArrayList<Integer> nizKorisnika = new ArrayList<>();

                            for (String javnoIme : listaKorisnikaZaGrupu) {
                                ResultSet rs5 = stm.executeQuery("select idKorisnik from student where javnoIme='" + javnoIme + "'");
                                rs5.next();
                                nizKorisnika.add(rs5.getInt("idKorisnik"));
                            }

                        for (int i = 0; i < nizKorisnika.size(); i++) {
                            stm.executeUpdate("insert into uGrupi (idKorisnik, idGrupa, drugClan) values (" + nizKorisnika.get(i) + ", " + idGrupa + ", 'pozvan')");
                        }
                        break;
                    }
                    case "Formirana grupa studenata": {
                        ResultSet rs7 = stm.executeQuery("select ug.idKorisnik from uGrupi ug, grupa g, korisnik k where g.idGrupa=ug.idGrupa && nazivGrupe='" + nazivGrupe + "' && k.korisnickoIme!='" + korisnik.getKorisnickoIme() + "' && k.idKorisnik=ug.idKorisnik");

                        ArrayList<Integer> nizKorisnika = new ArrayList<>();

                        while (rs7.next()) {
                            nizKorisnika.add(rs7.getInt("idKorisnik"));
                        }
                        for (int i = 0; i < nizKorisnika.size(); i++) {
                            stm.executeUpdate("insert into uGrupi (idKorisnik, idGrupa, drugClan) values (" + nizKorisnika.get(i) + ", " + idGrupa + ", 'pozvan')");
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
                            stm.executeUpdate("insert into uGrupi (idKorisnik, idGrupa, drugClan) values (" + nizKorisnika.get(i) + ", " + idGrupa + ", 'pozvan')");
                        }
                        break;
                    }
                    case "Diplomirani studenti odredjenog fakulteta": {
                        ResultSet rs3 = stm.executeQuery("select idSifFakulteta from sifFakulteta where nazivFakulteta='" + nazivFakulteta + "'");

                        int idFakulteta = rs3.getInt("idSifFakulteta");

                        ResultSet rs8 = stm.executeQuery("select idKorisnik from korisnik k, sifFakulteta sf, zavrsio z, diploma d where k.korisnickoIme!='" + korisnik.getKorisnickoIme() + "' && k.idKorisnik=z.idKorisnik && z.idDiploma=d.idDiploma && d.idSifFakulteta=" + idFakulteta);

                        ArrayList<Integer> nizKorisnika = new ArrayList<>();

                        while (rs8.next()) {
                            nizKorisnika.add(rs8.getInt("idKorisnik"));
                        }
                        for (int i = 0; i < nizKorisnika.size(); i++) {
                            stm.executeUpdate("insert into uGrupi (idKorisnik, idGrupa, drugClan) values (" + nizKorisnika.get(i) + ", " + idGrupa + ", 'pozvan')");
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
                            stm.executeUpdate("insert into uGrupi (idKorisnik, idGrupa, drugClan) values (" + nizKorisnika.get(i) + ", " + idGrupa + ", 'pozvan')");
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
                            stm.executeUpdate("insert into uGrupi (idKorisnik, idGrupa, drugClan) values (" + nizKorisnika.get(i) + ", " + idGrupa + ", 'pozvan')");
                        }
                        break;
                    }
                }
                
                poruka = "Uspesno kreiranje grupe";
                
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
                FacesContext.getCurrentInstance().addMessage("porukaKreiranjeGrupe", message);
                
                return "grupa?nazivGrupe=" + nazivGrupe;
            }

        } catch (SQLException ex) {
            Logger.getLogger(KreiranjeGrupe.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
