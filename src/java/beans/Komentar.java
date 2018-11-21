
package beans;

import java.time.LocalDateTime;

public class Komentar {
    private String datumKreiranja, tekstKomentara, javnoImeAutora;
    private int idKomentari, idDiskusija, idKorisnik;
    LocalDateTime datumKreiranjaKomentara;

    public String getJavnoImeAutora() {
        return javnoImeAutora;
    }

    public void setJavnoImeAutora(String javnoImeAutora) {
        this.javnoImeAutora = javnoImeAutora;
    }

    public String getDatumKreiranja() {
        return datumKreiranja;
    }

    public void setDatumKreiranja(String datumKreiranja) {
        this.datumKreiranja = datumKreiranja;
    }

    public LocalDateTime getDatumKreiranjaKomentara() {
        return datumKreiranjaKomentara;
    }

    public void setDatumKreiranjaKomentara(LocalDateTime datumKreiranjaKomentara) {
        this.datumKreiranjaKomentara = datumKreiranjaKomentara;
    }

    public String getTekstKomentara() {
        return tekstKomentara;
    }

    public void setTekstKomentara(String tekstKomentara) {
        this.tekstKomentara = tekstKomentara;
    }

    public int getIdKomentari() {
        return idKomentari;
    }

    public void setIdKomentari(int idKomentari) {
        this.idKomentari = idKomentari;
    }

    public int getIdDiskusija() {
        return idDiskusija;
    }

    public void setIdDiskusija(int idDiskusija) {
        this.idDiskusija = idDiskusija;
    }

    public int getIdKorisnik() {
        return idKorisnik;
    }

    public void setIdKorisnik(int idKorisnik) {
        this.idKorisnik = idKorisnik;
    }
    
}
