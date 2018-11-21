package controllers;

import beans.Diskusija;
import beans.Preporuka;
import beans.Korisnik;
import beans.Obavestenje;
import beans.Oglas;
import beans.Vest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class Brisanje {

    private String poruka;
    private List<Vest> listaVestiZaBrisanje;
    private List<Diskusija> listaDiskusijeZaBrisanje;
    private List<Obavestenje> listaObavestenjaZaBrisanje;
    private List<Oglas> listaOglasaZaBrisanje;
    private List<Preporuka> listaPreporukaZaBrisanje;
    private int brojVestiZaBrisanje, brojDiskusijaZaBrisanje, brojObavestenjaZaBrisanje, brojOglasaZaBrisanje, brojPreporukaZaBrisanje;

    public int getBrojVestiZaBrisanje() {
        return brojVestiZaBrisanje;
    }

    public void setBrojVestiZaBrisanje(int brojVestiZaBrisanje) {
        this.brojVestiZaBrisanje = brojVestiZaBrisanje;
    }

    public int getBrojDiskusijaZaBrisanje() {
        return brojDiskusijaZaBrisanje;
    }

    public void setBrojDiskusijaZaBrisanje(int brojDiskusijaZaBrisanje) {
        this.brojDiskusijaZaBrisanje = brojDiskusijaZaBrisanje;
    }

    public int getBrojObavestenjaZaBrisanje() {
        return brojObavestenjaZaBrisanje;
    }

    public void setBrojObavestenjaZaBrisanje(int brojObavestenjaZaBrisanje) {
        this.brojObavestenjaZaBrisanje = brojObavestenjaZaBrisanje;
    }

    public int getBrojOglasaZaBrisanje() {
        return brojOglasaZaBrisanje;
    }

    public void setBrojOglasaZaBrisanje(int brojOglasaZaBrisanje) {
        this.brojOglasaZaBrisanje = brojOglasaZaBrisanje;
    }

    public int getBrojPreporukaZaBrisanje() {
        return brojPreporukaZaBrisanje;
    }

    public void setBrojPreporukaZaBrisanje(int brojPreporukaZaBrisanje) {
        this.brojPreporukaZaBrisanje = brojPreporukaZaBrisanje;
    }

    public List<Vest> getListaVestiZaBrisanje() {
        return listaVestiZaBrisanje;
    }

    public void setListaVestiZaBrisanje(List<Vest> listaVestiZaBrisanje) {
        this.listaVestiZaBrisanje = listaVestiZaBrisanje;
    }

    public List<Diskusija> getListaDiskusijeZaBrisanje() {
        return listaDiskusijeZaBrisanje;
    }

    public void setListaDiskusijeZaBrisanje(List<Diskusija> listaDiskusijeZaBrisanje) {
        this.listaDiskusijeZaBrisanje = listaDiskusijeZaBrisanje;
    }

    public List<Obavestenje> getListaObavestenjaZaBrisanje() {
        return listaObavestenjaZaBrisanje;
    }

    public void setListaObavestenjaZaBrisanje(List<Obavestenje> listaObavestenjaZaBrisanje) {
        this.listaObavestenjaZaBrisanje = listaObavestenjaZaBrisanje;
    }

    public List<Oglas> getListaOglasaZaBrisanje() {
        return listaOglasaZaBrisanje;
    }

    public void setListaOglasaZaBrisanje(List<Oglas> listaOglasaZaBrisanje) {
        this.listaOglasaZaBrisanje = listaOglasaZaBrisanje;
    }

    public List<Preporuka> getListaPreporukaZaBrisanje() {
        return listaPreporukaZaBrisanje;
    }

    public void setListaPreporukaZaBrisanje(List<Preporuka> listaPreporukaZaBrisanje) {
        this.listaPreporukaZaBrisanje = listaPreporukaZaBrisanje;
    }

    public void ucitajVestiZaBrisanje() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            listaVestiZaBrisanje = new ArrayList<>();

            ResultSet VestiZaBrisanje = stm.executeQuery("select idVesti, tekstVesti, datumPostavljanjaVesti, autorVesti, nivoVidljivosti, naslovVesti, arhiviranaVest, nazivKategorije, metaVesti from Vesti v, vidljivost vv, kategorije k where v.zahtevZaBrisanjeVesti='true' && vv.idVidljivost=v.idVidljivost && v.idKategorije=k.idKategorije");
            while (VestiZaBrisanje.next()) {
                Vest v = new Vest();
                v.setIdVesti(VestiZaBrisanje.getInt("idVesti"));
                v.setNaslovVesti(VestiZaBrisanje.getString("naslovVesti"));
                v.setTekstVesti(VestiZaBrisanje.getString("tekstVesti"));
                v.setAutorVesti(VestiZaBrisanje.getString("autorVesti"));
                v.setArhiviranaVest(VestiZaBrisanje.getString("arhiviraVest"));
                v.setNivoVidljivosti(VestiZaBrisanje.getString("nivoVidljivosti"));
                v.setNazivKategorije(VestiZaBrisanje.getString("nazivKategorije"));
                v.setMetaVesti(VestiZaBrisanje.getString("metaVesti"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumPostavljanjaVesti = LocalDateTime.parse(VestiZaBrisanje.getTimestamp("datumPostavljanjaVesti").toString(), datePattern);

                v.setDatumPostavljanjaVesti(datumPostavljanjaVesti);

                brojVestiZaBrisanje++;
                listaVestiZaBrisanje.add(v);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Brisanje.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajDiskusijeZaBrisanje() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            listaDiskusijeZaBrisanje = new ArrayList<>();

            ResultSet DiskusijeZaBrisanje = stm.executeQuery("select idDiskusija, tekstDiskusija, datumPostavljanja, autorDiskusije, nivoVidljivosti, nazivKategorije, nazivDiskusije, arhiviranaDiskusija, metaDiskusija, idUcestvuje_kreira from diskusija d, vidljivost v, kategorije k where zahtevZaBrisanjeDiskusije='true' && d.idVidljivost=v.idVidljivost && d.idKategorije=k.idKategorije");

            while (DiskusijeZaBrisanje.next()) {
                Diskusija d = new Diskusija();
                d.setIdDiskusija(DiskusijeZaBrisanje.getInt("idDiskusija"));
                d.setNazivDiskusije(DiskusijeZaBrisanje.getString("nazivDiskusije"));
                d.setTekstDiskusije(DiskusijeZaBrisanje.getString("tekstDiskusija"));
                d.setAutorDiskusije(DiskusijeZaBrisanje.getString("autorDiskusije"));
                d.setArhiviranaDiskusija(DiskusijeZaBrisanje.getString("arhiviranaDiskusija"));
                d.setNivoVidljivosti(DiskusijeZaBrisanje.getString("nivoVidljivosti"));
                d.setKategorijaDiskusije(DiskusijeZaBrisanje.getString("nazivKategorije"));
                d.setMetaDiskusija(DiskusijeZaBrisanje.getString("metaDiskusija"));
                d.setIdUcestvujeKreira(DiskusijeZaBrisanje.getInt("idUcestvuje_kreira"));
                
                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumPostavljanja = LocalDateTime.parse(DiskusijeZaBrisanje.getTimestamp("datumPostavljanja").toString(), datePattern);

                d.setDatumPostavljanjaDiskusije(datumPostavljanja);

                brojDiskusijaZaBrisanje++;
                listaDiskusijeZaBrisanje.add(d);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Brisanje.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    public void ucitajObavestenjaZaBrisanje() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            listaObavestenjaZaBrisanje = new ArrayList<>();

            ResultSet rs = stm.executeQuery("select idObavestenja, tekstObavestenja, datumKreiranjaObavestenja, autorObavestenja, nivoVidljivosti, naslovObavestenja, arhiviroObavestenje, metaObavestenja, idUcestvuje_kreira from obavestenja o, vidljivost v where zahtevZaBrisanjeObavestenja='true' && o.idVidljivost=v.idVidljivost");

            while (rs.next()) {
                Obavestenje o = new Obavestenje();
                o.setIdObavestenja(rs.getInt("idObavestenja"));
                o.setNaslovObavestenja(rs.getString("naslovObavestenja"));
                o.setTekstOvavestenja(rs.getString("tekstObavestenja"));
                o.setAutorObavestenja(rs.getString("autorObavestenja"));
                o.setArhiviranoObavestenje(rs.getString("arhiviroObavestenje"));
                o.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                o.setIdUcestvujeKreira(rs.getInt("idUcestvuje_kreira"));
                o.setMetaObavestenja(rs.getString("metaObavestenja"));
                
                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumKreiranjaObavestenja = LocalDateTime.parse(rs.getTimestamp("datumKreiranjaObavestenja").toString(), datePattern);

                o.setDatumKreiranjaObavestenja(datumKreiranjaObavestenja);
                
                brojObavestenjaZaBrisanje++;
                listaObavestenjaZaBrisanje.add(o);
            }
            }catch (SQLException ex) {
            Logger.getLogger(Brisanje.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void ucitajOglaseZaBrisanje(){
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            listaOglasaZaBrisanje = new ArrayList<>();
            
            ResultSet rs = stm.executeQuery("select idOglasi, tekstOglasa, datumPostavljanja, datumIsticanja, autorOglasa, nivoVidljivosti, naslovOglasa, arhiviriOglasi, metaOglasi, idUcestvuje_kreira from Oglasi o, vidljivost v where zahtevZaBrisanjeOglasa='true' && o.idVidljivost=v.idVidljivost");
            
            while(rs.next()){
                Oglas o = new Oglas();
                o.setIdOglasi(rs.getInt("idOglasi"));
                o.setIdUcestvujeKreira(rs.getInt("idUcestvuje_kreira"));
                o.setNaslovOglasa(rs.getString("naslovOglasa"));
                o.setTekstOglasa(rs.getString("tekstOglasa"));
                o.setAutorOglasa(rs.getString("autorOglasa"));
                o.setArhiviraniOglasi(rs.getString("arhiviriOglasi"));
                o.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                o.setMetaOglasi(rs.getString("metaOglasi"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumPostavljanja").toString(), datePattern);
                LocalDateTime datumIsticanja = LocalDateTime.parse(rs.getTimestamp("datumIsticanja").toString(), datePattern);
                
                o.setDatumPostavljanja(datumPostavljanja);
                o.setDatumIsticanja(datumIsticanja);
                
                brojOglasaZaBrisanje++;
                listaOglasaZaBrisanje.add(o);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Brisanje.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ucitajPreporukeZaBrisanje(){
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            listaPreporukaZaBrisanje = new ArrayList<>();
            
            ResultSet rs = stm.executeQuery("select idPreporuke, tekstPreporuke, datumKreiranjaPreporuke, autorPreporuke, nivoVidljivosti, nazivPreporuke, metaPreporuke, idUcestvuje_kreira from Preporuka p, vidljivost v where zahtevZaBrisanjePreporuke='true' && p.idVidljivost=v.idVidljivost");
            
            while(rs.next()){
                Preporuka p = new Preporuka();
                p.setIdPreporuke(rs.getInt("idPreporuke"));
                p.setNazivPreporuke(rs.getString("nazivPreporuke"));
                p.setTekstPreporuke(rs.getString("tekstPreporuke"));
                p.setAutorPreporuke(rs.getString("autorPreporuke"));
                p.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                p.setIdUcestvujeKreira(rs.getInt("idUcestvuje_kreira"));
                p.setMetaPreporuke(rs.getString("metaPreporuke"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumKreiranjaPreporuke = LocalDateTime.parse(rs.getTimestamp("datumKreiranjaPreporuke").toString(), datePattern);

                p.setDatumKreiranjaPreporuke(datumKreiranjaPreporuke);
                
                brojPreporukaZaBrisanje++;
                listaPreporukaZaBrisanje.add(p);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Brisanje.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void obrisiVest() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);

            int idVesti = Integer.parseInt(params.get("idVesti"));

            if (korisnik.getUloga().equals("administrator")) {

                stm.executeUpdate("delete from ucestvuje_kreira where idUcestvujeKreira=(select idUcestvuje_kreira from vesti where idVesti=" + idVesti + ")");

                poruka = "Vest je obrisana";
            } else if (korisnik.getUloga().equals("student") || korisnik.getUloga().equals("kompanija")) {

                stm.executeUpdate("update vesti set zahtevZaBrisanje='true' where idVesti=" + idVesti);

                poruka = "Zahtev za brisanje vesti je prosledjen";
            }

            FacesContext.getCurrentInstance().addMessage("porukaBrisanjeVesti", message);

        } catch (SQLException ex) {
            Logger.getLogger(Arhiviranje.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void obrisiDiskusiju() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);

            int idDiskusija = Integer.parseInt(params.get("idDiskusije"));

            if (korisnik.getUloga().equals("administrator")) {

                stm.executeUpdate("delete from ucestvuje_kreira where idUcestvuje_kreira=(select idUcestvuje_kreira from diskusija where idDiskusija=" + idDiskusija + ")");

                poruka = "Diskusija je obrisana";

            } else if (korisnik.getUloga().equals("student") || korisnik.getUloga().equals("kompanija")) {
                ResultSet rs = stm.executeQuery("select * from komentari where idDiskusija=" + idDiskusija);
                if (rs.next()) {
                    stm.executeUpdate("update Diskusija set zahtevZaBrisanjeDiskusije='true' where idDiskusija=" + idDiskusija);

                    poruka = "Ne mozete obrisati diskusiju koja je prokomentarisana. Prosledjen je zahtev za brisanje";
                } else {
                    stm.executeUpdate("delete from ucestvuje_kreira where idUcestvuje_kreira=(select idUcestvuje_kreira from diskusija where idDiskusija=" + idDiskusija + ")");

                    poruka = "Diskusija je obrisana";
                }
            }
            FacesContext.getCurrentInstance().addMessage("porukaBrisanjeDiskusije", message);

        } catch (SQLException ex) {
            Logger.getLogger(Arhiviranje.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void obrisiPreporuku() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);

            int idPreporuke = Integer.parseInt(params.get("idPreporuke"));

            if (korisnik.getUloga().equals("administrator")) {

                stm.executeUpdate("delete from ucestvuje_kreira where idUcestvuje_kreira=(select idUcestvuje_kreira from preporuke where idPreporuke=" + idPreporuke + ")");

                poruka = "Preporuka je obrisana";

            } else if (korisnik.getUloga().equals("kompanija")) {
                ResultSet rs = stm.executeQuery("select idPreporuke from ocenaPreporuke where idPreporuke=" + idPreporuke);
                if (rs.next()) {
                    stm.executeUpdate("update Preporuke set zahtevZaBrisanjePreporuke='true' where idDiskusija=" + idPreporuke);

                    poruka = "Preporuka je vec ocenjena. Zahtev za brisanje preporuke je prosledjen";
                } else {
                    stm.executeUpdate("delete from ucestvuje_kreira where idUcestvuje_kreira=(select idUcestvuje_kreira from preporuke where idPreporuke=" + idPreporuke + ")");

                    poruka = "Preporuka je obrisana";
                }
            }
            FacesContext.getCurrentInstance().addMessage("porukaBrisanjeDiskusije", message);

        } catch (SQLException ex) {
            Logger.getLogger(Arhiviranje.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void obrisiOglas() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);

            int idOglasi = Integer.parseInt(params.get("idOglasi"));

            if (korisnik.getUloga().equals("administrator")) {

                stm.executeUpdate("delete from ucestvuje_kreira where idUcestvuje_kreira=(select idUcestvuje_kreira from Oglasi where idOglasi=" + idOglasi + ")");

                poruka = "Oglas je obrisan";

            } else if (korisnik.getUloga().equals("kompanija")) {
                stm.executeUpdate("update Oglasi set zahtevZaBrisanjeOglasa='true' where idOglasi=" + idOglasi);

                poruka = "Zahtev za brisanje oglasa je prosledjen";
            }

            FacesContext.getCurrentInstance().addMessage("porukaBrisanjeDiskusije", message);

        } catch (SQLException ex) {
            Logger.getLogger(Arhiviranje.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
