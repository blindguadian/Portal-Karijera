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
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped

public class DodavanjeObavestenja {

    private String naslov, tekst, autor, arhiviranaObavestenja, poruka, nivoVidljivosti, kurs, nazivGrupeStudenata, saljeNa, metaObavestenja;

    private final String[] nivoiVidljivosti = {
        "Studenti odredjenog kursa", "Svi studenti", "Studenti koji su rezultat pretrage", "Formirana grupa studenata", "Svi korisnici"};
    private final String[] saljeNaLista = {
        "Na profil korisnika", "Na email korisnika"};

    /*Lista za korisnike izabrane po search-u*/
    private List<String> listaKorisnikaZaObavestenje;

    String email = "nemanjat@yahoo.com",
            password = "blindguadian1",
            host = "smtp.mail.yahoo.com",
            port = "587";

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

    public String getSaljeNa() {
        return saljeNa;
    }

    public void setSaljeNa(String saljeNa) {
        this.saljeNa = saljeNa;
    }

    public String getMetaObavestenja() {
        return metaObavestenja;
    }

    public void setMetaObavestenja(String metaObavestenja) {
        this.metaObavestenja = metaObavestenja;
    }

    public List<String> getListaKorisnikaZaObavestenje() {
        return listaKorisnikaZaObavestenje;
    }

    public void setListaKorisnikaZaObavestenje(List<String> listaKorisnikaZaObavestenje) {
        this.listaKorisnikaZaObavestenje = listaKorisnikaZaObavestenje;
    }

    public String[] getsaljeNaLista() {
        return saljeNaLista;
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

    public String getArhiviranaObavestenja() {
        return arhiviranaObavestenja;
    }

    public void setArhiviranaObavestenja(String arhiviranaObavestenja) {
        this.arhiviranaObavestenja = arhiviranaObavestenja;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public String getNivoVidljivosti() {
        return nivoVidljivosti;
    }

    public void setNivoVidljivosti(String nivoVidljivosti) {
        this.nivoVidljivosti = nivoVidljivosti;
    }

    public String[] getNivoiVidljivosti() {
        return nivoiVidljivosti;
    }

    public String posljiObavestenje() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            ResultSet rs = stm.executeQuery("select k.idKorisnik, javnoIme, v.idVidljivost from korisnik k, " + korisnik.getUloga() + " x, vidljivost v where k.korisnickoIme='" + korisnik.getKorisnickoIme() + "' && nivoVidljivosti='" + nivoVidljivosti + "' && k.idKorisnik=x.idKorisnik");
            rs.next();

            DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
            String vremeUpisaObavestenja = LocalDateTime.now().format(datePattern);

            String idKorisnik = rs.getString("idKorisnik");
            autor = rs.getString("javnoIme");
            arhiviranaObavestenja = "false";

            int idVidljivost = rs.getInt("idVidljivost");

            stm.executeUpdate("insert into ucestvuje_kreira values (" + idKorisnik + ", 'kreator', 'obavestenje')");
            
            stm.executeUpdate("insert into obavestenja (naslovObavestenja, tekstObavestenja, datumKreiranjaObavestenja, arhiviranoObavestenje, idVidljivost, saljeNa, metaObavestenja, idUcestvuje_kreira) values ('" + naslov + "', '" + tekst + "', '" + vremeUpisaObavestenja + "', '" + arhiviranaObavestenja + "', " + idVidljivost + ", '" + saljeNa + "', '" + metaObavestenja + "', (SELECT MAX(idUcestvuje_kreira) FROM ucestvuje_kreira))");

            ResultSet rs1 = stm.executeQuery("select idObavestenja from Obavestenja where naslovObavestenja='" + naslov + "' && datumKreiranjaObavestenja='" + vremeUpisaObavestenja + "'");
            rs1.next();
            int idObavestenja = rs1.getInt("idObavestenja");

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);

            poruka = "Neuspesno slanje obavestenja. Pokusajte ponovo";

            FacesContext.getCurrentInstance().addMessage("porukaSlanjeObavestenja", message);

            if (saljeNa.equals("Na profil korisnika")) {
                switch (nivoVidljivosti) {
                    case "Studenti odredjenog kursa":

                        ResultSet rs2 = stm.executeQuery("select idKorisnik from student where kurs='" + kurs + "' && idKorisnik!=" + idKorisnik);

                        ArrayList<Integer> idKorisnika1 = new ArrayList<>();

                        while (rs2.next()) {
                            idKorisnika1.add(rs.getInt("idKorisnik"));
                        }

                        for (int i = 0; i < idKorisnika1.size(); i++) {
                            stm.executeUpdate("insert into ucestvuje_kreira values (" + idKorisnika1.get(i) + ", 'ucesnik', 'obavestenje')");
                        }
                        break;
                    case "Svi studenti":
                        ResultSet rs3 = stm.executeQuery("select idKorisnik from student where idKorisnik!=" + idKorisnik);

                        ArrayList<Integer> idKorisnika2 = new ArrayList<>();

                        while (rs3.next()) {
                            idKorisnika2.add(rs.getInt("idKorisnik"));
                        }

                        for (int i = 0; i < idKorisnika2.size(); i++) {
                            stm.executeUpdate("insert into ucestvuje_kreira values (" + idKorisnika2.get(i) + ", 'ucesnik', 'obavestenje')");
                        }

                        break;
                    case "Studenti koji su rezultat pretrage":{
                        ArrayList<Integer> idKorisnika3 = new ArrayList<>();

                            for (String javnoIme : listaKorisnikaZaObavestenje) {
                                ResultSet rs5 = stm.executeQuery("select idKorisnik from student where javnoIme='" + javnoIme + "'");
                                rs5.next();
                                idKorisnika3.add(rs5.getInt("idKorisnik"));
                            }


                        for (int i = 0; i < idKorisnika3.size(); i++) {
                            stm.executeUpdate("insert into ucestvuje_kreira values (" + idKorisnika3.get(i) + ", 'ucesnik', 'obavestenje')");
                        }
                        break;
                    }
                    case "Formirana grupa studenata":

                        ResultSet rs6 = stm.executeQuery("select idKorisnik from uGrupi ug, grupa g where g.idGrupa=ug.idGrupa && g.nazivGrupe='" + nazivGrupeStudenata + "' && idKorisnik!=" + idKorisnik);

                        ArrayList<Integer> idKorisnika4 = new ArrayList<>();

                        while (rs6.next()) {
                            idKorisnika4.add(rs.getInt("idKorisnik"));
                        }

                        for (int i = 0; i < idKorisnika4.size(); i++) {
                            stm.executeUpdate("insert into ucestvuje_kreira values (" + idKorisnika4.get(i) + ", 'ucesnik', 'obavestenje')");
                        }

                        break;
                    case "Svi korisnici":

                        ResultSet rs7 = stm.executeQuery("(select idKorisnik from Kompanija where idKorisnik!=" + idKorisnik + ") union all (select idKorisnik from Student where idKorisnik!=" + idKorisnik + ")");

                        ArrayList<Integer> idKorisnika5 = new ArrayList<>();

                        while (rs7.next()) {
                            idKorisnika5.add(rs.getInt("idKorisnik"));
                        }

                        for (int i = 0; i < idKorisnika5.size(); i++) {
                            stm.executeUpdate("insert into ucestvuje_kreira values (" + idKorisnika5.get(i) + ", 'ucesnik', 'obavestenje')");
                        }

                        break;
                }
                poruka = "Uspesno slanje obavestenja";

                FacesContext.getCurrentInstance().addMessage("porukaSlanjeObavestenja", message);

                return "prikazObavestenja?idObavestenja=" + idObavestenja;
                
            } else if (saljeNa.equals("Na email korisnika")) {
                switch (nivoVidljivosti) {
                    case "Studenti odredjenog kursa": {
                        ResultSet rs2 = stm.executeQuery("select k.idKorisnik, mail from student s, korisnik k where k.idKorisnik=s.idKorisnik && kurs='" + kurs + "' && s.idKorisnik!=" + idKorisnik);

                        ArrayList<String> mailKorisnika1 = new ArrayList<>();
                        ArrayList<Integer> idKorisnika1 = new ArrayList<>();

                        while (rs2.next()) {
                            idKorisnika1.add(rs.getInt("idKorisnik"));
                            mailKorisnika1.add("mail");
                        }

                        for (int i = 0; i < idKorisnika1.size(); i++) {
                            stm.executeUpdate("insert into ucestvuje_kreira values (" + idKorisnika1.get(i) + ", 'ucesnik', 'obavestenje')");
                            posaljiMail(mailKorisnika1.get(i));
                        }
                        break;
                    }
                    case "Svi studenti": {
                        ResultSet rs3 = stm.executeQuery("select k.idKorisnik, mail from student s, korisnik k where k.idKorisnik=s.idKorisnik && s.idKorisnik!=" + idKorisnik);

                        ArrayList<String> mailKorisnika2 = new ArrayList<>();
                        ArrayList<Integer> idKorisnika2 = new ArrayList<>();

                        while (rs3.next()) {
                            idKorisnika2.add(rs.getInt("idKorisnik"));
                            mailKorisnika2.add("mail");
                        }

                        for (int i = 0; i < idKorisnika2.size(); i++) {
                            stm.executeUpdate("insert into ucestvuje_kreira values (" + idKorisnika2.get(i) + ", 'ucesnik', 'obavestenje')");
                            posaljiMail(mailKorisnika2.get(i));
                        }

                        break;
                    }
                    case "Studenti koji su rezultat pretrage": {
                        ArrayList<Integer> idKorisnika3 = new ArrayList<>();
                        ArrayList<String> mailKorisnika3 = new ArrayList<>();

                        ResultSet rs4 = stm.executeQuery("select javnoIme, k.idKorisnik, mail from Student s, Korisnik k where k.idKorisnik=k.idKorisnik");

                        listaKorisnikaZaObavestenje = new ArrayList<>();

                        while (rs4.next()) {
                            for (int i = 0; i < listaKorisnikaZaObavestenje.size(); i++) {
                                if (listaKorisnikaZaObavestenje.get(i).equals(rs4.getString("javnoIme"))) {
                                    idKorisnika3.add(rs4.getInt("idKorisnik"));
                                    mailKorisnika3.add(rs4.getString("mail"));
                                }
                            }
                        }

                        ResultSet rs5 = stm.executeQuery("select idKorisnik, javnoIme from student");

                        for (int i = 0; i < idKorisnika3.size(); i++) {
                            stm.executeUpdate("insert into ucestvuje_kreira values (" + idKorisnika3.get(i) + ", 'ucesnik', 'obavestenje')");
                            posaljiMail(mailKorisnika3.get(i));
                        }

                        break;
                    }
                    case "Formirana grupa studenata": {

                        ResultSet rs6 = stm.executeQuery("select k.idKorisnik, mail from uGrupi ug, grupa g, korisnik k where k.idKorisnik=ug.idKorisnik && g.idGrupa=ug.idGrupa && g.nazivGrupe='" + nazivGrupeStudenata + "' && k.idKorisnik!=" + idKorisnik);

                        ArrayList<Integer> idKorisnika4 = new ArrayList<>();
                        ArrayList<String> mailKorisnika4 = new ArrayList<>();

                        while (rs6.next()) {
                            idKorisnika4.add(rs.getInt("idKorisnik"));
                            mailKorisnika4.add(rs.getString("mail"));
                        }

                        for (int i = 0; i < idKorisnika4.size(); i++) {
                            stm.executeUpdate("insert into ucestvuje_kreira values (" + idKorisnika4.get(i) + ", 'ucesnik', 'obavestenje')");
                            posaljiMail(mailKorisnika4.get(i));
                        }

                        break;
                    }
                    case "Svi korisnici": {

                        ResultSet rs7 = stm.executeQuery("(select k.idKorisnik, mail from Kompanija kom, Korisnik k where k.idKorisnik=kom.idKorisnik && k.idKorisnik!=" + idKorisnik + ") union all (select k.idKorisnik, mail from Student s, Korisnik k where k.idKorisnik=kom.idKorisnik && idKorisnik!=" + idKorisnik + ")");

                        ArrayList<Integer> idKorisnika5 = new ArrayList<>();

                        while (rs7.next()) {
                            idKorisnika5.add(rs.getInt("idKorisnik"));
                        }

                        for (int i = 0; i < idKorisnika5.size(); i++) {
                            stm.executeUpdate("insert into ucestvuje_kreira values (" + idKorisnika5.get(i) + ", 'ucesnik', 'obavestenje'))");
                        }
                        break;
                        
                    }
                }

                poruka = "Uspesno slanje obavestenja";

                FacesContext.getCurrentInstance().addMessage("porukaDodavanjeOglasa", message);
                
                return "prikazObavestenja?idObavestenja=" + idObavestenja;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DodavanjeVesti.class.getName()).log(Level.SEVERE, null, ex);

        }
        return null;
    }

    public void posaljiMail(String mail) {
        try {
            Properties props = System.getProperties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.user", email);
            props.put("mail.smtp.password", password);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props);

            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(email));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
            msg.setSubject("Registarcija na Portal Karijera");
            msg.setContent("<h1 style=\"color:red;\">Obavestenje</h1><br/>" + tekst, "text/html");

            Transport transport = session.getTransport("smtp");
            transport.connect(host, email, password);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();

        } catch (AddressException ex) {
            Logger.getLogger(DodavanjeObavestenja.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(DodavanjeObavestenja.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
