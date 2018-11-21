package controllers;

import beans.Diploma;
import beans.Korisnik;
import beans.Zaposlenje;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.model.UploadedFile;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Korisnik
 */
@ManagedBean
@SessionScoped
public class AzurirajPodatke implements Serializable {

    private String poruka;
    private UploadedFile slikaStudenta, logoKompanije, CVStudenta;
    private List<Diploma> listaDiplomaStudenta;
    private List<Zaposlenje> listaZaposlenjaStudenta;
    private Diploma diploma;
    private Zaposlenje zaposlenje;

    public Diploma getDiploma() {
        return diploma;
    }

    public void setDiploma(Diploma diploma) {
        this.diploma = diploma;
    }

    public Zaposlenje getZaposlenje() {
        return zaposlenje;
    }

    public void setZaposlenje(Zaposlenje zaposlenje) {
        this.zaposlenje = zaposlenje;
    }

    public List<Diploma> getListaDiplomaStudenta() {
        return listaDiplomaStudenta;
    }

    public void setListaDiplomaStudenta(List<Diploma> listaDiplomaStudenta) {
        this.listaDiplomaStudenta = listaDiplomaStudenta;
    }

    public List<Zaposlenje> getListaZaposlenjaStudenta() {
        return listaZaposlenjaStudenta;
    }

    public void setListaZaposlenjaStudenta(List<Zaposlenje> listaZaposlenjaStudenta) {
        this.listaZaposlenjaStudenta = listaZaposlenjaStudenta;
    }

    public UploadedFile getSlikaStudenta() {
        return slikaStudenta;
    }

    public void setSlikaStudenta(UploadedFile slikaStudenta) {
        this.slikaStudenta = slikaStudenta;
    }

    public UploadedFile getLogoKompanije() {
        return logoKompanije;
    }

    public void setLogoKompanije(UploadedFile logoKompanije) {
        this.logoKompanije = logoKompanije;
    }

    public UploadedFile getCVStudenta() {
        return CVStudenta;
    }

    public void setCVStudenta(UploadedFile CVStudenta) {
        this.CVStudenta = CVStudenta;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    private void uploadLogoKompanije() {

        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            UploadedFile logo = getLogoKompanije();

            String filePath = "C:/Projekat4/Files/LogoKompanije";
            byte[] bytes = null;

            if (null != logo) {
                bytes = logo.getContents();
                String filename = FilenameUtils.getName(logo.getFileName());
                try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath + filename)))) {
                    stream.write(bytes);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(AzurirajPodatke.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(AzurirajPodatke.class.getName()).log(Level.SEVERE, null, ex);
                }
                stm.executeUpdate("update kompanija set logo='" + filePath + filename + "' where korisnickoIme='" + korisnik.getKorisnickoIme() + "'");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AzurirajPodatke.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void uploadSlikuStudenta() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            UploadedFile slika = getSlikaStudenta();

            String filePath = "C:/Projekat4/Files/SlikaStudenta";
            byte[] bytes = null;

            if (null != slika) {
                bytes = slika.getContents();
                String filename = FilenameUtils.getName(slika.getFileName());
                try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath + filename)))) {
                    stream.write(bytes);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(AzurirajPodatke.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(AzurirajPodatke.class.getName()).log(Level.SEVERE, null, ex);
                }
                stm.executeUpdate("update student set slika='" + filePath + filename + "' where korisnickoIme='" + korisnik.getKorisnickoIme() + "'");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AzurirajPodatke.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void uploadCVStudenta() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            UploadedFile CV = getCVStudenta();

            String filePath = "C:/Projekat4/Files/CVStudenta";
            byte[] bytes = null;

            if (null != CV) {
                bytes = CV.getContents();
                String filename = FilenameUtils.getName(CV.getFileName());
                try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath + filename)))) {
                    stream.write(bytes);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(AzurirajPodatke.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(AzurirajPodatke.class.getName()).log(Level.SEVERE, null, ex);
                }
                stm.executeUpdate("update student set CV='" + filePath + filename + "' where korisnickoIme='" + korisnik.getKorisnickoIme() + "'");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AzurirajPodatke.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void dodajDiplomu() {
        try {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);

            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            ResultSet rs = stm.executeQuery("select nazivFakulteta, odsek from diploma d, zavrsio z, korisnik k, sifFakulteta sf where k.idKorisnik=z.idKorisnik && z.idDiploma=d.idDiploma && k.korisnickoIme='" + korisnik.getKorisnickoIme() + "' && sf.idSifFakulteta=d.idSifFakulteta");

            while (rs.next()) {
                if (diploma.getNazivFakulteta().equals(rs.getString("nazivFakulteta")) && diploma.getOdsek().equals(rs.getString("odsek"))) {
                    poruka = "Ova diploma vec postoji";
                    FacesContext.getCurrentInstance().addMessage("porukaDodavanjeDiplome", message);
                    return;
                }
            }
            listaDiplomaStudenta.add(diploma);
            
            ResultSet rs1 = stm.executeQuery("select idSifFakulteta, idSifMesta, idSifPozicija, idSifKompanija from sifFakulteta sf, sifMesta sm, sifPozicija sp, sifKompanija sk where sf.nazivFakulteta='" + diploma.getNazivFakulteta() + "' && sm.nazivMesta='" + zaposlenje.getNazivMesta() + "' && sp.nazivPozicije='" + zaposlenje.getNazivPozicije() + "' && sk.nazivKompanije='" + zaposlenje.getNazivKompanije() + "'");
            rs1.next();

            int idSIfFakulteta = rs1.getInt("idSifFakulteta");
            
            stm.executeUpdate("insert into diploma values (" + idSIfFakulteta + ", '" + diploma.getOdsek() +"', '" + diploma.getNivoStudija() + "', '" + diploma.getGodUpisa() + "', '" + diploma.getGodZavrsetka() + "', '" + diploma.getZvanje() + "')");
            
            ResultSet rs3 = stm.executeQuery("select idKorisnik from korisnik where korisnickoIme='" + korisnik.getKorisnickoIme() + "'");
            
            int idKorisnik = rs3.getInt("idKorisnik");
            
            ResultSet rs2 = stm.executeQuery("select idDiploma from diploma where idSifFakulteta=" + idSIfFakulteta + " && odsek='" + diploma.getOdsek() + "'");
            
            int idDiploma = rs2.getInt("idDiploma");
            
            stm.executeUpdate("insert into zavrsio values (" + idDiploma + ", " + idKorisnik + ")");
            
        } catch (SQLException ex) {
            Logger.getLogger(AzurirajPodatke.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void dodajZaposlenje() {
        try {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);

            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            ResultSet rs = stm.executeQuery("select nazivKompanije, nazivPozicije, k.idKorisnik from zaposlenje z, sifPozicija sp, korisnik k, radi r where k.idKorisnik=r.idKorisnik && z.idZaposlenje=r.idZaposlenje && k.korisnickoIme='" + korisnik.getKorisnickoIme() + "' && sp.idSifPozicija=z.idSifPozicija");
            while (rs.next()) {
                if (zaposlenje.getNazivKompanije().equals(rs.getString("nazivKompanije")) && zaposlenje.getNazivPozicije().equals(rs.getString("nazivPozicije"))) {
                    poruka = "Ovo zaposlenje vec postoji";
                    FacesContext.getCurrentInstance().addMessage("porukaDodavanjeZaposlenja", message);
                    return;
                }
            }
            listaZaposlenjaStudenta.add(zaposlenje);

            ResultSet rs1 = stm.executeQuery("select idSifMesta, idSifPozicija, idSifKompanija from sifMesta sm, sifPozicija sp, sifKompanija sk where sm.nazivMesta='" + zaposlenje.getNazivMesta() + "' && sp.nazivPozicije='" + zaposlenje.getNazivPozicije() + "' && sk.nazivKompanije='" + zaposlenje.getNazivKompanije() + "'");
            rs1.next();

            int idSifMesta = rs1.getInt("idSifMesta");
            int idSifPozicija = rs1.getInt("idSifPozicija");
            int idSifKompanija = rs1.getInt("idSifKompanija");

            stm.executeUpdate("insert into zaposlenje values (" + idSifKompanija + ", " + idSifMesta + ", " + idSifPozicija + ", '" + zaposlenje.getPocetak() + "', '" + zaposlenje.getKraj() + "')");

            ResultSet rs2 = stm.executeQuery("select idZaposlenje from zaposlenje, korisnik where idSifKompanija=" + idSifKompanija + " && idSifMesta=" + idSifMesta + " && idSifPozicija=" + idSifPozicija);
            
            int idZaposlenje = rs2.getInt("idZaposlenje");
            
            ResultSet rs3 = stm.executeQuery("select idKorisnik from korisnik where korisnickoIme='" + korisnik.getKorisnickoIme() + "'");
            
            int idKorisnik = rs3.getInt("idKorisnik");
            
            stm.executeUpdate("insert into radi values (" + idZaposlenje + ", " + idKorisnik + ")");
            
        } catch (SQLException ex) {
            Logger.getLogger(AzurirajPodatke.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String azurirajKorisnika() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            if (korisnik.getUloga().equals("student")) {
                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd");
                String datumRodjenja = korisnik.getDatumRodjenja().format(datePattern);

                String update = "update student s, korisnik k set ime='" + korisnik.getIme() + "', prezime='" + korisnik.getPrezime() + "', lozinka='" + korisnik.getLozinka() + "', mail='" + korisnik.getMail() + "', srednjeIme='" + korisnik.getSrednjeIme() + "', datumRodjenja='" + datumRodjenja + "', pol='" + korisnik.getPol() + "', mailVidljivost='" + korisnik.getMailVidljivost() + "', telefon='" + korisnik.getTelefon() + "', s.idSifUlica=(select idSifUlica from sifUlica where nazivUlice='" + korisnik.getNazivUlice() + "'), brojPrebivalista=" + korisnik.getBrojPrebivalista() + ", s.idSifDrzavljanstva=(select idSifDrzavljanstva from sifDrzavljanstva where nazivDrzavljanstva='" + korisnik.getNazivDrzave() + "'), s.idSifOblastInteresovanja=(select idSifOblastInteresovanja from sifOblastInteresovanja where nazivInteresovanja='" + korisnik.getNazivInteresovanja() + "'), s.idSifProfesionalneVestine=(select idSifProfesionalneVestine from sifProfesionalneVestine where nazivVestine='" + korisnik.getNazivVestine() + "'), javnoIme='" + korisnik.getJavnoIme() + "', imeVidljivost='" + korisnik.getImeVidljivost() + "', prezimeVidljivost='" + korisnik.getPrezimeVidljivost() + "', mailVidljivost='" + korisnik.getMailVidljivost() + "', srednjeImeVidljivost='" + korisnik.getSrednjeImeVidljivost() + "', datRodjenjaVidljivost='" + korisnik.getDatumRodjenjaVidljivost() + "', polVidljivost='" + korisnik.getPolVidljivost() + "', telefonVidljivost='" + korisnik.getTelefonVidljivost() + "', idSifUlicaVidljivost='" + korisnik.getIdSifUlicaVidljivost() + "', brojPrebivalistaVidljivost='" + korisnik.getBrojPrebivalistaVidljivost() + "', idSifDrzavaVidljivost='" + korisnik.getIdSifDrzavaVidljivost() + "', idSifMestaVidljivost='" + korisnik.getIdSifMestaVidljivost() + "', idSifDrzavljanstvoVidljivost='" + korisnik.getIdSifDrzavljanstvoVidljivost() + "', idSifOblastInteresovanjaVidljivost='" + korisnik.getIdSifOblastInteresovanjaVidljivost() + "', idSifProfesionalneVestineVidljivost='" + korisnik.getIdSifProfesionalneVestineVidljivost() + "', slikaVidljivost='" + korisnik.getSlikaVidljivost() + "', CVVidljivost='" + korisnik.getCVVidljivost() + "' where k.korisnickoIme='" + korisnik.getKorisnickoIme() + "' && k.idKorisnik=s.idKorisnik";
                stm.executeUpdate(update);

                uploadSlikuStudenta();
                uploadCVStudenta();

                poruka = "Uspesno azuriranje";
                return "korisnickiNalog";
            } else if (korisnik.getUloga().equals("kompanija")) {
                String update = "update kompanija kom, korisnik k set lozinka='" + korisnik.getLozinka() + "', mail='" + korisnik.getMail() + "', kom.idSIfKompanija=(select idSifKompanija from sifKompanija where nazivKompanije='" + korisnik.getNazivKompanije() + "'), telefon='" + korisnik.getTelefon() + "', idSifUlica=(select idSifUlica from sifUlica where nazivUlice='" + korisnik.getNazivUlice() + "'), brojSedista=" + korisnik.getBrojSedista() + ", idSifOblastPoslovanja=(select idSifOblastPoslovanja from sifOblastPoslovanja where nazivOblastPoslovanja'" + korisnik.getOblastPoslovanja() + "'), brojZaposlenih=" + korisnik.getBrojZaposlenih() + ", tekstOKomp='" + korisnik.getTekstOKomp() + "', webAdresa='" + korisnik.getWeb() + "', javnoIme='" + korisnik.getJavnoIme() + "', PIB=" + korisnik.getPIB() + ", mailVidljivost='" + korisnik.getMailVidljivost() + "' where k.korisnickoIme='" + korisnik.getKorisnickoIme() + "' && k.idKorisnik=kom.idKorisnik";
                stm.executeUpdate(update);

                uploadLogoKompanije();

                poruka = "Uspesno azuriranje";
                return "korisnickiNalog";
            } else if (korisnik.getUloga().equals("administrator")) {
                String update = "update administrator a, korisnik k set ime='" + korisnik.getIme() + "', prezime='" + korisnik.getPrezime() + "', lozinka='" + korisnik.getLozinka() + "', mail='" + korisnik.getMail() + "', mailVidljivost='" + korisnik.getMailVidljivost() + "' where k.korisnickoIme='" + korisnik.getKorisnickoIme() + "' && k.idKorisnik=a.idKorisnik";
                stm.executeUpdate(update);

                poruka = "Uspesno azuriranje";
                return "korisnickiNalog";
            }

        } catch (SQLException ex) {
            Logger.getLogger(AzurirajPodatke.class.getName()).log(Level.SEVERE, null, ex);

            poruka = "Greska pri upisu u bazu";
            return null;
        }

        return null;
    }

    public void podaciOKorisniku() {

        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            if (korisnik.getUloga().equals(("administrator"))) {

                ResultSet rs = stm.executeQuery("select ime, prezime, lozinka, mail, mailVidljivost from administrator a, korisnik k where k.korisnickoIme='" + korisnik.getKorisnickoIme() + "' && k.idKorisnik=a.idKorisnik");
                rs.next();

                korisnik.setIme(rs.getString("ime"));
                korisnik.setPrezime(rs.getString("prezime"));
                korisnik.setLozinka(rs.getString("lozinka"));
                korisnik.setMail(rs.getString("mail"));
                korisnik.setMailVidljivost(rs.getString("mailVidljivost"));

                sesija.setAttribute("korisnik", korisnik);
//                String path = "/faces/korisnickiNalog2.xhtml";
//                request.getRequestDispatcher(path).forward(request, response);

            } else if (korisnik.getUloga().equals(("student"))) {

                ResultSet rs = stm.executeQuery("select ime, prezime, lozinka, status, mail, srednjeIme, datumRodjenja, pol, telefon, nazivUlice, brojPrebivalista, nazivDrzave, nazivMesta, nazivDrzavljanstva, sifOblastInt.nazivInteresovanja, sifProfV.nazivVestine, javnoIme, imeVidljivost, prezimeVidljivost, mailVidljivost, srednjeImeVidljivost, datRodjenjaVidljivost, polVidljivost, telefonVidljivost, idSifUlicaVidljivost, brojPrebivalistaVidljivost, idSifDrzavaVidljivost, idSifMestaVidljivost, idSifDrzavljanstvoVidljivost, idSifOblastInteresovanjaVidljivost, idSifProfesionalneVestineVidljivost, slikaVidljivost, CVVidljivost, mailVidljivost from sifProfesionalneVestine sifProfV, sifOblastInteresovanja sifOblastInt, sifDrzavljanstva sifDrzavlj, sifDrzava sifDrzava, sifMesta sifM, sifUlica sifU, student s, korisnik k, vestine ves, interesovanja inter where k.korisnickoIme='" + korisnik.getKorisnickoIme() + "' && k.idKorisnik=s.idKorisnik && s.idSifUlica=sifU.idSifUlica && sifM.idSifMesta=sifU.idSifMesta && sifM.idSifDrzava=sifDrzava.idSifDrzava && s.idSifDrzavljanstva=sifDrzavlj.idSifDrzavljanstva && s.idKorisnik=inter.idKorisnik && inter.idSifOblastInteresovanja=sifOblastInt.idSifOblastInteresovanja && s.idSifOblastInteresovanja=sifOblastInt.idSifOblastInteresovanja && s.idKorisnik=ves.idKorisnik && ves.idSifProfesionalneVestine=sifProfV.idSifProfesionalneVestine");
                rs.next();

                korisnik.setIme(rs.getString("ime"));
                korisnik.setPrezime(rs.getString("prezime"));
                korisnik.setLozinka(rs.getString("lozinka"));
                korisnik.setMail(rs.getString("mail"));
                korisnik.setSrednjeIme(rs.getString("srednjeIme"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd");
                String datumRodjenja = rs.getDate("datumRodjenja").toString();
                LocalDate datumRodjenja1 = LocalDate.parse(datumRodjenja, datePattern);
                korisnik.setDatumRodjenja(datumRodjenja1);

                korisnik.setStatus(rs.getString("status"));
                korisnik.setPol(rs.getString("pol"));
                korisnik.setTelefon(rs.getString("telefon"));
                korisnik.setNazivUlice(rs.getString("nazivUlice"));
                korisnik.setBrojPrebivalista(rs.getInt("brojPrebivalista"));
                korisnik.setNazivDrzave(rs.getString("nazivDrzave"));
                korisnik.setNazivMesta(rs.getString("nazivMesta"));
                korisnik.setNazivDrzavljanstva(rs.getString("nazivDrzavljanstva"));
                korisnik.setNazivInteresovanja(rs.getString("nazivInteresovanja"));
                korisnik.setNazivVestine(rs.getString("nazivVestine"));
                korisnik.setJavnoIme(rs.getString("javnoIme"));

                korisnik.setImeVidljivost(rs.getString("imeVidljivost"));
                korisnik.setPrezimeVidljivost(rs.getString("prezimeVidljivost"));
                korisnik.setMailVidljivost(rs.getString("mailVidljivost"));
                korisnik.setSrednjeImeVidljivost(rs.getString("srednjeImeVidljivost"));
                korisnik.setDatumRodjenjaVidljivost(rs.getString("datRodjenjaVidljivost"));
                korisnik.setPolVidljivost(rs.getString("polVidljivost"));
                korisnik.setTelefonVidljivost(rs.getString("telefonVidljivost"));
                korisnik.setIdSifUlicaVidljivost(rs.getString("idSifUlicaVidljivost"));
                korisnik.setBrojPrebivalistaVidljivost(rs.getString("brojPrebivalistaVidljivost"));
                korisnik.setIdSifDrzavaVidljivost(rs.getString("idSifDrzavaVidljivost"));
                korisnik.setIdSifMestaVidljivost(rs.getString("idSifMestaVidljivost"));
                korisnik.setIdSifDrzavljanstvoVidljivost(rs.getString("idSifDrzavljanstvoVidljivost"));
                korisnik.setIdSifOblastInteresovanjaVidljivost(rs.getString("idSifOblastInteresovanjaVidljivost"));
                korisnik.setIdSifProfesionalneVestineVidljivost(rs.getString("idSifProfesionalneVestineVidljivost"));
                korisnik.setJavnoImeVidljivost(rs.getString("javnoImeVidljivost"));
                korisnik.setStatusVidljivost(rs.getString("statusVidljivost"));
                korisnik.setSlikaVidljivost(rs.getString("slikaVidljivost"));
                korisnik.setCVVidljivost(rs.getString("CVVidljivost"));
                korisnik.setMailVidljivost(rs.getString("mailVidljivost"));

                sesija.setAttribute("korisnik", korisnik);

            } else if (korisnik.getUloga().equals(("kompanija"))) {

                ResultSet rs = stm.executeQuery("select lozinka, mail, nazivKompanije, telefon, nazivUlice, brojSedista, nazivDrzave, nazivMesta, nazivOblastPoslovanja, brojZaposlenih, tekstOKomp, webAdresa, javnoIme from sifOblastPoslovanja sifObP, sifKompanija sifKom, sifDrzava sifDrzava, sifMesta sifM, sifUlica sifU, kompanija kom, korisnik k where sifObP.sifOblastPoslovanja=kom.sifOblastPoslovanja && k.korisnickoIme='" + korisnik.getKorisnickoIme() + "' && k.idKorisnik=kom.idKorisnik && kom.idSifKompanija=sifKom.idSifKompanija && kom.idSifUlica=sifU.idSifUlica && sifM.idSifMesta=sifU.idSifMesta && sifM.idSifDrzava=sifDrzava.idSifDrzava");
                rs.next();

                korisnik.setLozinka(rs.getString("lozinka"));
                korisnik.setMail(rs.getString("mail"));
                korisnik.setTelefon(rs.getString("telefon"));
                korisnik.setNazivUlice(rs.getString("nazivUlice"));
                korisnik.setNazivDrzave(rs.getString("nazivDrzave"));
                korisnik.setNazivMesta(rs.getString("nazivMesta"));
                korisnik.setJavnoIme(rs.getString("javnoIme"));
                korisnik.setNazivKompanije(rs.getString("nazivKompanije"));
                korisnik.setOblastPoslovanja(rs.getString("oblastPoslovanja"));
                korisnik.setBrojSedista(rs.getInt("brojSedista"));
                korisnik.setBrojZaposlenih(rs.getInt("brojZaposlenih"));
                korisnik.setTekstOKomp(rs.getString("tekstOKomp"));
                korisnik.setWeb(rs.getString("web"));
                korisnik.setMailVidljivost(rs.getString("mailVidljivost"));

                sesija.setAttribute("korisnik", korisnik);
            }

        } catch (Exception ex) {
            Logger.getLogger(AzurirajPodatke.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
