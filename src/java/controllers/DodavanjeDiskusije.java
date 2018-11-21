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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@ViewScoped
public class DodavanjeDiskusije {

    private String nazivDiskusije, tekstDiskusije, kategorijaDiskusije, nivoVidljivosti, autorDiskusije, arhiviranaDiskusija, poruka, nazivGrupeStudenata, metaDiskusija;
    private LocalDateTime datumPostavljanjaDiskusije;
    private String[] nivoiVidljivosti = {
        "Svi i gosti", "Studenti sa istog kursa", "Svi studenti", "Studenti koji su rezultat pretrage", "Formirana grupa studenata", "Svi korisnici"};
    private List<String> listaKorisnikaZaDiskusiju;

    public String getMetaDiskusija() {
        return metaDiskusija;
    }

    public void setMetaDiskusija(String metaDiskusija) {
        this.metaDiskusija = metaDiskusija;
    }

    public List<String> getListaKorisnikaZaDiskusiju() {
        return listaKorisnikaZaDiskusiju;
    }

    public void setListaKorisnikaZaDiskusiju(List<String> listaKorisnikaZaDiskusiju) {
        this.listaKorisnikaZaDiskusiju = listaKorisnikaZaDiskusiju;
    }
    
    public String getNazivGrupeStudenata() {
        return nazivGrupeStudenata;
    }

    public void setNazivGrupeStudenata(String nazivGrupeStudenata) {
        this.nazivGrupeStudenata = nazivGrupeStudenata;
    }

    public String[] getNivoiVidljivosti() {
        return nivoiVidljivosti;
    }

    public void setNivoiVidljivosti(String[] nivoiVidljivosti) {
        this.nivoiVidljivosti = nivoiVidljivosti;
    }

    public String getNazivDiskusije() {
        return nazivDiskusije;
    }

    public void setNazivDiskusije(String nazivDiskusije) {
        this.nazivDiskusije = nazivDiskusije;
    }

    public String getTekstDiskusije() {
        return tekstDiskusije;
    }

    public void setTekstDiskusije(String tekstDiskusije) {
        this.tekstDiskusije = tekstDiskusije;
    }

    public String getKategorijaDiskusije() {
        return kategorijaDiskusije;
    }

    public void setKategorijaDiskusije(String kategorijaDiskusije) {
        this.kategorijaDiskusije = kategorijaDiskusije;
    }

    public String getNivoVidljivosti() {
        return nivoVidljivosti;
    }

    public void setNivoVidljivosti(String nivoVidljivosti) {
        this.nivoVidljivosti = nivoVidljivosti;
    }

    public String getAutorDiskusije() {
        return autorDiskusije;
    }

    public void setAutorDiskusije(String autorDiskusije) {
        this.autorDiskusije = autorDiskusije;
    }

    public String getArhiviranaDiskusija() {
        return arhiviranaDiskusija;
    }

    public void setArhiviranaDiskusija(String arhiviranaDiskusija) {
        this.arhiviranaDiskusija = arhiviranaDiskusija;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public LocalDateTime getDatumPostavljanjaDiskusije() {
        return datumPostavljanjaDiskusije;
    }

    public void setDatumPostavljanjaDiskusije(LocalDateTime datumPostavljanjaDiskusije) {
        this.datumPostavljanjaDiskusije = datumPostavljanjaDiskusije;
    }

    public String objaviDiskusiju() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("select nazivDiskusije from diskusija");

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            if (korisnik.getUloga() != null) {
                while (rs.next()) {
                    if (rs.getString("nazivDiskusije").equals(nazivDiskusije)) {
                        poruka = "Diskusija sa ovim nazivom vec postoji";
                        
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
                        FacesContext.getCurrentInstance().addMessage("porukaDodavanjeDiskusije", message);
                        
                        return null;
                    }
                }
                ResultSet rs1 = stm.executeQuery("select k.idKorisnik, javnoIme, v.idVidljivost from korisnik k, " + korisnik.getUloga() + " x, vidljivost v where k.korisnickoIme='" + korisnik.getKorisnickoIme() + "' && nivoVidljivosti='" + nivoVidljivosti + "' && k.idKorisnik=x.idKorisnik");
                rs1.next();

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                String datumPostavljanjaD = LocalDateTime.now().format(datePattern);

                autorDiskusije = rs1.getString("javnoIme");
                arhiviranaDiskusija = "false";
                int idVidljivost = rs1.getInt("idVidljivost");
                int idKorisnik = rs1.getInt("idKorisnik");
                
                ResultSet kategorija = stm.executeQuery("select idKategorije from Kategorije where nazivKategorije='" + kategorijaDiskusije + "'");
                int idKategorije = kategorija.getInt("idKategorije");
                
                stm.executeUpdate("insert into ucestvuje_kreira values (" + idKorisnik + ", 'kreator', 'diskusija')");
                
                stm.executeUpdate("insert into diskusija (nazivDiskusije, tekstDiskusija, datumPostavljanja, arhiviranaDiskusija, autorDiskusije, idKategorije, idVidljivost, metaDiskusija, idUcestvuje_kreira) values ('" + nazivDiskusije + "', '" + tekstDiskusije + "', '" + datumPostavljanjaD + "', '" + arhiviranaDiskusija + "', '" + autorDiskusije + "', " + idKategorije + ", " + idVidljivost + ", '" + metaDiskusija + "', (SELECT MAX(idUcestvuje_kreira) FROM ucestvuje_kreira))");
                
                ResultSet idDiskusije = stm.executeQuery("select idDiskusija from diskusija where datumPostavljanja='" + datumPostavljanjaD + "'");
                idDiskusije.next();
                
                switch (nivoVidljivosti) {
                    case "Svi i gosti":{
                        ResultSet rs3 = stm.executeQuery("select idKorisnik from korisnik where idKorisnik!=" + idKorisnik);
                        
                        ArrayList<Integer> nizKorisnika = new ArrayList<>();
                        
                        while (rs3.next()) {
                            nizKorisnika.add(rs3.getInt("idKorisnik"));
                            }
                        for (int i = 0; i < nizKorisnika.size(); i++) {
                            stm.executeUpdate("insert into ucestvuje_kreira values (" + nizKorisnika.get(i) + ", 'ucestvuje', 'diskusija')");
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
                            stm.executeUpdate("insert into ucestvuje_kreira values (" + nizKorisnika.get(i) + ", 'ucestvuje', 'diskusija')");
                        }
                        break;
                    }
                    case "Svi studenti":{
                        ResultSet rs5 = stm.executeQuery("select k.idKorisnik from korisnik k, student s where k.idKorisnik=s.idKorisnik && s.idKorisnik!=" + idKorisnik);
                        
                        ArrayList<Integer> nizKorisnika = new ArrayList<>();
                        
                        while (rs5.next()) {
                            nizKorisnika.add(rs5.getInt("idKorisnik"));
                            }
                        for (int i = 0; i < nizKorisnika.size(); i++) {
                            stm.executeUpdate("insert into ucestvuje_kreira values (" + nizKorisnika.get(i) + ", 'ucestvuje', 'diskusija'))");
                        }
                        break;
                    }
                    case "Studenti koji su rezultat pretrage":{
                        ArrayList<Integer> idKorisnika3 = new ArrayList<>();

                            for (String javnoIme : listaKorisnikaZaDiskusiju) {
                                ResultSet rs5 = stm.executeQuery("select idKorisnik from student where javnoIme='" + javnoIme + "'");
                                rs5.next();
                                idKorisnika3.add(rs5.getInt("idKorisnik"));
                            }


                        for (int i = 0; i < idKorisnika3.size(); i++) {
                            stm.executeUpdate("insert into ucestvuje_kreira values (" + idKorisnika3.get(i) + ", 'ucesnik', 'obavestenje')");
                        }
                        break;
                    }
                    case "Formirana grupa studenata":{
                        ResultSet rs7 = stm.executeQuery("select idKorisnik from uGrupi ug, grupa g where g.idGrupa=ug.idGrupa && nazivGrupe='" + nazivGrupeStudenata + "' && idKorisnik!=" + idKorisnik);
                        
                        ArrayList<Integer> nizKorisnika = new ArrayList<>();
                        
                        while (rs7.next()) {
                            nizKorisnika.add(rs7.getInt("idKorisnik"));
                            }
                        for (int i = 0; i < nizKorisnika.size(); i++) {
                            stm.executeUpdate("insert into ucestvuje_kreira values (" + nizKorisnika.get(i) + ", 'ucestvuje', 'diskusija')");
                        }
                        break;
                    }
                    case "Svi korisnici":
                        ResultSet rs8 = stm.executeQuery("select idKorisnik from korisnik where idKorisnik!=" + idKorisnik);
                        
                        ArrayList<Integer> nizKorisnika = new ArrayList<>();
                        
                        while (rs8.next()) {
                            nizKorisnika.add(rs8.getInt("idKorisnik"));
                            }
                        for (int i = 0; i < nizKorisnika.size(); i++) {
                            stm.executeUpdate("insert into ucestvuje_kreira values (" + nizKorisnika.get(i) + ", 'ucestvuje', 'diskusija')");
                        }
                        break;
                        
                }

                poruka = "Uspesno dodavanje diskusije";

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
                FacesContext.getCurrentInstance().addMessage("porukaDodavanjeDiskusije", message);

                return "prikazDiskusije?idDiskusija=" + idDiskusije.getInt("idDiskusija");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DodavanjeVesti.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
