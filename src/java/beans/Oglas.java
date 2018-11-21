
package beans;

import java.time.LocalDateTime;

public class Oglas {
    private String datumPostavljanjaOglasa, datumIsticanjaOglasa, naslovOglasa, tekstOglasa, autorOglasa, nivoVidljivosti, arhiviraniOglasi, metaOglasi;
    private LocalDateTime datumPostavljanja, datumIsticanja;
    private int idOglasi, idUcestvujeKreira;

    public int getIdUcestvujeKreira() {
        return idUcestvujeKreira;
    }

    public void setIdUcestvujeKreira(int idUcestvujeKreira) {
        this.idUcestvujeKreira = idUcestvujeKreira;
    }

    public String getDatumPostavljanjaOglasa() {
        return datumPostavljanjaOglasa;
    }

    public void setDatumPostavljanjaOglasa(String datumPostavljanjaOglasa) {
        this.datumPostavljanjaOglasa = datumPostavljanjaOglasa;
    }

    public String getDatumIsticanjaOglasa() {
        return datumIsticanjaOglasa;
    }

    public void setDatumIsticanjaOglasa(String datumIsticanjaOglasa) {
        this.datumIsticanjaOglasa = datumIsticanjaOglasa;
    }
    
    

    public String getMetaOglasi() {
        return metaOglasi;
    }

    public void setMetaOglasi(String metaOglasi) {
        this.metaOglasi = metaOglasi;
    }

    public int getIdOglasi() {
        return idOglasi;
    }

    public void setIdOglasi(int idOglasi) {
        this.idOglasi = idOglasi;
    }
    
    public String getNaslovOglasa() {
        return naslovOglasa;
    }

    public void setNaslovOglasa(String naslovOglasa) {
        this.naslovOglasa = naslovOglasa;
    }

    public String getTekstOglasa() {
        return tekstOglasa;
    }

    public void setTekstOglasa(String tekstOglasa) {
        this.tekstOglasa = tekstOglasa;
    }

    public String getAutorOglasa() {
        return autorOglasa;
    }

    public void setAutorOglasa(String autorOglasa) {
        this.autorOglasa = autorOglasa;
    }

    public String getNivoVidljivosti() {
        return nivoVidljivosti;
    }

    public void setNivoVidljivosti(String nivoVidljivosti) {
        this.nivoVidljivosti = nivoVidljivosti;
    }

    public String getArhiviraniOglasi() {
        return arhiviraniOglasi;
    }

    public void setArhiviraniOglasi(String arhiviraniOglasi) {
        this.arhiviraniOglasi = arhiviraniOglasi;
    }

    public LocalDateTime getDatumPostavljanja() {
        return datumPostavljanja;
    }

    public void setDatumPostavljanja(LocalDateTime datumPostavljanja) {
        this.datumPostavljanja = datumPostavljanja;
    }

    public LocalDateTime getDatumIsticanja() {
        return datumIsticanja;
    }

    public void setDatumIsticanja(LocalDateTime datumIsticanja) {
        this.datumIsticanja = datumIsticanja;
    }
    
}
