
package beans;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Anketa {
    private String prvoPitanje, nazivAnkete, tekstAnkete, nivoVidljivosti, autor, arhiviranaAnketa, autorAnkete, datumTrajanja, tipUcesnika, vidljivostRezultata, metaAnkete;
    private LocalDateTime datumTrajanjaAnkete;
    private int idAnketa, idObjave;
    private HashMap<Integer, String> listaPitanjaAnkete;

    public String getPrvoPitanje() {
        return prvoPitanje;
    }

    public void setPrvoPitanje(String prvoPitanje) {
        this.prvoPitanje = prvoPitanje;
    }

    public String getMetaAnkete() {
        return metaAnkete;
    }

    public void setMetaAnkete(String metaAnkete) {
        this.metaAnkete = metaAnkete;
    }

    public String getVidljivostRezultata() {
        return vidljivostRezultata;
    }

    public void setVidljivostRezultata(String vidljivostRezultata) {
        this.vidljivostRezultata = vidljivostRezultata;
    }

    public String getTipUcesnika() {
        return tipUcesnika;
    }

    public void setTipUcesnika(String tipUcesnika) {
        this.tipUcesnika = tipUcesnika;
    }
    
    public int getIdObjave() {
        return idObjave;
    }

    public void setIdObjave(int idObjave) {
        this.idObjave = idObjave;
    }
    
    public String getDatumTrajanja() {
        return datumTrajanja;
    }

    public void setDatumTrajanja(String datumTrajanja) {
        this.datumTrajanja = datumTrajanja;
    }

    public HashMap<Integer, String> getListaPitanjaAnkete() {
        return listaPitanjaAnkete;
    }

    public void setListaPitanjaAnkete(HashMap<Integer, String> listaPitanjaAnkete) {
        this.listaPitanjaAnkete = listaPitanjaAnkete;
    }

    public int getIdAnketa() {
        return idAnketa;
    }

    public void setIdAnketa(int idAnketa) {
        this.idAnketa = idAnketa;
    }

    public LocalDateTime getDatumTrajanjaAnkete() {
        return datumTrajanjaAnkete;
    }

    public void setDatumTrajanjaAnkete(LocalDateTime datumTrajanjaAnkete) {
        this.datumTrajanjaAnkete = datumTrajanjaAnkete;
    }
    
    public String getAutorAnkete() {
        return autorAnkete;
    }

    public void setAutorAnkete(String autorAnkete) {
        this.autorAnkete = autorAnkete;
    }

    public String getArhiviranaAnketa() {
        return arhiviranaAnketa;
    }

    public void setArhiviranaAnketa(String arhiviranaAnketa) {
        this.arhiviranaAnketa = arhiviranaAnketa;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getNazivAnkete() {
        return nazivAnkete;
    }

    public void setNazivAnkete(String nazivAnkete) {
        this.nazivAnkete = nazivAnkete;
    }

    public String getTekstAnkete() {
        return tekstAnkete;
    }

    public void setTekstAnkete(String tekstAnkete) {
        this.tekstAnkete = tekstAnkete;
    }

    public String getNivoVidljivosti() {
        return nivoVidljivosti;
    }

    public void setNivoVidljivosti(String nivoVidljivosti) {
        this.nivoVidljivosti = nivoVidljivosti;
    }
    
    
}
