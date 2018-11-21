package controllers;

import beans.Korisnik;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*
Klasa koja prima podatke o gostu iz forme (ViewScoped); sadrzi metode za kreiranje objekta (student/kompanija)
u bazi i popunjavanje datih podataka.
 */
@ManagedBean
@ViewScoped
public class Registracija {

    private String ime, prezime, srednjeIme, korisnickoIme, lozinka, mail, kurs, datumRodjenja, imeKompanije, mesto, ulica, brojSedista, oblastPoslovanja;
    private int PIN, brojZaposlenih;
    private long PIB;
    private List<Korisnik> listaStudenataZaRegistraciju, listaKompanijaZaRegistraciju;
    private List<String> sifarnikUlice, sifarnikMesta, sifarnikDrzave;
    private boolean uslov;
    /* Property "Message" sadrzi poruku vezanu za gresku pri upisu u bazu. 
Message je razlicit od null u dva slucaja:
    -kada postoji korisnik sa istim unesenim korisnickim imenom (korisnickoIme);
    -kada postoji korisnik sa istim unesenim mailom (mail).
    
    tabela sifMesto -->naziv
    tabela sifUlica --> naziv

    tabela sifKompanija --> naziv
     */
    private String poruka;

    public List<Korisnik> getListaStudenataZaRegistraciju() {
        return listaStudenataZaRegistraciju;
    }

    public void setListaStudenataZaRegistraciju(List<Korisnik> listaStudenataZaRegistraciju) {
        this.listaStudenataZaRegistraciju = listaStudenataZaRegistraciju;
    }

    public List<Korisnik> getListaKompanijaZaRegistraciju() {
        return listaKompanijaZaRegistraciju;
    }

    public void setListaKompanijaZaRegistraciju(List<Korisnik> listaKompanijaZaRegistraciju) {
        this.listaKompanijaZaRegistraciju = listaKompanijaZaRegistraciju;
    }

    public String getImeKompanije() {
        return imeKompanije;
    }

    public void setImeKompanije(String imeKompanije) {
        this.imeKompanije = imeKompanije;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getBrojSedista() {
        return brojSedista;
    }

    public void setBrojSedista(String brojSedista) {
        this.brojSedista = brojSedista;
    }

    public String getOblastPoslovanja() {
        return oblastPoslovanja;
    }

    public void setOblastPoslovanja(String oblastPoslovanja) {
        this.oblastPoslovanja = oblastPoslovanja;
    }

    public int getBrojZaposlenih() {
        return brojZaposlenih;
    }

    public void setBrojZaposlenih(int brojZaposlenih) {
        this.brojZaposlenih = brojZaposlenih;
    }

    public long getPIB() {
        return PIB;
    }

    public void setPIB(long PIB) {
        this.PIB = PIB;
    }

    private String[] kursevi = {
        "Java", "PHP"};

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getSrednjeIme() {
        return srednjeIme;
    }

    public void setSrednjeIme(String srednjeIme) {
        this.srednjeIme = srednjeIme;
    }

    public String getKurs() {
        return kurs;
    }

    public void setKurs(String kurs) {
        this.kurs = kurs;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public int getPIN() {
        return PIN;
    }

    public void setPIN(int PIN) {
        this.PIN = PIN;
    }

    public String getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(String datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String[] getKursevi() {
        return kursevi;
    }

    public void setKursevi(String[] kursevi) {
        this.kursevi = kursevi;
    }
    
    public boolean isUslov(){
        return uslov;
    }
    
    public void setUslov(boolean uslov){
        this.uslov = uslov;
    }
    
    String email = "nemanjat@yahoo.com",
            password = "blindguadian1",
            host = "smtp.mail.yahoo.com",
            port = "587";

    /*
    registracijaStudent() pravi novi upis u tabelu Student i upisuje podatke za 
    datog studenta (ime, prezime, srednjeIme, kurs, mail, korisnickoIme, lozinka, 
    PIN, datumRodjenja);
    Niz kurseva dat je u tabeli Kursevi(String);
     */
    public String registracijaStudent() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("select korisnickoIme, mail from korisnik where korisnickoIme='" + korisnickoIme + "' || mail='" + mail + "'");
            boolean provera=false;
            if (rs.next()){
                provera=true;
            }
            if (!provera){
                Statement stm2 = conn.createStatement();
                stm.executeUpdate("insert into korisnik (korisnickoIme, mail, lozinka, mailVidljivost) values ('" + korisnickoIme + "', '" + mail + "','" + lozinka + "', 'true')");
                
                ResultSet rs2=stm.executeQuery("select idKorisnik from korisnik where korisnickoIme='" + korisnickoIme + "'");
                rs2.next();
                int idKorisnik=rs2.getInt("idKorisnik");
                
                stm.executeUpdate("insert into student (idKorisnik, ime, prezime, srednjeIme, datumRodjenja, kurs, PIN, stanjeNaloga, imeVidljivost, prezimeVidljivost, srednjeImeVidljivost, datRodjenjaVidljivost, polVidljivost, kursVidljivost, PINVidljivost, telefonVidljivost, idSifUlicaVidljivost, brojPrebivalistaVidljivost, idSifMestaVidljivost, idSifDrzavaVidljivost, idSifDrzavljanstvoVidljivost, slikaVidljivost, CVVidljivost, statusVidljivost, idSifOblastInteresovanjaVidljivost, idSifProfesionalneVestineVidljivost) values (" + idKorisnik + ", '" + ime + "', '" + prezime + "', '" + srednjeIme + "', '" + datumRodjenja + "', '" + kurs + "', " + PIN + ",  'na cekanju', 'true', 'true', 'true', 'true', 'true', 'true', 'true', 'true', 'true', 'true', 'true', 'true', 'true', 'true', 'true', 'true', 'true', 'true')");
                
                
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
                msg.setContent("<h1 style=\"color:red;\">Registracija korisnika</h1><br/> Vas zahtev za kreiranje naloga je uspesno dostavljen. Cim admin prihvati zahtev, dobicete obavestenje na ovaj mail. Srecno, zaista ce Vam trebati na ovom sajtu. Ajd boze pomozi", "text/html");

                Transport transport = session.getTransport("smtp");
                transport.connect(host, email, password);
                transport.sendMessage(msg, msg.getAllRecipients());
                transport.close();

                return "potvrdaRegistracije";
            
            }
        } catch (SQLException | MessagingException ex) {
            Logger.getLogger(Registracija.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public String registracijaKompanija() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("(select korisnickoIme as korisnickoImeStudent, mail from student where korisnickoIme='" + korisnickoIme + "' || mail='" + mail + "') union all (select korisnickoIme as korisnickoImeKompanija, mail from kompanija where korisnickoIme='" + korisnickoIme + "' || mail='" + mail + "')");

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
            
            boolean provera=false;
            if (rs.next()){
                provera=true;
            }
            if (!provera){
                ResultSet rsulica = stm.executeQuery("select idSifUlica from sifUlica where nazivUlice='" + ulica + "'");
                int idUlice = rsulica.getInt("idSifUlica");
                
                stm.executeUpdate("insert into SifKompanija(nazivKompanije) values ('" + imeKompanije + "')");
                
                ResultSet nazivKomp = stm.executeQuery("select idSifKompanija from sifKompanija where nazivKompanije='" + imeKompanije + "'");
                
                int idSifKompanije = nazivKomp.getInt("idSifKompanije");
                
                ResultSet idKomp = stm.executeQuery("select idKorisnik from korisnik where korisnickoIme='" + korisnickoIme + "'");
                int idKorisnik = idKomp.getInt("idKorisnik");
                
                stm.executeUpdate("insert into kompanija (idKorisnik, idSifKompanija, brojSedista, mail, oblastPoslovanja, brojZaposlenih, korisnickoIme, lozinka, PIB, stanjeNaloga, idSifUlica) values (" + idKorisnik + ", " + idSifKompanije + ", " + brojSedista + ", '" + mail + "', '" + oblastPoslovanja + "', " + brojZaposlenih + ", '" + korisnickoIme + "', '" + lozinka + "', " + PIB + ", 'na cekanju', " + idUlice + ")");

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
                msg.setContent("<h1 style=\"color:red;\">Registracija korisnika</h1><br/> Vas zahtev za kreiranje naloga je uspesno dostavljen. Cim admin prihvati zahtev, dobicete obavestenje na ovaj mail. Srecno, zaista ce Vam trebati na ovom sajtu. Ajd boze pomozi", "text/html");

                Transport transport = session.getTransport("smtp");
                transport.connect(host, email, password);
                transport.sendMessage(msg, msg.getAllRecipients());
                transport.close();

                return "potvrdaRegistracije";

            } else if (rs.getString("mail").equals(mail)) {
                poruka = "Nalog sa ovim mejlom vec postoji.";
                FacesContext.getCurrentInstance().addMessage("porukaArhiviranjeVesti", message);
                return null;
            } else {
                poruka = "Nalog sa ovim korisnickim imenom vec postoji.";
                FacesContext.getCurrentInstance().addMessage("porukaArhiviranjeVesti", message);
                return null;
            }

        } catch (SQLException | MessagingException ex) {
            Logger.getLogger(Registracija.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public String potvrdiStudenta() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
            
            int idKorisnik = Integer.parseInt(params.get("idKorisnik"));
            
            stm.executeUpdate("update student set stanjeNaloga='aktivan' where idKorisnik=" + idKorisnik);
            
            poruka="Studentov nalog je sada aktivan";
            
            FacesContext.getCurrentInstance().addMessage("porukaArhiviranjeVesti", message);
            
            return "registracijaAdmin";
        } catch (SQLException ex) {
            Logger.getLogger(Registracija.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public String potvrdiKompaniju(){
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
            
            int idKorisnik = Integer.parseInt(params.get("idKorisnik"));
            
            stm.executeUpdate("update kompanija set stanjeNaloga='aktivan' where idKorisnik=" + idKorisnik);
            
            poruka="Studentov nalog je sada aktivan";
            
            FacesContext.getCurrentInstance().addMessage("porukaArhiviranjeVesti", message);
            
            return "registracijaAdmin";
        } catch (SQLException ex) {
            Logger.getLogger(Registracija.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public void ucitajKorisnikeZaRegistraciju(){
        try{
        Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

//            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
//            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            ResultSet studenti = stm.executeQuery("select ime, prezime, srednjeIme, datumRodjenja, kurs, PIN, mail, korisnickoIme, lozinka, javnoIme from student, korisnik where stanjeNaloga='na cekanju'");

            listaStudenataZaRegistraciju = new ArrayList<>();

            while (studenti.next()) {
                Korisnik s = new Korisnik();
                
                s.setIme(studenti.getString("ime"));
                s.setPrezime(studenti.getString("prezime"));
                s.setLozinka(studenti.getString("lozinka"));
                s.setMail(studenti.getString("mail"));
                s.setSrednjeIme(studenti.getString("srednjeIme"));
                s.setKurs(studenti.getString("kurs"));
                s.setPIN(studenti.getInt("PIN"));
                s.setKorisnickoIme(studenti.getString("korisnickoIme"));
                s.setJavnoIme(studenti.getString("javnoIme"));
                
                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd");
                String datumRodjenjax = studenti.getDate("datumRodjenja").toString();
                LocalDate datumRodjenja1 = LocalDate.parse(datumRodjenjax, datePattern);
                s.setDatumRodjenja(datumRodjenja1);

                listaStudenataZaRegistraciju.add(s);
            }
            
            ResultSet kompanije = stm.executeQuery("select nazivKompanije, javnoIme, brojSedista, mail, oblastPoslovanja, brojZaposlenih, korisnickoIme, lozinka, PIB, stanjeNaloga, nazivUlice, nazivMesta, nazivDrzave from kompanija kom, korisnik, sifUlica su, sifMesta, sifDrzava, sifKompanija sk where sk.idSifKompanije=kom.idSifKompanije stanjeNaloga='na cekanju' && kom.idSifUlica=su.idSifUlica");
            
            listaKompanijaZaRegistraciju = new ArrayList<>();
           
            while(kompanije.next()){
                Korisnik k = new Korisnik();
                
                k.setLozinka(kompanije.getString("lozinka"));
                k.setKorisnickoIme(kompanije.getString("korisnickoIme"));
                k.setMail(kompanije.getString("mail"));
                k.setNazivUlice(kompanije.getString("nazivUlice"));
                k.setNazivDrzave(kompanije.getString("nazivDrzave"));
                k.setNazivMesta(kompanije.getString("nazivMesta"));
                k.setJavnoIme(kompanije.getString("javnoIme"));
                k.setNazivKompanije(kompanije.getString("nazivKompanije"));
                k.setOblastPoslovanja(kompanije.getString("oblastPoslovanja"));
                k.setBrojSedista(kompanije.getInt("brojSedista"));
                k.setBrojZaposlenih(kompanije.getInt("brojZaposlenih"));
                k.setPIB(kompanije.getInt("PIB"));
                k.setNazivUlice(kompanije.getString("nazivUlice"));


                listaKompanijaZaRegistraciju.add(k);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
