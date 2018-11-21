package controllers;

import beans.Korisnik;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;

@ManagedBean
@SessionScoped
public class PretragaKorisnika {

    private List<Korisnik> listaSvihStudenata;
    private List<Korisnik> listaSvihKompanija;
    private List<Korisnik> listaPretrazenihStudenata;
    private List<Korisnik> listaPretrazenihKompanija;
    private List<String> listaMesta;
    private String[] listaZaposlenja = {"Zaposlen/a", "Nezaposlen/a"};
    private List<String> listaKurseva;
    private String kurs, zaposlenje, mesto;

    public List<Korisnik> getListaSvihKompanija() {
        return listaSvihKompanija;
    }

    public void setListaSvihKompanija(List<Korisnik> listaSvihKompanija) {
        this.listaSvihKompanija = listaSvihKompanija;
    }

    public List<Korisnik> getListaPretrazenihKompanija() {
        return listaPretrazenihKompanija;
    }

    public void setListaPretrazenihKompanija(List<Korisnik> listaPretrazenihKompanija) {
        this.listaPretrazenihKompanija = listaPretrazenihKompanija;
    }

    public String getKurs() {
        return kurs;
    }

    public void setKurs(String kurs) {
        this.kurs = kurs;
    }

    public String getZaposlenje() {
        return zaposlenje;
    }

    public void setZaposlenje(String zaposlenje) {
        this.zaposlenje = zaposlenje;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public List<Korisnik> getListaClanova() {
        return listaSvihStudenata;
    }

    public void setListaClanova(List<Korisnik> listaClanova) {
        this.listaSvihStudenata = listaClanova;
    }

    public List<Korisnik> getListaSvihStudenata() {
        return listaSvihStudenata;
    }

    public void setListaSvihStudenata(List<Korisnik> listaSvihStudenata) {
        this.listaSvihStudenata = listaSvihStudenata;
    }

    public List<Korisnik> getListaPretrazenihStudenata() {
        return listaPretrazenihStudenata;
    }

    public void setListaPretrazenihStudenata(List<Korisnik> listaPretrazenihStudenata) {
        this.listaPretrazenihStudenata = listaPretrazenihStudenata;
    }

    public List<String> getListaMesta() {
        return listaMesta;
    }

    public void setListaMesta(List<String> listaMesta) {
        this.listaMesta = listaMesta;
    }

    public String[] getListaZaposlenja() {
        return listaZaposlenja;
    }

    public void setListaZaposlenja(String[] listaZaposlenja) {
        this.listaZaposlenja = listaZaposlenja;
    }

    public List<String> getListaKurseva() {
        return listaKurseva;
    }

    public void setListaKurseva(List<String> listaKurseva) {
        this.listaKurseva = listaKurseva;
    }

    public void ucitajListuSvihClanova() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            ResultSet bazaListaStudenata = stm.executeQuery("select ime, prezime, srednjeIme, kurs, javnoIme, nazivMesta, status, imeVidljivost, prezimeVidljivost, srednjeImeVidljivost, kursVidljivost, idSifMestaVidljivost, statusVidljivost from student s, sifMesta sm, sifUlica su");

            listaSvihStudenata = new ArrayList<>();
            listaPretrazenihStudenata = new LinkedList<>();

            while (bazaListaStudenata.next()) {
                Korisnik k = new Korisnik();

                k.setIme(bazaListaStudenata.getString("ime"));
                k.setPrezime(bazaListaStudenata.getString("prezime"));
                k.setSrednjeIme(bazaListaStudenata.getString("srednjeIme"));
                k.setKurs(bazaListaStudenata.getString("kurs"));
                k.setJavnoIme(bazaListaStudenata.getString("javnoIme"));
                k.setImeVidljivost(bazaListaStudenata.getString("imeVidljivost"));
                k.setStatus(bazaListaStudenata.getString("status"));
                k.setNazivMesta(bazaListaStudenata.getString("nazivMesta"));
                k.setPrezimeVidljivost(bazaListaStudenata.getString("prezimeVidljivost"));
                k.setSrednjeImeVidljivost(bazaListaStudenata.getString("srednjeImeVidljivost"));
                k.setKursVidljivost(bazaListaStudenata.getString("kursVidljivost"));
                k.setIdSifMestaVidljivost(bazaListaStudenata.getString("idSifMestaVidljivost"));
                k.setStatusVidljivost(bazaListaStudenata.getString("statusVidljivost"));

                listaSvihStudenata.add(k);
                listaPretrazenihStudenata.add(k);
            }

            listaSvihKompanija = new ArrayList<>();
            listaPretrazenihKompanija = new LinkedList<>();

            ResultSet bazaListaKompanija = stm.executeQuery("select javnoIme, nazivMesta, oblastPoslovanja from kompanija k, sifMesta sm, sifUlica su where k.idSifUlica=su.idSifUlica && su.idSifMesta=sm.idSifMesta");

            while (bazaListaKompanija.next()) {
                Korisnik k = new Korisnik();

                k.setJavnoIme(bazaListaKompanija.getString("javnoIme"));
                k.setNazivMesta(bazaListaKompanija.getString("nazivMesta"));
                k.setOblastPoslovanja(bazaListaKompanija.getString("oblastPoslovanja"));

                listaSvihKompanija.add(k);
                listaPretrazenihKompanija.add(k);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PretragaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajListuMesta() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            ResultSet bazaListaMesta = stm.executeQuery("select nazivMesta from sifMesta");

            listaMesta = new ArrayList<>();

            while (bazaListaMesta.next()) {
                listaMesta.add(bazaListaMesta.getString("nazivMesta"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PretragaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajListuKurseva() {

        listaKurseva = new ArrayList<>();

        for (int i = 0; i < listaSvihStudenata.size(); i++) {
            if (listaSvihStudenata.get(i).getKurs() != null) {
                if (!listaKurseva.contains(listaSvihStudenata.get(i).getKurs())) {
                    listaKurseva.add(listaSvihStudenata.get(i).getKurs());
                }
            }
        }
    }

    public void pretraziKorisnikePoMestu(AjaxBehaviorEvent e) {
        
        if(kurs == null && zaposlenje == null){
            listaPretrazenihStudenata = new LinkedList<>();
        }
        
        for (int i = 0; i < listaSvihStudenata.size(); i++) {
            if (listaSvihStudenata.get(i).getNazivMesta().toLowerCase().startsWith(mesto.toLowerCase())) {
                listaPretrazenihStudenata.add(listaSvihStudenata.get(i));
            }
        }
    }
    
    public void pretraziKorisnikePoKursu(AjaxBehaviorEvent e) {
        
        if(mesto == null && zaposlenje == null){
            listaPretrazenihStudenata = new LinkedList<>();
        }
        
        for (int i = 0; i < listaSvihStudenata.size(); i++) {
            if (listaSvihStudenata.get(i).getKurs().toLowerCase().startsWith(kurs.toLowerCase())) {
                listaPretrazenihStudenata.add(listaSvihStudenata.get(i));
            }
        }
    }

    public void pretraziKorisnikePoZaposlenju(AjaxBehaviorEvent e) {
        
        if(kurs == null && mesto == null){
            listaPretrazenihStudenata = new LinkedList<>();
        }
        
        for (int i = 0; i < listaSvihStudenata.size(); i++) {
            if (listaSvihStudenata.get(i).getStatus().toLowerCase().startsWith(zaposlenje.toLowerCase())) {
                listaPretrazenihStudenata.add(listaSvihStudenata.get(i));
            }
        }
    }
    
}
