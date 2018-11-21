package controllers;

import beans.Komentar;
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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class UcitajKomentareZaDiskusiju {

    private List<Komentar> listaKomentara;

    public List<Komentar> getListaKomentara() {
        return listaKomentara;
    }

    public void setListaKomentara(List<Komentar> listaKomentara) {
        this.listaKomentara = listaKomentara;
    }
    
    public void ucitajKomentare() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            
            int id = Integer.parseInt(params.get("idDiskusija"));
            
            ResultSet rs = stm.executeQuery("(select idKomentari, tekstKomentara, idDiskusija, datumKreiranjaKomentara, k.idKorisnik, s.javnoIme from komentari k, student s where idDiskusija=" + id + " && s.idKorisnik=k.idKorisnik order by datumKreiranjaKomentara DESC) UNION ALL (select idKomentari, tekstKomentara, idDiskusija, datumKreiranjaKomentara, k.idKorisnik, kom.javnoIme from komentari k, kompanija kom where idDiskusija=" + id + " && kom.idKorisnik=k.idKorisnik order by datumKreiranjaKomentara DESC)");
            listaKomentara = new ArrayList<>();
            while(rs.next()){
                Komentar k = new Komentar();
                k.setTekstKomentara(rs.getString("tekstKomentara"));
                k.setIdDiskusija(rs.getInt("idDiskusija"));
                k.setIdKomentari(rs.getInt("idKomentari"));
                k.setIdKorisnik(rs.getInt("idKorisnik"));
                k.setJavnoImeAutora(rs.getString("javnoIme"));
                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumKreiranjaKomentara = LocalDateTime.parse(rs.getTimestamp("datumKreiranjaKomentara").toString(), datePattern);
                k.setDatumKreiranja(rs.getTimestamp("datumKreiranjaKomentara").toString());
                k.setDatumKreiranjaKomentara(datumKreiranjaKomentara);
                
                listaKomentara.add(k);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UcitajKomentareZaDiskusiju.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
