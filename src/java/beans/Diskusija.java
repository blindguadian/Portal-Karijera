
package beans;

import java.time.LocalDateTime;

public class Diskusija {
    private String datumPostavljanja, nazivDiskusije, tekstDiskusije, kategorijaDiskusije, nivoVidljivosti, autorDiskusije, arhiviranaDiskusija, poruka, metaDiskusija, prviKomentar;
    private LocalDateTime datumPostavljanjaDiskusije;
    private int idDiskusija, idUcestvujeKreira;

    public String getPrviKomentar() {
        return prviKomentar;
    }

    public void setPrviKomentar(String prviKomentar) {
        this.prviKomentar = prviKomentar;
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

    public String getMetaDiskusija() {
        return metaDiskusija;
    }

    public void setMetaDiskusija(String metaDiskusija) {
        this.metaDiskusija = metaDiskusija;
    }

    public int getIdDiskusija() {
        return idDiskusija;
    }

    public void setIdDiskusija(int idDiskusija) {
        this.idDiskusija = idDiskusija;
    }

    public String getNazivDiskusije() {
        return nazivDiskusije;
    }

    public void setNazivDiskusije(String nazivDiskusije) {
        this.nazivDiskusije = nazivDiskusije;
    }

    public String getTekstDiskusije() {
        return tekstDiskusije;
    }

    public void setTekstDiskusije(String tekstDiskusije) {
        this.tekstDiskusije = tekstDiskusije;
    }

    public String getKategorijaDiskusije() {
        return kategorijaDiskusije;
    }

    public void setKategorijaDiskusije(String kategorijaDiskusije) {
        this.kategorijaDiskusije = kategorijaDiskusije;
    }

    public String getNivoVidljivosti() {
        return nivoVidljivosti;
    }

    public void setNivoVidljivosti(String nivoVidljivosti) {
        this.nivoVidljivosti = nivoVidljivosti;
    }

    public String getAutorDiskusije() {
        return autorDiskusije;
    }

    public void setAutorDiskusije(String autorDiskusije) {
        this.autorDiskusije = autorDiskusije;
    }

    public String getArhiviranaDiskusija() {
        return arhiviranaDiskusija;
    }

    public void setArhiviranaDiskusija(String arhiviranaDiskusija) {
        this.arhiviranaDiskusija = arhiviranaDiskusija;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public LocalDateTime getDatumPostavljanjaDiskusije() {
        return datumPostavljanjaDiskusije;
    }

    public void setDatumPostavljanjaDiskusije(LocalDateTime datumPostavljanjaDiskusije) {
        this.datumPostavljanjaDiskusije = datumPostavljanjaDiskusije;
    }
    
    
}
