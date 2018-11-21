
package beans;

public class Pitanje {
    private String tekstPitanja, tipPitanja;
    private int redniBrojPitanja;

    public int getRedniBrojPitanja() {
        return redniBrojPitanja;
    }

    public void setRedniBrojPitanja(int redniBrojPitanja) {
        this.redniBrojPitanja = redniBrojPitanja;
    }
    
    public String getTipPitanja() {
        return tipPitanja;
    }

    public void setTipPitanja(String tipPitanja) {
        this.tipPitanja = tipPitanja;
    }

    public String getTekstPitanja() {
        return tekstPitanja;
    }

    public void setTekstPitanja(String tekstPitanja) {
        this.tekstPitanja = tekstPitanja;
    }
    
}
