
package controllers;

import beans.Anketa;
import beans.Diskusija;
import beans.Obavestenje;
import beans.Oglas;
import beans.Preporuka;
import beans.Vest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped

public class UcitajObavestenje {
//    private List<Diskusija> diskusija;
    private Diskusija diskusija;
    private Anketa anketa;
    private Obavestenje obavestenje;
    private Oglas oglas;
    private Vest vest;
    private Preporuka preporuka;

    
    public Anketa getAnketa() {
        return anketa;
    }

    public void setAnketa(Anketa anketa) {
        this.anketa = anketa;
    }

    public Obavestenje getObavestenje() {
        return obavestenje;
    }

    public void setObavestenje(Obavestenje obavestenje) {
        this.obavestenje = obavestenje;
    }

    public Oglas getOglas() {
        return oglas;
    }

    public void setOglas(Oglas oglas) {
        this.oglas = oglas;
    }

    public Vest getVest() {
        return vest;
    }

    public void setVest(Vest vest) {
        this.vest = vest;
    }

    public Preporuka getPreporuka() {
        return preporuka;
    }

    public void setPreporuka(Preporuka preporuka) {
        this.preporuka = preporuka;
    }

    public Diskusija getDiskusija() {
        return diskusija;
    }

    public void setDiskusija(Diskusija diskusija) {
        this.diskusija = diskusija;
    }

    
    
    public void ucitajDiskusiju() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            
            int id = Integer.parseInt(params.get("idDiskusija"));
            
            ResultSet rs = stm.executeQuery("select idDiskusija, tekstDiskusija, datumPostavljanja, autorDiskusije, nivoVidljivosti, nazivKategorije, nazivDiskusije, arhiviranaDiskusija from diskusija d, vidljivost v, kategorije k where d.idDiskusija=" + id + " && d.idVidljivost=v.idVidljivost && d.idKategorije=k.idKategorije");
            
            while(rs.next()){
                diskusija = new Diskusija();
                diskusija.setIdDiskusija(rs.getInt("idDiskusija"));
                diskusija.setNazivDiskusije(rs.getString("nazivDiskusije"));
                diskusija.setTekstDiskusije(rs.getString("tekstDiskusija"));
                diskusija.setAutorDiskusije(rs.getString("autorDiskusije"));
                diskusija.setArhiviranaDiskusija(rs.getString("arhiviranaDiskusija"));
                diskusija.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                diskusija.setKategorijaDiskusije(rs.getString("nazivKategorije"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumPostavljanja").toString(), datePattern);
                diskusija.setDatumPostavljanja(rs.getTimestamp("datumPostavljanja").toString());
                diskusija.setDatumPostavljanjaDiskusije(datumPostavljanja);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajKomentareZaDiskusiju.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void ucitajAnketu(){
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            
            int id = Integer.parseInt(params.get("idAnkete"));
            String tipUcesnika = params.get("tipUcesnika");
            
            ResultSet rs = stm.executeQuery("select idAnkete, tekstAnkete, datumTrajanjaAnkete, autorAnkete, nivoVidljivosti, nazivAnkete, arhiviranaAnketa, vidljivostRezultata from ankete a, vidljivost v where a.idAnkete=" + id + " && a.idVidljivost=v.idVidljivost");
            
            while(rs.next()){
                anketa = new Anketa();
                anketa.setIdAnketa(rs.getInt("idAnkete"));
                anketa.setNazivAnkete(rs.getString("nazivAnkete"));
                anketa.setTekstAnkete(rs.getString("tekstAnkete"));
                anketa.setAutorAnkete(rs.getString("autorAnkete"));
                anketa.setArhiviranaAnketa(rs.getString("arhiviranaAnketa"));
                anketa.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                anketa.setVidljivostRezultata(rs.getString("vidljivostRezultata"));
                anketa.setTipUcesnika(tipUcesnika);
                
                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumTrajanjaAnkete = LocalDateTime.parse(rs.getTimestamp("datumTrajanjaAnkete").toString(), datePattern);

                anketa.setDatumTrajanjaAnkete(datumTrajanjaAnkete);
                anketa.setDatumTrajanja(rs.getTimestamp("datumTrajanjaAnkete").toString());
            }
            
            ResultSet pitanja = stm.executeQuery("select * from pitanjaAnkete where idAnkete=" + id);
            HashMap<Integer, String> listaPitanja = new HashMap<>();
            
            while(pitanja.next()){
                listaPitanja.put(pitanja.getInt("redniBrojPitanja"), pitanja.getString("pitanje"));
            }
           
            anketa.setListaPitanjaAnkete(listaPitanja);
        } catch (SQLException ex) {
            Logger.getLogger(UcitajKomentareZaDiskusiju.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ucitajObavestenje(){
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            
            int id = Integer.parseInt(params.get("idObavestenja"));
            
            ResultSet rs = stm.executeQuery("select idObavestenja, tekstObavestenja, datumKreiranjaObavestenja, autorObavestenja, nivoVidljivosti, naslovObavestenja, arhiviranoObavestenje from obavestenja o, vidljivost v where o.idObavestenja=" + id + " && o.idVidljivost=v.idVidljivost");            
            while(rs.next()){
                obavestenje = new Obavestenje();
                obavestenje.setIdObavestenja(rs.getInt("idObavestenja"));
                obavestenje.setNaslovObavestenja(rs.getString("naslovObavestenja"));
                obavestenje.setTekstOvavestenja(rs.getString("tekstObavestenja"));
                obavestenje.setAutorObavestenja(rs.getString("autorObavestenja"));
                obavestenje.setArhiviranoObavestenje(rs.getString("arhiviroObavestenje"));
                obavestenje.setNivoVidljivosti(rs.getString("nivoVidljivosti"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumKreiranjaObavestenja = LocalDateTime.parse(rs.getTimestamp("datumKreiranjaObavestenja").toString(), datePattern);
                obavestenje.setDatumKreiranja(rs.getTimestamp("datumKreiranjaObavestenja").toString());
                obavestenje.setDatumKreiranjaObavestenja(datumKreiranjaObavestenja);

            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajKomentareZaDiskusiju.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ucitajOglas(){
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            
            int id = Integer.parseInt(params.get("idOglasi"));
            
            ResultSet rs = stm.executeQuery("select idOglasa, tekstOglasa, datumPostavljanja, datumIsticanja, autorOglasa, nivoVidljivosti, naslovOglasa, arhiviraniOglasi from Oglasi o, vidljivost v where o.idOglasa=" + id + " && o.idVidljivost=v.idVidljivost");            
            while(rs.next()){
                oglas = new Oglas();
                oglas.setIdOglasi(rs.getInt("idOglasi"));
                oglas.setNaslovOglasa(rs.getString("naslovOglasa"));
                oglas.setTekstOglasa(rs.getString("tekstOglasa"));
                oglas.setAutorOglasa(rs.getString("autorOglasa"));
                oglas.setArhiviraniOglasi(rs.getString("arhiviriOglasi"));
                oglas.setNivoVidljivosti(rs.getString("nivoVidljivosti"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumPostavljanja").toString(), datePattern);
                LocalDateTime datumIsticanja = LocalDateTime.parse(rs.getTimestamp("datumIsticanja").toString(), datePattern);
                oglas.setDatumPostavljanjaOglasa(rs.getTimestamp("datumPostavljanja").toString());
                oglas.setDatumIsticanjaOglasa(rs.getTimestamp("datumIsticanja").toString());
                oglas.setDatumPostavljanja(datumPostavljanja);
                oglas.setDatumIsticanja(datumIsticanja);

            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajKomentareZaDiskusiju.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ucitajVest(){
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            
            int id = Integer.parseInt(params.get("idVesti"));
            
            ResultSet rs = stm.executeQuery("select v.idVesti, tekstVesti, datumPostavljanjaVesti, autorVesti, nivoVidljivosti, naslovVesti, arhiviranaVest, nazivKategorije from Vesti v, vidljivost vv, kategorije k where v.idVesti=" + id + " && vv.idVidljivost=v.idVidljivost && v.idKategorije=k.idKategorije");
            
            while(rs.next()){
                vest = new Vest();
                vest.setIdVesti(rs.getInt("idVesti"));
                vest.setNaslovVesti(rs.getString("naslovVesti"));
                vest.setTekstVesti(rs.getString("tekstVesti"));
                vest.setAutorVesti(rs.getString("autorVesti"));
                vest.setArhiviranaVest(rs.getString("arhiviranaVest"));
                vest.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                vest.setNazivKategorije(rs.getString("nazivKategorije"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumPostavljanjaVesti = LocalDateTime.parse(rs.getTimestamp("datumPostavljanjaVesti").toString(), datePattern);
                vest.setDatumPostavljanja(rs.getTimestamp("datumPostavljanjaVesti").toString());
                vest.setDatumPostavljanjaVesti(datumPostavljanjaVesti);

            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajKomentareZaDiskusiju.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ucitajPreporuku(){
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            
            int id = Integer.parseInt(params.get("idPreporuke"));
            
            ResultSet rs = stm.executeQuery("select idPreporuke, tekstPreporuke, datumKreiranjaPreporuke, autorPreporuke, nivoVidljivosti, nazivPreporuke from Preporuke p, vidljivost v where p.idPreporuke=" + id + " && p.idVidljivost=v.idVidljivost");            
            while(rs.next()){
                preporuka = new Preporuka();
                preporuka.setIdPreporuke(rs.getInt("idPreporuke"));
                preporuka.setNazivPreporuke(rs.getString("nazivPreporuke"));
                preporuka.setTekstPreporuke(rs.getString("tekstPreporuke"));
                preporuka.setAutorPreporuke(rs.getString("autorPreporuke"));
                preporuka.setNivoVidljivosti(rs.getString("nivoVidljivosti"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumKreiranjaPreporuke = LocalDateTime.parse(rs.getTimestamp("datumKreiranjaPreporuke").toString(), datePattern);
                preporuka.setDatumKreiranja(rs.getTimestamp("datumKreiranjaPreporuke").toString());
                preporuka.setDatumKreiranjaPreporuke(datumKreiranjaPreporuke);

            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajKomentareZaDiskusiju.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
