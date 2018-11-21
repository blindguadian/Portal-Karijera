
package beans;

import java.time.LocalDateTime;

public class Statistika {
    private String statistikaZa, listaNazivaStavke, listaVrednostiStavke, datumKreiranja, nazivStatistike, tekstStatistike, autorStatistike, nivoVidljivosti, arhiviranaStatistika, metaStatistike, stavkaStatistike;
    private LocalDateTime datumKreiranjaStatistike;
    private int idStatistike, idUcestvujeKreira, ukupanBrojKorisnika;

    public String getListaNazivaStavke() {
        return listaNazivaStavke;
    }

    public void setListaNazivaStavke(String listaNazivaStavke) {
        this.listaNazivaStavke = listaNazivaStavke;
    }

    public String getListaVrednostiStavke() {
        return listaVrednostiStavke;
    }

    public void setListaVrednostiStavke(String listaVrednostiStavke) {
        this.listaVrednostiStavke = listaVrednostiStavke;
    }

    public int getUkupanBrojKorisnika() {
        return ukupanBrojKorisnika;
    }

    public void setUkupanBrojKorisnika(int ukupanBrojKorisnika) {
        this.ukupanBrojKorisnika = ukupanBrojKorisnika;
    }
            
    public String getStatistikaZa() {
        return statistikaZa;
    }

    public void setStatistikaZa(String statistikaZa) {
        this.statistikaZa = statistikaZa;
    }

    public String getStavkaStatistike() {
        return stavkaStatistike;
    }

    public void setStavkaStatistike(String stavkaStatistike) {
        this.stavkaStatistike = stavkaStatistike;
    }

    public String getDatumKreiranja() {
        return datumKreiranja;
    }

    public void setDatumKreiranja(String datumKreiranja) {
        this.datumKreiranja = datumKreiranja;
    }

    public String getNazivStatistike() {
        return nazivStatistike;
    }

    public void setNazivStatistike(String nazivStatistike) {
        this.nazivStatistike = nazivStatistike;
    }

    public String getTekstStatistike() {
        return tekstStatistike;
    }

    public void setTekstStatistike(String tekstStatistike) {
        this.tekstStatistike = tekstStatistike;
    }

    public String getAutorStatistike() {
        return autorStatistike;
    }

    public void setAutorStatistike(String autorStatistike) {
        this.autorStatistike = autorStatistike;
    }

    public String getNivoVidljivosti() {
        return nivoVidljivosti;
    }

    public void setNivoVidljivosti(String nivoVidljivosti) {
        this.nivoVidljivosti = nivoVidljivosti;
    }

    public String getArhiviranaStatistika() {
        return arhiviranaStatistika;
    }

    public void setArhiviranaStatistika(String arhiviranaStatistika) {
        this.arhiviranaStatistika = arhiviranaStatistika;
    }

    public String getMetaStatistike() {
        return metaStatistike;
    }

    public void setMetaStatistike(String metaStatistike) {
        this.metaStatistike = metaStatistike;
    }

    public LocalDateTime getDatumKreiranjaStatistike() {
        return datumKreiranjaStatistike;
    }

    public void setDatumKreiranjaStatistike(LocalDateTime datumKreiranjaStatistike) {
        this.datumKreiranjaStatistike = datumKreiranjaStatistike;
    }

    public int getIdStatistike() {
        return idStatistike;
    }

    public void setIdStatistike(int idStatistike) {
        this.idStatistike = idStatistike;
    }

    public int getIdUcestvujeKreira() {
        return idUcestvujeKreira;
    }

    public void setIdUcestvujeKreira(int idUcestvujeKreira) {
        this.idUcestvujeKreira = idUcestvujeKreira;
    }
    
    
}
