
package beans;

import java.time.LocalDateTime;

public class Obavestenje {
    private String datumKreiranja, naslovObavestenja, tekstOvavestenja, autorObavestenja, nivoVidljivosti, arhiviranoObavestenje, metaObavestenja, saljeNa, procitano;
    private LocalDateTime datumKreiranjaObavestenja;
    private int idObavestenja, idUcestvujeKreira;
    
    public String getProcitano() {
        return procitano;
    }

    public void setProcitano(String procitano) {
        this.procitano = procitano;
    }

    public String getSaljeNa() {
        return saljeNa;
    }

    public void setSaljeNa(String saljeNa) {
        this.saljeNa = saljeNa;
    }

    public int getIdUcestvujeKreira() {
        return idUcestvujeKreira;
    }

    public void setIdUcestvujeKreira(int idUcestvujeKreira) {
        this.idUcestvujeKreira = idUcestvujeKreira;
    }

    public String getDatumKreiranja() {
        return datumKreiranja;
    }

    public void setDatumKreiranja(String datumKreiranja) {
        this.datumKreiranja = datumKreiranja;
    }

    public String getMetaObavestenja() {
        return metaObavestenja;
    }

    public void setMetaObavestenja(String metaObavestenja) {
        this.metaObavestenja = metaObavestenja;
    }

    public int getIdObavestenja() {
        return idObavestenja;
    }

    public void setIdObavestenja(int idObavestenja) {
        this.idObavestenja = idObavestenja;
    }
    
    public String getNaslovObavestenja() {
        return naslovObavestenja;
    }

    public void setNaslovObavestenja(String naslovObavestenja) {
        this.naslovObavestenja = naslovObavestenja;
    }

    public String getTekstOvavestenja() {
        return tekstOvavestenja;
    }

    public void setTekstOvavestenja(String tekstOvavestenja) {
        this.tekstOvavestenja = tekstOvavestenja;
    }

    public String getAutorObavestenja() {
        return autorObavestenja;
    }

    public void setAutorObavestenja(String autorObavestenja) {
        this.autorObavestenja = autorObavestenja;
    }

    public String getNivoVidljivosti() {
        return nivoVidljivosti;
    }

    public void setNivoVidljivosti(String nivoVidljivosti) {
        this.nivoVidljivosti = nivoVidljivosti;
    }

    public String getArhiviranoObavestenje() {
        return arhiviranoObavestenje;
    }

    public void setArhiviranoObavestenje(String arhiviranoObavestenje) {
        this.arhiviranoObavestenje = arhiviranoObavestenje;
    }

    public LocalDateTime getDatumKreiranjaObavestenja() {
        return datumKreiranjaObavestenja;
    }

    public void setDatumKreiranjaObavestenja(LocalDateTime datumKreiranjaObavestenja) {
        this.datumKreiranjaObavestenja = datumKreiranjaObavestenja;
    }
    
}
