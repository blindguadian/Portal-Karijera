package controllers;

import beans.Korisnik;
import beans.Preporuka;
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
            Statement stm = conn.createStatement();

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            ResultSet rs = stm.executeQuery("select nazivPreporuke, tekstPreporuke, autorPreporuke from preporuke p, ucestvuje_kreira uk, korisnik k where p.idPreporuke=uk.idTipObavestenja && uk.tipObavestenja='preporuka' && uk.idKorisnik=k.idKorisnik && uk.tipUcesnika='kreira' && k.korisnickoIme='" + korisnik.getKorisnickoIme() + "'");

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
            Statement stm = conn.createStatement();

            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

            int idGrupe = Integer.parseInt(params.get("idGrupe"));

            ResultSet rs = stm.executeQuery("SELECT p.idPreporuke, nazivPreporuke, tekstPreporuke, autorPreporuke, datumKreiranjaPreporuke, nivoVidljivosti, metaPreporuke FROM Preporuke p, Vidljivost v, Grupa g, uGrupi ug, Ucestvuje_kreira uk WHERE p.idVidljivost=v.idVidljivost && v.nivoVidljivosti='Formirana grupa studenata' && g.idGrupa=" + idGrupe + " && g.idGrupa=ug.idGrupa && ug.idKorisnik=uk.idKorisnik && uk.idUcestvuje_kreira=p.idUcestvuje_kreira");
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
            Statement stm = conn.createStatement();

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            ResultSet rs = stm.executeQuery("select p.idPreporuke, nazivPreporuke, tekstPreporuke, autorPreporuke, datumKreiranjaPreporuke, nivoVidljivosti, metaPreporuke from ucestvuje_kreira uk, preporuke p, korisnik k, vidljivost v where v.idVidljivost=p.idVidljivost && k.idKorisnik=uk.idKorisnik && k.korisnickoIme='" + korisnik.getKorisnickoIme() + "' && p.idPreporuke=uk.idTipObavestenja && uk.tipObavestenja='preporuka' order by p.datumKreiranjaPreporuke desc");

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
            Statement stm = conn.createStatement();

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            ResultSet rs = stm.executeQuery("select p.idPreporuke, nazivPreporuke, tekstPreporuke, autorPreporuke, datumKreiranjaPreporuke, nivoVidljivosti, metaPreporuke from ucestvuje_kreira uk, preporuke p, korisnik k, vidljivost v where v.idVidljivost=p.idVidljivost && k.idKorisnik=uk.idKorisnik && k.korisnickoIme='" + korisnik.getKorisnickoIme() + "' && p.idPreporuke=uk.idTipObavestenja && uk.tipObavestenja='preporuka' order by p.datumKreiranjaPreporuke desc limit 5");

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
                ResultSet pitanja = stm.executeQuery("select ocena_komentar from ocenaPreporuke where idPreporuke=" + preporuka.getIdPreporuke());
                int brojOcena = 0, suma = 0, prosecnaOcena = 0;
                while (pitanja.next()) {
                    brojOcena++;
                    suma += pitanja.getInt("ocena_komentar");
                }
                prosecnaOcena = suma / brojOcena;
                preporuka.setProsecnaOcena(prosecnaOcena);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajPoslednjihPetZaGosta() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery("select p.idPreporuke, nazivPreporuke, tekstPreporuke, autorPreporuke, datumKreiranjaPreporuke, nivoVidljivosti, metaPreporuke from preporuke p, vidljivost vv where vv.nivoVidljivosti='Svi i gosti' && p.idVidljivost=vv.idVidljivost order by p.datumKreiranjaPreporuke desc limit 5");

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
                ResultSet pitanja = stm.executeQuery("select ocena_komentar from ocenaPreporuke where idPreporuke=" + preporuka.getIdPreporuke());
                float prosecnaOcena = 0, brojOcena = 0, suma = 0;
                while (pitanja.next()) {
                    brojOcena++;
                    suma += pitanja.getInt("ocena_komentar");
                }
                if (brojOcena == 0) {
                    prosecnaOcena = 1;
                } else {
                    prosecnaOcena = suma / brojOcena;
                }
                preporuka.setProsecnaOcena(prosecnaOcena);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajSvePreporukeZaGosta() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery("select p.idPreporuke, nazivPreporuke, tekstPreporuke, autorPreporuke, datumKreiranjaPreporuke, nivoVidljivosti, metaPreporuke from preporuke p, vidljivost vv where vv.nivoVidljivosti='Svi i gosti' && p.idVidljivost=vv.idVidljivost order by p.datumKreiranjaPreporuke desc");

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
}
