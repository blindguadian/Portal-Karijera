package controllers;

import beans.Anketa;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

@ManagedBean
@ViewScoped

public class DodavanjeStatistike {

    private String nazivStatistike, tekstStatistike, nivoVidljivosti, metaStatistike, stavkaStatistike, statistikaZa;
    private int idAnketeZaObjavu;
    private String[] listaStatistikaZa = {"Studenti", "Kompanije", "Anketa"}, listaStavkiZaStudente = {"Mesto", "Zaposlenost", "Kurs", "Pol", "Nivo obrazovanja"}, listaStavkiZaKompanije = {"Mesto", "Oblast poslovanja"}, listaStavkiZaAnketu = {"Mesto", "Zaposlenost", "Kurs", "Pol", "Nivo obrazovanja", "Odziv"};
    private String[] nivoiVidljivosti = {
        "Studenti odredjenog kursa", "Svi studenti", "Studenti koji su rezultat pretrage", "Formirana grupa studenata", "Svi korisnici", "Diplomirani studenti odredjenog fakulteta", "Odredjena kompanija", "Sve kompanije"};
    private List<String> listaKorisnikaZaStatistiku;
    private List<Anketa> listaAnketa;
    private BarChartModel modelZaStatistiku;
    private HashMap<String, BarChartModel> listaModelaZaAnketu;

    public HashMap<String, BarChartModel> getListaModelaZaAnketu() {
        return listaModelaZaAnketu;
    }

    public void setListaModelaZaAnketu(HashMap<String, BarChartModel> listaModelaZaAnketu) {
        this.listaModelaZaAnketu = listaModelaZaAnketu;
    }

    public int getIdAnketeZaObjavu() {
        return idAnketeZaObjavu;
    }

    public void setIdAnketeZaObjavu(int idAnketeZaObjavu) {
        this.idAnketeZaObjavu = idAnketeZaObjavu;
    }

    public String[] getListaStavkiZaAnketu() {
        return listaStavkiZaAnketu;
    }

    public void setListaStavkiZaAnketu(String[] listaStavkiZaAnketu) {
        this.listaStavkiZaAnketu = listaStavkiZaAnketu;
    }

    public List<Anketa> getListaAnketa() {
        return listaAnketa;
    }

    public void setListaAnketa(List<Anketa> listaAnketa) {
        this.listaAnketa = listaAnketa;
    }

    public BarChartModel getModelZaStatistiku() {
        return modelZaStatistiku;
    }

    public void setModelZaStatistiku(BarChartModel modelZaStatistiku) {
        this.modelZaStatistiku = modelZaStatistiku;
    }

    public String[] getListaStavkiZaStudente() {
        return listaStavkiZaStudente;
    }

    public void setListaStavkiZaStudente(String[] listaStavkiZaStudente) {
        this.listaStavkiZaStudente = listaStavkiZaStudente;
    }

    public String[] getListaStavkiZaKompanije() {
        return listaStavkiZaKompanije;
    }

    public void setListaStavkiZaKompanije(String[] listaStavkiZaKompanije) {
        this.listaStavkiZaKompanije = listaStavkiZaKompanije;
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

    public String getNivoVidljivosti() {
        return nivoVidljivosti;
    }

    public void setNivoVidljivosti(String nivoVidljivosti) {
        this.nivoVidljivosti = nivoVidljivosti;
    }

    public String getMetaStatistike() {
        return metaStatistike;
    }

    public void setMetaStatistike(String metaStatistike) {
        this.metaStatistike = metaStatistike;
    }

    public String getStavkaStatistike() {
        return stavkaStatistike;
    }

    public void setStavkaStatistike(String stavkaStatistike) {
        this.stavkaStatistike = stavkaStatistike;
    }

    public String getStatistikaZa() {
        return statistikaZa;
    }

    public void setStatistikaZa(String statistikaZa) {
        this.statistikaZa = statistikaZa;
    }

    public String[] getListaStatistikaZa() {
        return listaStatistikaZa;
    }

    public void setListaStatistikaZa(String[] listaStatistikaZa) {
        this.listaStatistikaZa = listaStatistikaZa;
    }

    public String[] getNivoiVidljivosti() {
        return nivoiVidljivosti;
    }

    public void setNivoiVidljivosti(String[] nivoiVidljivosti) {
        this.nivoiVidljivosti = nivoiVidljivosti;
    }

    public List<String> getListaKorisnikaZaStatistiku() {
        return listaKorisnikaZaStatistiku;
    }

    public void setListaKorisnikaZaStatistiku(List<String> listaKorisnikaZaStatistiku) {
        this.listaKorisnikaZaStatistiku = listaKorisnikaZaStatistiku;
    }

    public void ucitajAnkete() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);

            PreparedStatement ps = conn.prepareStatement("select * from ankete");

            ResultSet ankete = ps.executeQuery();

            while (ankete.next()) {
                Anketa a = new Anketa();
                a.setArhiviranaAnketa(ankete.getString("arhiviranaAnketa"));
                a.setAutorAnkete(ankete.getString("autorAnkete"));
                a.setIdAnketa(ankete.getInt("idAnketa"));
                a.setIdObjave(ankete.getInt("idObjave"));
                a.setMetaAnkete(ankete.getString("metaAnkete"));
                a.setNazivAnkete(ankete.getString("nazivAnkete"));
                a.setNivoVidljivosti(ankete.getString("nivoVidljivosti"));
                a.setTekstAnkete(ankete.getString("tekstAnkete"));
                a.setVidljivostRezultata(ankete.getString("vidljivostRezultata"));

                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss.S");
                LocalDateTime datumTrajanja = LocalDateTime.parse(ankete.getTimestamp("datumTrajanjaAnkete").toString(), datePattern);

                a.setDatumTrajanja(ankete.getTimestamp("datumTrajanjaAnkete").toString());
                a.setDatumTrajanjaAnkete(datumTrajanja);

                listaAnketa.add(a);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DodavanjeStatistike.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void objaviStatistiku() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);

            /* Display name / Values from the frontpage dropdown:
            StatistikaZa:
                -Stundeti - student
                -Kompanije - kompanija
                -Anketa - ankete
            StavkaStatistike:
                -Mesto - nazivMesta
                -Zaposlenost - status
                -Kurs - kurs
                -Pol - pol
                -Nivo obrazovanja - nivoObrazovanja
                -Oblast poslovanja - oblastPoslovanja
                -Odziv - tipUcesnika
             */
            PreparedStatement ps = conn.prepareStatement("select count(s.idKorisnik) as Broj korisnika, ? from ? where ? group by ?");

            BarChartModel model;
            ChartSeries chartPonudjeniOdgovori;

            if (statistikaZa.equals("ankete")) {
                for (String stavkeStatistikeZaAnketu : listaStavkiZaAnketu) {

                    ps.setString(1, stavkeStatistikeZaAnketu);
                    if (stavkaStatistike.equals("nazivMesta")) {
                        ps.setString(2, statistikaZa + " a, sifMesta sm, sifUlica su, ucestvuje_kreira uk, student s");
                        ps.setString(3, "sm.idSifMesta=su.idSifMesta && su.idSifUlica=s.idSifUlica && uk.idObjave=a.idObjave && a.idAnkete=" + idAnketeZaObjavu + " && s.idKorisnik=uk.idKorisnik");
                    } else {
                        ps.setString(2, statistikaZa + " a, ucestvuje_kreira uk, student s");
                        ps.setString(3, "uk.idObjave=a.idObjave && a.idAnkete=" + idAnketeZaObjavu + " && s.idKorisnik=uk.idKorisnik");
                    }
                    ps.setString(4, stavkaStatistike);

                    ResultSet statistikaAnkete = ps.executeQuery();

                    chartPonudjeniOdgovori = new ChartSeries();

                    //chartPonudjeniOdgovori.setLabel("Broj korisnika");
                    int ukupanBrojKorisnika = 0;

                    while (statistikaAnkete.next()) {
                        String pojedinacneStavkeZaStatistiku = statistikaAnkete.getString(stavkaStatistike);
                        int brojKorisnikaZaStavku = statistikaAnkete.getInt("Broj korisnika");

                        ukupanBrojKorisnika += brojKorisnikaZaStavku;

                        chartPonudjeniOdgovori.set(pojedinacneStavkeZaStatistiku, brojKorisnikaZaStavku);
                    }

                    model = new BarChartModel();

                    model.addSeries(chartPonudjeniOdgovori);
                    //model.setTitle("Bar Chart");
                    model.setLegendPosition("ne");
                    model.setSeriesColors("#17BDB8");

                    //Axis xAxis = model.getAxis(AxisType.X);
                    //xAxis.setLabel(statistikaZa);
                    Axis yAxis = model.getAxis(AxisType.Y);
                    yAxis.setLabel("Broj korisnika");
                    yAxis.setMin(0);
                    yAxis.setMax(ukupanBrojKorisnika);
                    
                    listaModelaZaAnketu.put(statistikaZa, model);
                }
                return;
            } else if (stavkaStatistike.equals("nazivMesta")) {
                ps.setString(1, "nazivMesta");
                ps.setString(2, statistikaZa + " s, sifMesta sm, sifUlica su");
                ps.setString(3, "sm.idSifMesta=su.idSifMesta && su.idSifUlica=s.idSifUlica");
                ps.setString(4, "nazivMesta");
            } else {
                ps.setString(1, "s." + stavkaStatistike);
                ps.setString(2, statistikaZa + " s, korisnik k");
                ps.setString(3, "k.idKorisnik=s.idKorisnik");
                ps.setString(4, "s." + stavkaStatistike);
//                ResultSet rs = ps.executeQuery();
//                rs.next();
//
//                int brojKolona = rs.getMetaData().getColumnCount();
//
//                for (int i = 1; i <= brojKolona; i++) {
//                    naziviStavke += rs.getMetaData().getColumnName(i) + ", ";
//                    vrednostiZaSvakuStavku += rs.getString(i) + ", ";
//                }
            }

            ResultSet statistikaAnkete = ps.executeQuery();

            chartPonudjeniOdgovori = new ChartSeries();

            //chartPonudjeniOdgovori.setLabel("Broj korisnika");
            int ukupanBrojKorisnika = 0;

            while (statistikaAnkete.next()) {
                String pojedinacneStavkeZaStatistiku = statistikaAnkete.getString(stavkaStatistike);
                int brojKorisnikaZaStavku = statistikaAnkete.getInt("Broj korisnika");

                ukupanBrojKorisnika += brojKorisnikaZaStavku;

                chartPonudjeniOdgovori.set(pojedinacneStavkeZaStatistiku, brojKorisnikaZaStavku);
            }

            model = new BarChartModel();

            model.addSeries(chartPonudjeniOdgovori);
            //model.setTitle("Bar Chart");
            model.setLegendPosition("ne");
            model.setSeriesColors("#17BDB8");

            //Axis xAxis = model.getAxis(AxisType.X);
            //xAxis.setLabel(statistikaZa);
            Axis yAxis = model.getAxis(AxisType.Y);
            yAxis.setLabel("Broj korisnika");
            yAxis.setMin(0);
            yAxis.setMax(ukupanBrojKorisnika);

            modelZaStatistiku = model;

        } catch (SQLException ex) {
            Logger.getLogger(DodavanjeStatistike.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
