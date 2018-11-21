package controllers;

import beans.Diskusija;
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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class UcitajDiskusijeZaKorisnika {

    private List<Diskusija> listaSvihDiskusijaKojeJeKorisnikNapisao, listaDiskusijaIzGrupe, listaSvihDiskusijaZaKorisnika, listaPoslednjihPetDiskusijaZaKorisnika;

    public List<Diskusija> getListaSvihDiskusijaKojeJeKorisnikNapisao() {
        return listaSvihDiskusijaKojeJeKorisnikNapisao;
    }

    public void setListaSvihDiskusijaKojeJeKorisnikNapisao(List<Diskusija> listaSvihDiskusijaKojeJeKorisnikNapisao) {
        this.listaSvihDiskusijaKojeJeKorisnikNapisao = listaSvihDiskusijaKojeJeKorisnikNapisao;
    }

    public List<Diskusija> getListaDiskusijaIzGrupe() {
        return listaDiskusijaIzGrupe;
    }

    public void setListaDiskusijaIzGrupe(List<Diskusija> listaDiskusijaIzGrupe) {
        this.listaDiskusijaIzGrupe = listaDiskusijaIzGrupe;
    }

    public List<Diskusija> getListaSvihDiskusijaZaKorisnika() {
        return listaSvihDiskusijaZaKorisnika;
    }

    public void setListaSvihDiskusijaZaKorisnika(List<Diskusija> listaSvihDiskusijaZaKorisnika) {
        this.listaSvihDiskusijaZaKorisnika = listaSvihDiskusijaZaKorisnika;
    }

    public List<Diskusija> getListaPoslednjihPetDiskusijaZaKorisnika() {
        return listaPoslednjihPetDiskusijaZaKorisnika;
    }

    public void setListaPoslednjihPetDiskusijaZaKorisnika(List<Diskusija> listaPoslednjihPetDiskusijaZaKorisnika) {
        this.listaPoslednjihPetDiskusijaZaKorisnika = listaPoslednjihPetDiskusijaZaKorisnika;
    }

    public void ucitajSveDiskusijeKojeJeKorisnikNapisao() {

        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            ResultSet rs = stm.executeQuery("select nazivDiskusije, tekstDiskusija, autorDiskusije from diskusija d, ucestvuje_kreira uk, korisnik k where d.idDiskusija=uk.idTipObavestenja && uk.tipObavestenja='diskusija' && uk.idKorisnik=k.idKorisnik && uk.tipUcesnika='kreira'");

            listaSvihDiskusijaKojeJeKorisnikNapisao = new ArrayList<>();

            while (rs.next()) {
                Diskusija d = new Diskusija();
                d.setNazivDiskusije(rs.getString("nazivDiskusije"));
                d.setTekstDiskusije(rs.getString("tekstDiskusija"));
                d.setAutorDiskusije(rs.getString("autorDiskusije"));

                listaSvihDiskusijaKojeJeKorisnikNapisao.add(d);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void ucitajDiskusijeIzGrupe() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

            int idGrupe = Integer.parseInt(params.get("idGrupe"));

            ResultSet rs = stm.executeQuery("SELECT d.idDiskusija, nazivDiskusije, tekstDiskusija, autorDiskusije, datumPostavljanja, nivoVidljivosti, nazivKategorije, arhiviranaDiskusija, metaDiskusija FROM Diskusija d, Vidljivost v, Kategorije k, Grupa g, uGrupi ug, Ucestvuje_kreira uk WHERE d.idVidljivost=v.idVidljivost && d.idKategorije=k.idKategorije && v.nivoVidljivosti='Formirana grupa studenata' && g.idGrupa=" + idGrupe + " && g.idGrupa=ug.idGrupa && ug.idKorisnik=uk.idKorisnik && uk.idUcestvuje_kreira=d.idUcestvuje_kreira && uk.tipObavestenja='diskusija'");
            listaDiskusijaIzGrupe = new ArrayList<>();

            while (rs.next()) {
                Diskusija d = new Diskusija();
                d.setIdDiskusija(rs.getInt("idDiskusija"));
                d.setNazivDiskusije(rs.getString("nazivDiskusije"));
                d.setTekstDiskusije(rs.getString("tekstDiskusiija"));
                d.setAutorDiskusije(rs.getString("autorDiskusije"));
                d.setArhiviranaDiskusija(rs.getString("arhiviranaDiskusija"));
                d.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                d.setKategorijaDiskusije(rs.getString("nazivKategorije"));
                d.setMetaDiskusija(rs.getString("metaDiskusija"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumPostavljanja").toString(), datePattern);
                d.setDatumPostavljanja(rs.getTimestamp("datumPostavljanja").toString().substring(0, rs.getTimestamp("datumPostavljanja").toString().length() - 5));
                d.setDatumPostavljanjaDiskusije(datumPostavljanja);

                listaDiskusijaIzGrupe.add(d);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajSveDiskusijeZaKorisnika() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            ResultSet rs = stm.executeQuery("select d.idDiskusija, nazivDiskusije, tekstDiskusija, autorDiskusije, datumPostavljanja, nivoVidljivosti, nazivKategorije, arhiviranaDiskusija, metaDiskusija from ucestvuje_kreira uk, diskusija d, korisnik k, vidljivost v, kategorije kat where d.idKategorije=kat.idKategorije && d.idVidljivost=v.idVidljivost && k.idKorisnik=uk.idKorisnik && k.korisnickoIme='" + korisnik.getKorisnickoIme() + "' && d.idDiskusija=uk.idTipObavestenja && uk.tipObavestenja='diskusija' order by d.datumPostavljanja desc");

            listaSvihDiskusijaZaKorisnika = new ArrayList<>();

            while (rs.next()) {
                Diskusija d = new Diskusija();
                d.setIdDiskusija(rs.getInt("idDiskusija"));
                d.setNazivDiskusije(rs.getString("nazivDiskusije"));
                d.setTekstDiskusije(rs.getString("tekstDiskusiija"));
                d.setAutorDiskusije(rs.getString("autorDiskusije"));
                d.setArhiviranaDiskusija(rs.getString("arhiviranaDiskusija"));
                d.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                d.setKategorijaDiskusije(rs.getString("nazivKategorije"));
                d.setMetaDiskusija(rs.getString("metaDiskusija"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumPostavljanja").toString(), datePattern);
                d.setDatumPostavljanja(rs.getTimestamp("datumPostavljanja").toString().substring(0, rs.getTimestamp("datumPostavljanja").toString().length() - 5));
                d.setDatumPostavljanjaDiskusije(datumPostavljanja);

                listaSvihDiskusijaZaKorisnika.add(d);
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

            ResultSet rs = stm.executeQuery("select d.idDiskusija, nazivDiskusije, tekstDiskusija, autorDiskusije, datumPostavljanja, nivoVidljivosti, nazivKategorije, arhiviranaDiskusija, metaDiskusija from ucestvuje_kreira uk, diskusija d, korisnik k, vidljivost v, kategorije kat where v.idVidljivost=d.idVidljivost && kat.idKategorije=d.idKategorije && k.idKorisnik=uk.idKorisnik && k.korisnickoIme='" + korisnik.getKorisnickoIme() + "' && d.idDiskusija=uk.idTipObavestenja && uk.tipObavestenja='diskusija' order by d.datumPostavljanja desc limit 5");

            listaPoslednjihPetDiskusijaZaKorisnika = new ArrayList<>();

            while (rs.next()) {
                Diskusija d = new Diskusija();
                d.setIdDiskusija(rs.getInt("idDiskusija"));
                d.setNazivDiskusije(rs.getString("nazivDiskusije"));
                d.setTekstDiskusije(rs.getString("tekstDiskusija"));
                d.setAutorDiskusije(rs.getString("autorDiskusije"));
                d.setArhiviranaDiskusija(rs.getString("arhiviranaDiskusija"));
                d.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                d.setKategorijaDiskusije(rs.getString("nazivKategorije"));
                d.setMetaDiskusija(rs.getString("metaDiskusija"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumPostavljanja").toString(), datePattern);
                d.setDatumPostavljanja(rs.getTimestamp("datumPostavljanja").toString().substring(0, rs.getTimestamp("datumPostavljanja").toString().length() - 5));
                d.setDatumPostavljanjaDiskusije(datumPostavljanja);

                listaPoslednjihPetDiskusijaZaKorisnika.add(d);
            }

            for (Diskusija diskusija : listaPoslednjihPetDiskusijaZaKorisnika) {
                ResultSet komentari = stm.executeQuery("select tekstKomentara from komentari where idDiskusija=" + diskusija.getIdDiskusija());
                while (komentari.next()) {
                    diskusija.setPrviKomentar(komentari.getString("tekstKomentara"));
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajPoslednjihPetZaGosta() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery("select d.idDiskusija, nazivDiskusije, tekstDiskusija, autorDiskusije, datumPostavljanja, nivoVidljivosti, nazivKategorije, arhiviranaDiskusija, metaDiskusija from diskusija d, vidljivost vv, kategorije kat where kat.idKategorije=d.idKategorije && vv.nivoVidljivosti='Svi i gosti' && d.idVidljivost=vv.idVidljivost && arhiviranaDiskusija='false' order by d.datumPostavljanja desc limit 5");

            listaPoslednjihPetDiskusijaZaKorisnika = new ArrayList<>();

            while (rs.next()) {
                Diskusija d = new Diskusija();
                d.setIdDiskusija(rs.getInt("idDiskusija"));
                d.setNazivDiskusije(rs.getString("nazivDiskusije"));
                d.setTekstDiskusije(rs.getString("tekstDiskusija"));
                d.setAutorDiskusije(rs.getString("autorDiskusije"));
                d.setArhiviranaDiskusija(rs.getString("arhiviranaDiskusija"));
                d.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                d.setKategorijaDiskusije(rs.getString("nazivKategorije"));
                d.setMetaDiskusija(rs.getString("metaDiskusija"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumPostavljanja").toString(), datePattern);
                d.setDatumPostavljanja(rs.getTimestamp("datumPostavljanja").toString().substring(0, rs.getTimestamp("datumPostavljanja").toString().length() - 5));
                d.setDatumPostavljanjaDiskusije(datumPostavljanja);

                listaPoslednjihPetDiskusijaZaKorisnika.add(d);
            }

            for (Diskusija diskusija : listaPoslednjihPetDiskusijaZaKorisnika) {
                ResultSet komentari = stm.executeQuery("select tekstKomentara from komentari where idDiskusija=" + diskusija.getIdDiskusija() + " order by datumKreiranjaKomentara limit 1");
                while (komentari.next()) {
                    diskusija.setPrviKomentar(komentari.getString("tekstKomentara"));
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajSveDiskusijeZaGosta() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery("select d.idDiskusija, nazivDiskusije, tekstDiskusija, autorDiskusije, datumPostavljanja, nivoVidljivosti, nazivKategorije, arhiviranaDiskusija, metaDiskusija from diskusija d, vidljivost vv, kategorije k where vv.nivoVidljivosti='Svi i gosti' && d.idVidljivost=vv.idVidljivost && k.idKategorije=d.idKategorije order by d.datumPostavljanja desc");
            listaSvihDiskusijaZaKorisnika = new ArrayList<>();

            while (rs.next()) {
                Diskusija d = new Diskusija();
                d.setIdDiskusija(rs.getInt("idDiskusija"));
                d.setNazivDiskusije(rs.getString("nazivDiskusije"));
                d.setTekstDiskusije(rs.getString("tekstDiskusiija"));
                d.setAutorDiskusije(rs.getString("autorDiskusije"));
                d.setArhiviranaDiskusija(rs.getString("arhiviranaDiskusija"));
                d.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                d.setKategorijaDiskusije(rs.getString("nazivKategorije"));
                d.setMetaDiskusija(rs.getString("metaDiskusija"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumPostavljanja").toString(), datePattern);
                d.setDatumPostavljanja(rs.getTimestamp("datumPostavljanja").toString().substring(0, rs.getTimestamp("datumPostavljanja").toString().length() - 5));
                d.setDatumPostavljanjaDiskusije(datumPostavljanja);

                listaSvihDiskusijaZaKorisnika.add(d);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
