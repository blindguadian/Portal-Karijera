package controllers;

import beans.Korisnik;
import beans.Vest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@RequestScoped
public class UcitajVestiZaKorisnika {

    private List<Vest> listaVestiIzGrupe, listaSvihVestiKojeJeKorisnikNapisao, listaSvihVestiZaKorisnika, listaPoslednjihPetVestiZaKorisnika;
    private String izabranaGrupaStudenata;

    public String getIzabranaGrupaStudenata() {
        return izabranaGrupaStudenata;
    }

    public void setIzabranaGrupaStudenata(String izabranaGrupaStudenata) {
        this.izabranaGrupaStudenata = izabranaGrupaStudenata;
    }

    public int getIdPoslednjihVesti(int index) {
        return listaPoslednjihPetVestiZaKorisnika.get(index).getIdVesti();
    }

    public String getNaslovPoslednjihVesti(int index) {
        return listaPoslednjihPetVestiZaKorisnika.get(index).getNaslovVesti();
    }

    public String getAutorPoslednjihVesti(int index) {
        return listaPoslednjihPetVestiZaKorisnika.get(index).getAutorVesti();
    }

    public String getDatumPostavljanjaPoslednjihVesti(int index) {
        return listaPoslednjihPetVestiZaKorisnika.get(index).getDatumPostavljanjaVestiNaslovna();
    }

    public String getMetaPoslednjihVesti(int index) {
        return listaPoslednjihPetVestiZaKorisnika.get(index).getMetaVest();
    }

    public List<Vest> getListaSvihVestiKojeJeKorisnikNapisao() {
        return listaSvihVestiKojeJeKorisnikNapisao;
    }

    public void setListaSvihVestiKojeJeKorisnikNapisao(List<Vest> listaSvihVestiKojeJeKorisnikNapisao) {
        this.listaSvihVestiKojeJeKorisnikNapisao = listaSvihVestiKojeJeKorisnikNapisao;
    }

    public List<Vest> getListaVestiIzGrupe() {
        return listaVestiIzGrupe;
    }

    public void setListaVestiIzGrupe(List<Vest> listaVestiIzGrupe) {
        this.listaVestiIzGrupe = listaVestiIzGrupe;
    }

    public List<Vest> getListaSvihVestiZaKorisnika() {
        return listaSvihVestiZaKorisnika;
    }

    public void setListaSvihVestiZaKorisnika(List<Vest> listaSvihVestiZaKorisnika) {
        this.listaSvihVestiZaKorisnika = listaSvihVestiZaKorisnika;
    }

    public List<Vest> getListaPoslednjihPetVestiZaKorisnika() {
        return listaPoslednjihPetVestiZaKorisnika;
    }

    public void setListaPoslednjihPetVestiZaKorisnika(List<Vest> listaPoslednjihPetVestiZaKorisnika) {
        this.listaPoslednjihPetVestiZaKorisnika = listaPoslednjihPetVestiZaKorisnika;
    }

    public void ucitajVestiIzGrupe() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);

            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

            int idGrupe = Integer.parseInt(params.get("idGrupe"));

            PreparedStatement ps = conn.prepareStatement("select idVesti, autorVesti, naslovVesti, tekstVesti, datumPostavljanjaVesti, nivoVidljivosti, nazivKategorije, arhiviranaVest, metaVesti from Vesti v, Vidljivost vv, Kategorije k, Grupa g, UcestvujeGrupaStudenata ugs, Objave o where v.idVidljivost=vv.idVidljivost && v.idKategorije=k.idKategorije && vv.nivoVidljivosti='Formirana grupa studenata' && g.idGrupa=? && g.idGrupa=ugs.idGrupa && ugs.idObjave=o.idObjave && o.idObjave=v.idObjave && arhiviranaVest='false'");
            ps.setInt(1, idGrupe);

            ResultSet rs = ps.executeQuery();

            listaVestiIzGrupe = new ArrayList<>();

            while (rs.next()) {
                Vest v = new Vest();
                v.setIdVesti(rs.getInt("idVesti"));
                v.setNaslovVesti(rs.getString("naslovVesti"));
                v.setTekstVesti(rs.getString("tekstVesti"));
                v.setAutorVesti(rs.getString("autorVesti"));
                v.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                v.setNazivKategorije(rs.getString("nazivKategorije"));
                v.setArhiviranaVest(rs.getString("arhiviranaVest"));
                v.setMetaVesti(rs.getString("metaVesti"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumPostavljanjaVesti").toString(), datePattern);

                v.setDatumPostavljanja(rs.getTimestamp("datumPostavljanjaVesti").toString());
                v.setDatumPostavljanjaVesti(datumPostavljanja);

                listaVestiIzGrupe.add(v);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajVestiZaDatuGrupu() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);

            PreparedStatement ps = conn.prepareStatement("select idVesti, autorVesti, naslovVesti, tekstVesti, datumPostavljanjaVesti, nivoVidljivosti, nazivKategorije, arhiviranaVest, metaVesti from Vesti v, Vidljivost vv, Kategorije k, Grupa g, UcestvujeGrupaStudenata ugs, Objave o where v.idVidljivost=vv.idVidljivost && v.idKategorije=k.idKategorije && vv.nivoVidljivosti='Formirana grupa studenata' && g.nazivGrupe=? && g.idGrupa=ugs.idGrupa && ugs.idObjave=o.idObjave && o.idObjave=v.idObjave && arhiviranaVest='false'");
            ps.setString(1, izabranaGrupaStudenata);

            ResultSet rs = ps.executeQuery();

            listaVestiIzGrupe = new ArrayList<>();

            while (rs.next()) {
                Vest v = new Vest();
                v.setIdVesti(rs.getInt("idVesti"));
                v.setNaslovVesti(rs.getString("naslovVesti"));
                v.setTekstVesti(rs.getString("tekstVesti"));
                v.setAutorVesti(rs.getString("autorVesti"));
                v.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                v.setNazivKategorije(rs.getString("nazivKategorije"));
                v.setArhiviranaVest(rs.getString("arhiviranaVest"));
                v.setMetaVesti(rs.getString("metaVesti"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumPostavljanjaVesti").toString(), datePattern);

                v.setDatumPostavljanja(rs.getTimestamp("datumPostavljanjaVesti").toString());
                v.setDatumPostavljanjaVesti(datumPostavljanja);

                listaVestiIzGrupe.add(v);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajSveVestiZaKorisnika() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            PreparedStatement ps;

            if (korisnik != null) {
                ps = conn.prepareStatement("select idVesti, naslovVesti, autorVesti, tekstVesti, arhiviranaVest, datumPostavljanjaVesti, nivoVidljivosti, nazivKategorije, metaVesti from vesti v, korisnik k, vidljivost vv, kategorije kg, ucestvuje_kreira uk where v.idKategorije=kg.idKategorije && v.idVidljivost=vv.idVidljivost && k.idKorisnik=uk.idKorisnik && uk.idObjave=v.idObjave && k.korisnickoIme=? order by v.datumPostavljanjaVesti desc");
                ps.setString(1, korisnik.getKorisnickoIme());
            } else {
                ps = conn.prepareStatement("select idVesti, naslovVesti, autorVesti, tekstVesti, arhiviranaVest, datumPostavljanjaVesti, nivoVidljivosti, nazivKategorije, metaVesti from vesti v, vidljivost vv, kategorije kg where v.idKategorije=kg.idKategorije && v.idVidljivost=vv.idVidljivost && vv.nivoVidljivosti='Svi i gosti' order by v.datumPostavljanjaVesti desc");
            }

            ResultSet rs = ps.executeQuery();

            listaSvihVestiZaKorisnika = new ArrayList<>();

            while (rs.next()) {
                Vest v = new Vest();
                v.setIdVesti(rs.getInt("idVesti"));
                v.setNaslovVesti(rs.getString("naslovVesti"));
                v.setTekstVesti(rs.getString("tekstVesti"));
                v.setAutorVesti(rs.getString("autorVesti"));
                v.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                v.setNazivKategorije(rs.getString("nazivKategorije"));
                v.setArhiviranaVest(rs.getString("arhiviranaVest"));
                v.setMetaVesti(rs.getString("metaVesti"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumPostavljanjaVesti").toString(), datePattern);
                v.setDatumPostavljanja(rs.getTimestamp("datumPostavljanjaVesti").toString());
                v.setDatumPostavljanjaVesti(datumPostavljanja);

                listaSvihVestiZaKorisnika.add(v);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajPoslednjihPetZaKorisnika() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");
            
            PreparedStatement ps;

            if (korisnik != null) {
                ps = conn.prepareStatement("select idVesti, naslovVesti, autorVesti, tekstVesti, arhiviranaVest, datumPostavljanjaVesti, nivoVidljivosti, nazivKategorije, metaVesti from vesti v, korisnik k, vidljivost vv, kategorije kg, ucestvuje_kreira uk where v.idKategorije=kg.idKategorije && v.idVidljivost=vv.idVidljivost && k.idKorisnik=uk.idKorisnik && uk.idObjave=v.idObjave && k.korisnickoIme=? order by v.datumPostavljanjaVesti desc limit 5");
                ps.setString(1, korisnik.getKorisnickoIme());
            } else {
                ps = conn.prepareStatement("select idVesti, naslovVesti, autorVesti, tekstVesti, arhiviranaVest, datumPostavljanjaVesti, nivoVidljivosti, nazivKategorije, metaVesti from vesti v, vidljivost vv, kategorije kg where v.idKategorije=kg.idKategorije && v.idVidljivost=vv.idVidljivost && vv.nivoVidljivosti='Svi i gosti' order by v.datumPostavljanjaVesti desc limit 5");
            }

            ResultSet rs = ps.executeQuery();
            
            listaPoslednjihPetVestiZaKorisnika = new ArrayList<>();

            while (rs.next()) {
                Vest v = new Vest();
                v.setIdVesti(rs.getInt("idVesti"));
                v.setNaslovVesti(rs.getString("naslovVesti"));
                v.setTekstVesti(rs.getString("tekstVesti"));
                v.setAutorVesti(rs.getString("autorVesti"));
                v.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                v.setNazivKategorije(rs.getString("nazivKategorije"));
                v.setArhiviranaVest(rs.getString("arhiviranaVest"));
                v.setMetaVesti(rs.getString("metaVesti"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumPostavljanjaVesti").toString(), datePattern);

                v.setDatumPostavljanjaVestiNaslovna(rs.getTimestamp("datumPostavljanjaVesti").toString().substring(0, rs.getTimestamp("datumPostavljanjaVesti").toString().length() - 5));
                v.setDatumPostavljanja(rs.getTimestamp("datumPostavljanjaVesti").toString());
                v.setDatumPostavljanjaVesti(datumPostavljanja);

                listaPoslednjihPetVestiZaKorisnika.add(v);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ucitajSveVestiKojeJeKorisnikNapisao() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            PreparedStatement ps;
            
            ps = conn.prepareStatement("select idVesti, datumPostavljanjaVesti, metaVesti, naslovVesti, tekstVesti, autorVesti from vesti v, ucestvuje_kreira uk, korisnik k where k.idKorisnik=uk.idKorisnik && uk.tipUcesnika='kreira' && uk.idObjave=v.idObjave && k.korisnickoIme=?");
            ps.setString(1, korisnik.getKorisnickoIme());
             
            ResultSet rs = ps.executeQuery();
            
            listaSvihVestiKojeJeKorisnikNapisao = new ArrayList<>();

            while (rs.next()) {
                Vest v = new Vest();
                v.setNaslovVesti(rs.getString("naslovVesti"));
                v.setTekstVesti(rs.getString("tekstVesti"));
                v.setAutorVesti(rs.getString("autorVesti"));
                v.setArhiviranaVest(rs.getString("arhiviranaVest"));
                v.setMetaVesti(rs.getString("metaVesti"));
                v.setDatumPostavljanja(rs.getTimestamp("datumPostavljanjaVesti").toString());
                listaSvihVestiKojeJeKorisnikNapisao.add(v);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public void ucitajPoslednjihPetZaGosta() {
//        try {
//            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
//            Statement stm = conn.createStatement();
//
//            ResultSet rs = stm.executeQuery("select v.idVesti, v.naslovVesti, v.tekstVesti, v.arhiviranaVest, autorVesti, datumPostavljanjaVesti, nivoVidljivosti, nazivKategorije, metaVesti from vesti v, vidljivost vv, kategorije kg where v.idKategorije=kg.idKategorije && v.idVidljivost=vv.idVidljivost && vv.nivoVidljivosti='Svi i gosti' order by v.datumPostavljanjaVesti desc limit 5");
//
//            listaPoslednjihPetVestiZaKorisnika = new ArrayList<>();
//
//            while (rs.next()) {
//                Vest v = new Vest();
//                v.setIdVesti(rs.getInt("idVesti"));
//                v.setNaslovVesti(rs.getString("naslovVesti"));
//                v.setTekstVesti(rs.getString("tekstVesti"));
//                v.setAutorVesti(rs.getString("autorVesti"));
//                v.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
//                v.setNazivKategorije(rs.getString("nazivKategorije"));
//                v.setArhiviranaVest(rs.getString("arhiviranaVest"));
//                v.setMetaVesti(rs.getString("metaVesti"));
//
//                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
//
//                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumPostavljanjaVesti").toString(), datePattern);
//
//                v.setDatumPostavljanjaVestiNaslovna(rs.getTimestamp("datumPostavljanjaVesti").toString().substring(0, rs.getTimestamp("datumPostavljanjaVesti").toString().length() - 5));
//                v.setDatumPostavljanja(rs.getTimestamp("datumPostavljanjaVesti").toString());
//                v.setDatumPostavljanjaVesti(datumPostavljanja);
//
//                listaPoslednjihPetVestiZaKorisnika.add(v);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public void ucitajSveVestiZaGosta() {
//        try {
//            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
//            Statement stm = conn.createStatement();
//
//            ResultSet rs = stm.executeQuery("select v.idVesti, v.naslovVesti, v.autorVesti, v.tekstVesti, v.arhiviranaVest, datumPostavljanjaVesti, nivoVidljivosti, nazivKategorije, metaVesti from vesti v, vidljivost vv, kategorije kg where v.idKategorije=kg.idKategorije && vv.nivoVidljivosti='Svi i gosti' && v.idVidljivost=vv.idVidljivost order by v.datumPostavljanjaVesti desc");
//
//            listaSvihVestiZaKorisnika = new ArrayList<>();
//
//            while (rs.next()) {
//                Vest v = new Vest();
//                v.setIdVesti(rs.getInt("idVesti"));
//                v.setNaslovVesti(rs.getString("naslovVesti"));
//                v.setTekstVesti(rs.getString("tekstVesti"));
//                v.setAutorVesti(rs.getString("autorVesti"));
//                v.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
//                v.setNazivKategorije(rs.getString("nazivKategorije"));
//                v.setArhiviranaVest(rs.getString("arhiviranaVest"));
//                v.setMetaVesti(rs.getString("metaVesti"));
//
//                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
//                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumPostavljanjaVesti").toString(), datePattern);
//
//                v.setDatumPostavljanja(rs.getTimestamp("datumPostavljanjaVesti").toString());
//                v.setDatumPostavljanjaVesti(datumPostavljanja);
//
//                listaSvihVestiZaKorisnika.add(v);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    
}
