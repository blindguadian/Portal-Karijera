
package beans;

import java.time.LocalDate;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class Korisnik {
    private String nazivFakulteta, ime, prezime, srednjeIme, korisnickoIme, status, lozinka, uloga, mail, kurs, pol, telefon, nazivDrzavljanstva, nazivUlice, nazivMesta, nazivDrzave, oblastPoslovanja, nazivKompanije, zaposlenjePozicija, diplomaFakultet, diplomaOdsek, diplomaNivoStudija, diplomaGodinaUpisa, diplomaGodinaZavrsetka, diplomaZvanje, diplomaUniverzitet,  trenutneStudijeUniverzitetNaziv, trenutneStudijeFakultetNaziv, trenutneStudijeDrzavaNaziv, trenutneStudijeMestoNaziv, trenutneStudijeNivo, nazivVestine, nazivInteresovanja, javnoIme, tekstOKomp, web, javnoImeVidljivost, idSifProfesionalneVestineVidljivost, idSifOblastInteresovanjaVidljivost, idSifDrzavljanstvoVidljivost, idSifMestaVidljivost, idSifDrzavaVidljivost, brojPrebivalistaVidljivost, idSifUlicaVidljivost, telefonVidljivost, polVidljivost, datumRodjenjaVidljivost, srednjeImeVidljivost, mailVidljivost, prezimeVidljivost, imeVidljivost, statusVidljivost, idSifKompanijeVidljivost, oblastPoslovanjaVidljivost, brojSedistaVidljivost, brojZaposlenihVidljivost, logoVidljivost, PIBVidljivost, tekstOKompanijiVidljivost, webVidljivost, slikaVidljivost, CVVidljivost, kursVidljivost;
    private int PIN, brojZaposlenih, brojPrebivalista, zaposlenjePocetakRada, zaposlenjeKrajRada, brojSedista, idKorisnik;
    private long PIB;
    private LocalDate trenutneStudijeGodinaUpisa, datumRodjenja;

    private String[] profesionalneVestineLista, oblastInteresovanjaLista, nazivDrzavljanstvaLista, ulicaNazivLista, mestoNazivLista, imeKompanijeLista, pozicijaLista, fakultetLista, univerzitetLista, drzavaLista;

    public String getKursVidljivost() {
        return kursVidljivost;
    }

    public void setKursVidljivost(String kursVidljivost) {
        this.kursVidljivost = kursVidljivost;
    }

    public String getNazivFakulteta() {
        return nazivFakulteta;
    }

    public void setNazivFakulteta(String nazivFakulteta) {
        this.nazivFakulteta = nazivFakulteta;
    }

    public int getIdKorisnik() {
        return idKorisnik;
    }

    public void setIdKorisnik(int idKorisnik) {
        this.idKorisnik = idKorisnik;
    }
    
    public String getSlikaVidljivost() {
        return slikaVidljivost;
    }

    public void setSlikaVidljivost(String slikaVidljivost) {
        this.slikaVidljivost = slikaVidljivost;
    }

    public String getCVVidljivost() {
        return CVVidljivost;
    }

    public void setCVVidljivost(String CVVidljivost) {
        this.CVVidljivost = CVVidljivost;
    }

    public String getIdSifKompanijeVidljivost() {
        return idSifKompanijeVidljivost;
    }

    public void setIdSifKompanijeVidljivost(String idSifKompanijeVidljivost) {
        this.idSifKompanijeVidljivost = idSifKompanijeVidljivost;
    }

    public String getOblastPoslovanjaVidljivost() {
        return oblastPoslovanjaVidljivost;
    }

    public void setOblastPoslovanjaVidljivost(String oblastPoslovanjaVidljivost) {
        this.oblastPoslovanjaVidljivost = oblastPoslovanjaVidljivost;
    }

    public String getBrojSedistaVidljivost() {
        return brojSedistaVidljivost;
    }

    public void setBrojSedistaVidljivost(String brojSedistaVidljivost) {
        this.brojSedistaVidljivost = brojSedistaVidljivost;
    }

    public String getBrojZaposlenihVidljivost() {
        return brojZaposlenihVidljivost;
    }

    public void setBrojZaposlenihVidljivost(String brojZaposlenihVidljivost) {
        this.brojZaposlenihVidljivost = brojZaposlenihVidljivost;
    }

    public String getLogoVidljivost() {
        return logoVidljivost;
    }

    public void setLogoVidljivost(String logoVidljivost) {
        this.logoVidljivost = logoVidljivost;
    }

    public String getPIBVidljivost() {
        return PIBVidljivost;
    }

    public void setPIBVidljivost(String PIBVidljivost) {
        this.PIBVidljivost = PIBVidljivost;
    }

    public String getTekstOKompanijiVidljivost() {
        return tekstOKompanijiVidljivost;
    }

    public void setTekstOKompanijiVidljivost(String tekstOKompanijiVidljivost) {
        this.tekstOKompanijiVidljivost = tekstOKompanijiVidljivost;
    }

    public String getWebVidljivost() {
        return webVidljivost;
    }

    public void setWebVidljivost(String webVidljivost) {
        this.webVidljivost = webVidljivost;
    }

    
    public String getStatusVidljivost() {
        return statusVidljivost;
    }

    public void setStatusVidljivost(String statusVidljivost) {
        this.statusVidljivost = statusVidljivost;
    }

    public String getJavnoImeVidljivost() {
        return javnoImeVidljivost;
    }

    public void setJavnoImeVidljivost(String javnoImeVidljivost) {
        this.javnoImeVidljivost = javnoImeVidljivost;
    }

    public String getIdSifProfesionalneVestineVidljivost() {
        return idSifProfesionalneVestineVidljivost;
    }

    public void setIdSifProfesionalneVestineVidljivost(String idSifProfesionalneVestineVidljivost) {
        this.idSifProfesionalneVestineVidljivost = idSifProfesionalneVestineVidljivost;
    }

    public String getIdSifOblastInteresovanjaVidljivost() {
        return idSifOblastInteresovanjaVidljivost;
    }

    public void setIdSifOblastInteresovanjaVidljivost(String idSifOblastInteresovanjaVidljivost) {
        this.idSifOblastInteresovanjaVidljivost = idSifOblastInteresovanjaVidljivost;
    }

    public String getIdSifDrzavljanstvoVidljivost() {
        return idSifDrzavljanstvoVidljivost;
    }

    public void setIdSifDrzavljanstvoVidljivost(String idSifDrzavljanstvoVidljivost) {
        this.idSifDrzavljanstvoVidljivost = idSifDrzavljanstvoVidljivost;
    }

    public String getIdSifMestaVidljivost() {
        return idSifMestaVidljivost;
    }

    public void setIdSifMestaVidljivost(String idSifMestaVidljivost) {
        this.idSifMestaVidljivost = idSifMestaVidljivost;
    }

    public String getIdSifDrzavaVidljivost() {
        return idSifDrzavaVidljivost;
    }

    public void setIdSifDrzavaVidljivost(String idSifDrzavaVidljivost) {
        this.idSifDrzavaVidljivost = idSifDrzavaVidljivost;
    }

    public String getBrojPrebivalistaVidljivost() {
        return brojPrebivalistaVidljivost;
    }

    public void setBrojPrebivalistaVidljivost(String brojPrebivalistaVidljivost) {
        this.brojPrebivalistaVidljivost = brojPrebivalistaVidljivost;
    }

    public String getIdSifUlicaVidljivost() {
        return idSifUlicaVidljivost;
    }

    public void setIdSifUlicaVidljivost(String idSifUlicaVidljivost) {
        this.idSifUlicaVidljivost = idSifUlicaVidljivost;
    }

    public String getTelefonVidljivost() {
        return telefonVidljivost;
    }

    public void setTelefonVidljivost(String telefonVidljivost) {
        this.telefonVidljivost = telefonVidljivost;
    }

    public String getPolVidljivost() {
        return polVidljivost;
    }

    public void setPolVidljivost(String polVidljivost) {
        this.polVidljivost = polVidljivost;
    }

    public String getDatumRodjenjaVidljivost() {
        return datumRodjenjaVidljivost;
    }

    public void setDatumRodjenjaVidljivost(String datumRodjenjaVidljivost) {
        this.datumRodjenjaVidljivost = datumRodjenjaVidljivost;
    }

    public String getSrednjeImeVidljivost() {
        return srednjeImeVidljivost;
    }

    public void setSrednjeImeVidljivost(String srednjeImeVidljivost) {
        this.srednjeImeVidljivost = srednjeImeVidljivost;
    }

    public String getMailVidljivost() {
        return mailVidljivost;
    }

    public void setMailVidljivost(String mailVidljivost) {
        this.mailVidljivost = mailVidljivost;
    }

    public String getPrezimeVidljivost() {
        return prezimeVidljivost;
    }

    public void setPrezimeVidljivost(String prezimeVidljivost) {
        this.prezimeVidljivost = prezimeVidljivost;
    }

    public String getImeVidljivost() {
        return imeVidljivost;
    }

    public void setImeVidljivost(String imeVidljivost) {
        this.imeVidljivost = imeVidljivost;
    }

    
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getTekstOKomp() {
        return tekstOKomp;
    }

    public void setTekstOKomp(String tekstOKomp) {
        this.tekstOKomp = tekstOKomp;
    }

    public String getJavnoIme() {
        return javnoIme;
    }

    public void setJavnoIme(String javnoIme) {
        this.javnoIme = javnoIme;
    }

    public String getNazivDrzave() {
        return nazivDrzave;
    }

    public void setNazivDrzave(String nazivDrzave) {
        this.nazivDrzave = nazivDrzave;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }
    
    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getUloga() {
        return uloga;
    }

    public void setUloga(String uloga) {
        this.uloga = uloga;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getSrednjeIme() {
        return srednjeIme;
    }

    public void setSrednjeIme(String srednjeIme) {
        this.srednjeIme = srednjeIme;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getKurs() {
        return kurs;
    }

    public void setKurs(String kurs) {
        this.kurs = kurs;
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getNazivDrzavljanstva() {
        return nazivDrzavljanstva;
    }

    public void setNazivDrzavljanstva(String nazivDrzavljanstva) {
        this.nazivDrzavljanstva = nazivDrzavljanstva;
    }

    public String getNazivUlice() {
        return nazivUlice;
    }

    public void setNazivUlice(String nazivUlice) {
        this.nazivUlice = nazivUlice;
    }

    public String getNazivMesta() {
        return nazivMesta;
    }

    public void setNazivMesta(String nazivMesta) {
        this.nazivMesta = nazivMesta;
    }

    public String getOblastPoslovanja() {
        return oblastPoslovanja;
    }

    public void setOblastPoslovanja(String oblastPoslovanja) {
        this.oblastPoslovanja = oblastPoslovanja;
    }

    public String getNazivKompanije() {
        return nazivKompanije;
    }

    public void setNazivKompanije(String nazivKompanije) {
        this.nazivKompanije = nazivKompanije;
    }

    public String getZaposlenjePozicija() {
        return zaposlenjePozicija;
    }

    public void setZaposlenjePozicija(String zaposlenjePozicija) {
        this.zaposlenjePozicija = zaposlenjePozicija;
    }

    public String getDiplomaFakultet() {
        return diplomaFakultet;
    }

    public void setDiplomaFakultet(String diplomaFakultet) {
        this.diplomaFakultet = diplomaFakultet;
    }

    public String getDiplomaOdsek() {
        return diplomaOdsek;
    }

    public void setDiplomaOdsek(String diplomaOdsek) {
        this.diplomaOdsek = diplomaOdsek;
    }

    public String getDiplomaNivoStudija() {
        return diplomaNivoStudija;
    }

    public void setDiplomaNivoStudija(String diplomaNivoStudija) {
        this.diplomaNivoStudija = diplomaNivoStudija;
    }

    public String getDiplomaGodinaUpisa() {
        return diplomaGodinaUpisa;
    }

    public void setDiplomaGodinaUpisa(String diplomaGodinaUpisa) {
        this.diplomaGodinaUpisa = diplomaGodinaUpisa;
    }

    public String getDiplomaGodinaZavrsetka() {
        return diplomaGodinaZavrsetka;
    }

    public void setDiplomaGodinaZavrsetka(String diplomaGodinaZavrsetka) {
        this.diplomaGodinaZavrsetka = diplomaGodinaZavrsetka;
    }

    public String getDiplomaZvanje() {
        return diplomaZvanje;
    }

    public void setDiplomaZvanje(String diplomaZvanje) {
        this.diplomaZvanje = diplomaZvanje;
    }

    public String getDiplomaUniverzitet() {
        return diplomaUniverzitet;
    }

    public void setDiplomaUniverzitet(String diplomaUniverzitet) {
        this.diplomaUniverzitet = diplomaUniverzitet;
    }

    public String getTrenutneStudijeUniverzitetNaziv() {
        return trenutneStudijeUniverzitetNaziv;
    }

    public void setTrenutneStudijeUniverzitetNaziv(String trenutneStudijeUniverzitetNaziv) {
        this.trenutneStudijeUniverzitetNaziv = trenutneStudijeUniverzitetNaziv;
    }

    public String getTrenutneStudijeFakultetNaziv() {
        return trenutneStudijeFakultetNaziv;
    }

    public void setTrenutneStudijeFakultetNaziv(String trenutneStudijeFakultetNaziv) {
        this.trenutneStudijeFakultetNaziv = trenutneStudijeFakultetNaziv;
    }

    public String getTrenutneStudijeDrzavaNaziv() {
        return trenutneStudijeDrzavaNaziv;
    }

    public void setTrenutneStudijeDrzavaNaziv(String trenutneStudijeDrzavaNaziv) {
        this.trenutneStudijeDrzavaNaziv = trenutneStudijeDrzavaNaziv;
    }

    public String getTrenutneStudijeMestoNaziv() {
        return trenutneStudijeMestoNaziv;
    }

    public void setTrenutneStudijeMestoNaziv(String trenutneStudijeMestoNaziv) {
        this.trenutneStudijeMestoNaziv = trenutneStudijeMestoNaziv;
    }

    public String getTrenutneStudijeNivo() {
        return trenutneStudijeNivo;
    }

    public void setTrenutneStudijeNivo(String trenutneStudijeNivo) {
        this.trenutneStudijeNivo = trenutneStudijeNivo;
    }

    public String getNazivVestine() {
        return nazivVestine;
    }

    public void setNazivVestine(String nazivVestine) {
        this.nazivVestine = nazivVestine;
    }

    public String getNazivInteresovanja() {
        return nazivInteresovanja;
    }

    public void setNazivInteresovanja(String nazivInteresovanja) {
        this.nazivInteresovanja = nazivInteresovanja;
    }

    public int getPIN() {
        return PIN;
    }

    public void setPIN(int PIN) {
        this.PIN = PIN;
    }

    public int getBrojZaposlenih() {
        return brojZaposlenih;
    }

    public void setBrojZaposlenih(int brojZaposlenih) {
        this.brojZaposlenih = brojZaposlenih;
    }

    public int getBrojPrebivalista() {
        return brojPrebivalista;
    }

    public void setBrojPrebivalista(int brojPrebivalista) {
        this.brojPrebivalista = brojPrebivalista;
    }

    public int getZaposlenjePocetakRada() {
        return zaposlenjePocetakRada;
    }

    public void setZaposlenjePocetakRada(int zaposlenjePocetakRada) {
        this.zaposlenjePocetakRada = zaposlenjePocetakRada;
    }

    public int getZaposlenjeKrajRada() {
        return zaposlenjeKrajRada;
    }

    public void setZaposlenjeKrajRada(int zaposlenjeKrajRada) {
        this.zaposlenjeKrajRada = zaposlenjeKrajRada;
    }

    public int getBrojSedista() {
        return brojSedista;
    }

    public void setBrojSedista(int brojSedista) {
        this.brojSedista = brojSedista;
    }

    public long getPIB() {
        return PIB;
    }

    public void setPIB(long PIB) {
        this.PIB = PIB;
    }

    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public LocalDate getTrenutneStudijeGodinaUpisa() {
        return trenutneStudijeGodinaUpisa;
    }

    public void setTrenutneStudijeGodinaUpisa(LocalDate trenutneStudijeGodinaUpisa) {
        this.trenutneStudijeGodinaUpisa = trenutneStudijeGodinaUpisa;
    }

    public String[] getProfesionalneVestineLista() {
        return profesionalneVestineLista;
    }

    public void setProfesionalneVestineLista(String[] profesionalneVestineLista) {
        this.profesionalneVestineLista = profesionalneVestineLista;
    }

    public String[] getOblastInteresovanjaLista() {
        return oblastInteresovanjaLista;
    }

    public void setOblastInteresovanjaLista(String[] oblastInteresovanjaLista) {
        this.oblastInteresovanjaLista = oblastInteresovanjaLista;
    }

    public String[] getNazivDrzavljanstvaLista() {
        return nazivDrzavljanstvaLista;
    }

    public void setNazivDrzavljanstvaLista(String[] nazivDrzavljanstvaLista) {
        this.nazivDrzavljanstvaLista = nazivDrzavljanstvaLista;
    }

    public String[] getUlicaNazivLista() {
        return ulicaNazivLista;
    }

    public void setUlicaNazivLista(String[] ulicaNazivLista) {
        this.ulicaNazivLista = ulicaNazivLista;
    }

    public String[] getMestoNazivLista() {
        return mestoNazivLista;
    }

    public void setMestoNazivLista(String[] mestoNazivLista) {
        this.mestoNazivLista = mestoNazivLista;
    }

    public String[] getImeKompanijeLista() {
        return imeKompanijeLista;
    }

    public void setImeKompanijeLista(String[] imeKompanijeLista) {
        this.imeKompanijeLista = imeKompanijeLista;
    }

    public String[] getPozicijaLista() {
        return pozicijaLista;
    }

    public void setPozicijaLista(String[] pozicijaLista) {
        this.pozicijaLista = pozicijaLista;
    }

    public String[] getFakultetLista() {
        return fakultetLista;
    }

    public void setFakultetLista(String[] fakultetLista) {
        this.fakultetLista = fakultetLista;
    }

    public String[] getUniverzitetLista() {
        return univerzitetLista;
    }

    public void setUniverzitetLista(String[] univerzitetLista) {
        this.univerzitetLista = univerzitetLista;
    }

    public String[] getDrzavaLista() {
        return drzavaLista;
    }

    public void setDrzavaLista(String[] drzavaLista) {
        this.drzavaLista = drzavaLista;
    }
    
    
    
}
