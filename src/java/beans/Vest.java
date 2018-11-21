
package beans;

import java.time.LocalDateTime;

public class Vest {
    
    private String datumPostavljanjaVestiNaslovna, datumPostavljanja, naslovVesti, tekstVesti, autorVesti, arhiviranaVest, nivoVidljivosti, nazivKategorije, metaVesti;
    private LocalDateTime datumPostavljanjaVesti;
    private int idVesti, idUcestvujeKreira;

    public String getDatumPostavljanjaVestiNaslovna() {
        return datumPostavljanjaVestiNaslovna;
    }

    public void setDatumPostavljanjaVestiNaslovna(String datumPostavljanjaVestiNaslovna) {
        this.datumPostavljanjaVestiNaslovna = datumPostavljanjaVestiNaslovna;
    }

    public int getIdUcestvujeKreira() {
        return idUcestvujeKreira;
    }

    public void setIdUcestvujeKreira(int idUcestvujeKreira) {
        this.idUcestvujeKreira = idUcestvujeKreira;
    }

    public String getDatumPostavljanja() {
        return datumPostavljanja;
    }

    public void setDatumPostavljanja(String datumPostavljanja) {
        this.datumPostavljanja = datumPostavljanja;
    }

    public String getMetaVest() {
        return metaVesti;
    }

    public void setMetaVesti(String metaVesti) {
        this.metaVesti = metaVesti;
    }

    public int getIdVesti() {
        return idVesti;
    }

    public void setIdVesti(int idVesti) {
        this.idVesti = idVesti;
    }
    
    public String getNazivKategorije() {
        return nazivKategorije;
    }

    public void setNazivKategorije(String nazivKategorije) {
        this.nazivKategorije = nazivKategorije;
    }

    public String getNivoVidljivosti() {
        return nivoVidljivosti;
    }

    public void setNivoVidljivosti(String nivoVidljivosti) {
        this.nivoVidljivosti = nivoVidljivosti;
    }

    public String getNaslovVesti() {
        return naslovVesti;
    }

    public void setNaslovVesti(String naslovVesti) {
        this.naslovVesti = naslovVesti;
    }

    public String getTekstVesti() {
        return tekstVesti;
    }

    public void setTekstVesti(String tekstVesti) {
        this.tekstVesti = tekstVesti;
    }

    public String getAutorVesti() {
        return autorVesti;
    }

    public void setAutorVesti(String autorVesti) {
        this.autorVesti = autorVesti;
    }

    public String getArhiviranaVest() {
        return arhiviranaVest;
    }

    public void setArhiviranaVest(String arhiviranaVest) {
        this.arhiviranaVest = arhiviranaVest;
    }

    public LocalDateTime getDatumPostavljanjaVesti() {
        return datumPostavljanjaVesti;
    }

    public void setDatumPostavljanjaVesti(LocalDateTime datumPostavljanjaVesti) {
        this.datumPostavljanjaVesti = datumPostavljanjaVesti;
    }
    
    
}
