
package beans;

import java.time.LocalDate;

public class Zaposlenje {
    private String nazivKompanije, nazivMesta, nazivPozicije;
    private LocalDate pocetak, kraj;
    private int idZaposlenje;

    public String getNazivKompanije() {
        return nazivKompanije;
    }

    public void setNazivKompanije(String nazivKompanije) {
        this.nazivKompanije = nazivKompanije;
    }

    public String getNazivMesta() {
        return nazivMesta;
    }

    public void setNazivMesta(String nazivMesta) {
        this.nazivMesta = nazivMesta;
    }

    public String getNazivPozicije() {
        return nazivPozicije;
    }

    public void setNazivPozicije(String nazivPozicije) {
        this.nazivPozicije = nazivPozicije;
    }

    public LocalDate getPocetak() {
        return pocetak;
    }

    public void setPocetak(LocalDate pocetak) {
        this.pocetak = pocetak;
    }

    public LocalDate getKraj() {
        return kraj;
    }

    public void setKraj(LocalDate kraj) {
        this.kraj = kraj;
    }

    public int getIdZaposlenje() {
        return idZaposlenje;
    }

    public void setIdZaposlenje(int idZaposlenje) {
        this.idZaposlenje = idZaposlenje;
    }
    
}
