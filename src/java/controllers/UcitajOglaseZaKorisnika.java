package controllers;

import beans.Korisnik;
import beans.Oglas;
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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class UcitajOglaseZaKorisnika {

    private List<Oglas> listaSvihOglasaKojeJeKorisnikNapisao, listaOglasaIzGrupe, listaSvihOglasaZaKorisnika, listaPoslednjihPetOglasaZaKorisnika;

    public List<Oglas> getListaOglasaIzGrupe() {
        return listaOglasaIzGrupe;
    }

    public void setListaOglasaIzGrupe(List<Oglas> listaOglasaIzGrupe) {
        this.listaOglasaIzGrupe = listaOglasaIzGrupe;
    }

    public List<Oglas> getListaSvihOglasaZaKorisnika() {
        return listaSvihOglasaZaKorisnika;
    }

    public void setListaSvihOglasaZaKorisnika(List<Oglas> listaSvihOglasaZaKorisnika) {
        this.listaSvihOglasaZaKorisnika = listaSvihOglasaZaKorisnika;
    }

    public List<Oglas> getListaPoslednjihPetOglasaZaKorisnika() {
        return listaPoslednjihPetOglasaZaKorisnika;
    }

    public void setListaPoslednjihPetOglasaZaKorisnika(List<Oglas> listaPoslednjihPetOglasaZaKorisnika) {
        this.listaPoslednjihPetOglasaZaKorisnika = listaPoslednjihPetOglasaZaKorisnika;
    }

    public void ucitajSveOglaseKojeJeKorisnikNapisao() {
        
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            
            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");
            
            ResultSet rs = stm.executeQuery("select naslovOglasa, tekstOglasa, autorOglasa from oglasi o, ucestvuje_kreira uk, korisnik k where o.idOglasa=uk.idTipObavestenja && uk.tipObavestenja='oglas' && uk.idKorisnik=k.idKorisnik && uk.tipUcesnika='kreira' && k.korisnickoIme='" + korisnik.getKorisnickoIme() + "'");
            
            listaSvihOglasaKojeJeKorisnikNapisao = new ArrayList<>();
            
            while (rs.next()) {
                Oglas o = new Oglas();
                o.setNaslovOglasa(rs.getString("naslovOglasa"));
                o.setTekstOglasa(rs.getString("tekstOglasa"));
                o.setAutorOglasa(rs.getString("autorOglasa"));
                                                
                listaSvihOglasaKojeJeKorisnikNapisao.add(o);
            }
        }catch (SQLException ex) {
            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void ucitajOglaseIzGrupe() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            
            int idGrupe = Integer.parseInt(params.get("idGrupe"));
            
            ResultSet rs = stm.executeQuery("SELECT o.idOglasa, naslovOglasa, tekstOglasa, autorOglasa, datumPostavljanja, datumIsticanja, nivoVidljivosti, arhiviraniOglasi, metaOglasi FROM Oglasi o, Vidljivost v, Grupa g, uGrupi ug, Ucestvuje_kreira uk WHERE o.idVidljivost=v.idVidljivost && v.nivoVidljivosti='Formirana grupa studenata' && g.idGrupa=" + idGrupe + " && g.idGrupa=ug.idGrupa && ug.idKorisnik=uk.idKorisnik && uk.idUcestvuje_kreira=o.idUcestvuje_kreira");            
            listaOglasaIzGrupe = new ArrayList<>();

            while (rs.next()) {
                Oglas o = new Oglas();
                o.setNaslovOglasa(rs.getString("naslovOglasa"));
                o.setTekstOglasa(rs.getString("tekstOglasa"));
                o.setAutorOglasa(rs.getString("autorOglasa"));
                o.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                o.setArhiviraniOglasi(rs.getString("arhiviraniOglasi"));
                o.setMetaOglasi(rs.getString("metaOglasi"));
                
                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumPostavljanja").toString(), datePattern);
                LocalDateTime datumIsticanja = LocalDateTime.parse(rs.getTimestamp("datumIsticanja").toString(), datePattern);
                o.setDatumPostavljanjaOglasa(rs.getTimestamp("datumPostavljanja").toString().substring(0, rs.getTimestamp("datumPostavljanja").toString().length()-5));
                o.setDatumIsticanjaOglasa(rs.getTimestamp("datumIsticanja").toString().substring(0, rs.getTimestamp("datumIsticanja").toString().length()-5));
                o.setDatumPostavljanja(datumPostavljanja);
                o.setDatumIsticanja(datumIsticanja);

                listaOglasaIzGrupe.add(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajSveOglaseZaKorisnika() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            ResultSet rs = stm.executeQuery("select o.idOglasa, naslovOglasa, tekstOglasa, autorOglasa, datumPostavljanja, datumIsticanja, nivoVidljivosti, arhiviraniOglasi, metaOglasi from ucestvuje_kreira uk, oglasi o, vidljivost v, korisnik k where k.idKorisnik=uk.idKorisnik && k.korisnickoIme='" + korisnik.getKorisnickoIme() + "' && o.idOglasa=uk.idTipObavestenja && uk.tipObavestenja='oglas' && o.idVidljivost=v.idVidljivost order by o.datumPostavljanja desc");
            listaSvihOglasaZaKorisnika = new ArrayList<>();

            while (rs.next()) {
                Oglas o = new Oglas();
                o.setNaslovOglasa(rs.getString("naslovOglasa"));
                o.setTekstOglasa(rs.getString("tekstOglasa"));
                o.setAutorOglasa(rs.getString("autorOglasa"));
                o.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                o.setArhiviraniOglasi(rs.getString("arhiviraniOglasi"));
                o.setMetaOglasi(rs.getString("metaOglasi"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumPostavljanja").toString(), datePattern);
                LocalDateTime datumIsticanja = LocalDateTime.parse(rs.getTimestamp("datumIsticanja").toString(), datePattern);
                o.setDatumPostavljanjaOglasa(rs.getTimestamp("datumPostavljanja").toString().substring(0, rs.getTimestamp("datumPostavljanja").toString().length()-5));
                o.setDatumIsticanjaOglasa(rs.getTimestamp("datumIsticanja").toString().substring(0, rs.getTimestamp("datumIsticanja").toString().length()-5));
                o.setDatumPostavljanja(datumPostavljanja);
                o.setDatumIsticanja(datumIsticanja);

                listaSvihOglasaZaKorisnika.add(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajPoslednjihPetZaKorisnika() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            ResultSet rs = stm.executeQuery("select o.idOglasa, naslovOglasa, tekstOglasa, autorOglasa, datumPostavljanja, datumIsticanja, nivoVidljivosti, arhiviraniOglasi, metaOglasi from ucestvuje_kreira uk, oglasi o, vidljivost v, korisnik k where k.idKorisnik=uk.idKorisnik && k.korisnickoIme='" + korisnik.getKorisnickoIme() + "' && o.idOglasa=uk.idTipObavestenja && uk.tipObavestenja='oglas' && o.idVidljivost=v.idVidljivost order by o.datumPostavljanja desc limit 5");
            
            listaPoslednjihPetOglasaZaKorisnika = new ArrayList<>();

            while (rs.next()) {
                Oglas o = new Oglas();
                o.setNaslovOglasa(rs.getString("naslovOglasa"));
                o.setTekstOglasa(rs.getString("tekstOglasa"));
                o.setAutorOglasa(rs.getString("autorOglasa"));
                o.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                o.setArhiviraniOglasi(rs.getString("arhiviraniOglasi"));
                o.setMetaOglasi(rs.getString("metaOglasi"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumPostavljanja").toString(), datePattern);
                LocalDateTime datumIsticanja = LocalDateTime.parse(rs.getTimestamp("datumIsticanja").toString(), datePattern);
                o.setDatumPostavljanjaOglasa(rs.getTimestamp("datumPostavljanja").toString().substring(0, rs.getTimestamp("datumPostavljanja").toString().length()-5));
                o.setDatumIsticanjaOglasa(rs.getTimestamp("datumIsticanja").toString().substring(0, rs.getTimestamp("datumIsticanja").toString().length()-5));
                o.setDatumPostavljanja(datumPostavljanja);
                o.setDatumIsticanja(datumIsticanja);

                listaPoslednjihPetOglasaZaKorisnika.add(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajPoslednjihPetZaGosta() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery("select o.idOglasa, naslovOglasa, tekstOglasa, autorOglasa, datumPostavljanja, datumIsticanja, nivoVidljivosti, arhiviraniOglasi, metaOglasi from oglasi o, vidljivost vv where vv.nivoVidljivosti='Svi i gosti' && o.idVidljivost=vv.idVidljivost order by o.datumPostavljanja desc limit 5");
            listaPoslednjihPetOglasaZaKorisnika = new ArrayList<>();

            while (rs.next()) {
                Oglas o = new Oglas();
                o.setIdOglasi(rs.getInt("idOglasa"));
                o.setNaslovOglasa(rs.getString("naslovOglasa"));
                o.setTekstOglasa(rs.getString("tekstOglasa"));
                o.setAutorOglasa(rs.getString("autorOglasa"));
                o.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                o.setArhiviraniOglasi(rs.getString("arhiviraniOglasi"));
                o.setMetaOglasi(rs.getString("metaOglasi"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumPostavljanja").toString(), datePattern);
                LocalDateTime datumIsticanja = LocalDateTime.parse(rs.getTimestamp("datumIsticanja").toString(), datePattern);
                o.setDatumPostavljanjaOglasa(rs.getTimestamp("datumPostavljanja").toString().substring(0, rs.getTimestamp("datumPostavljanja").toString().length()-5));
                o.setDatumIsticanjaOglasa(rs.getTimestamp("datumIsticanja").toString().substring(0, rs.getTimestamp("datumIsticanja").toString().length()-5));
                o.setDatumPostavljanja(datumPostavljanja);
                o.setDatumIsticanja(datumIsticanja);

                listaPoslednjihPetOglasaZaKorisnika.add(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajSveOglaseZaGosta() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery("select o.idOglasa, naslovOglasa, tekstOglasa, autorOglasa, datumPostavljanja, datumIsticanja, nivoVidljivosti, arhiviraniOglasi, metaOglasi from oglasi o, vidljivost vv where vv.nivoVidljivosti='Svi i gosti' && o.idVidljivost=vv.idVidljivost order by o.datumPostavljanja desc");
            listaSvihOglasaZaKorisnika = new ArrayList<>();

            while (rs.next()) {
                Oglas o = new Oglas();
                o.setNaslovOglasa(rs.getString("naslovOglasa"));
                o.setTekstOglasa(rs.getString("tekstOglasa"));
                o.setAutorOglasa(rs.getString("autorOglasa"));
                o.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                o.setArhiviraniOglasi(rs.getString("arhiviraniOglasi"));
                o.setMetaOglasi(rs.getString("metaOglasi"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumPostavljanja").toString(), datePattern);
                LocalDateTime datumIsticanja = LocalDateTime.parse(rs.getTimestamp("datumIsticanja").toString(), datePattern);
                o.setDatumPostavljanjaOglasa(rs.getTimestamp("datumPostavljanja").toString().substring(0, rs.getTimestamp("datumPostavljanja").toString().length()-5));
                o.setDatumIsticanjaOglasa(rs.getTimestamp("datumIsticanja").toString().substring(0, rs.getTimestamp("datumIsticanja").toString().length()-5));
                o.setDatumPostavljanja(datumPostavljanja);
                o.setDatumIsticanja(datumIsticanja);

                listaSvihOglasaZaKorisnika.add(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
