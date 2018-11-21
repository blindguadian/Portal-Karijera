
package beans;

import java.time.LocalDateTime;

public class Preporuka {
    private String datumKreiranja, nazivPreporuke, tekstPreporuke, autorPreporuke, nivoVidljivosti, metaPreporuke;
    private LocalDateTime datumKreiranjaPreporuke;
    private int idPreporuke, idUcestvujeKreira;
    private float prosecnaOcena;

    public float getProsecnaOcena() {
        return prosecnaOcena;
    }

    public void setProsecnaOcena(float prosecnaOcena) {
        this.prosecnaOcena = prosecnaOcena;
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

    public String getMetaPreporuke() {
        return metaPreporuke;
    }

    public void setMetaPreporuke(String metaPreporuke) {
        this.metaPreporuke = metaPreporuke;
    }

    public int getIdPreporuke() {
        return idPreporuke;
    }

    public void setIdPreporuke(int idPreporuke) {
        this.idPreporuke = idPreporuke;
    }
    
    public String getNazivPreporuke() {
        return nazivPreporuke;
    }

    public void setNazivPreporuke(String nazivPreporuke) {
        this.nazivPreporuke = nazivPreporuke;
    }

    public String getTekstPreporuke() {
        return tekstPreporuke;
    }

    public void setTekstPreporuke(String tekstPreporuke) {
        this.tekstPreporuke = tekstPreporuke;
    }

    public String getAutorPreporuke() {
        return autorPreporuke;
    }

    public void setAutorPreporuke(String autorPreporuke) {
        this.autorPreporuke = autorPreporuke;
    }

    public String getNivoVidljivosti() {
        return nivoVidljivosti;
    }

    public void setNivoVidljivosti(String nivoVidljivosti) {
        this.nivoVidljivosti = nivoVidljivosti;
    }

    public LocalDateTime getDatumKreiranjaPreporuke() {
        return datumKreiranjaPreporuke;
    }

    public void setDatumKreiranjaPreporuke(LocalDateTime datumKreiranjaPreporuke) {
        this.datumKreiranjaPreporuke = datumKreiranjaPreporuke;
    }
    
}
