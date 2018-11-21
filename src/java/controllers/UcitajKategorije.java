
package controllers;

import beans.Kategorija;
import beans.Korisnik;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class UcitajKategorije {
    private List<Kategorija> listaSvihKategorija;

    public List<Kategorija> getListaSvihKategorija() {
        return listaSvihKategorija;
    }

    public void setListaSvihKategorija(List<Kategorija> listaSvihKategorija) {
        this.listaSvihKategorija = listaSvihKategorija;
    }
    
    public void ucitajSveKategorije(){
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");
        
            ResultSet rs = stm.executeQuery("select idKategorije, nazivKategorije from kategorije");
            
            listaSvihKategorija = new ArrayList<>();
            
            while(rs.next()){
                Kategorija k = new Kategorija();
                k.setIdKategorije(rs.getInt("idKategorije"));
                k.setNazivKategorije(rs.getString("nazivKategorije"));
                
                listaSvihKategorija.add(k);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajKategorije.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
