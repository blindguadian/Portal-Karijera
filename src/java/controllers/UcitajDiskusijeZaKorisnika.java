package controllers;

import beans.Diskusija;
import beans.Grupa;
import beans.Korisnik;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;

@ManagedBean
@ViewScoped
public class UcitajDiskusijeZaKorisnika {

    private List<Diskusija> listaSvihDiskusijaKojeJeKorisnikNapisao, listaDiskusijaIzGrupe, listaDiskusijaZaDatuGrupu, listaSvihDiskusijaZaKorisnika, listaPoslednjihPetDiskusijaZaKorisnika;
    private List<Integer> brojNeprocitanihDiskusijaPoGrupama;
    private int trenutanBrojPrikazanihSvihDiskusija = 12;
    private int trenutanBrojPrikazanihMojihDiskusija = 12;
    private int trenutanBrojPrikazanihDiskusijaIzGrupe = 12;
    private String izabranaGrupaStudenata;
    private int brojNepregledanihSvihDiskusija, brojNepregledanihDiskusijaIzGrupa;

    HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

    public List<Integer> getBrojNeprocitanihDiskusijaPoGrupama() {
        return brojNeprocitanihDiskusijaPoGrupama;
    }

    public void setBrojNeprocitanihDiskusijaPoGrupama(List<Integer> brojNeprocitanihDiskusijaPoGrupama) {
        this.brojNeprocitanihDiskusijaPoGrupama = brojNeprocitanihDiskusijaPoGrupama;
    }

    public int getBrojNepregledanihSvihDiskusija() {
        return brojNepregledanihSvihDiskusija;
    }

    public void setBrojNepregledanihSvihDiskusija(int brojNepregledanihSvihDiskusija) {
        this.brojNepregledanihSvihDiskusija = brojNepregledanihSvihDiskusija;
    }

    public int getBrojNepregledanihDiskusijaIzGrupa() {
        return brojNepregledanihDiskusijaIzGrupa;
    }

    public void setBrojNepregledanihDiskusijaIzGrupa(int brojNepregledanihDiskusijaIzGrupa) {
        this.brojNepregledanihDiskusijaIzGrupa = brojNepregledanihDiskusijaIzGrupa;
    }

    public String getIzabranaGrupaStudenata() {
        return izabranaGrupaStudenata;
    }

    public void setIzabranaGrupaStudenata(String izabranaGrupaStudenata) {
        this.izabranaGrupaStudenata = izabranaGrupaStudenata;
    }

    public List<Diskusija> getListaDiskusijaZaDatuGrupu() {
        return listaDiskusijaZaDatuGrupu;
    }

    public void setListaDiskusijaZaDatuGrupu(List<Diskusija> listaDiskusijaZaDatuGrupu) {
        this.listaDiskusijaZaDatuGrupu = listaDiskusijaZaDatuGrupu;
    }

    public int getTrenutanBrojPrikazanihMojihDiskusija() {
        return trenutanBrojPrikazanihMojihDiskusija;
    }

    public void setTrenutanBrojPrikazanihMojihDiskusija(int trenutanBrojPrikazanihMojihDiskusija) {
        this.trenutanBrojPrikazanihMojihDiskusija = trenutanBrojPrikazanihMojihDiskusija;
    }

    public int getTrenutanBrojPrikazanihDiskusijaIzGrupe() {
        return trenutanBrojPrikazanihDiskusijaIzGrupe;
    }

    public void setTrenutanBrojPrikazanihDiskusijaIzGrupe(int trenutanBrojPrikazanihDiskusijaIzGrupe) {
        this.trenutanBrojPrikazanihDiskusijaIzGrupe = trenutanBrojPrikazanihDiskusijaIzGrupe;
    }

    public int getTrenutanBrojPrikazanihSvihDiskusija() {
        return trenutanBrojPrikazanihSvihDiskusija;
    }

    public void setTrenutanBrojPrikazanihSvihDiskusija(int trenutanBrojPrikazanihSvihDiskusija) {
        this.trenutanBrojPrikazanihSvihDiskusija = trenutanBrojPrikazanihSvihDiskusija;
    }

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

            PreparedStatement ps;

            if (null == korisnik) {
                return;
            } else {
                ps = conn.prepareStatement("select d.idDiskusija, nazivDiskusije, tekstDiskusija, autorDiskusije, datumPostavljanja, nivoVidljivosti, nazivKategorije, arhiviranaDiskusija, metaDiskusija, pregledaoObjavu from diskusija d, objave o, ucestvuje_kreira uk, korisnik k, vidljivost v, kategorije ka where ka.idKategorije=d.idKategorije && k.korisnickoIme=? && uk.idKorisnik=k.idKorisnik && uk.tipUcesnika='Kreira' && uk.idObjave=o.idObjave && o.idObjave=d.idObjave && v.idVidljivost=d.idVidljivost");
                ps.setString(1, korisnik.getKorisnickoIme());
            }

            ResultSet rs = ps.executeQuery();

            listaSvihDiskusijaKojeJeKorisnikNapisao = new ArrayList<>();

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
                d.setProcitana(rs.getString("pregledaoObjavu"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumPostavljanja").toString(), datePattern);
                d.setDatumPostavljanja(rs.getTimestamp("datumPostavljanja").toString().substring(0, rs.getTimestamp("datumPostavljanja").toString().length() - 5));
                d.setDatumPostavljanjaDiskusije(datumPostavljanja);

                listaSvihDiskusijaKojeJeKorisnikNapisao.add(d);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /* Ucitava diskusije za konkretnu grupu*/
    public void ucitajDiskusijeZaDatuGrupu(ValueChangeEvent e) {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);

            PreparedStatement ps = conn.prepareStatement("select idDiskusija, nazivDiskusije, tekstDiskusija, autorDiskusije, datumPostavljanja, nivoVidljivosti, nazivKategorije, arhiviranaDiskusija, metaDiskusija, pregledaoObjavu from Diskusija d, Vidljivost v, Kategorije k, Grupa g, ucestvujeGrupaStudenata ugs, Objave o, Korisnik kor, ucestvuje_kreira uk where d.idVidljivost=v.idVidljivost && d.idKategorije=k.idKategorije && v.nivoVidljivosti='Formirana grupa studenata' && g.nazivGrupe=? && g.idGrupa=ugs.idGrupa && ugs.idObjave=o.idObjave && o.idObjave=d.idObjave && arhiviranaDiskusija='false' && uk.idObjave=o.idObjave && kor.korisnickoIme=? && kor.idKorisnik=uk.idKorisnik");

            izabranaGrupaStudenata = e.getNewValue().toString();

            ps.setString(1, izabranaGrupaStudenata);
            ps.setString(2, korisnik.getKorisnickoIme());

            ResultSet rs = ps.executeQuery();

            listaDiskusijaZaDatuGrupu = new ArrayList<>();

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
                d.setProcitana(rs.getString("pregledaoObjavu"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumPostavljanja").toString(), datePattern);
                d.setDatumPostavljanja(rs.getTimestamp("datumPostavljanja").toString().substring(0, rs.getTimestamp("datumPostavljanja").toString().length() - 5));
                d.setDatumPostavljanjaDiskusije(datumPostavljanja);

                listaDiskusijaZaDatuGrupu.add(d);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UcitajDiskusijeZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajBrojNeprocitanihDiskusijaZaGrupeUKojimaSeKorisnikNalazi() {
        try {
            if (korisnik != null) {
                Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
                
                PreparedStatement ps;
                
                UcitajGrupeStudenata uc = new UcitajGrupeStudenata();

                List<Grupa> listaGrupa;
                brojNeprocitanihDiskusijaPoGrupama = new ArrayList<>();

                uc.ucitajGrupeUKojimaSeKorisnikNalazi();
                listaGrupa = uc.getListaGrupaGdeJeKorisnikClan();

                int ukupanBrojNeprocitanihDiskusijaZaGrupe = 0; 

                for (Grupa g : listaGrupa) {
                    int brojNeprocitanihDiskusijaZaGrupu = 0;
                    ps = conn.prepareStatement("select pregledaoObjavu from ucestvujeGrupaStudenata ugs, ucestvuje_kreira uk, korisnik k, objave o, grupa g where o.tipObavestenja='diskusija' && k.korisnickoIme=? && k.idKorisnik=uk.idKorisnik && o.idObjave=uk.idObjave && pregledaoObjavu='false' && ugs.idObjave=o.idObjave && g.idGrupa=ugs.idGrupa && g.nazivGrupe=?");
                    ps.setString(1, korisnik.getKorisnickoIme());
                    ps.setString(2, g.getNazivGrupe());
                    
                    ResultSet rs = ps.executeQuery();
                    
                    while(rs.next()){
                        ukupanBrojNeprocitanihDiskusijaZaGrupe++;
                        brojNeprocitanihDiskusijaZaGrupu++;
                    }
                    brojNeprocitanihDiskusijaPoGrupama.add(brojNeprocitanihDiskusijaZaGrupu);
                }
                brojNepregledanihDiskusijaIzGrupa = ukupanBrojNeprocitanihDiskusijaZaGrupe;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajDiskusijeZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    /* Ucitava diskusije za grupu na stranici date grupe*/
    public void ucitajDiskusijeIzGrupe() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);

            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

            int idGrupe = Integer.parseInt(params.get("idGrupe"));

            PreparedStatement ps = conn.prepareStatement("select idDiskusija, nazivDiskusije, tekstDiskusija, autorDiskusije, datumPostavljanja, nivoVidljivosti, nazivKategorije, arhiviranaDiskusija, metaDiskusija, pregledaoObjavu from Diskusija d, Vidljivost v, Kategorije k, Grupa g, ucestvujeGrupaStudenata ugs, ucestvuje_kreira uk, Objave o, Korisnik kor where d.idVidljivost=v.idVidljivost && d.idKategorije=k.idKategorije && v.nivoVidljivosti='Formirana grupa studenata' && g.idGrupa=? && g.idGrupa=ugs.idGrupa && ugs.idObjave=o.idObjave && o.idObjave=d.idObjave && arhiviranaDiskusija='false' && kor.idKorisnik=uk.idKorisnik && uk.idObjave=o.idObjave && kor.korisnickoIme=?");
            ps.setInt(1, idGrupe);
            ps.setString(2, korisnik.getKorisnickoIme());

            ResultSet rs = ps.executeQuery();

            listaDiskusijaIzGrupe = new ArrayList<>();

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
                d.setProcitana(rs.getString("pregledaoObjavu"));

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

            PreparedStatement ps;

            if (null == korisnik) {
                ps = conn.prepareStatement("select idDiskusija, nazivDiskusije, tekstDiskusija, autorDiskusije, datumPostavljanja, nivoVidljivosti, nazivKategorije, arhiviranaDiskusija, metaDiskusija from diskusija d, vidljivost vv, kategorije k where vv.nivoVidljivosti='Svi i gosti' && d.idVidljivost=vv.idVidljivost && k.idKategorije=d.idKategorije && arhiviranaDiskusija='false' order by d.datumPostavljanja desc");
            } else {
                ps = conn.prepareStatement("select d.idDiskusija, nazivDiskusije, tekstDiskusija, autorDiskusije, datumPostavljanja, nivoVidljivosti, nazivKategorije, arhiviranaDiskusija, metaDiskusija, pregledaoObjavu from ucestvuje_kreira uk, diskusija d, korisnik k, vidljivost v, kategorije kat, objave o where d.idKategorije=kat.idKategorije && d.idVidljivost=v.idVidljivost && k.idKorisnik=uk.idKorisnik && k.korisnickoIme=? && d.idObjave=o.idObjave && uk.idObjave=o.idObjave && arhiviranaDiskusija='false' order by d.datumPostavljanja desc");
                ps.setString(1, korisnik.getKorisnickoIme());
            }

            ResultSet rs = ps.executeQuery();

            listaSvihDiskusijaZaKorisnika = new ArrayList<>();
            int brojNepregledanihDiskusija = 0;

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
                if (korisnik == null) {
                    d.setProcitana("true");
                } else {
                    d.setProcitana(rs.getString("pregledaoObjavu"));
                    if ("false".equals(d.getProcitana())) {
                        brojNepregledanihDiskusija++;
                    }
                }

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumPostavljanja").toString(), datePattern);
                d.setDatumPostavljanja(rs.getTimestamp("datumPostavljanja").toString().substring(0, rs.getTimestamp("datumPostavljanja").toString().length() - 5));
                d.setDatumPostavljanjaDiskusije(datumPostavljanja);

                listaSvihDiskusijaZaKorisnika.add(d);
            }
            brojNepregledanihSvihDiskusija = brojNepregledanihDiskusija;
        } catch (SQLException ex) {
            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajPoslednjihPetZaKorisnika() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);

            PreparedStatement ps;

            if (korisnik != null) {
                ps = conn.prepareStatement("select idDiskusija, nazivDiskusije, tekstDiskusija, autorDiskusije, datumPostavljanja, nivoVidljivosti, nazivKategorije, arhiviranaDiskusija, metaDiskusija, pregledaoObjavu from objave o, diskusija d, korisnik k, vidljivost v, kategorije kat, ucestvuje_kreira uk where v.idVidljivost=d.idVidljivost && kat.idKategorije=d.idKategorije && k.idKorisnik=uk.idKorisnik && k.korisnickoIme='senka' && d.idObjave=o.idObjave && uk.idObjave=o.idObjave && arhiviranaDiskusija='false' order by d.datumPostavljanja limit 5");
                ps.setString(1, korisnik.getKorisnickoIme());
            } else {
                ps = conn.prepareStatement("select idDiskusija, nazivDiskusije, tekstDiskusija, autorDiskusije, datumPostavljanja, nivoVidljivosti, nazivKategorije, arhiviranaDiskusija, metaDiskusija from diskusija d, vidljivost vv, kategorije kat where kat.idKategorije=d.idKategorije && vv.nivoVidljivosti='Svi i gosti' && d.idVidljivost=vv.idVidljivost && arhiviranaDiskusija='false' order by d.datumPostavljanja desc limit 5");
            }

            ResultSet rs = ps.executeQuery();

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
                if (korisnik == null) {
                    d.setProcitana("true");
                } else {
                    d.setProcitana(rs.getString("pregledaoObjavu"));
                }

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumPostavljanja").toString(), datePattern);
                d.setDatumPostavljanja(rs.getTimestamp("datumPostavljanja").toString().substring(0, rs.getTimestamp("datumPostavljanja").toString().length() - 5));
                d.setDatumPostavljanjaDiskusije(datumPostavljanja);

                listaPoslednjihPetDiskusijaZaKorisnika.add(d);
            }

            for (Diskusija diskusija : listaPoslednjihPetDiskusijaZaKorisnika) {
                PreparedStatement komentari = conn.prepareStatement("select tekstKomentara from komentari where idDiskusija=?");

                komentari.setInt(1, diskusija.getIdDiskusija());

                ResultSet rss = komentari.executeQuery();

                while (rss.next()) {
                    diskusija.setPrviKomentar(rss.getString("tekstKomentara"));
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public void ucitajPoslednjihPetZaGosta() {
//        try {
//            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
//
//            PreparedStatement ps = conn.prepareStatement("select d.idDiskusija, nazivDiskusije, tekstDiskusija, autorDiskusije, datumPostavljanja, nivoVidljivosti, nazivKategorije, arhiviranaDiskusija, metaDiskusija from diskusija d, vidljivost vv, kategorije kat where kat.idKategorije=d.idKategorije && vv.nivoVidljivosti='Svi i gosti' && d.idVidljivost=vv.idVidljivost && arhiviranaDiskusija='false' order by d.datumPostavljanja desc limit 5");
//
//            ResultSet rs = ps.executeQuery();
//
//            listaPoslednjihPetDiskusijaZaKorisnika = new ArrayList<>();
//
//            while (rs.next()) {
//                Diskusija d = new Diskusija();
//                d.setIdDiskusija(rs.getInt("idDiskusija"));
//                d.setNazivDiskusije(rs.getString("nazivDiskusije"));
//                d.setTekstDiskusije(rs.getString("tekstDiskusija"));
//                d.setAutorDiskusije(rs.getString("autorDiskusije"));
//                d.setArhiviranaDiskusija(rs.getString("arhiviranaDiskusija"));
//                d.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
//                d.setKategorijaDiskusije(rs.getString("nazivKategorije"));
//                d.setMetaDiskusija(rs.getString("metaDiskusija"));
//
//                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
//                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumPostavljanja").toString(), datePattern);
//                d.setDatumPostavljanja(rs.getTimestamp("datumPostavljanja").toString().substring(0, rs.getTimestamp("datumPostavljanja").toString().length() - 5));
//                d.setDatumPostavljanjaDiskusije(datumPostavljanja);
//
//                listaPoslednjihPetDiskusijaZaKorisnika.add(d);
//            }
//
//            for (Diskusija diskusija : listaPoslednjihPetDiskusijaZaKorisnika) {
//                PreparedStatement komentari = conn.prepareStatement("select tekstKomentara from komentari where idDiskusija=? order by datumKreiranjaKomentara limit 1");
//
//                komentari.setInt(1, diskusija.getIdDiskusija());
//
//                ResultSet rss = ps.executeQuery();
//
//                while (rss.next()) {
//                    diskusija.setPrviKomentar(rss.getString("tekstKomentara"));
//                }
//            }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    public void ucitajSveDiskusijeZaGosta() {
//        try {
//            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
//            Statement stm = conn.createStatement();
//
//            ResultSet rs = stm.executeQuery("select d.idDiskusija, nazivDiskusije, tekstDiskusija, autorDiskusije, datumPostavljanja, nivoVidljivosti, nazivKategorije, arhiviranaDiskusija, metaDiskusija from diskusija d, vidljivost vv, kategorije k where vv.nivoVidljivosti='Svi i gosti' && d.idVidljivost=vv.idVidljivost && k.idKategorije=d.idKategorije order by d.datumPostavljanja desc");
//            listaSvihDiskusijaZaKorisnika = new ArrayList<>();
//
//            while (rs.next()) {
//                Diskusija d = new Diskusija();
//                d.setIdDiskusija(rs.getInt("idDiskusija"));
//                d.setNazivDiskusije(rs.getString("nazivDiskusije"));
//                d.setTekstDiskusije(rs.getString("tekstDiskusija"));
//                d.setAutorDiskusije(rs.getString("autorDiskusije"));
//                d.setArhiviranaDiskusija(rs.getString("arhiviranaDiskusija"));
//                d.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
//                d.setKategorijaDiskusije(rs.getString("nazivKategorije"));
//                d.setMetaDiskusija(rs.getString("metaDiskusija"));
//
//                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
//                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumPostavljanja").toString(), datePattern);
//                d.setDatumPostavljanja(rs.getTimestamp("datumPostavljanja").toString().substring(0, rs.getTimestamp("datumPostavljanja").toString().length() - 5));
//                d.setDatumPostavljanjaDiskusije(datumPostavljanja);
//
//                listaSvihDiskusijaZaKorisnika.add(d);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(UcitajVestiZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public void povecajBrojTrenutnoPrikazanihSvihDiskusija() {
        trenutanBrojPrikazanihSvihDiskusija += 12;
    }

    public void povecajBrojTrenutnoPrikazanihMojihDiskusija() {
        trenutanBrojPrikazanihMojihDiskusija += 12;
    }

    public void povecajBrojTrenutnoPrikazanihDiskusijaIzGrupe() {
        trenutanBrojPrikazanihDiskusijaIzGrupe += 12;
    }
}
