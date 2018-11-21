package controllers;

import beans.Korisnik;
import beans.Statistika;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DonutChartModel;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.PieChartModel;

@ManagedBean
@ViewScoped

public class UcitajStatistiku {

    private Map<Integer, BarChartModel> listaStatistikaZaPitanjaAnkete;
    private Map<Integer, DonutChartModel> listaStatistikaZaIntPitanjaAnkete;
    private Map<Integer, Map<String, HorizontalBarChartModel>> listaStatistikaZaKorisnikePoPitanjimaAnkete;
    private List<Statistika> opstaStatistikaZaAnketu;
    private Map<String, Statistika> listaStatistikaOStudentimaZaAdmina, listaStatistikaOKompanijamaZaAdmina;
    private Map<Integer, ArrayList<String>> listaStringOdgovoraNaPitanja;
    private String[] statistikaZaStudente = {"Zaposlenost", "Kurs", "Pol", "Mesto", "Nivo obrazovanja"};
    private String[] statistikaZaKompanije = {"Mesto", "Oblast poslovanja", "Broj zaposlenih"};
    private Statistika statistika;
    private String stavkaStatistikeZaStudente, stavkaStatistikeZaKompanije;
    private PieChartModel modelZaStatistiku, modelZaStatistikuZaAdmina;

    public Map<Integer, DonutChartModel> getListaStatistikaZaIntPitanjaAnkete() {
        return listaStatistikaZaIntPitanjaAnkete;
    }

    public void setListaStatistikaZaIntPitanjaAnkete(Map<Integer, DonutChartModel> listaStatistikaZaIntPitanjaAnkete) {
        this.listaStatistikaZaIntPitanjaAnkete = listaStatistikaZaIntPitanjaAnkete;
    }

    public Map<String, Statistika> getListaStatistikaOKompanijamaZaAdmina() {
        return listaStatistikaOKompanijamaZaAdmina;
    }

    public void setListaStatistikaOKompanijamaZaAdmina(Map<String, Statistika> listaStatistikaOKompanijamaZaAdmina) {
        this.listaStatistikaOKompanijamaZaAdmina = listaStatistikaOKompanijamaZaAdmina;
    }

    public Map<String, Statistika> getListaStatistikaOStudentimaZaAdmina() {
        return listaStatistikaOStudentimaZaAdmina;
    }

    public void setListaStatistikaOStudentimaZaAdmina(Map<String, Statistika> listaStatistikaOStudentimaZaAdmina) {
        this.listaStatistikaOStudentimaZaAdmina = listaStatistikaOStudentimaZaAdmina;
    }

    public Map<Integer, ArrayList<String>> getListaStringOdgovoraNaPitanja() {
        return listaStringOdgovoraNaPitanja;
    }

    public void setListaStringOdgovoraNaPitanja(Map<Integer, ArrayList<String>> listaStringOdgovoraNaPitanja) {
        this.listaStringOdgovoraNaPitanja = listaStringOdgovoraNaPitanja;
    }

    public PieChartModel getModelZaStatistiku() {
        return modelZaStatistiku;
    }

    public void setModelZaStatistiku(PieChartModel modelZaStatistiku) {
        this.modelZaStatistiku = modelZaStatistiku;
    }

    public PieChartModel getModelZaStatistikuZaAdmina() {
        return modelZaStatistikuZaAdmina;
    }

    public void setModelZaStatistikuZaAdmina(PieChartModel modelZaStatistikuZaAdmina) {
        this.modelZaStatistikuZaAdmina = modelZaStatistikuZaAdmina;
    }

    public Map<Integer, Map<String, HorizontalBarChartModel>> getListaStatistikaZaKorisnikePoPitanjimaAnkete() {
        return listaStatistikaZaKorisnikePoPitanjimaAnkete;
    }

    public Map<Integer, BarChartModel> getListaStatistikaZaPitanjaAnkete() {
        return listaStatistikaZaPitanjaAnkete;
    }

    public String getStavkaStatistikeZaStudente() {
        return stavkaStatistikeZaStudente;
    }

    public void setStavkaStatistikeZaStudente(String stavkaStatistikeZaStudente) {
        this.stavkaStatistikeZaStudente = stavkaStatistikeZaStudente;
    }

    public String getStavkaStatistikeZaKompanije() {
        return stavkaStatistikeZaKompanije;
    }

    public void setStavkaStatistikeZaKompanije(String stavkaStatistikeZaKompanije) {
        this.stavkaStatistikeZaKompanije = stavkaStatistikeZaKompanije;
    }

    public String[] getStatistikaZaKompanije() {
        return statistikaZaKompanije;
    }

    public void setStatistikaZaKompanije(String[] statistikaZaKompanije) {
        this.statistikaZaKompanije = statistikaZaKompanije;
    }

    public String[] getStatistikaZaStudente() {
        return statistikaZaStudente;
    }

    public void setStatistikaZaStudente(String[] statistikaZaStudente) {
        this.statistikaZaStudente = statistikaZaStudente;
    }

    public List<Statistika> getOpstaStatistikaZaAnketu() {
        return opstaStatistikaZaAnketu;
    }

    public void setOpstaStatistikaZaAnketu(List<Statistika> opstaStatistikaZaAnketu) {
        this.opstaStatistikaZaAnketu = opstaStatistikaZaAnketu;
    }

    public Statistika getStatistika() {
        return statistika;
    }

    public void setStatistika(Statistika statistika) {
        this.statistika = statistika;
    }

    public void ucitajStatistikuOKorisnicima() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

            int idStatistika = Integer.parseInt(params.get("idStatistika"));

            ResultSet rs = stm.executeQuery("select ukupanBrojKorisnika, naziviStavke, vrednostiStavke, statistikaZa, idStatistika, nazivStatistike, tekstStatistike, a.javnoIme, nivoVidljivosti, arhiviranaStatistika, metaStatistike, stavkaStatistike, datumStatistike from statistika s, vidljivost v, administrator a where s.idStatistika=" + idStatistika + " && s.idVidljivost=v.idVidljivost && a.idKorisnik=s.idKorisnik");

            while (rs.next()) {
                statistika = new Statistika();
                statistika.setIdStatistike(rs.getInt("idStatistike"));
                statistika.setNazivStatistike(rs.getString("nazivStatistike"));
                statistika.setTekstStatistike(rs.getString("tekstStatistike"));
                statistika.setAutorStatistike(rs.getString("javnoIme"));
                statistika.setArhiviranaStatistika(rs.getString("arhiviranaStatistika"));
                statistika.setNivoVidljivosti(rs.getString("nivoVidljivosti"));
                statistika.setStavkaStatistike(rs.getString("stavkaStatistike"));
                statistika.setStatistikaZa(rs.getString("statistikaZa"));
                statistika.setUkupanBrojKorisnika(rs.getInt("ukupanBrojKorisnika"));
                statistika.setListaNazivaStavke(rs.getString("naziviStavke"));
                statistika.setListaVrednostiStavke(rs.getString("vrednostiStavke"));
                statistika.setMetaStatistike(rs.getString("metaStatistike"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumPostavljanja = LocalDateTime.parse(rs.getTimestamp("datumStatistike").toString(), datePattern);
                statistika.setDatumKreiranja(rs.getTimestamp("datumStatistike").toString());
                statistika.setDatumKreiranjaStatistike(datumPostavljanja);
            }

            PieChartModel model = new PieChartModel();

            Map<String, Integer> kolicinaKorisnikaPoStavkamaMapa = new HashMap();

            StringTokenizer vrednostiZaSvakuStavku = new StringTokenizer(statistika.getListaVrednostiStavke(), ", ");
            StringTokenizer naziviSvakeStavke = new StringTokenizer(statistika.getListaNazivaStavke(), ", ");

            int brojElemenata = vrednostiZaSvakuStavku.countTokens();

            for (int i = 1; i <= brojElemenata; i++) {
                kolicinaKorisnikaPoStavkamaMapa.put(naziviSvakeStavke.nextToken(), Integer.parseInt(vrednostiZaSvakuStavku.nextToken()));
            }

            for (Map.Entry<String, Integer> stavkeStatistike : kolicinaKorisnikaPoStavkamaMapa.entrySet()) {
                model.set(stavkeStatistike.getKey(), stavkeStatistike.getValue());
            }

            model.setTitle("Broj korisnika");
            model.setLegendPosition("w");
            model.setShadow(true);

            modelZaStatistiku = model;
        } catch (SQLException ex) {
            Logger.getLogger(UcitajKomentareZaDiskusiju.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajStatistikuZaAnketu() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();
//
//            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

            int idAnketa = Integer.parseInt(params.get("idAnketa"));

            ResultSet idKorisnika = stm.executeQuery("select distinct idKorisnik from odgovoriankete where idAnkete=" + idAnketa);
            int brojKorisnika = 0;

            while (idKorisnika.next()) {
                brojKorisnika++;
            }

            ResultSet brojPitanjaAnkete = stm.executeQuery("select MAX(redniBrojPitanja) from pitanjaAnkete where idAnkete=" + idAnketa);
            brojPitanjaAnkete.next();
            int brojpitanja = brojPitanjaAnkete.getInt("redniBrojPitanja");

            for (int i = 1; i <= brojpitanja; i++) {
                ResultSet pitanje = stm.executeQuery("select tipPitanja, pa.ponudjeniOdgovori from pitanjaAnkete pa where redniBrojPitanja=" + i + " && idAnkete=" + idAnketa);
                pitanje.next();

                String tipPitanja = pitanje.getString("tipPitanja");
                StringTokenizer ponudjeniOdgovoriZaPitanje = new StringTokenizer(pitanje.getString("ponudjeniOdgovori"), ", ");

                ResultSet odgovoriNaPitanje = stm.executeQuery("select odgovoriAnkete from odgovoriAnkete oa, pitanjaAnkete pa where oa.idPitanjaAnkete=pa.idPitanjaAnkete && pa.redniBrojPitanja=" + i + " && oa.idAnkete=" + idAnketa);

                BarChartModel model = new BarChartModel();
                DonutChartModel modelZaIntPitanje = new DonutChartModel();
                
                if (tipPitanja.equals("ponudjeniOdgovori") || tipPitanja.equals("daNe")) {

                    ChartSeries chartPonudjeniOdgovori = new ChartSeries();
                    chartPonudjeniOdgovori.setLabel("Broj korisnika");

                    Map<String, Integer> odgovoriMapa = new HashMap();

                    while (ponudjeniOdgovoriZaPitanje.hasMoreTokens()) {
                        odgovoriMapa.put(ponudjeniOdgovoriZaPitanje.nextToken(), 0);
                    }

                    while (odgovoriNaPitanje.next()) {
                        String odgovorNaPitanje = odgovoriNaPitanje.getString("odgovoriAnkete");
                        odgovoriMapa.put(odgovorNaPitanje, odgovoriMapa.get(odgovorNaPitanje) + 1);
                    }

                    for (Map.Entry<String, Integer> ponudjeniOdgovori : odgovoriMapa.entrySet()) {
                        chartPonudjeniOdgovori.set(ponudjeniOdgovori.getKey(), ponudjeniOdgovori.getValue());
                    }

                    model.addSeries(chartPonudjeniOdgovori);
                    //model.setTitle("Bar Chart");
                    //model.setLegendPosition("ne");
                    model.setSeriesColors("#17BDB8");
                    Axis xAxis = model.getAxis(AxisType.X);
                    xAxis.setLabel("Odgovor");
                    Axis yAxis = model.getAxis(AxisType.Y);
                    yAxis.setLabel("Broj korisnika");
                    yAxis.setMin(0);
                    yAxis.setMax(brojKorisnika);

                    listaStatistikaZaPitanjaAnkete.put(i, model);

                } else if (tipPitanja.equals("int")) {

                    Number number;
                    Map<String, Number> chartInt = new LinkedHashMap();
                    
                    Map<String, Integer> odgovoriMapa = new HashMap();

                    while (odgovoriNaPitanje.next()) {
                        String odgovorNaPitanje = odgovoriNaPitanje.getString("odgovoriAnkete");
                        odgovoriMapa.put(odgovorNaPitanje, odgovoriMapa.containsKey(odgovorNaPitanje) ? odgovoriMapa.get(odgovorNaPitanje) + 1 : 1);
                    }

                    for (Map.Entry<String, Integer> ponudjeniOdgovori : odgovoriMapa.entrySet()) {
                        number = (Number) ponudjeniOdgovori.getValue();
                        chartInt.put(ponudjeniOdgovori.getKey(), number);
                    }

                    modelZaIntPitanje.setLegendPosition("w");
                    modelZaIntPitanje.setExtender("donutCharts");
                    modelZaIntPitanje.setShowDataLabels(true);
                    modelZaIntPitanje.setShadow(true);
                    modelZaIntPitanje.setSliceMargin(5);

                    modelZaIntPitanje.addCircle(chartInt);

                    listaStatistikaZaIntPitanjaAnkete.put(i, modelZaIntPitanje);
                } else if (tipPitanja.equals("string")) {

                    ArrayList<String> odgovoriStringLista = new ArrayList<>();

                    while (odgovoriNaPitanje.next()) {
                        String odgovorNaPitanje = odgovoriNaPitanje.getString("odgovoriAnkete");
                        odgovoriStringLista.add(odgovorNaPitanje);
                    }

                    listaStringOdgovoraNaPitanja.put(i, odgovoriStringLista);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajKomentareZaDiskusiju.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajStatistikuOKorisnicimaZaAnketu() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

            int idAnketa = Integer.parseInt(params.get("idAnketa"));

            ResultSet idKorisnika = stm.executeQuery("select distinct idKorisnik from odgovoriankete where idAnkete=" + idAnketa);
            int brojKorisnika = 0;

            while (idKorisnika.next()) {
                brojKorisnika++;
            }

            ResultSet brojPitanjaAnkete = stm.executeQuery("select MAX(redniBrojPitanja) from pitanjaAnkete where idAnkete=" + idAnketa);
            brojPitanjaAnkete.next();
            int brojpitanja = brojPitanjaAnkete.getInt("redniBrojPitanja");

            ResultSet statistikaOStudentima = stm.executeQuery("select k.uloga, tipPitanja, redniBrojPitanja from korisnik k, ankete a, pitanjaAnkete pa, odgovoriAnkete oa where k.idKorisnik=oa.idKorisnik && a.idAnkete=pa.idAnkete && a.idAnkete=oa.idAnkete && a.idAnkete=" + idAnketa + " && pa.idPitanjaAnkete=oa.idPitanjaAnkete order by pa.redniBrojPitanja");
            statistikaOStudentima.next();

            String vidljivostRezultata = statistikaOStudentima.getString("vidljivostRezultata");
            String autor = statistikaOStudentima.getString("autorAnkete");

            if (vidljivostRezultata.equals("true") || korisnik.getJavnoIme().equals(autor)) {
                for (int i = 1; i <= brojpitanja; i++) {

                    Map<String, HorizontalBarChartModel> mapaZaStavkuStudenata = new HashMap<>();

                    ResultSet pitanje = stm.executeQuery("select tipPitanja, ponudjeniOdgovori from pitanjaAnkete pa where redniBrojPitanja=" + i + " && idAnkete=" + idAnketa);
                    pitanje.next();

                    String tipPitanja = pitanje.getString("tipPitanja");
                    StringTokenizer ponudjeniOdgovoriZaPitanje = new StringTokenizer(pitanje.getString("ponudjeniOdgovori"), ", ");

                    ResultSet statistikaOKorisnicimaZaPitanje = stm.executeQuery("select oa.odgovoriAnkete, oa.idPitanjaAnkete, pol as Pol, kurs as Kurs, status as Zaposlenost, sm.nazivMesta as Mesto, nivoObrazovanja as 'Nivo obrazovanja' from student s, sifMesta sm, sifUlica su, odgovoriAnkete oa, pitanjaAnkete pa where s.idSifUlica=su.idSifUlica && su.idSifMesta=sm.idSifMesta && oa.idKorisnik=s.idKorisnik && oa.idAnkete=pa.idAnkete && pa.redniBrojPitanja=" + i + " && oa.idAnkete=" + idAnketa + " && pa.idPitanjaAnkete=oa.idPitanjaAnkete");

                    if (tipPitanja.equals("ponudjeniOdgovori") || tipPitanja.equals("daNe")) {
                        for (String stavkeStatistikeZaStudente : statistikaZaStudente) {

                            HorizontalBarChartModel model = new HorizontalBarChartModel();

                            Map<String, Map<String, Integer>> konacnaStatistikaMapa = new HashMap<>();

                            Map<String, ChartSeries> listaChartovaSaVrednoscuStavkiOStudentima = new HashMap<>();

                            while (ponudjeniOdgovoriZaPitanje.hasMoreTokens()) {

                                Map<String, Integer> statistikaZaStavkuMapa = new HashMap();

                                while (statistikaOKorisnicimaZaPitanje.next()) {
                                    String statistikaZaStavku = statistikaOKorisnicimaZaPitanje.getString(stavkeStatistikeZaStudente);
                                    statistikaZaStavkuMapa.put(statistikaZaStavku, statistikaZaStavkuMapa.containsKey(statistikaZaStavku) ? statistikaZaStavkuMapa.get(statistikaZaStavku) + 1 : 1);
                                }

                                for (Map.Entry<String, Integer> vrednostStavkeOStudentu : statistikaZaStavkuMapa.entrySet()) {
                                    if (!listaChartovaSaVrednoscuStavkiOStudentima.containsKey(vrednostStavkeOStudentu.getKey())) {
                                        ChartSeries chartVrednostStavkeOStudentu = new ChartSeries();

                                        chartVrednostStavkeOStudentu.setLabel(vrednostStavkeOStudentu.getKey());
                                        chartVrednostStavkeOStudentu.set(ponudjeniOdgovoriZaPitanje.nextToken(), vrednostStavkeOStudentu.getValue());

                                        listaChartovaSaVrednoscuStavkiOStudentima.put(vrednostStavkeOStudentu.getKey(), chartVrednostStavkeOStudentu);
                                    } else {
                                        listaChartovaSaVrednoscuStavkiOStudentima.get(vrednostStavkeOStudentu.getKey()).set(ponudjeniOdgovoriZaPitanje.nextToken(), vrednostStavkeOStudentu.getValue());
                                    }
                                }

                                konacnaStatistikaMapa.put(ponudjeniOdgovoriZaPitanje.nextToken(), statistikaZaStavkuMapa);
                            }

                            for (Map.Entry<String, ChartSeries> chartoviZaModel : listaChartovaSaVrednoscuStavkiOStudentima.entrySet()) {
                                model.addSeries(chartoviZaModel.getValue());
                            }

                            model.setTitle(stavkeStatistikeZaStudente);
                            model.setLegendPosition("e");

                            switch (stavkeStatistikeZaStudente) {
                                case "Zaposlenost": {
                                    model.setSeriesColors("#0c5105");
                                }
                                case "Pol": {
                                    model.setSeriesColors("#460782");
                                }
                                case "Mesto": {
                                    model.setSeriesColors("#820740");
                                }
                                case "Nivo obrazovanja": {
                                    model.setSeriesColors("#ccc20c");
                                }
                                case "Kurs": {
                                    model.setSeriesColors("#077d82");
                                }
                            }

                            model.setStacked(true);

                            Axis xAxis = model.getAxis(AxisType.X);
                            xAxis.setLabel("Broj korisnika");
                            xAxis.setMin(0);
                            xAxis.setMax(brojKorisnika);

                            Axis yAxis = model.getAxis(AxisType.Y);
                            yAxis.setLabel("Odgovor");

                            mapaZaStavkuStudenata.put(stavkeStatistikeZaStudente, model);
                        }
                    } else if (tipPitanja.equals("int")) {
                        for (String stavkeStatistikeZaStudente : statistikaZaStudente) {

                            HorizontalBarChartModel model = new HorizontalBarChartModel();

                            Map<String, Map<String, Integer>> konacnaStatistikaMapa = new HashMap<>();

                            Map<String, ChartSeries> listaChartovaSaVrednoscuStavkiOStudentima = new HashMap<>();
                            
                            Map<String, Integer> odgovoriMapa = new HashMap<>();

                            while (statistikaOKorisnicimaZaPitanje.next()) {
                                String odgovorNaPitanje = statistikaOKorisnicimaZaPitanje.getString("odgovoriAnkete");
                                odgovoriMapa.put((odgovorNaPitanje), odgovoriMapa.containsKey(odgovorNaPitanje) ? odgovoriMapa.get(odgovorNaPitanje) + 1 : 1);
                            }

                            for(Map.Entry<String, Integer> odgovoriStudenata : odgovoriMapa.entrySet()) {

                                Map<String, Integer> statistikaZaStavkuMapa = new HashMap();

                                while (statistikaOKorisnicimaZaPitanje.next()) {
                                    String statistikaZaStavku = statistikaOKorisnicimaZaPitanje.getString(stavkeStatistikeZaStudente);
                                    statistikaZaStavkuMapa.put(statistikaZaStavku, statistikaZaStavkuMapa.containsKey(statistikaZaStavku) ? statistikaZaStavkuMapa.get(statistikaZaStavku) + 1 : 1);
                                }

                                for (Map.Entry<String, Integer> vrednostStavkeOStudentu : statistikaZaStavkuMapa.entrySet()) {
                                    if (!listaChartovaSaVrednoscuStavkiOStudentima.containsKey(vrednostStavkeOStudentu.getKey())) {
                                        ChartSeries chartVrednostStavkeOStudentu = new ChartSeries();

                                        chartVrednostStavkeOStudentu.setLabel(vrednostStavkeOStudentu.getKey());
                                        chartVrednostStavkeOStudentu.set(odgovoriStudenata.getKey(), vrednostStavkeOStudentu.getValue());

                                        listaChartovaSaVrednoscuStavkiOStudentima.put(vrednostStavkeOStudentu.getKey(), chartVrednostStavkeOStudentu);
                                    } else {
                                        listaChartovaSaVrednoscuStavkiOStudentima.get(vrednostStavkeOStudentu.getKey()).set(odgovoriStudenata.getKey(), vrednostStavkeOStudentu.getValue());
                                    }
                                }

                                konacnaStatistikaMapa.put(odgovoriStudenata.getKey(), statistikaZaStavkuMapa);
                            }

                            for (Map.Entry<String, ChartSeries> chartoviZaModel : listaChartovaSaVrednoscuStavkiOStudentima.entrySet()) {
                                model.addSeries(chartoviZaModel.getValue());
                            }

                            model.setTitle(stavkeStatistikeZaStudente);
                            model.setLegendPosition("e");

                            switch (stavkeStatistikeZaStudente) {
                                case "Zaposlenost": {
                                    model.setSeriesColors("#0c5105");
                                }
                                case "Pol": {
                                    model.setSeriesColors("#460782");
                                }
                                case "Mesto": {
                                    model.setSeriesColors("#820740");
                                }
                                case "Nivo obrazovanja": {
                                    model.setSeriesColors("#ccc20c");
                                }
                                case "Kurs": {
                                    model.setSeriesColors("#077d82");
                                }
                            }

                            model.setStacked(true);

                            Axis xAxis = model.getAxis(AxisType.X);
                            xAxis.setLabel("Broj korisnika");
                            xAxis.setMin(0);
                            xAxis.setMax(brojKorisnika);

                            Axis yAxis = model.getAxis(AxisType.Y);
                            yAxis.setLabel("Odgovor");

                            mapaZaStavkuStudenata.put(stavkeStatistikeZaStudente, model);
                        }
                    }

                    listaStatistikaZaKorisnikePoPitanjimaAnkete.put(i, mapaZaStavkuStudenata);

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajKomentareZaDiskusiju.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ucitajStatistikuPoStavciZaAdministratora() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            ResultSet statistikaOStudentima = stm.executeQuery("select pol as Pol, kurs as Kurs, status as Zaposlenost, sm.nazivMesta as Mesto, nivoObrazovanja as 'Nivo obrazovanja' from student s, sifMesta sm, sifUlica su where s.idSifUlica=su.idSifUlica && su.idSifMesta=sm.idSifMesta");

            for (String stavkeStatistikeZaStudente : statistikaZaStudente) {
                Statistika s = new Statistika();
                s.setArhiviranaStatistika("false");
                s.setAutorStatistike(korisnik.getJavnoIme());
                s.setDatumKreiranjaStatistike(LocalDateTime.now());
                s.setDatumKreiranja(LocalDateTime.now().toString());
                s.setStatistikaZa("studente");

                Map<String, Integer> stavkaStatistikeMapa = new HashMap<>();

                while (statistikaOStudentima.next()) {
                    String statistikaZaStavku = statistikaOStudentima.getString(stavkeStatistikeZaStudente);
                    stavkaStatistikeMapa.put(statistikaZaStavku, stavkaStatistikeMapa.containsKey(statistikaZaStavku) ? stavkaStatistikeMapa.get(statistikaZaStavku) + 1 : 1);
                }

                String listaVrednostiStavke = "", listaNazivaStavke = "";
                int ukupanBrojKorisnika = 0;

                for (Map.Entry<String, Integer> vrednostStavkeOStudentu : stavkaStatistikeMapa.entrySet()) {
                    listaVrednostiStavke = listaVrednostiStavke.concat(vrednostStavkeOStudentu + ", ");
                    listaNazivaStavke = listaNazivaStavke.concat(vrednostStavkeOStudentu.getKey() + ", ");
                    ukupanBrojKorisnika = ukupanBrojKorisnika + vrednostStavkeOStudentu.getValue();
                }

                listaVrednostiStavke = listaVrednostiStavke.substring(0, listaVrednostiStavke.length() - 2);
                listaNazivaStavke = listaNazivaStavke.substring(0, listaVrednostiStavke.length() - 2);

                s.setListaVrednostiStavke(listaVrednostiStavke);
                s.setListaNazivaStavke(listaNazivaStavke);
                s.setStavkaStatistike(stavkeStatistikeZaStudente);
                s.setUkupanBrojKorisnika(ukupanBrojKorisnika);

                listaStatistikaOStudentimaZaAdmina.put(stavkeStatistikeZaStudente, s);
            }

            ResultSet statistikaOKompanijama = stm.executeQuery("select nazivMesta as Mesto, oblastPoslovanja as 'Oblast poslovanja', brojZaposlenih as 'Broj zaposlenih' from kompanija kom, sifMesta sm, sifUlica su where kom.idSifUlica=su.idSifUlica && su.idSifMesta=sm.idSifMesta");

            for (String stavkeStatistikeZaKompanije : statistikaZaKompanije) {
                Statistika s = new Statistika();
                s.setArhiviranaStatistika("false");
                s.setAutorStatistike(korisnik.getJavnoIme());
                s.setDatumKreiranjaStatistike(LocalDateTime.now());
                s.setDatumKreiranja(LocalDateTime.now().toString());
                s.setStatistikaZa("kompanije");

                Map<String, Integer> stavkaStatistikeMapa = new HashMap<>();

                while (statistikaOKompanijama.next()) {
                    String statistikaZaStavku = statistikaOKompanijama.getString(stavkeStatistikeZaKompanije);
                    stavkaStatistikeMapa.put(statistikaZaStavku, stavkaStatistikeMapa.containsKey(statistikaZaStavku) ? stavkaStatistikeMapa.get(statistikaZaStavku) + 1 : 1);
                }

                String listaVrednostiStavke = "", listaNazivaStavke = "";
                int ukupanBrojKorisnika = 0;

                for (Map.Entry<String, Integer> vrednostStavkeOKompaniji : stavkaStatistikeMapa.entrySet()) {
                    listaVrednostiStavke = listaVrednostiStavke.concat(vrednostStavkeOKompaniji + ", ");
                    listaNazivaStavke = listaNazivaStavke.concat(vrednostStavkeOKompaniji.getKey() + ", ");
                    ukupanBrojKorisnika = ukupanBrojKorisnika + vrednostStavkeOKompaniji.getValue();
                }

                listaVrednostiStavke = listaVrednostiStavke.substring(0, listaVrednostiStavke.length() - 2);
                listaNazivaStavke = listaNazivaStavke.substring(0, listaVrednostiStavke.length() - 2);

                s.setListaVrednostiStavke(listaVrednostiStavke);
                s.setListaNazivaStavke(listaNazivaStavke);
                s.setStavkaStatistike(stavkeStatistikeZaKompanije);
                s.setUkupanBrojKorisnika(ukupanBrojKorisnika);

                listaStatistikaOStudentimaZaAdmina.put(stavkeStatistikeZaKompanije, s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajKomentareZaDiskusiju.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void prikaziStatistikuOStudentimaZaStavkuZaAdmina() {
        PieChartModel statistikaOKorisnicimaZaAdmina = new PieChartModel();

        Map<String, Integer> kolicinaKorisnikaPoStavkamaMapa = new HashMap();

        StringTokenizer vrednostiZaSvakuStavku = new StringTokenizer(listaStatistikaOStudentimaZaAdmina.get(stavkaStatistikeZaStudente).getListaVrednostiStavke(), ", ");
        StringTokenizer naziviSvakeStavke = new StringTokenizer(listaStatistikaOStudentimaZaAdmina.get(stavkaStatistikeZaStudente).getListaNazivaStavke(), ", ");

        int brojElemenata = vrednostiZaSvakuStavku.countTokens();

        for (int i = 1; i <= brojElemenata; i++) {
            kolicinaKorisnikaPoStavkamaMapa.put(naziviSvakeStavke.nextToken(), Integer.parseInt(vrednostiZaSvakuStavku.nextToken()));
        }

        for (Map.Entry<String, Integer> stavkeStatistike : kolicinaKorisnikaPoStavkamaMapa.entrySet()) {
            statistikaOKorisnicimaZaAdmina.set(stavkeStatistike.getKey(), stavkeStatistike.getValue());
        }

        statistikaOKorisnicimaZaAdmina.setTitle("Broj korisnika");
        statistikaOKorisnicimaZaAdmina.setLegendPosition("w");
        statistikaOKorisnicimaZaAdmina.setShadow(true);

        modelZaStatistikuZaAdmina = statistikaOKorisnicimaZaAdmina;
    }

    public void prikaziStatistikuOKompanijamaZaStavkuZaAdmina() {
        PieChartModel statistikaOKorisnicimaZaAdmina = new PieChartModel();

        Map<String, Integer> kolicinaKorisnikaPoStavkamaMapa = new HashMap();

        StringTokenizer vrednostiZaSvakuStavku = new StringTokenizer(listaStatistikaOKompanijamaZaAdmina.get(stavkaStatistikeZaKompanije).getListaVrednostiStavke(), ", ");
        StringTokenizer naziviSvakeStavke = new StringTokenizer(listaStatistikaOKompanijamaZaAdmina.get(stavkaStatistikeZaKompanije).getListaNazivaStavke(), ", ");

        int brojElemenata = vrednostiZaSvakuStavku.countTokens();

        for (int i = 1; i <= brojElemenata; i++) {
            kolicinaKorisnikaPoStavkamaMapa.put(naziviSvakeStavke.nextToken(), Integer.parseInt(vrednostiZaSvakuStavku.nextToken()));
        }

        for (Map.Entry<String, Integer> stavkeStatistike : kolicinaKorisnikaPoStavkamaMapa.entrySet()) {
            statistikaOKorisnicimaZaAdmina.set(stavkeStatistike.getKey(), stavkeStatistike.getValue());
        }

        statistikaOKorisnicimaZaAdmina.setTitle("Broj korisnika");
        statistikaOKorisnicimaZaAdmina.setLegendPosition("w");
        statistikaOKorisnicimaZaAdmina.setShadow(true);

        modelZaStatistikuZaAdmina = statistikaOKorisnicimaZaAdmina;
    }
}
