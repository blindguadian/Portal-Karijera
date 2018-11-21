package controllers;

import beans.Korisnik;
import beans.Pitanje;
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
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class DodavanjeAnkete {

    private String nazivAnkete, tekstAnkete, nivoVidljivosti, poruka, autorAnkete, arhiviranaAnketa, nazivGrupeStudenata, kurs;
    private int daniTrajanjaAnkete;
    private List<Pitanje> pitanjaAnkete;
    private String[] nivoiVidljivosti = {"Svi studenti", "Studenti koji su rezultat pretrage", "Formirana grupa studenata", "Svi korisnici"};
    private List<String> listaKorisnikaZaAnketu;
//    @PostConstruct

    public List<String> getListaKorisnikaZaAnketu() {
        return listaKorisnikaZaAnketu;
    }

    public void setListaKorisnikaZaAnketu(List<String> listaKorisnikaZaAnketu) {
        this.listaKorisnikaZaAnketu = listaKorisnikaZaAnketu;
    }
    
    public List<Pitanje> getPitanjaAnkete() {
        return pitanjaAnkete;
    }

    public void setPitanjaAnkete(List<Pitanje> pitanjaAnkete) {
        this.pitanjaAnkete = pitanjaAnkete;
    }

    public String[] getNivoiVidljivosti() {
        return nivoiVidljivosti;
    }

    public void setNivoiVidljivosti(String[] nivoiVidljivosti) {
        this.nivoiVidljivosti = nivoiVidljivosti;
    }

    public String getNazivGrupeStudenata() {
        return nazivGrupeStudenata;
    }

    public void setNazivGrupeStudenata(String nazivGrupeStudenata) {
        this.nazivGrupeStudenata = nazivGrupeStudenata;
    }

    public int getDaniTrajanjaAnkete() {
        return daniTrajanjaAnkete;
    }

    public void setDaniTrajanjaAnkete(int daniTrajanjaAnkete) {
        this.daniTrajanjaAnkete = daniTrajanjaAnkete;
    }

    public String getArhiviranaAnketa() {
        return arhiviranaAnketa;
    }

    public void setArhiviranaAnketa(String arhiviranaAnketa) {
        this.arhiviranaAnketa = arhiviranaAnketa;
    }

    public String getAutorAnkete() {
        return autorAnkete;
    }

    public void setAutorAnkete(String autorAnkete) {
        this.autorAnkete = autorAnkete;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public String getNazivAnkete() {
        return nazivAnkete;
    }

    public void setNazivAnkete(String nazivAnkete) {
        this.nazivAnkete = nazivAnkete;
    }

    public String getTekstAnkete() {
        return tekstAnkete;
    }

    public void setTekstAnkete(String tekstAnkete) {
        this.tekstAnkete = tekstAnkete;
    }

    public String getNivoVidljivosti() {
        return nivoVidljivosti;
    }

    public void setNivoVidljivosti(String nivoVidljivosti) {
        this.nivoVidljivosti = nivoVidljivosti;
    }

    public void postaviPitanja() {
        pitanjaAnkete = new ArrayList();
        pitanjaAnkete.add(new Pitanje());
    }

//    public List<String> sacuvajPitanja(){
//        
//    }
    
    public String dodajPitanje() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
        if (pitanjaAnkete.size() < 20) {
            pitanjaAnkete.add(new Pitanje());           
            return null;
        } else {
            poruka = "Ne mozete dodati vise od 20 pitanja";
            FacesContext.getCurrentInstance().addMessage("porukaDodavanjeAnkete", message);
            return null;
        }
        
    }
    
    public void sacuvajPitanje(ValueChangeEvent e){
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
        
        String novaVrednost = e.getNewValue().toString();
        int index = (int) e.getComponent().getAttributes().get("index");
        
        for(int i=0; i<pitanjaAnkete.size(); i++){
            if(pitanjaAnkete.get(i).getTekstPitanja() == null){
            }else if(novaVrednost.equals(pitanjaAnkete.get(i).getTekstPitanja())){
                poruka = "Ovo pitanje je vec postavljeno";
                FacesContext.getCurrentInstance().addMessage("porukaDodavanjePitanja", message);
                return;
            }
        }
        pitanjaAnkete.get(index).setTekstPitanja(novaVrednost);
    }
    
    public void ukloniPitanje(ActionEvent e) {
        String index = e.getComponent().getAttributes().get("index").toString();
        int i = Integer.parseInt(index);
        pitanjaAnkete.remove(i);
    }

    public String objaviAnketu() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("select nazivAnkete from ankete");

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            if (korisnik.getUloga().equals("kompanija")) {
                while (rs.next()) {
                    if (rs.getString("nazivAnkete").equals(nazivAnkete)) {
                        poruka = "Anketa sa ovim nazivom vec postoji";
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
                        FacesContext.getCurrentInstance().addMessage("porukaDodavanjeAnkete", message);
                        return null;
                    }
                }

                ResultSet rs1 = stm.executeQuery("select k.idKorisnik, javnoIme, v.idVidljivost from korisnik k, kompanija kom, vidljivost v where k.korisnickoIme='" + korisnik.getKorisnickoIme() + "' && v.nivoVidljivosti='" + nivoVidljivosti + "' && k.idKorisnik=kom.idKorisnik");
                rs1.next();

                int idKorisnik = rs1.getInt("idKorisnik");
                int idVidljivost = rs1.getInt("idVidljivost");
                autorAnkete = rs1.getString("javnoIme");
                arhiviranaAnketa = "false";
//                String kurs = rs1.getString("kurs");

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                String datumTrajanjaAnkete = LocalDateTime.now().plusDays(daniTrajanjaAnkete).format(datePattern);
                
                stm.executeUpdate("insert into ucestvuje_kreira (idKorisnik, tipUcesnika, tipObavestenja) values (" + idKorisnik + ", 'kreator', 'anketa')");
                
                stm.executeUpdate("insert into ankete (nazivAnkete, tekstAnkete, datumTrajanjaAnkete, arhiviranaAnketa, autorAnkete, idVidljivost, idUcestvuje_kreira) values ('" + nazivAnkete + "', '" + tekstAnkete + "', '" + datumTrajanjaAnkete + "', '" + arhiviranaAnketa + "', '" + autorAnkete + "', " + idVidljivost + ", (select MAX(idUcestvuje_kreira) from ucestvuje_kreira))");

                ResultSet rs2 = stm.executeQuery("select idAnkete from ankete a, ucestvuje_kreira uk where idKorisnik=" + idKorisnik + " && tipUcesnika='kreator' && a.idUcestvuje_kreira=(select MAX(idUcestvuje_kreira) from ankete)");
                rs2.next();

                int idAnkete = rs2.getInt("idAnkete");

                
                switch (nivoVidljivosti) {
//                    case "Svi i gosti":
//                        ResultSet rs3 = stm.executeQuery("select idKorisnik from korisnik k where k.idKorisnik!=" + idKorisnik);
//
//                        List<Integer> nizKorisnika3 = new ArrayList<>();
//
//                        while (rs3.next()) {
//                            nizKorisnika3.add(rs3.getInt("idKorisnik"));
//                        }
//                        for (int i = 0; i < nizKorisnika3.size(); i++) {
//                            stm.executeUpdate("insert into ucestvuje_kreira values (" + nizKorisnika3.get(i) + ", " + idAnkete + ", 'anketa', 'nijePopunio')");
//                        }
//                        for (int i=0; i<anketaPitanja.size(); i++){
//                            stm.executeUpdate("insert into pitanjaAnkete values (" + idAnkete + ", '" + pitanjaAnkete.get(i) + "')");
//                        }
//                        break;

                    case "Studenti sa odredjenog kursa": {
                        ResultSet rs3 = stm.executeQuery("select idKorisnik from student where kurs='" + kurs + "' && idKorisnik!=" + idKorisnik);

                        ArrayList<Integer> idKorisnika1 = new ArrayList<>();

                        while (rs3.next()) {
                            idKorisnika1.add(rs3.getInt("idKorisnik"));
                        }

                        for (int i = 0; i < idKorisnika1.size(); i++) {
                            stm.executeUpdate("insert into ucestvuje_kreira (idKorisnik, idTipObavestenja, tipObavestenja, tipUcesnika) values (" + idKorisnika1.get(i) + ", " + idAnkete + ", 'anketa', 'ucesnik')");
                        }
                        for (int i = 0; i < pitanjaAnkete.size(); i++) {
                            stm.executeUpdate("insert into pitanjaAnkete values (" + idAnkete + ", '" + pitanjaAnkete.get(i).getTekstPitanja() + "', '" + pitanjaAnkete.get(i).getTipPitanja() + "', " + pitanjaAnkete.get(i).getRedniBrojPitanja() + ")");
                        }
                        break;
                    }
                    case "Svi studenti":
                        ResultSet rs5 = stm.executeQuery("select k.idKorisnik from korisnik k, student s where k.idKorisnik=s.idKorisnik && k.idKorisnik!=" + idKorisnik);

                        List<Integer> nizKorisnika5 = new ArrayList<>();

                        while (rs5.next()) {
                            nizKorisnika5.add(rs5.getInt("idKorisnik"));
                        }

                        for (int i = 0; i < nizKorisnika5.size(); i++) {
                            stm.executeUpdate("insert into ucestvuje_kreira (idKorisnik, tipUcesnika, tipObavestenja) values (" + nizKorisnika5.get(i) + ", 'nijePopunio', 'anketa')");
                        }
                        for (int i = 0; i < pitanjaAnkete.size(); i++) {
                            stm.executeUpdate("insert into pitanjaAnkete values (" + idAnkete + ", '" + pitanjaAnkete.get(i).getTekstPitanja() + "', '" + pitanjaAnkete.get(i).getTipPitanja() + "', " + pitanjaAnkete.get(i).getRedniBrojPitanja() + ")");
                        }

                        break;

                    case "Studenti koji su rezultat pretrage":{
                        ArrayList<Integer> idKorisnika3 = new ArrayList<>();

                            for (String javnoIme : listaKorisnikaZaAnketu) {
                                ResultSet rsx = stm.executeQuery("select idKorisnik from student where javnoIme='" + javnoIme + "'");
                                rsx.next();
                                idKorisnika3.add(rsx.getInt("idKorisnik"));
                            }
                            

                        for (int i = 0; i < idKorisnika3.size(); i++) {
                            stm.executeUpdate("insert into ucestvuje_kreira values (" + idKorisnika3.get(i) + ", 'ucestvuje', 'anketa')");
                        }
                        for (int i = 0; i < pitanjaAnkete.size(); i++) {
                            stm.executeUpdate("insert into pitanjaAnkete values (" + idAnkete + ", '" + pitanjaAnkete.get(i).getTekstPitanja() + "', '" + pitanjaAnkete.get(i).getTipPitanja() + "', " + pitanjaAnkete.get(i).getRedniBrojPitanja() + ")");
                        }
                        break;
                    }
                    case "Formirana grupa studenata":
                        List<Integer> nizKorisnika7 = new ArrayList<>();

                        ResultSet rs7 = stm.executeQuery("select idKorisnik from uGrupi ug, grupa g where g.idGrupa=ug.idGrupa && nazivGrupe='" + nazivGrupeStudenata + "' && ug.idKorisnik!=" + idKorisnik);
                        while (rs7.next()) {
                            nizKorisnika7.add(rs7.getInt("idKorisnik"));
                        }
                        for (int i = 0; i < nizKorisnika7.size(); i++) {
                            stm.executeUpdate("insert into ucestvuje_kreira values (" + nizKorisnika7.get(i) + ", " + idAnkete + ", 'anketa', 'nijePopunio')");
                        }
                        for (int i = 0; i < pitanjaAnkete.size(); i++) {
                            stm.executeUpdate("insert into pitanjaAnkete values (" + idAnkete + ", '" + pitanjaAnkete.get(i).getTekstPitanja() + "', '" + pitanjaAnkete.get(i).getTipPitanja() + "', " + pitanjaAnkete.get(i).getRedniBrojPitanja() + ")");
                        }
                        break;

                    case "Svi korisnici":
                        ResultSet rs8 = stm.executeQuery("select idKorisnik from korisnik k where k.idKorisnik!=" + idKorisnik);

                        List<Integer> nizKorisnika8 = new ArrayList<>();

                        while (rs8.next()) {
                            nizKorisnika8.add(rs8.getInt("idKorisnik"));
                        }
                        for (int i = 0; i < nizKorisnika8.size(); i++) {
                            stm.executeUpdate("insert into ucestvuje_kreira values (" + nizKorisnika8.get(i) + ", " + idAnkete + ", 'anketa', 'nijePopunio')");
                        }
                        for (int i = 0; i < pitanjaAnkete.size(); i++) {
                            stm.executeUpdate("insert into pitanjaAnkete values (" + idAnkete + ", '" + pitanjaAnkete.get(i).getTekstPitanja() + "', '" + pitanjaAnkete.get(i).getTipPitanja() + "', " + pitanjaAnkete.get(i).getRedniBrojPitanja() + ")");
                        }
                        break;

                }

                poruka = "Uspesno dodavanje ankete";

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
                FacesContext.getCurrentInstance().addMessage("porukaDodavanjeAnkete", message);

                return "ankete";
            }
        } catch (SQLException ex) {
            Logger.getLogger(DodavanjeVesti.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
