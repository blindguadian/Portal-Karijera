package controllers;

import beans.Anketa;
import beans.Korisnik;
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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@ViewScoped
public class UcitajAnketeZaKorisnika {

    private List<Anketa> listaSvihAnketaKojeJeKorisnikNapisao, listaAnketaIzGrupe, listaSvihAnketaZaKorisnika, listaPoslednjihPetAnketaZaKorisnika, listaNepopunjenihAnketa, listaPopunjenihAnketa;
    private int brojNepopunjenihAnketa, brojPopunjenihAnketa;
    private String izabranaGrupaStudenata;

    public List<Anketa> getListaSvihAnketaKojeJeKorisnikNapisao() {
        return listaSvihAnketaKojeJeKorisnikNapisao;
    }

    public void setListaSvihAnketaKojeJeKorisnikNapisao(List<Anketa> listaSvihAnketaKojeJeKorisnikNapisao) {
        this.listaSvihAnketaKojeJeKorisnikNapisao = listaSvihAnketaKojeJeKorisnikNapisao;
    }

    public List<Anketa> getListaNepopunjenihAnketa() {
        return listaNepopunjenihAnketa;
    }

    public void setListaNepopunjenihAnketa(List<Anketa> listaNepopunjenihAnketa) {
        this.listaNepopunjenihAnketa = listaNepopunjenihAnketa;
    }

    public List<Anketa> getListaPopunjenihAnketa() {
        return listaPopunjenihAnketa;
    }

    public void setListaPopunjenihAnketa(List<Anketa> listaPopunjenihAnketa) {
        this.listaPopunjenihAnketa = listaPopunjenihAnketa;
    }

    public int getBrojNepopunjenihAnketa() {
        return brojNepopunjenihAnketa;
    }

    public void setBrojNepopunjenihAnketa(int brojNepopunjenihAnketa) {
        this.brojNepopunjenihAnketa = brojNepopunjenihAnketa;
    }

    public int getBrojPopunjenihAnketa() {
        return brojPopunjenihAnketa;
    }

    public void setBrojPopunjenihAnketa(int brojPopunjenihAnketa) {
        this.brojPopunjenihAnketa = brojPopunjenihAnketa;
    }

    public List<Anketa> getListaAnketaIzGrupe() {
        return listaAnketaIzGrupe;
    }

    public void setListaAnketaIzGrupe(List<Anketa> listaAnketaIzGrupe) {
        this.listaAnketaIzGrupe = listaAnketaIzGrupe;
    }

    public List<Anketa> getListaSvihAnketaZaKorisnika() {
        return listaSvihAnketaZaKorisnika;
    }

    public void setListaSvihAnketaZaKorisnika(List<Anketa> listaSvihAnketaZaKorisnika) {
        this.listaSvihAnketaZaKorisnika = listaSvihAnketaZaKorisnika;
    }

    public List<Anketa> getListaPoslednjihPetAnketaZaKorisnika() {
        return listaPoslednjihPetAnketaZaKorisnika;
    }

    public void setListaPoslednjihPetAnketaZaKorisnika(List<Anketa> listaPoslednjihPetAnketaZaKorisnika) {
        this.listaPoslednjihPetAnketaZaKorisnika = listaPoslednjihPetAnketaZaKorisnika;
    }

    public void ucitajSveAnketeKojeJeKorisnikNapisao() {

        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            PreparedStatement ps;

            if (korisnik != null) {
                ps = conn.prepareStatement("select nazivAnkete, tekstAnkete, autorAnkete from ankete a, ucestvuje_kreira uk, korisnik k where a.idObjave=uk.idObjave && uk.idKorisnik=k.idKorisnik && uk.tipUcesnika='kreira' && k.korisnickoIme=? && arhiviranaAnketa='false'");
                ps.setString(1, korisnik.getKorisnickoIme());
            } else {
                return;
            }

            ResultSet rs = ps.executeQuery();

            listaSvihAnketaKojeJeKorisnikNapisao = new ArrayList<>();

            while (rs.next()) {
                Anketa a = new Anketa();
                a.setIdAnketa(rs.getInt("idAnkete"));
                a.setNazivAnkete(rs.getString("nazivAnkete"));
                a.setTekstAnkete(rs.getString("tekstAnkete"));
                a.setArhiviranaAnketa(rs.getString("arhiviranaAnketa"));
                a.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                a.setTipUcesnika("kreira");

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumTrajanjaAnkete = LocalDateTime.parse(rs.getTimestamp("datumTrajanjaAnkete").toString(), datePattern);
                a.setDatumTrajanja(rs.getTimestamp("datumTrajanjaAnkete").toString().substring(0, rs.getTimestamp("datumTrajanjaAnkete").toString().length() - 5));
                a.setDatumTrajanjaAnkete(datumTrajanjaAnkete);
                listaSvihAnketaKojeJeKorisnikNapisao.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void ucitajPopunjeneAnketeKorisnika() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            PreparedStatement ps;

            ps = conn.prepareStatement("select idAnkete, nazivAnkete, tekstAnkete, datumTrajanjaAnkete, nivoVidljivosti, arhiviranaAnketa from ucestvuje_kreira uk, ankete a, vidljivost v, korisnik k where k.idKorisnik=uk.idKorisnik && k.korisnickoIme=? && a.idVidljivost=v.idVidljivost && uk.tipucesnika='popunio' && arhiviranaAnketa='false' && a.idObjave=uk.idObjave");
            ps.setString(1, korisnik.getKorisnickoIme());

            ResultSet rs = ps.executeQuery();

            listaPopunjenihAnketa = new ArrayList<>();

            while (rs.next()) {
                Anketa a = new Anketa();
                a.setIdAnketa(rs.getInt("idAnkete"));
                a.setNazivAnkete(rs.getString("nazivAnkete"));
                a.setTekstAnkete(rs.getString("tekstAnkete"));
                a.setArhiviranaAnketa(rs.getString("arhiviranaAnketa"));
                a.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                a.setTipUcesnika("popunio");

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumTrajanjaAnkete = LocalDateTime.parse(rs.getTimestamp("datumTrajanjaAnkete").toString(), datePattern);
                a.setDatumTrajanja(rs.getTimestamp("datumTrajanjaAnkete").toString().substring(0, rs.getTimestamp("datumTrajanjaAnkete").toString().length() - 5));
                a.setDatumTrajanjaAnkete(datumTrajanjaAnkete);

                listaSvihAnketaZaKorisnika.add(a);
                brojPopunjenihAnketa++;
            }

        } catch (SQLException ex) {
            Logger.getLogger(UcitajAnketeZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void ucitajNepopunjeneAnketeKorisnika() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            PreparedStatement ps;

            ps = conn.prepareStatement("select idAnkete, nazivAnkete, tekstAnkete, datumTrajanjaAnkete, nivoVidljivosti, arhiviranaAnketa from ucestvuje_kreira uk, ankete a, vidljivost v, korisnik k where k.idKorisnik=uk.idKorisnik && k.korisnickoIme=? && a.idVidljivost=v.idVidljivost && uk.tipucesnika='popunjava' && arhiviranaAnketa='false' && a.idObjave=uk.idObjave");
            ps.setString(1, korisnik.getKorisnickoIme());

            ResultSet rs = ps.executeQuery();

            listaPopunjenihAnketa = new ArrayList<>();

            while (rs.next()) {
                Anketa a = new Anketa();
                a.setIdAnketa(rs.getInt("idAnkete"));
                a.setNazivAnkete(rs.getString("nazivAnkete"));
                a.setTekstAnkete(rs.getString("tekstAnkete"));
                a.setArhiviranaAnketa(rs.getString("arhiviranaAnketa"));
                a.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                a.setTipUcesnika("popunjava");

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumTrajanjaAnkete = LocalDateTime.parse(rs.getTimestamp("datumTrajanjaAnkete").toString(), datePattern);
                a.setDatumTrajanja(rs.getTimestamp("datumTrajanjaAnkete").toString().substring(0, rs.getTimestamp("datumTrajanjaAnkete").toString().length() - 5));
                a.setDatumTrajanjaAnkete(datumTrajanjaAnkete);

                listaSvihAnketaZaKorisnika.add(a);
                brojNepopunjenihAnketa++;
            }

        } catch (SQLException ex) {
            Logger.getLogger(UcitajAnketeZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void ucitajAnketeIzGrupe() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);

            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

            int idGrupe = Integer.parseInt(params.get("idGrupe"));

            PreparedStatement ps = conn.prepareStatement("select idAnkete, nazivAnkete, tekstAnkete, datumTrajanjaAnkete, nivoVidljivosti, arhiviranaAnketa FROM Ankete a, Vidljivost v, Grupa g, UcestvujeGrupaStudenata ugs, Objave o where a.idVidljivost=v.idVidljivost && v.nivoVidljivosti='Formirana grupa studenata' && g.idGrupa=? && g.idGrupa=ugs.idGrupa && ugs.idObjave=o.idObjave && o.idObjave=a.idObjave && arhiviranaAnketa='false'");
            ps.setInt(1, idGrupe);

            ResultSet rs = ps.executeQuery();

            listaAnketaIzGrupe = new ArrayList<>();

            while (rs.next()) {
                Anketa a = new Anketa();
                a.setIdAnketa(rs.getInt("idAnkete"));
                a.setNazivAnkete(rs.getString("nazivAnkete"));
                a.setTekstAnkete(rs.getString("tekstAnkete"));
                a.setArhiviranaAnketa(rs.getString("arhiviranaAnketa"));
                a.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                a.setTipUcesnika(rs.getString("tipUcesnika"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumTrajanjaAnkete = LocalDateTime.parse(rs.getTimestamp("datumTrajanjaAnkete").toString(), datePattern);
                a.setDatumTrajanja(rs.getTimestamp("datumTrajanjaAnkete").toString().substring(0, rs.getTimestamp("datumTrajanjaAnkete").toString().length() - 5));
                a.setDatumTrajanjaAnkete(datumTrajanjaAnkete);

                listaAnketaIzGrupe.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajAnketeZaDatuGrupu() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);

            PreparedStatement ps = conn.prepareStatement("select idAnkete, nazivAnkete, tekstAnkete, datumTrajanjaAnkete, nivoVidljivosti, arhiviranaAnketa FROM Ankete a, Vidljivost v, Grupa g, UcestvujeGrupaStudenata ugs, Objave o where a.idVidljivost=v.idVidljivost && v.nivoVidljivosti='Formirana grupa studenata' && g.nazivGrupe=? && g.idGrupa=ugs.idGrupa && ugs.idObjave=o.idObjave && o.idObjave=a.idObjave && arhiviranaAnketa='false'");
            ps.setString(1, izabranaGrupaStudenata);

            ResultSet rs = ps.executeQuery();

            listaAnketaIzGrupe = new ArrayList<>();

            while (rs.next()) {
                Anketa a = new Anketa();
                a.setIdAnketa(rs.getInt("idAnkete"));
                a.setNazivAnkete(rs.getString("nazivAnkete"));
                a.setTekstAnkete(rs.getString("tekstAnkete"));
                a.setArhiviranaAnketa(rs.getString("arhiviranaAnketa"));
                a.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                a.setTipUcesnika(rs.getString("tipUcesnika"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumTrajanjaAnkete = LocalDateTime.parse(rs.getTimestamp("datumTrajanjaAnkete").toString(), datePattern);
                a.setDatumTrajanja(rs.getTimestamp("datumTrajanjaAnkete").toString().substring(0, rs.getTimestamp("datumTrajanjaAnkete").toString().length() - 5));
                a.setDatumTrajanjaAnkete(datumTrajanjaAnkete);

                listaAnketaIzGrupe.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ucitajSveAnketeZaKorisnika() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            PreparedStatement ps;

            if (korisnik != null) {
                ps = conn.prepareStatement("select idAnkete, nazivAnkete, tekstAnkete, datumTrajanjaAnkete, nivoVidljivosti, arhiviranaAnketa, tipUcesnika from ucestvuje_kreira uk, ankete a, vidljivost v, korisnik k where k.idKorisnik=uk.idKorisnik && k.korisnickoIme=? && a.idObjave=uk.idObjave && a.arhiviranaAnketa='false' && v.idVidljivost=a.idVidljivost");
                ps.setString(1, korisnik.getKorisnickoIme());
            } else {
                ps = conn.prepareStatement("select idAnkete, nazivAnkete, tekstAnkete, datumTrajanjaAnkete, nivoVidljivosti, arhiviranaAnketa from ankete a, vidljivost v where a.arhiviranaAnketa='false' && v.idVidljivost=a.idVidljivost && v.nivoVidljivosti='Svi i gosti'");
            }

            ResultSet rs = ps.executeQuery();

            listaSvihAnketaZaKorisnika = new ArrayList<>();

            while (rs.next()) {
                Anketa a = new Anketa();
                a.setIdAnketa(rs.getInt("idAnkete"));
                a.setNazivAnkete(rs.getString("nazivAnkete"));
                a.setTekstAnkete(rs.getString("tekstAnkete"));
                a.setArhiviranaAnketa(rs.getString("arhiviranaAnketa"));
                a.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                if (korisnik != null) {
                    a.setTipUcesnika(rs.getString("tipUcesnika"));
                } else {
                    a.setTipUcesnika("Pregleda");
                }

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumTrajanjaAnkete = LocalDateTime.parse(rs.getTimestamp("datumTrajanjaAnkete").toString(), datePattern);
                a.setDatumTrajanja(rs.getTimestamp("datumTrajanjaAnkete").toString().substring(0, rs.getTimestamp("datumTrajanjaAnkete").toString().length() - 5));
                a.setDatumTrajanjaAnkete(datumTrajanjaAnkete);

                listaSvihAnketaZaKorisnika.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajPoslednjihPetAnketaZaKorisnika() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            PreparedStatement ps;

            if (korisnik != null) {
                ps = conn.prepareStatement("select idAnkete, nazivAnkete, tekstAnkete, datumTrajanjaAnkete, nivoVidljivosti, arhiviranaAnketa, metaAnkete, tipUcesnika from ucestvuje_kreira uk, ankete a, vidljivost v, korisnik k where k.idKorisnik=uk.idKorisnik && k.korisnickoIme=? && a.idObjave=uk.idObjave && a.arhiviranaAnketa='false' && v.idVidljivost=a.idVidljivost order by a.datumTrajanjaAnkete limit 5");
                ps.setString(1, korisnik.getKorisnickoIme());
            } else {
                ps = conn.prepareStatement("select idAnkete, nazivAnkete, tekstAnkete, datumTrajanjaAnkete, nivoVidljivosti, arhiviranaAnketa, metaAnkete from ankete a, vidljivost v where a.arhiviranaAnketa='false' && v.idVidljivost=a.idVidljivost && v.nivoVidljivosti='Svi i gosti' order by a.datumTrajanjaAnkete limit 5");
            }

            ResultSet rs = ps.executeQuery();

            listaPoslednjihPetAnketaZaKorisnika = new ArrayList<>();

            while (rs.next()) {
                Anketa a = new Anketa();
                a.setIdAnketa(rs.getInt("idAnkete"));
                a.setNazivAnkete(rs.getString("nazivAnkete"));
                a.setTekstAnkete(rs.getString("tekstAnkete"));
                a.setArhiviranaAnketa(rs.getString("arhiviranaAnketa"));
                a.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                if (korisnik != null) {
                    a.setTipUcesnika(rs.getString("tipUcesnika"));
                } else {
                    a.setTipUcesnika("Pregleda");
                }
                a.setMetaAnkete(rs.getString("metaAnkete"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumTrajanjaAnkete = LocalDateTime.parse(rs.getTimestamp("datumTrajanjaAnkete").toString(), datePattern);
                a.setDatumTrajanja(rs.getTimestamp("datumTrajanjaAnkete").toString().substring(0, rs.getTimestamp("datumTrajanjaAnkete").toString().length() - 5));
                a.setDatumTrajanjaAnkete(datumTrajanjaAnkete);

                listaPoslednjihPetAnketaZaKorisnika.add(a);
            }

            for (Anketa anketa : listaPoslednjihPetAnketaZaKorisnika) {
                PreparedStatement ps2 = conn.prepareStatement("select pitanje from pitanjaAnkete where idAnkete=? && redniBrojPitanja=?");
                ps2.setInt(1, anketa.getIdAnketa());
                ps2.setInt(2, 1);

                ResultSet pitanja = ps2.executeQuery();

                while (pitanja.next()) {
                    anketa.setPrvoPitanje(pitanja.getString("pitanje"));
                }
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
//            ResultSet rs = stm.executeQuery("SELECT metaAnkete, a.idAnkete, nazivAnkete, tekstAnkete, datumTrajanjaAnkete, v.nivoVidljivosti, arhiviranaAnketa from ankete a, vidljivost v where a.arhiviranaAnketa='false' && a.arhiviranaAnketa='false' && a.idVidljivost=v.idVidljivost && v.nivoVidljivosti='Svi i gosti' order by a.datumTrajanjaAnkete limit 5");
//            listaPoslednjihPetAnketaZaKorisnika = new ArrayList<>();
//
//            while (rs.next()) {
//                Anketa a = new Anketa();
//                a.setIdAnketa(rs.getInt("idAnkete"));
//                a.setNazivAnkete(rs.getString("nazivAnkete"));
//                a.setTekstAnkete(rs.getString("tekstAnkete"));
//                a.setArhiviranaAnketa(rs.getString("arhiviranaAnketa"));
//                a.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
//                a.setMetaAnkete(rs.getString("metaAnkete"));
//
//                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
//                LocalDateTime datumTrajanjaAnkete = LocalDateTime.parse(rs.getTimestamp("datumTrajanjaAnkete").toString(), datePattern);
//                a.setDatumTrajanja(rs.getTimestamp("datumTrajanjaAnkete").toString().substring(0, rs.getTimestamp("datumTrajanjaAnkete").toString().length() - 5));
//                a.setDatumTrajanjaAnkete(datumTrajanjaAnkete);
//
//                listaPoslednjihPetAnketaZaKorisnika.add(a);
//            }
//
//            for (Anketa anketa : listaPoslednjihPetAnketaZaKorisnika) {
//                ResultSet pitanja = stm.executeQuery("select pitanje from pitanjaAnkete where idAnkete=" + anketa.getIdAnketa() + " && redniBrojPitanja=1");
//                while (pitanja.next()) {
//                    anketa.setPrvoPitanje(pitanja.getString("pitanje"));
//                }
//            }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public void ucitajSveAnketeZaGosta() {
//        try {
//            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
//            Statement stm = conn.createStatement();
//
//            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
//            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");
//            if (korisnik.getKorisnickoIme() == null) {
//                ResultSet rs = stm.executeQuery("SELECT a.idAnkete, nazivAnkete, tekstAnkete, datumTrajanjaAnkete, nivoVidljivosti, arhiviranaAnketa from ankete a, vidljivost vv where vv.nivoVidljivosti='Svi i gosti' && a.idVidljivost=vv.idVidljivost");
//                listaSvihAnketaZaKorisnika = new ArrayList<>();
//
//                while (rs.next()) {
//                    Anketa a = new Anketa();
//                    a.setIdAnketa(rs.getInt("idAnkete"));
//                    a.setNazivAnkete(rs.getString("nazivAnkete"));
//                    a.setTekstAnkete(rs.getString("tekstAnkete"));
//                    a.setArhiviranaAnketa(rs.getString("arhiviranaAnketa"));
//                    a.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
//                    a.setTipUcesnika("gost");
//
//                    DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
//                    LocalDateTime datumTrajanjaAnkete = LocalDateTime.parse(rs.getTimestamp("datumTrajanjaAnkete").toString(), datePattern);
//                    a.setDatumTrajanja(rs.getTimestamp("datumTrajanjaAnkete").toString().substring(0, rs.getTimestamp("datumTrajanjaAnkete").toString().length() - 5));
//                    a.setDatumTrajanjaAnkete(datumTrajanjaAnkete);
//
//                    listaSvihAnketaZaKorisnika.add(a);
//                }
//            } else {
//                ResultSet rs = stm.executeQuery("SELECT tipUcesnika, a.idAnkete, nazivAnkete, tekstAnkete, datumTrajanjaAnkete, nivoVidljivosti, arhiviranaAnketa from korisnik k, ucestvuje_kreira uk, ankete a, vidljivost vv where vv.nivoVidljivosti='Svi i gosti' && a.idVidljivost=vv.idVidljivost && uk.idTipObavestenja=a.idAnkete && uk.idKorisnik=k.idKorisnik && k.korisnickoIme='" + korisnik.getKorisnickoIme() + "'");
//                listaSvihAnketaZaKorisnika = new ArrayList<>();
//
//                while (rs.next()) {
//                    Anketa a = new Anketa();
//                    a.setIdAnketa(rs.getInt("idAnkete"));
//                    a.setNazivAnkete(rs.getString("nazivAnkete"));
//                    a.setTekstAnkete(rs.getString("tekstAnkete"));
//                    a.setArhiviranaAnketa(rs.getString("arhiviranaAnketa"));
//                    a.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
//                    a.setTipUcesnika(rs.getString("tipUcesnika"));
//
//                    DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
//                    LocalDateTime datumTrajanjaAnkete = LocalDateTime.parse(rs.getTimestamp("datumTrajanjaAnkete").toString(), datePattern);
//                    a.setDatumTrajanja(rs.getTimestamp("datumTrajanjaAnkete").toString().substring(0, rs.getTimestamp("datumTrajanjaAnkete").toString().length() - 5));
//                    a.setDatumTrajanjaAnkete(datumTrajanjaAnkete);
//
//                    listaSvihAnketaZaKorisnika.add(a);
//                }
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
