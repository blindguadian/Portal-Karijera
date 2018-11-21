package controllers;

import beans.Grupa;
import beans.Korisnik;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public class UcitajGrupeStudenata {

    private List<Grupa> listaSvihGrupa, listaGrupaGdeJeKorisnikClan;
    private List<Korisnik> listaSvihClanovaGrupe;

    public List<Grupa> getListaSvihGrupa() {
        return listaSvihGrupa;
    }

    public void setListaSvihGrupa(List<Grupa> listaSvihGrupa) {
        this.listaSvihGrupa = listaSvihGrupa;
    }

    public List<Grupa> getListaGrupaGdeJeKorisnikClan() {
        return listaGrupaGdeJeKorisnikClan;
    }

    public void setListaGrupaGdeJeKorisnikClan(List<Grupa> listaGrupaGdeJeKorisnikClan) {
        this.listaGrupaGdeJeKorisnikClan = listaGrupaGdeJeKorisnikClan;
    }

    public void sveGrupeStudenata() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("select idGrupa, nazivGrupe, nivoVidljivosti from grupa g, vidljivost v where v.idVidljivost=g.idVidljivost");

            listaSvihGrupa = new ArrayList<>();

            while (rs.next()) {
                Grupa g = new Grupa();
                g.setIdGrupa(rs.getInt("idGrupa"));
                g.setNazivGrupe(rs.getString("nazivGrupe"));
                g.setNivoVidljivosti(rs.getString("nivoVidljivosti"));

                listaSvihGrupa.add(g);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UcitajGrupeStudenata.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sveGrupeGdeJeKorisnikClan() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            ResultSet rs = stm.executeQuery("select g.idGrupa, nazivGrupe, nivoVidljivosti from grupa g, uGrupi ug, vidljivost v, korisnik k where g.idGrupa=ug.idGrupa && k.idKorisnik=ug.idKorisnik && k.korisnickoIme='" + korisnik.getKorisnickoIme() + "' && g.idVidljivost=v.idVidljivost");

            listaGrupaGdeJeKorisnikClan = new ArrayList<>();

            while (rs.next()) {
                Grupa g = new Grupa();
                g.setIdGrupa(rs.getInt("idGrupa"));
                g.setNazivGrupe(rs.getString("nazivGrupe"));
                g.setNivoVidljivosti(rs.getString("nivoVidljivosti"));

                listaGrupaGdeJeKorisnikClan.add(g);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UcitajGrupeStudenata.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajGrupu() {
        try {
            Connection conn = DriverManager.getConnection(db.db.user, db.db.password, db.db.connectionString);
            Statement stm = conn.createStatement();
            
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            
            int id = Integer.parseInt(params.get("id"));
            
            ResultSet rs = stm.executeQuery("(select javnoIme from uGrupi ug, student s where ug.idGrupa=" + id + " && s.idKorisnik=ug.idKorisnik) union all (select javnoIme from uGrupi ug, kompanija kom where ug.idGrupa=" + id + " && kom.idKorisnik=ug.idKorisnik)");
            
            listaSvihClanovaGrupe = new ArrayList<>();
            
            while(rs.next()){
                Korisnik k = new Korisnik();
                k.setJavnoIme(rs.getString("javnoIme"));
                
                listaSvihClanovaGrupe.add(k);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajGrupeStudenata.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
