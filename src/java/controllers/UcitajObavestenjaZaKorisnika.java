package controllers;

import beans.Korisnik;
import beans.Obavestenje;
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
public class UcitajObavestenjaZaKorisnika {

    private List<Obavestenje> listaSvihObavestenjaKojeJeKorisnikNapisao, listaObavestenjaIzGrupe, listaSvihObavestenjaZaKorisnika, listaPoslednjihPetObavestenjaZaKorisnika;

    public int getIdPoslednjegObavestenja(int index){
        return listaPoslednjihPetObavestenjaZaKorisnika.get(index).getIdObavestenja();
    }
    
    public String getNaslovPoslednjegObavestenja(int index){
        return listaPoslednjihPetObavestenjaZaKorisnika.get(index).getNaslovObavestenja();
    }
    
    public String getAutorPoslednjegObavestenja(int index){
        return listaPoslednjihPetObavestenjaZaKorisnika.get(index).getAutorObavestenja();
    }
    
    public String getDatumPostavljanjaPoslednjegObavestenja(int index){
        return listaPoslednjihPetObavestenjaZaKorisnika.get(index).getDatumKreiranja();
    }
    
    public String getMetaPoslednjegObavestenja(int index){
        return listaPoslednjihPetObavestenjaZaKorisnika.get(index).getMetaObavestenja();
    }
    
    public List<Obavestenje> getListaSvihObavestenjaKojeJeKorisnikNapisao() {
        return listaSvihObavestenjaKojeJeKorisnikNapisao;
    }

    public void setListaSvihObavestenjaKojeJeKorisnikNapisao(List<Obavestenje> listaSvihObavestenjaKojeJeKorisnikNapisao) {
        this.listaSvihObavestenjaKojeJeKorisnikNapisao = listaSvihObavestenjaKojeJeKorisnikNapisao;
    }

    public List<Obavestenje> getListaObavestenjaIzGrupe() {
        return listaObavestenjaIzGrupe;
    }

    public void setListaObavestenjaIzGrupe(List<Obavestenje> listaObavestenjaIzGrupe) {
        this.listaObavestenjaIzGrupe = listaObavestenjaIzGrupe;
    }

    public List<Obavestenje> getListaSvihObavestenjaZaKorisnika() {
        return listaSvihObavestenjaZaKorisnika;
    }

    public void setListaSvihObavestenjaZaKorisnika(List<Obavestenje> listaSvihObavestenjaZaKorisnika) {
        this.listaSvihObavestenjaZaKorisnika = listaSvihObavestenjaZaKorisnika;
    }

    public List<Obavestenje> getListaPoslednjihPetObavestenjaZaKorisnika() {
        return listaPoslednjihPetObavestenjaZaKorisnika;
    }

    public void setListaPoslednjihPetObavestenjaZaKorisnika(List<Obavestenje> listaPoslednjihPetObavestenjaZaKorisnika) {
        this.listaPoslednjihPetObavestenjaZaKorisnika = listaPoslednjihPetObavestenjaZaKorisnika;
    }

    public void ucitajSvaObavestenjaKojaJeKorisnikNapisao() {
        
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            
            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");
            
            ResultSet rs = stm.executeQuery("select naslovObavestenja, tekstObavestenja, autorObavestenja from obavestenja o, ucestvuje_kreira uk, korisnik k where o.idObavestenja=uk.idtipObavestenja && uk.tipObavestenja='obavestenje' && uk.idKorisnik=k.idKorisnik && uk.tipUcesnika='kreira' && k.korisnickoIme='" + korisnik.getKorisnickoIme() + "'");
            
            listaSvihObavestenjaKojeJeKorisnikNapisao = new ArrayList<>();
            
            while (rs.next()) {
                Obavestenje o = new Obavestenje();
                o.setNaslovObavestenja(rs.getString("naslovObavestenja"));
                o.setTekstOvavestenja(rs.getString("tekstObavestenja"));
                o.setAutorObavestenja(rs.getString("autorObavestenja"));
                                                
                listaSvihObavestenjaKojeJeKorisnikNapisao.add(o);
            }
        }catch (SQLException ex) {
            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void ucitajObavestenjaIzGrupe() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            
            int idGrupe = Integer.parseInt(params.get("idGrupe"));
            
            ResultSet rs = stm.executeQuery("SELECT o.idObavestenja, naslovObavestenja, tekstObavestenja, datumKreiranjaObavestenja, nivoVidljivosti, arhiviranoObavestenje, metaObavestenja FROM Obavestenja o, Vidljivost v, Grupa g, uGrupi ug, Ucestvuje_kreira uk WHERE o.idVidljivost=v.idVidljivost && v.nivoVidljivosti='Formirana grupa studenata' && g.idGrupa=" + idGrupe + " && g.idGrupa=ug.idGrupa && ug.idKorisnik=uk.idKorisnik && uk.idUcestvuje_kreira=o.idUcestvuje_kreira");
            listaObavestenjaIzGrupe = new ArrayList<>();

            while (rs.next()) {
                Obavestenje o = new Obavestenje();
                o.setIdObavestenja(rs.getInt("idObavestenja"));
                o.setNaslovObavestenja(rs.getString("naslovObavestenja"));
                o.setTekstOvavestenja(rs.getString("tekstObavestenja"));
                o.setAutorObavestenja(rs.getString("autorObavestenja"));
                o.setArhiviranoObavestenje(rs.getString("arhiviranObavestenje"));
                o.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                o.setMetaObavestenja(rs.getString("metaObavestenja"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumKreiranjaObavestenja").toString(), datePattern);
                o.setDatumKreiranja(rs.getTimestamp("datumKreiranjaObavestenja").toString().substring(0, rs.getTimestamp("datumKreiranjaObavestenja").toString().length()-5));
                o.setDatumKreiranjaObavestenja(datumPostavljanja);

                listaObavestenjaIzGrupe.add(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajSvaObavestenjaZaKorisnika() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            ResultSet rs = stm.executeQuery("select o.idObavestenja, naslovObavestenja, tekstObavestenja, datumKreiranjaObavestenja, nivoVidljivosti, arhiviranoObavestenje, metaObavestenja from ucestvuje_kreira uk, obavestenja o, korisnik k, vidljivost v where v.idVidljivost=o.idVidljivost && k.idKorisnik=uk.idKorisnik && k.korisnickoIme='" + korisnik.getKorisnickoIme() + "' && o.idObavestenja=uk.idtipObavestenja && uk.tipObavestenja='obavestenje' order by o.datumKreiranjaObavestenja desc");

            listaSvihObavestenjaZaKorisnika = new ArrayList<>();

            while (rs.next()) {
                Obavestenje o = new Obavestenje();
                o.setIdObavestenja(rs.getInt("idObavestenja"));
                o.setNaslovObavestenja(rs.getString("naslovObavestenja"));
                o.setTekstOvavestenja(rs.getString("tekstObavestenja"));
                o.setAutorObavestenja(rs.getString("autorObavestenja"));
                o.setArhiviranoObavestenje(rs.getString("arhiviranObavestenje"));
                o.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                o.setMetaObavestenja(rs.getString("metaObavestenja"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumPostavljanja").toString(), datePattern);
                o.setDatumKreiranja(rs.getTimestamp("datumKreiranjaObavestenja").toString().substring(0, rs.getTimestamp("datumKreiranjaObavestenja").toString().length()-5));
                o.setDatumKreiranjaObavestenja(datumPostavljanja);

                listaSvihObavestenjaZaKorisnika.add(o);
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

            ResultSet rs = stm.executeQuery("select o.idObavestenja, naslovObavestenja, tekstObavestenja, datumKreiranjaObavestenja, nivoVidljivosti, arhiviranoObavestenje, metaObavestenja, autorObavestenja from ucestvuje_kreira uk, obavestenja o, korisnik k, vidljivost v where v.idVidljivost=o.idVidljivost && k.idKorisnik=uk.idKorisnik && k.korisnickoIme='" + korisnik.getKorisnickoIme() + "' && o.idObavestenja=uk.idtipObavestenja && uk.tipObavestenja='obavestenje' order by o.datumKreiranjaObavestenja desc limit 5");

            listaPoslednjihPetObavestenjaZaKorisnika = new ArrayList<>();

            while (rs.next()) {
                Obavestenje o = new Obavestenje();
                o.setIdObavestenja(rs.getInt("idObavestenja"));
                o.setNaslovObavestenja(rs.getString("naslovObavestenja"));
                o.setTekstOvavestenja(rs.getString("tekstObavestenja"));
                o.setAutorObavestenja(rs.getString("autorObavestenja"));
                o.setArhiviranoObavestenje(rs.getString("arhiviranoObavestenje"));
                o.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                o.setMetaObavestenja(rs.getString("metaObavestenja"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumKreiranjaObavestenja").toString(), datePattern);
                o.setDatumKreiranja(rs.getTimestamp("datumKreiranjaObavestenja").toString().substring(0, rs.getTimestamp("datumKreiranjaObavestenja").toString().length()-5));
                o.setDatumKreiranjaObavestenja(datumPostavljanja);

                listaPoslednjihPetObavestenjaZaKorisnika.add(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajPoslednjihPetZaGosta() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery("select autorObavestenja, o.idObavestenja, naslovObavestenja, tekstObavestenja, datumKreiranjaObavestenja, nivoVidljivosti, arhiviranoObavestenje, metaObavestenja from obavestenja o, vidljivost vv where vv.nivoVidljivosti='Svi i gosti' && o.idVidljivost=vv.idVidljivost order by o.datumKreiranjaObavestenja desc limit 5");

            listaPoslednjihPetObavestenjaZaKorisnika = new ArrayList<>();

            while (rs.next()) {
                Obavestenje o = new Obavestenje();
                o.setIdObavestenja(rs.getInt("idObavestenja"));
                o.setNaslovObavestenja(rs.getString("naslovObavestenja"));
                o.setTekstOvavestenja(rs.getString("tekstObavestenja"));
                o.setAutorObavestenja(rs.getString("autorObavestenja"));
                o.setArhiviranoObavestenje(rs.getString("arhiviranoObavestenje"));
                o.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                o.setMetaObavestenja(rs.getString("metaObavestenja"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumKreiranjaObavestenja").toString(), datePattern);
                o.setDatumKreiranja(rs.getTimestamp("datumKreiranjaObavestenja").toString().substring(0, rs.getTimestamp("datumKreiranjaObavestenja").toString().length()-5));
                o.setDatumKreiranjaObavestenja(datumPostavljanja);

                listaPoslednjihPetObavestenjaZaKorisnika.add(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajSvaObavestenjaZaGosta() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery("select o.idObavestenja, naslovObavestenja, tekstObavestenja, autorObavestenja, datumKreiranjaObavestenja, nivoVidljivosti, arhiviranoObavestenje, metaObavestenja from obavestenja o, vidljivost vv where vv.nivoVidljivosti='Svi i gosti' && o.idVidljivost=vv.idVidljivost order by o.datumKreiranjaObavestenja desc");

            listaSvihObavestenjaZaKorisnika = new ArrayList<>();

            while (rs.next()) {
                Obavestenje o = new Obavestenje();
                o.setIdObavestenja(rs.getInt("idObavestenja"));
                o.setNaslovObavestenja(rs.getString("naslovObavestenja"));
                o.setTekstOvavestenja(rs.getString("tekstObavestenja"));
                o.setAutorObavestenja(rs.getString("autorObavestenja"));
                o.setArhiviranoObavestenje(rs.getString("arhiviranObavestenje"));
                o.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                o.setMetaObavestenja(rs.getString("metaObavestenja"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumPostavljanja").toString(), datePattern);
                o.setDatumKreiranja(rs.getTimestamp("datumKreiranjaObavestenja").toString().substring(0, rs.getTimestamp("datumKreiranjaObavestenja").toString().length()-5));
                o.setDatumKreiranjaObavestenja(datumPostavljanja);

                listaSvihObavestenjaZaKorisnika.add(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
