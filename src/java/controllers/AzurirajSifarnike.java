package controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class AzurirajSifarnike {

    private String poruka, sifarnikDrzava, sifarnikDrzavljanstava, sifarnikFakulteta, sifarnikKompanija, sifarnikMesta, sifarnikOblastiInteresovanja, sifarnikPozicija, sifarnikProfesionalnihVestina, sifarnikUlica, sifarnikUniverziteta, staraVrednostSifarnikDrzava, staraVrednostSifarnikDrzavljanstava, staraVrednostSifarnikFakulteta, staraVrednostSifarnikKompanija, staraVrednostSifarnikMesta, staraVrednostSifarnikOblastiInteresovanja, staraVrednostSifarnikPozicija, staraVrednostSifarnikProfesionalnihVestina, staraVrednostSifarnikUlica, staraVrednostSifarnikUniverziteta, novaVrednostSifarnikDrzava, novaVrednostSifarnikDrzavljanstava, novaVrednostSifarnikFakulteta, novaVrednostSifarnikKompanija, novaVrednostSifarnikMesta, novaVrednostSifarnikOblastiInteresovanja, novaVrednostSifarnikPozicija, novaVrednostSifarnikProfesionalnihVestina, novaVrednostSifarnikUlica, novaVrednostSifarnikUniverziteta;
    private List<String> listaDrzava, listaDrzavljanstava, listaFakulteta, listaKompanija, listaMesta, listaOblastiInteresovanja, listaPozicija, listaProfesionalnihVestina, listaUlica, listaUniverziteta;

    public String getStaraVrednostSifarnikDrzava() {
        return staraVrednostSifarnikDrzava;
    }

    public void setStaraVrednostSifarnikDrzava(String staraVrednostSifarnikDrzava) {
        this.staraVrednostSifarnikDrzava = staraVrednostSifarnikDrzava;
    }

    public String getStaraVrednostSifarnikDrzavljanstava() {
        return staraVrednostSifarnikDrzavljanstava;
    }

    public void setStaraVrednostSifarnikDrzavljanstava(String staraVrednostSifarnikDrzavljanstava) {
        this.staraVrednostSifarnikDrzavljanstava = staraVrednostSifarnikDrzavljanstava;
    }

    public String getStaraVrednostSifarnikFakulteta() {
        return staraVrednostSifarnikFakulteta;
    }

    public void setStaraVrednostSifarnikFakulteta(String staraVrednostSifarnikFakulteta) {
        this.staraVrednostSifarnikFakulteta = staraVrednostSifarnikFakulteta;
    }

    public String getStaraVrednostSifarnikKompanija() {
        return staraVrednostSifarnikKompanija;
    }

    public void setStaraVrednostSifarnikKompanija(String staraVrednostSifarnikKompanija) {
        this.staraVrednostSifarnikKompanija = staraVrednostSifarnikKompanija;
    }

    public String getStaraVrednostSifarnikMesta() {
        return staraVrednostSifarnikMesta;
    }

    public void setStaraVrednostSifarnikMesta(String staraVrednostSifarnikMesta) {
        this.staraVrednostSifarnikMesta = staraVrednostSifarnikMesta;
    }

    public String getStaraVrednostSifarnikOblastiInteresovanja() {
        return staraVrednostSifarnikOblastiInteresovanja;
    }

    public void setStaraVrednostSifarnikOblastiInteresovanja(String staraVrednostSifarnikOblastiInteresovanja) {
        this.staraVrednostSifarnikOblastiInteresovanja = staraVrednostSifarnikOblastiInteresovanja;
    }

    public String getStaraVrednostSifarnikPozicija() {
        return staraVrednostSifarnikPozicija;
    }

    public void setStaraVrednostSifarnikPozicija(String staraVrednostSifarnikPozicija) {
        this.staraVrednostSifarnikPozicija = staraVrednostSifarnikPozicija;
    }

    public String getStaraVrednostSifarnikProfesionalnihVestina() {
        return staraVrednostSifarnikProfesionalnihVestina;
    }

    public void setStaraVrednostSifarnikProfesionalnihVestina(String staraVrednostSifarnikProfesionalnihVestina) {
        this.staraVrednostSifarnikProfesionalnihVestina = staraVrednostSifarnikProfesionalnihVestina;
    }

    public String getStaraVrednostSifarnikUlica() {
        return staraVrednostSifarnikUlica;
    }

    public void setStaraVrednostSifarnikUlica(String staraVrednostSifarnikUlica) {
        this.staraVrednostSifarnikUlica = staraVrednostSifarnikUlica;
    }

    public String getStaraVrednostSifarnikUniverziteta() {
        return staraVrednostSifarnikUniverziteta;
    }

    public void setStaraVrednostSifarnikUniverziteta(String staraVrednostSifarnikUniverziteta) {
        this.staraVrednostSifarnikUniverziteta = staraVrednostSifarnikUniverziteta;
    }

    public String getNovaVrednostSifarnikDrzava() {
        return novaVrednostSifarnikDrzava;
    }

    public void setNovaVrednostSifarnikDrzava(String novaVrednostSifarnikDrzava) {
        this.novaVrednostSifarnikDrzava = novaVrednostSifarnikDrzava;
    }

    public String getNovaVrednostSifarnikDrzavljanstava() {
        return novaVrednostSifarnikDrzavljanstava;
    }

    public void setNovaVrednostSifarnikDrzavljanstava(String novaVrednostSifarnikDrzavljanstava) {
        this.novaVrednostSifarnikDrzavljanstava = novaVrednostSifarnikDrzavljanstava;
    }

    public String getNovaVrednostSifarnikFakulteta() {
        return novaVrednostSifarnikFakulteta;
    }

    public void setNovaVrednostSifarnikFakulteta(String novaVrednostSifarnikFakulteta) {
        this.novaVrednostSifarnikFakulteta = novaVrednostSifarnikFakulteta;
    }

    public String getNovaVrednostSifarnikKompanija() {
        return novaVrednostSifarnikKompanija;
    }

    public void setNovaVrednostSifarnikKompanija(String novaVrednostSifarnikKompanija) {
        this.novaVrednostSifarnikKompanija = novaVrednostSifarnikKompanija;
    }

    public String getNovaVrednostSifarnikMesta() {
        return novaVrednostSifarnikMesta;
    }

    public void setNovaVrednostSifarnikMesta(String novaVrednostSifarnikMesta) {
        this.novaVrednostSifarnikMesta = novaVrednostSifarnikMesta;
    }

    public String getNovaVrednostSifarnikOblastiInteresovanja() {
        return novaVrednostSifarnikOblastiInteresovanja;
    }

    public void setNovaVrednostSifarnikOblastiInteresovanja(String novaVrednostSifarnikOblastiInteresovanja) {
        this.novaVrednostSifarnikOblastiInteresovanja = novaVrednostSifarnikOblastiInteresovanja;
    }

    public String getNovaVrednostSifarnikPozicija() {
        return novaVrednostSifarnikPozicija;
    }

    public void setNovaVrednostSifarnikPozicija(String novaVrednostSifarnikPozicija) {
        this.novaVrednostSifarnikPozicija = novaVrednostSifarnikPozicija;
    }

    public String getNovaVrednostSifarnikProfesionalnihVestina() {
        return novaVrednostSifarnikProfesionalnihVestina;
    }

    public void setNovaVrednostSifarnikProfesionalnihVestina(String novaVrednostSifarnikProfesionalnihVestina) {
        this.novaVrednostSifarnikProfesionalnihVestina = novaVrednostSifarnikProfesionalnihVestina;
    }

    public String getNovaVrednostSifarnikUlica() {
        return novaVrednostSifarnikUlica;
    }

    public void setNovaVrednostSifarnikUlica(String novaVrednostSifarnikUlica) {
        this.novaVrednostSifarnikUlica = novaVrednostSifarnikUlica;
    }

    public String getNovaVrednostSifarnikUniverziteta() {
        return novaVrednostSifarnikUniverziteta;
    }

    public void setNovaVrednostSifarnikUniverziteta(String novaVrednostSifarnikUniverziteta) {
        this.novaVrednostSifarnikUniverziteta = novaVrednostSifarnikUniverziteta;
    }
    
    public String getSifarnikDrzava() {
        return sifarnikDrzava;
    }

    public void setSifarnikDrzava(String sifarnikDrzava) {
        this.sifarnikDrzava = sifarnikDrzava;
    }

    public String getSifarnikDrzavljanstava() {
        return sifarnikDrzavljanstava;
    }

    public void setSifarnikDrzavljanstava(String sifarnikDrzavljanstava) {
        this.sifarnikDrzavljanstava = sifarnikDrzavljanstava;
    }

    public String getSifarnikFakulteta() {
        return sifarnikFakulteta;
    }

    public void setSifarnikFakulteta(String sifarnikFakulteta) {
        this.sifarnikFakulteta = sifarnikFakulteta;
    }

    public String getSifarnikKompanija() {
        return sifarnikKompanija;
    }

    public void setSifarnikKompanija(String sifarnikKompanija) {
        this.sifarnikKompanija = sifarnikKompanija;
    }

    public String getSifarnikMesta() {
        return sifarnikMesta;
    }

    public void setSifarnikMesta(String sifarnikMesta) {
        this.sifarnikMesta = sifarnikMesta;
    }

    public String getSifarnikOblastiInteresovanja() {
        return sifarnikOblastiInteresovanja;
    }

    public void setSifarnikOblastiInteresovanja(String sifarnikOblastiInteresovanja) {
        this.sifarnikOblastiInteresovanja = sifarnikOblastiInteresovanja;
    }

    public String getSifarnikPozicija() {
        return sifarnikPozicija;
    }

    public void setSifarnikPozicija(String sifarnikPozicija) {
        this.sifarnikPozicija = sifarnikPozicija;
    }

    public String getSifarnikProfesionalnihVestina() {
        return sifarnikProfesionalnihVestina;
    }

    public void setSifarnikProfesionalnihVestina(String sifarnikProfesionalnihVestina) {
        this.sifarnikProfesionalnihVestina = sifarnikProfesionalnihVestina;
    }

    public String getSifarnikUlica() {
        return sifarnikUlica;
    }

    public void setSifarnikUlica(String sifarnikUlica) {
        this.sifarnikUlica = sifarnikUlica;
    }

    public String getSifarnikUniverziteta() {
        return sifarnikUniverziteta;
    }

    public void setSifarnikUniverziteta(String sifarnikUniverziteta) {
        this.sifarnikUniverziteta = sifarnikUniverziteta;
    }

    public List<String> getListaDrzava() {
        return listaDrzava;
    }

    public void setListaDrzava(List<String> listaDrzava) {
        this.listaDrzava = listaDrzava;
    }

    public List<String> getListaDrzavljanstava() {
        return listaDrzavljanstava;
    }

    public void setListaDrzavljanstava(List<String> listaDrzavljanstava) {
        this.listaDrzavljanstava = listaDrzavljanstava;
    }

    public List<String> getListaFakulteta() {
        return listaFakulteta;
    }

    public void setListaFakulteta(List<String> listaFakulteta) {
        this.listaFakulteta = listaFakulteta;
    }

    public List<String> getListaKompanija() {
        return listaKompanija;
    }

    public void setListaKompanija(List<String> listaKompanija) {
        this.listaKompanija = listaKompanija;
    }

    public List<String> getListaMesta() {
        return listaMesta;
    }

    public void setListaMesta(List<String> listaMesta) {
        this.listaMesta = listaMesta;
    }

    public List<String> getListaOblastiInteresovanja() {
        return listaOblastiInteresovanja;
    }

    public void setListaOblastiInteresovanja(List<String> listaOblastiInteresovanja) {
        this.listaOblastiInteresovanja = listaOblastiInteresovanja;
    }

    public List<String> getListaPozicija() {
        return listaPozicija;
    }

    public void setListaPozicija(List<String> listaPozicija) {
        this.listaPozicija = listaPozicija;
    }

    public List<String> getListaProfesionalnihVestina() {
        return listaProfesionalnihVestina;
    }

    public void setListaProfesionalnihVestina(List<String> listaProfesionalnihVestina) {
        this.listaProfesionalnihVestina = listaProfesionalnihVestina;
    }

    public List<String> getListaUlica() {
        return listaUlica;
    }

    public void setListaUlica(List<String> listaUlica) {
        this.listaUlica = listaUlica;
    }

    public List<String> getListaUniverziteta() {
        return listaUniverziteta;
    }

    public void setListaUniverziteta(List<String> listaUniverziteta) {
        this.listaUniverziteta = listaUniverziteta;
    }
    
    public void dodajFakultet(String nazivUniverziteta) {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
            
            ResultSet rs = stm.executeQuery("select idSifUniverzitet from SifUniverzitet where nazivUniverziteta='" + nazivUniverziteta + "'");
            int idUniverzitet = rs.getInt("idSifUniverzitet");
            
            ResultSet ps = stm.executeQuery("select nazivFakulteta from sifFakulteta");
            while(ps.next()){
                if(ps.getString("nazivFakulteta").equalsIgnoreCase(sifarnikFakulteta)){
                    poruka = "Fakultet sa ovim nazivom vec postoji";
                    FacesContext.getCurrentInstance().addMessage("porukaDodavanjaFakulteta", message);
                    return;
                }
            }
            listaFakulteta.add(sifarnikFakulteta);
            stm.executeUpdate("insert into sifFakulteta (nazivFakulteta, idSifUniverzitet) values ('" + sifarnikFakulteta + "', " + idUniverzitet + ")");
            
            poruka = "Uspesno dodat fakultet";
            FacesContext.getCurrentInstance().addMessage("porukaDodavanjaFakulteta", message);
        } catch (SQLException ex) {
            Logger.getLogger(AzurirajSifarnike.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void dodajUniverzitet() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
            
            ResultSet ps = stm.executeQuery("select nazivUniverziteta from sifUniverzitet");
            while(ps.next()){
                if(ps.getString("nazivUniverziteta").equalsIgnoreCase(sifarnikUniverziteta)){
                    poruka = "Univerzitet sa ovim nazivom vec postoji";
                    FacesContext.getCurrentInstance().addMessage("porukaDodavanjaUniverziteta", message);
                    return;
                }
            }
            
            listaUniverziteta.add(sifarnikUniverziteta);
            stm.executeUpdate("insert into sifUniverzitet (nazivUniverziteta) values ('" + sifarnikUniverziteta + "')");
        
            poruka = "Uspesno dodat univerzitet";
            FacesContext.getCurrentInstance().addMessage("porukaDodavanjaUniverziteta", message);
        } catch (SQLException ex) {
            Logger.getLogger(AzurirajSifarnike.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void dodajDrzavu() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
            
            ResultSet ps = stm.executeQuery("select nazivDrzave from sifDrzava");
            while(ps.next()){
                if(ps.getString("nazivDrzave").equalsIgnoreCase(sifarnikDrzava)){
                    poruka = "Drzava sa ovim nazivom vec postoji";
                    FacesContext.getCurrentInstance().addMessage("porukaDodavanjaDrzave", message);
                    return;
                }
            }
            
            listaDrzava.add(sifarnikDrzava);
            stm.executeUpdate("insert into sifDrzava (nazivDrzave) values ('" + sifarnikDrzava + "')");
            
            poruka = "Uspesno dodata drzava";
            FacesContext.getCurrentInstance().addMessage("porukaDodavanjaDrzave", message);
        } catch (SQLException ex) {
            Logger.getLogger(AzurirajSifarnike.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void dodajDrzavljanstvo() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
            
            ResultSet ps = stm.executeQuery("select nazivDrzavljanstva from sifDrzavljanstva");
            while(ps.next()){
                if(ps.getString("nazivDrzavljanstva").equalsIgnoreCase(sifarnikDrzavljanstava)){
                    poruka = "Drzavljanstvo sa ovim nazivom vec postoji";
                    FacesContext.getCurrentInstance().addMessage("porukaDodavanjaDrzavljanstva", message);
                    return;
                }
            }
            
            listaDrzavljanstava.add(sifarnikDrzavljanstava);
            stm.executeUpdate("insert into sifDrzavljanstva (nazivDrzavljanstva) values ('" + sifarnikDrzavljanstava + "')");
        
            poruka = "Uspesno dodato drzavljanstvo";
            FacesContext.getCurrentInstance().addMessage("porukaDodavanjaDrzavljanstva", message);
        } catch (SQLException ex) {
            Logger.getLogger(AzurirajSifarnike.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void dodajKompaniju() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
            
            ResultSet ps = stm.executeQuery("select nazivKompanije from sifKompanija");
            while(ps.next()){
                if(ps.getString("nazivKompanije").equalsIgnoreCase(sifarnikKompanija)){
                    poruka = "Kompanija sa ovim nazivom vec postoji";
                    FacesContext.getCurrentInstance().addMessage("porukaDodavanjaKompanije", message);
                    return;
                }
            }
            
            listaKompanija.add(sifarnikKompanija);
            stm.executeUpdate("insert into sifKompanija (nazivKompanije) values ('" + sifarnikKompanija + "')");
            
            poruka = "Uspesno dodata kompanija";
            FacesContext.getCurrentInstance().addMessage("porukaDodavanjaKompanije", message);
        } catch (SQLException ex) {
            Logger.getLogger(AzurirajSifarnike.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void dodajMesto(String nazivDrzave) {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
            
            ResultSet ps = stm.executeQuery("select nazivMesta from sifMesta");
            while(ps.next()){
                if(ps.getString("nazivMesta").equalsIgnoreCase(sifarnikMesta)){
                    poruka = "Kompanija sa ovim nazivom vec postoji";
                    FacesContext.getCurrentInstance().addMessage("porukaDodavanjaMesta", message);
                    return;
                }
            }
            
            ResultSet rs = stm.executeQuery("select idSifDrzava from SifDrzava where nazivDrzave='" + nazivDrzave + "'");
            int idDrzave = rs.getInt("idSifDrzava");
            
            listaMesta.add(sifarnikMesta);
            stm.executeUpdate("insert into sifMesta (nazivMesta, idSifDrzava) values ('" + sifarnikMesta + "', " +idDrzave + ")");
        
            poruka = "Uspesno dodato mesto";
            FacesContext.getCurrentInstance().addMessage("porukaDodavanjaMesta", message);
        } catch (SQLException ex) {
            Logger.getLogger(AzurirajSifarnike.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void dodajOblastInteresovanja() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
            
            ResultSet ps = stm.executeQuery("select nazivInteresovanja from sifOblastInteresovanja");
            while(ps.next()){
                if(ps.getString("nazivInteresovanja").equalsIgnoreCase(sifarnikOblastiInteresovanja)){
                    poruka = "Interesovanje sa ovim nazivom vec postoji";
                    FacesContext.getCurrentInstance().addMessage("porukaDodavanjaInteresovanja", message);
                    return;
                }
            }
            
            listaOblastiInteresovanja.add(sifarnikOblastiInteresovanja);
            stm.executeUpdate("insert into sifOblastInteresovanja (nazivInteresovanja) values ('" + sifarnikOblastiInteresovanja + "')");
        
            poruka = "Uspesno dodato interesovanje";
            FacesContext.getCurrentInstance().addMessage("porukaDodavanjaInteresovanja", message);
        } catch (SQLException ex) {
            Logger.getLogger(AzurirajSifarnike.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void dodajPoziciju() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
            
            ResultSet ps = stm.executeQuery("select nazivPozicije from sifPozicija");
            while(ps.next()){
                if(ps.getString("nazivPozicije").equalsIgnoreCase(sifarnikPozicija)){
                    poruka = "Pozicija sa ovim nazivom vec postoji";
                    FacesContext.getCurrentInstance().addMessage("porukaDodavanjaPozicije", message);
                    return;
                }
            }
            
            listaPozicija.add(sifarnikPozicija);
            stm.executeUpdate("insert into sifPozicija (nazivPozicije) values ('" + sifarnikPozicija + "')");
        
            poruka = "Uspesno dodata pozicija";
            FacesContext.getCurrentInstance().addMessage("porukaDodavanjaPozicije", message);
        } catch (SQLException ex) {
            Logger.getLogger(AzurirajSifarnike.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void dodajProfesionalnuVestinu() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
            
            ResultSet ps = stm.executeQuery("select nazivVestine from sifProfesionalneVestine");
            while(ps.next()){
                if(ps.getString("nazivVestine").equalsIgnoreCase(sifarnikProfesionalnihVestina)){
                    poruka = "Vestina sa ovim nazivom vec postoji";
                    FacesContext.getCurrentInstance().addMessage("porukaDodavanjaVestine", message);
                    return;
                }
            }
            
            listaProfesionalnihVestina.add(sifarnikProfesionalnihVestina);
            stm.executeUpdate("insert into sifProfesionalneVestine (nazivVestine) values ('" + sifarnikProfesionalnihVestina + "')");
        
            poruka = "Uspesno dodata vestina";
            FacesContext.getCurrentInstance().addMessage("porukaDodavanjaVestine", message);
        } catch (SQLException ex) {
            Logger.getLogger(AzurirajSifarnike.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void dodajUlicu(String nazivMesta) {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
            
            ResultSet ps = stm.executeQuery("select nazivUlice from sifUlica");
            while(ps.next()){
                if(ps.getString("nazivUlice").equalsIgnoreCase(sifarnikUlica)){
                    poruka = "Ulica sa ovim nazivom vec postoji";
                    FacesContext.getCurrentInstance().addMessage("porukaDodavanjaUlice", message);
                    return;
                }
            }
            
            ResultSet rs = stm.executeQuery("select idSifMesta from SifMesta where nazivMesta='" + nazivMesta + "'");
            int idMesta = rs.getInt("idSifMesta");
            
            listaUlica.add(sifarnikUlica);
            stm.executeUpdate("insert into sifUlica (nazivUlice, idSifMesta) values ('" + sifarnikUlica + "', " +idMesta + ")");
        
            poruka = "Uspesno dodata ulica";
            FacesContext.getCurrentInstance().addMessage("porukaDodavanjaUlice", message);
        } catch (SQLException ex) {
            Logger.getLogger(AzurirajSifarnike.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void obrisiFakultet() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);

            listaFakulteta.remove(sifarnikFakulteta);
            stm.executeUpdate("delete from sifFakulteta where nazivFakulteta='" + sifarnikFakulteta + "'");
            
            poruka = "Uspesno obrisan fakultet";
            FacesContext.getCurrentInstance().addMessage("porukaBrisanjeFakulteta", message);
        } catch (SQLException ex) {
            Logger.getLogger(AzurirajSifarnike.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void obrisiUniverzitet() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
            
            
            listaUniverziteta.remove(sifarnikUniverziteta);
            stm.executeUpdate("delete from sifUniverzitet where nazivUniverziteta='" + sifarnikUniverziteta + "'");
        
            poruka = "Uspesno obrisan univerzitet";
            FacesContext.getCurrentInstance().addMessage("porukaBrisanjeUniverziteta", message);
        } catch (SQLException ex) {
            Logger.getLogger(AzurirajSifarnike.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void obrisiDrzavu() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
            
            listaDrzava.remove(sifarnikDrzava);
            stm.executeUpdate("delete from sifDrzava where nazivDrzave='" + sifarnikDrzava + "'");
            
            poruka = "Uspesno obrisana drzava";
            FacesContext.getCurrentInstance().addMessage("porukaBrisanjeDrzave", message);
        } catch (SQLException ex) {
            Logger.getLogger(AzurirajSifarnike.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void obrisiDrzavljanstvo() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
            
            listaDrzavljanstava.remove(sifarnikDrzavljanstava);
            stm.executeUpdate("delete from sifDrzavljanstva where nazivDrzavljanstva='" + sifarnikDrzavljanstava + "'");
        
            poruka = "Uspesno obrisano drzavljanstvo";
            FacesContext.getCurrentInstance().addMessage("porukaBrisanjeDrzavljanstva", message);
        } catch (SQLException ex) {
            Logger.getLogger(AzurirajSifarnike.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void obrisiKompaniju() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
            
            listaKompanija.remove(sifarnikKompanija);
            stm.executeUpdate("delete from sifKompanija where nazivKompanije='" + sifarnikKompanija + "'");
            
            poruka = "Uspesno obrisana kompanija";
            FacesContext.getCurrentInstance().addMessage("porukaBrisanjeKompanije", message);
        } catch (SQLException ex) {
            Logger.getLogger(AzurirajSifarnike.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void obrisiMesto() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);

            listaMesta.remove(sifarnikMesta);
            stm.executeUpdate("delete from sifMesta where nazivMesta='" + sifarnikMesta + "'");
        
            poruka = "Uspesno obrisano mesto";
            FacesContext.getCurrentInstance().addMessage("porukaBrisanjeMesta", message);
        } catch (SQLException ex) {
            Logger.getLogger(AzurirajSifarnike.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void obrisiOblastInteresovanja() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
            
            listaOblastiInteresovanja.remove(sifarnikOblastiInteresovanja);
            stm.executeUpdate("delete from sifOblastInteresovanja where nazivInteresovanja='" + sifarnikOblastiInteresovanja + "'");
        
            poruka = "Uspesno obrisano interesovanje";
            FacesContext.getCurrentInstance().addMessage("porukaBrisanjeInteresovanja", message);
        } catch (SQLException ex) {
            Logger.getLogger(AzurirajSifarnike.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void obrisiPoziciju() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
            
            listaPozicija.remove(sifarnikPozicija);
            stm.executeUpdate("delete from sifPozicija where nazivPozicije='" + sifarnikPozicija + "'");
        
            poruka = "Uspesno obrisana pozicija";
            FacesContext.getCurrentInstance().addMessage("porukaBrisanjePozicije", message);
        } catch (SQLException ex) {
            Logger.getLogger(AzurirajSifarnike.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void obrisiProfesionalnuVestinu() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
            
            listaProfesionalnihVestina.remove(sifarnikProfesionalnihVestina);
            stm.executeUpdate("delete from sifProfesionalneVestine where nazivVestine='" + sifarnikProfesionalnihVestina + "'");
        
            poruka = "Uspesno obrisana vestina";
            FacesContext.getCurrentInstance().addMessage("porukaBrisanjeVestine", message);
        } catch (SQLException ex) {
            Logger.getLogger(AzurirajSifarnike.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void obrisiUlicu() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, poruka, null);
            
            listaUlica.remove(sifarnikUlica);
            stm.executeUpdate("delete from sifUlica where nazivUlice='" + sifarnikUlica + "'" );
        
            poruka = "Uspesno obrisana ulica";
            FacesContext.getCurrentInstance().addMessage("porukaBrisanjeUlice", message);
        } catch (SQLException ex) {
            Logger.getLogger(AzurirajSifarnike.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajFakultete() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery("select nazivFakulteta from siffakulteta");

            listaFakulteta = new ArrayList<>();

            while (rs.next()) {
                listaFakulteta.add(rs.getString("nazivFakulteta"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajKategorije.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    public void ucitajDrzave() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery("select nazivDrzave from sifDrzava");

            listaDrzava = new ArrayList<>();

            while (rs.next()) {
                listaDrzava.add(rs.getString("nazivDrzave"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajKategorije.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajDrzavljanstva() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery("select nazivDrzavljanstva from sifDrzavljanstva");

            listaDrzavljanstava = new ArrayList<>();

            while (rs.next()) {
                listaDrzavljanstava.add(rs.getString("nazivDrzavljanstva"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajKategorije.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajKompanije() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery("select nazivKompanije from sifKompanija");

            listaKompanija = new ArrayList<>();

            while (rs.next()) {
                listaKompanija.add(rs.getString("nazivKompanije"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajKategorije.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajMesta() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery("select nazivMesta from sifMesta");

            listaMesta = new ArrayList<>();

            while (rs.next()) {
                listaMesta.add(rs.getString("nazivMesta"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajKategorije.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajOblastiInteresovanja() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery("select nazivInteresovanja from sifOblastInteresovanja");

            listaOblastiInteresovanja = new ArrayList<>();

            while (rs.next()) {
                listaOblastiInteresovanja.add(rs.getString("nazivInteresovanja"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajKategorije.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajPozicije() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery("select nazivPozicije from sifPozicija");

            listaPozicija = new ArrayList<>();

            while (rs.next()) {
                listaPozicija.add(rs.getString("nazivPozicije"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajKategorije.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajProfesionalneVestine() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery("select nazivVestine from sifProfesionalneVestine");

            listaProfesionalnihVestina = new ArrayList<>();

            while (rs.next()) {
                listaProfesionalnihVestina.add(rs.getString("nazivVestine"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajKategorije.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajUlice() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery("select nazivUlice from sifUlica");

            listaUlica = new ArrayList<>();

            while (rs.next()) {
                listaUlica.add(rs.getString("nazivUlice"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajKategorije.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajUniverzitete() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery("select nazivUniverziteta from sifUniverziteta");

            listaUniverziteta = new ArrayList<>();

            while (rs.next()) {
                listaUniverziteta.add(rs.getString("nazivUniverziteta"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajKategorije.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void editujSifarnikFakulteta() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            stm.executeUpdate("update sifFakulteta set nazivFakulteta='" + novaVrednostSifarnikFakulteta + "' where nazivFakulteta='" + staraVrednostSifarnikFakulteta + "'");
        } catch (SQLException ex) {
            Logger.getLogger(UcitajKategorije.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void editujSifarnikDrzave() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            stm.executeUpdate("update sifDrzava set nazivDrzave='" + novaVrednostSifarnikDrzava + "' where nazivDrzave='" + staraVrednostSifarnikDrzava + "'");
        } catch (SQLException ex) {
            Logger.getLogger(UcitajKategorije.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editujSifarnikDrzavljanstva() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            stm.executeUpdate("update sifDrzavljanstva set nazivDrzavljanstva='" + novaVrednostSifarnikDrzavljanstava + "' where nazivDrzavljanstva='" + staraVrednostSifarnikDrzavljanstava + "'");
        } catch (SQLException ex) {
            Logger.getLogger(UcitajKategorije.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editujSifarnikKompanije() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            stm.executeUpdate("update sifKompanija set nazivKompanije='" + novaVrednostSifarnikKompanija + "' where nazivKompanije='" + staraVrednostSifarnikKompanija + "'");
        } catch (SQLException ex) {
            Logger.getLogger(UcitajKategorije.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editujSifarnikMesta() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            stm.executeUpdate("update sifMesta set nazivMesta='" + novaVrednostSifarnikMesta + "' where nazivMesta='" + staraVrednostSifarnikMesta + "'");
        } catch (SQLException ex) {
            Logger.getLogger(UcitajKategorije.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editujSifarnikOblastiInteresovanja() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            stm.executeUpdate("update sifOblastInteresovanja set nazivInteresovanja='" + novaVrednostSifarnikOblastiInteresovanja + "' where nazivInteresovanja='" + staraVrednostSifarnikOblastiInteresovanja + "'");
        } catch (SQLException ex) {
            Logger.getLogger(UcitajKategorije.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editujSifarnikPozicija() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            stm.executeUpdate("update sifPozicija set nazivPozicije='" + novaVrednostSifarnikPozicija + "' where nazivPozicije='" + staraVrednostSifarnikPozicija + "'");
        } catch (SQLException ex) {
            Logger.getLogger(UcitajKategorije.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editujSifarnikProfesionalneVestine() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            stm.executeUpdate("update sifProfesionalneVestine set nazivVestine='" + novaVrednostSifarnikProfesionalnihVestina + "' where nazivVestine='" + staraVrednostSifarnikProfesionalnihVestina + "'");
        } catch (SQLException ex) {
            Logger.getLogger(UcitajKategorije.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editujSifarnikUlica() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            stm.executeUpdate("update sifUlica set nazivUlice='" + novaVrednostSifarnikUlica + "' where nazivUlice='" + staraVrednostSifarnikUlica + "'");
        } catch (SQLException ex) {
            Logger.getLogger(UcitajKategorije.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editujSifarnikUniverziteta() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            stm.executeUpdate("update sifUniverzitet set nazivUniverziteta='" + novaVrednostSifarnikUniverziteta + "' where nazivUniverziteta='" + staraVrednostSifarnikUniverziteta + "'");
        } catch (SQLException ex) {
            Logger.getLogger(UcitajKategorije.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
