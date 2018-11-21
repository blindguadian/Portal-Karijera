/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Korisnik;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Amph
 */

@ManagedBean
@SessionScoped
public class Arhiviranje {
    private String poruka;
    
    public void arhivirajVest(){
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");
            
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
            
            int idVesti = Integer.parseInt(params.get("idVesti"));
            
            ResultSet rs = stm.executeQuery("select arhiviranaVest, zahtevZaBrisanjeVesti from Vesti v, ucestvuje_kreira uk, korisnik k, " + korisnik.getUloga() + " x where k.korisnickoIme='" + korisnik.getKorisnickoIme() + "' && k.idKorisnik=uk.idKorisnik && v.idUvestvuje_kreira=uk.idUcestvuje_kreira && v.idVesti=" + idVesti + " && uk.tipUcesnika='kreator'");
            while(rs.next()){
                if(rs.getString("arhiviranaVest").equals("true")){
                    poruka = "Ova vest je vec arhivirana";  
                }else if(rs.getString("zahtevZaBrisanjeVesti").equals("true")){
                    poruka = "Zahtev za brisanje ove vesti je vec prosledjen";  
                }else{
                    stm.executeUpdate("update vesti set arhiviranaVest='true' where idVesti=" + idVesti);
                    poruka = "Vest je arhivirana";
                }
            }
            FacesContext.getCurrentInstance().addMessage("porukaArhiviranjeVesti", message);
        } catch (SQLException ex) {
            Logger.getLogger(Arhiviranje.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void arhivirajDiskusiju(){
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");
            
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
            
            int idDiskusija = Integer.parseInt(params.get("idDiskusija"));
            
            ResultSet rs = stm.executeQuery("select arhiviranaDiskusija, zahtevZaBrisanjeDiskusije from diskusija d, ucestvuje_kreira uk, korisnik k, " + korisnik.getUloga() + " x where k.korisnickoIme='" + korisnik.getKorisnickoIme() + "' && k.idKorisnik=uk.idKorisnik && uk.idUcestvuje_kreira=d.idUcestvuje_kreira && d.idDiskusija=" + idDiskusija + " && uk.tipUcesnika='kreator'");
            while(rs.next()){
                if(rs.getString("arhiviranaDiskusija").equals("true")){
                    poruka = "Ova diskusija je vec arhivirana";  
                }else if(rs.getString("zahtevZaBrisanjeDiskusije").equals("true")){
                    poruka = "Zahtev za brisanje ove diskusije je vec prosledjen";  
                }else{
                    stm.executeUpdate("update diskusija set arhiviranaDiskusija='true' where idDiskusija=" + idDiskusija);
                    poruka = "Diskusija je arhivirana";
                }
            }
            FacesContext.getCurrentInstance().addMessage("porukaArhiviranjeVesti", message);
        } catch (SQLException ex) {
            Logger.getLogger(Arhiviranje.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void arhivirajObavestenje(){
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");
            
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
            
            int idObavestenja = Integer.parseInt(params.get("idObavestenja"));
            
            ResultSet rs = stm.executeQuery("select arhiviranoObavestenje, zahtevZaBrisanjeObavestenja from obavestenja o, ucestvuje_kreira uk, korisnik k, " + korisnik.getUloga() + " x where k.korisnickoIme='" + korisnik.getKorisnickoIme() + "' && k.idKorisnik=uk.idKorisnik && uk.idUcestvuje_kreira=o.idUcestvuje_kreira && o.idObavestenja=" + idObavestenja + " && uk.tipUcesnika='kreator'");
            while(rs.next()){
                if(rs.getString("arhiviranoObavestenje").equals("true")){
                    poruka = "Ovo obavestenje je vec arhivirano";  
                }else if(rs.getString("zahtevZaBrisanjeObavestenja").equals("true")){
                    poruka = "Zahtev za brisanje ovog obavestenja je vec prosledjen";  
                }else{
                    stm.executeUpdate("update obavestenje set arhiviranoObavestenje='true' where idObavestenja=" + idObavestenja);
                    poruka = "Obavestenje je arhivirano";
                }
            }
            FacesContext.getCurrentInstance().addMessage("porukaArhiviranjeObavestenja", message);
        } catch (SQLException ex) {
            Logger.getLogger(Arhiviranje.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*Arhivira oglase ako je danasnji datum jednak datumu isteka oglasa*/
    public void automatskoArhiviranjeOglasa(){
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            
            DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
            LocalDateTime sada = LocalDateTime.now();
            
            ArrayList<Integer> listaOglasaZaArhiviranje = new ArrayList<>();
            
            ResultSet rs = stm.executeQuery("select datumIsticanja, idOglasa from oglasi");
            
            while(rs.next()){
                LocalDateTime datumIsticanjaOglasa = LocalDateTime.parse(rs.getTimestamp("datumIsticanja").toString(), datePattern);
                if(sada.isAfter(datumIsticanjaOglasa)){
                    listaOglasaZaArhiviranje.add(rs.getInt("idOglasa"));
                }
            }
            
            for(int i=0; i<listaOglasaZaArhiviranje.size(); i++){
                stm.executeUpdate("update oglasi set arhiviraniOglasi='true' where idOglasa=" + listaOglasaZaArhiviranje.get(i));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Arhiviranje.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
