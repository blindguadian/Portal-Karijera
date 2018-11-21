package controllers;

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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean
@SessionScoped
public class OtvoriGrupu {

    private String nazivGrupe;
    private List<Korisnik> listaClanova;

    public List<Korisnik> getListaClanova() {
        return listaClanova;
    }

    public void setListaClanova(List<Korisnik> listaClanova) {
        this.listaClanova = listaClanova;
    }

    public String getNazivGrupe() {
        return nazivGrupe;
    }

    public void setNazivGrupe(String nazivGrupe) {
        this.nazivGrupe = nazivGrupe;
    }

    public void podaciOGrupi() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            listaClanova = new ArrayList<>();

            ResultSet student = stm.executeQuery("select ime, prezime, javnoIme, imeVidljivost, prezimeVidljivost, korisnickoIme, drugClan from student s, korisnik k, grupa g, ugrupi u where nazivGrupe='" + nazivGrupe + "' && g.idGrupa=u.idGrupa && k.idKorisnik=u.idKorisnik && k.idKorisnik=s.idKorisnik");

            while (student.next()) {
                Korisnik k = new Korisnik();
                k.setKorisnickoIme(student.getString("korisnickoIme"));
                k.setIme(student.getString("ime"));
                k.setPrezime(student.getString("prezime"));
                k.setJavnoIme(student.getString("javnoIme"));
                k.setImeVidljivost(student.getString("imeVidljivost"));
                k.setPrezimeVidljivost(student.getString("prezimeVidljivost"));
                listaClanova.add(k);
            }
            
            ResultSet kompanija = stm.executeQuery("select nazivKompanije, javnoIme, korisnickoIme, drugClan from sifKompanija sk, kompanija kom, korisnik k, grupa g, ugrupi u where nazivGrupe='" + nazivGrupe + "' && g.idGrupa=u.idGrupa && k.idKorisnik=u.idKorisnik && k.idKorisnik=kom.idKorisnik && sk.idSifKompanija=kom.idSifKompanija");
            
            while(kompanija.next()){
                Korisnik k = new Korisnik();
                k.setKorisnickoIme(kompanija.getString("korisnickoIme"));
                k.setNazivKompanije(kompanija.getString("nazivKompanije"));
                k.setJavnoIme(kompanija.getString("javnoIme"));
                listaClanova.add(k);
            }


        } catch (SQLException ex) {
            Logger.getLogger(OtvoriGrupu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
