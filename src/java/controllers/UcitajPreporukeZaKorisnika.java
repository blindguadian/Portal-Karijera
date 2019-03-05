package controllers;

import beans.Korisnik;
import beans.Preporuka;
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
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class UcitajPreporukeZaKorisnika {

    private List<Preporuka> listaSvihPreporukaKojeJeKorisnikNapisao, listaPreporukaIzGrupe, listaSvihPreporukaZaKorisnika, listaPoslednjihPetPreporukaZaKorisnika;

    public List<Preporuka> getListaPreporukaIzGrupe() {
        return listaPreporukaIzGrupe;
    }

    public void setListaPreporukaIzGrupe(List<Preporuka> listaPreporukaIzGrupe) {
        this.listaPreporukaIzGrupe = listaPreporukaIzGrupe;
    }

    public List<Preporuka> getListaSvihPreporukaZaKorisnika() {
        return listaSvihPreporukaZaKorisnika;
    }

    public void setListaSvihPreporukaZaKorisnika(List<Preporuka> listaSvihPreporukaZaKorisnika) {
        this.listaSvihPreporukaZaKorisnika = listaSvihPreporukaZaKorisnika;
    }

    public List<Preporuka> getListaPoslednjihPetPreporukaZaKorisnika() {
        return listaPoslednjihPetPreporukaZaKorisnika;
    }

    public void setListaPoslednjihPetPreporukaZaKorisnika(List<Preporuka> listaPoslednjihPetPreporukaZaKorisnika) {
        this.listaPoslednjihPetPreporukaZaKorisnika = listaPoslednjihPetPreporukaZaKorisnika;
    }

    public void ucitajSvePreporukeKojeJeKorisnikNapisao() {

        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            PreparedStatement ps;

            if (korisnik != null) {
                ps = conn.prepareStatement("select nazivPreporuke, tekstPreporuke, autorPreporuke from preporuke p, objave o, ucestvuje_kreira uk, korisnik k where p.idPreporuke=o.idTipObavestenja && o.tipObavestenja='preporuka' && o.idKorisnik=k.idKorisnik && uk.tipUcesnika='kreira' && uk.idObjave=o.idObjave && k.korisnickoIme=?");
                ps.setString(1, korisnik.getKorisnickoIme());
            } else {
                return;
            }

            ResultSet rs = ps.executeQuery();

            listaSvihPreporukaKojeJeKorisnikNapisao = new ArrayList<>();

            while (rs.next()) {
                Preporuka p = new Preporuka();
                p.setNazivPreporuke(rs.getString("nazivPreporuke"));
                p.setTekstPreporuke(rs.getString("tekstPreporuke"));
                p.setAutorPreporuke(rs.getString("autorPreporuke"));

                listaSvihPreporukaKojeJeKorisnikNapisao.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void ucitajPreporukeIzGrupe() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            PreparedStatement ps;

            int idGrupe = Integer.parseInt(params.get("idGrupe"));

            if (korisnik != null) {
                ps = conn.prepareStatement("SELECT p.idPreporuke, nazivPreporuke, tekstPreporuke, autorPreporuke, datumKreiranjaPreporuke, nivoVidljivosti, metaPreporuke FROM Preporuke p, Vidljivost v, Grupa g, ucestvujeGrupaStudenata ugs, objave o WHERE p.idVidljivost=v.idVidljivost && g.idGrupa=? && g.idGrupa=ugs.idGrupa && ugs.idObjave=o.idObjave && p.idObjave=o.idObjave");
                ps.setInt(1, idGrupe);
            } else {
                return;
            }

            ResultSet rs = ps.executeQuery();

            listaPreporukaIzGrupe = new ArrayList<>();

            while (rs.next()) {
                Preporuka p = new Preporuka();
                p.setIdPreporuke(rs.getInt("idPreporuke"));
                p.setNazivPreporuke(rs.getString("nazivPreporuke"));
                p.setTekstPreporuke(rs.getString("tekstPreporuke"));
                p.setAutorPreporuke(rs.getString("autorPreporuke"));
                p.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                p.setMetaPreporuke(rs.getString("metaPreporuke"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumKreiranjaPreporuke").toString(), datePattern);
                p.setDatumKreiranja(rs.getTimestamp("datumKreiranjaPreporuke").toString().substring(0, rs.getTimestamp("datumKreiranjaPreporuke").toString().length() - 5));
                p.setDatumKreiranjaPreporuke(datumPostavljanja);

                listaPreporukaIzGrupe.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajSvePreporukeZaKorisnika() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            PreparedStatement ps;

            if (korisnik != null) {
                ps = conn.prepareStatement("select p.idPreporuke, nazivPreporuke, tekstPreporuke, autorPreporuke, datumKreiranjaPreporuke, nivoVidljivosti, metaPreporuke from ucestvuje_kreira uk, preporuke p, korisnik k, vidljivost v where v.idVidljivost=p.idVidljivost && k.idKorisnik=uk.idKorisnik && uk.idObjave=o.idObjave && k.korisnickoIme=? && p.idObjave=o.idObjave order by p.datumKreiranjaPreporuke desc");
                ps.setString(1, korisnik.getKorisnickoIme());
            } else {
                ps = conn.prepareStatement("select p.idPreporuke, nazivPreporuke, tekstPreporuke, autorPreporuke, datumKreiranjaPreporuke, nivoVidljivosti, metaPreporuke from preporuke p, vidljivost v where v.idVidljivost=p.idVidljivost && v.nivoVidljivosti='Svi i gosti' order by p.datumKreiranjaPreporuke desc");
            }

            ResultSet rs = ps.executeQuery();

            listaSvihPreporukaZaKorisnika = new ArrayList<>();

            while (rs.next()) {
                Preporuka p = new Preporuka();
                p.setIdPreporuke(rs.getInt("idPreporuke"));
                p.setNazivPreporuke(rs.getString("nazivPreporuke"));
                p.setTekstPreporuke(rs.getString("tekstPreporuke"));
                p.setAutorPreporuke(rs.getString("autorPreporuke"));
                p.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                p.setMetaPreporuke(rs.getString("metaPreporuke"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumKreiranjaPreporuke").toString(), datePattern);
                p.setDatumKreiranja(rs.getTimestamp("datumKreiranjaPreporuke").toString().substring(0, rs.getTimestamp("datumKreiranjaPreporuke").toString().length() - 5));
                p.setDatumKreiranjaPreporuke(datumPostavljanja);

                listaSvihPreporukaZaKorisnika.add(p);
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
                ps = conn.prepareStatement("select idPreporuke, nazivPreporuke, tekstPreporuke, autorPreporuke, datumKreiranjaPreporuke, nivoVidljivosti, metaPreporuke from ucestvuje_kreira uk, preporuke p, korisnik k, vidljivost v, objave o where v.idVidljivost=p.idVidljivost && k.idKorisnik=uk.idKorisnik && k.korisnickoIme=? && p.idObjave=o.idObjave && uk.idObjave=o.idObjave order by p.datumKreiranjaPreporuke desc limit 5");
                ps.setString(1, korisnik.getKorisnickoIme());
            } else {
                ps = conn.prepareStatement("select idPreporuke, nazivPreporuke, tekstPreporuke, autorPreporuke, datumKreiranjaPreporuke, nivoVidljivosti, metaPreporuke from preporuke p, vidljivost v where v.idVidljivost=p.idVidljivost && v.nivoVidljivosti='Svi i gosti' order by p.datumKreiranjaPreporuke desc limit 5");
            }

            ResultSet rs = ps.executeQuery();

            listaPoslednjihPetPreporukaZaKorisnika = new ArrayList<>();

            while (rs.next()) {
                Preporuka p = new Preporuka();
                p.setIdPreporuke(rs.getInt("idPreporuke"));
                p.setNazivPreporuke(rs.getString("nazivPreporuke"));
                p.setTekstPreporuke(rs.getString("tekstPreporuke"));
                p.setAutorPreporuke(rs.getString("autorPreporuke"));
                p.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                p.setMetaPreporuke(rs.getString("metaPreporuke"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumKreiranjaPreporuke").toString(), datePattern);
                p.setDatumKreiranja(rs.getTimestamp("datumKreiranjaPreporuke").toString().substring(0, rs.getTimestamp("datumKreiranjaPreporuke").toString().length() - 5));
                p.setDatumKreiranjaPreporuke(datumPostavljanja);

                listaPoslednjihPetPreporukaZaKorisnika.add(p);
            }

            for (Preporuka preporuka : listaPoslednjihPetPreporukaZaKorisnika) {

                PreparedStatement pitanjaPS = conn.prepareStatement("select ocenaPreporuke from ocenaPreporuke where idPreporuke=?");
                pitanjaPS.setInt(1, preporuka.getIdPreporuke());

                ResultSet pitanja = pitanjaPS.executeQuery();

                int brojOcena = 0, suma = 0, prosecnaOcena = 0;
                while (pitanja.next()) {
                    brojOcena++;
                    suma += pitanja.getInt("ocenaPreporuke");
                }
                if (brojOcena > 0) {
                    prosecnaOcena = suma / brojOcena;
                } else {
                    prosecnaOcena = 3;
                }
                preporuka.setProsecnaOcena(prosecnaOcena);
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
//            ResultSet rs = stm.executeQuery("select p.idPreporuke, nazivPreporuke, tekstPreporuke, autorPreporuke, datumKreiranjaPreporuke, nivoVidljivosti, metaPreporuke from preporuke p, vidljivost vv where vv.nivoVidljivosti='Svi i gosti' && p.idVidljivost=vv.idVidljivost order by p.datumKreiranjaPreporuke desc limit 5");
//
//            listaPoslednjihPetPreporukaZaKorisnika = new ArrayList<>();
//
//            while (rs.next()) {
//                Preporuka p = new Preporuka();
//                p.setIdPreporuke(rs.getInt("idPreporuke"));
//                p.setNazivPreporuke(rs.getString("nazivPreporuke"));
//                p.setTekstPreporuke(rs.getString("tekstPreporuke"));
//                p.setAutorPreporuke(rs.getString("autorPreporuke"));
//                p.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
//                p.setMetaPreporuke(rs.getString("metaPreporuke"));
//
//                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
//                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumKreiranjaPreporuke").toString(), datePattern);
//                p.setDatumKreiranja(rs.getTimestamp("datumKreiranjaPreporuke").toString().substring(0, rs.getTimestamp("datumKreiranjaPreporuke").toString().length() - 5));
//                p.setDatumKreiranjaPreporuke(datumPostavljanja);
//
//                listaPoslednjihPetPreporukaZaKorisnika.add(p);
//            }
//
//            for (Preporuka preporuka : listaPoslednjihPetPreporukaZaKorisnika) {
//                ResultSet pitanja = stm.executeQuery("select ocena_komentar from ocenaPreporuke where idPreporuke=" + preporuka.getIdPreporuke());
//                float prosecnaOcena = 0, brojOcena = 0, suma = 0;
//                while (pitanja.next()) {
//                    brojOcena++;
//                    suma += pitanja.getInt("ocena_komentar");
//                }
//                if (brojOcena == 0) {
//                    prosecnaOcena = 1;
//                } else {
//                    prosecnaOcena = suma / brojOcena;
//                }
//                preporuka.setProsecnaOcena(prosecnaOcena);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public void ucitajSvePreporukeZaGosta() {
//        try {
//            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
//            Statement stm = conn.createStatement();
//
//            ResultSet rs = stm.executeQuery("select p.idPreporuke, nazivPreporuke, tekstPreporuke, autorPreporuke, datumKreiranjaPreporuke, nivoVidljivosti, metaPreporuke from preporuke p, vidljivost vv where vv.nivoVidljivosti='Svi i gosti' && p.idVidljivost=vv.idVidljivost order by p.datumKreiranjaPreporuke desc");
//
//            listaSvihPreporukaZaKorisnika = new ArrayList<>();
//
//            while (rs.next()) {
//                Preporuka p = new Preporuka();
//                p.setIdPreporuke(rs.getInt("idPreporuke"));
//                p.setNazivPreporuke(rs.getString("nazivPreporuke"));
//                p.setTekstPreporuke(rs.getString("tekstPreporuke"));
//                p.setAutorPreporuke(rs.getString("autorPreporuke"));
//                p.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
//                p.setMetaPreporuke(rs.getString("metaPreporuke"));
//
//                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
//                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumKreiranjaPreporuke").toString(), datePattern);
//                p.setDatumKreiranja(rs.getTimestamp("datumKreiranjaPreporuke").toString().substring(0, rs.getTimestamp("datumKreiranjaPreporuke").toString().length() - 5));
//                p.setDatumKreiranjaPreporuke(datumPostavljanja);
//
//                listaSvihPreporukaZaKorisnika.add(p);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
