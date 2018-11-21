package controllers;

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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@ViewScoped
public class PopunjavanjeAnkete {

    private String poruka;
    private List<String> listaOdgovoraNaPitanjaAnkete;
    private int idAnkete;

    public List<String> getListaOdgovoraNaPitanjaAnkete() {
        return listaOdgovoraNaPitanjaAnkete;
    }

    public void setListaOdgovoraNaPitanjaAnkete(List<String> listaOdgovoraNaPitanjaAnkete) {
        this.listaOdgovoraNaPitanjaAnkete = listaOdgovoraNaPitanjaAnkete;
    }
    
    public void ucitajOdgovore(){
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

            int idAnkete1 = Integer.parseInt(params.get("idAnkete"));
            idAnkete=idAnkete1;
            ResultSet rs = stm.executeQuery("select * from pitanjaankete where idAnkete=" + idAnkete1);
            listaOdgovoraNaPitanjaAnkete = new ArrayList<>();
            while(rs.next()){
            listaOdgovoraNaPitanjaAnkete.add("");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PopunjavanjeAnkete.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String popuniAnketu() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            
            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");
            
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
                
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();


            
            ResultSet rs = stm.executeQuery("select idKorisnik from korisnik where korisnickoIme='" + korisnik.getKorisnickoIme() + "'");
            rs.next();
            int idKorisnik=rs.getInt("idKorisnik");
            
            ResultSet pitanja = stm.executeQuery("select idPitanjaAnkete from pitanjaAnkete where idAnkete=" + idAnkete + " order by idPitanjaAnkete");
            
            ArrayList<Integer> idPitanjaAnkete = new ArrayList<>();
            
            while(pitanja.next()){
                idPitanjaAnkete.add(pitanja.getInt("idPitanjaAnkete"));
            }
            
            for (int i = 0; i < idPitanjaAnkete.size(); i++) {
                stm.executeUpdate("insert into odgovoriankete(idKorisnik, idAnkete, odgovoriAnkete, idPitanjaAnkete) values (" + idKorisnik + ", " + idAnkete + ", '" + listaOdgovoraNaPitanjaAnkete.get(i) + "', " + idPitanjaAnkete.get(i) + ")");
            }
            
            poruka = "Uspesno popunjena anketa";
            FacesContext.getCurrentInstance().addMessage("porukaPopunjavanjeAnkete", message);
            
            return "prikazSvihAnketa?faces-redirect=true";
        } catch (SQLException ex) {
            Logger.getLogger(PopunjavanjeAnkete.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }
}
