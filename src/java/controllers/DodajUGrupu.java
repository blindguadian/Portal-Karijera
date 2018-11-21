package controllers;

import beans.Korisnik;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class DodajUGrupu {

    private String poruka, nazivGrupe;
    private String[] listaGrupa;

    public String getNazivGrupe() {
        return nazivGrupe;
    }

    public void setNazivGrupe(String nazivGrupe) {
        this.nazivGrupe = nazivGrupe;
    }

    public String[] getListaGrupa() {
        return listaGrupa;
    }

    public void setListaGrupa(String[] listaGrupa) {
        this.listaGrupa = listaGrupa;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public void dodajClana(Korisnik k) {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery("select idKorisnik from grupa g, korisnik k, uGrupi ug where k.idKorisnik=ug.idKorisnik && g.nazivGrupe='" + nazivGrupe + "' && k.korisnickoIme='" + k.getKorisnickoIme() + "'");
            
            if (rs.next()) {
                poruka = "Ovaj korisnik je vec clan grupe";

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
                FacesContext.getCurrentInstance().addMessage("porukaDodajClana", message);
                
            }else{
                
                stm.executeUpdate("insert into uGrupi values ((select idKorisnik from korisnik k where k.korisnickoIme='" + k.getKorisnickoIme() + "'), (select idGrupa from grupa g where nazivGrupe='" + nazivGrupe + "'), 'pozvan')");
                
                poruka = "Korisniku je poslat poziv za uclanjenje u grupu";
            }
        } catch (SQLException ex) {
            Logger.getLogger(DodajUGrupu.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
