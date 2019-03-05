package controllers;

import beans.Anketa;
import beans.Korisnik;
import beans.Statistika;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.primefaces.model.chart.PieChartModel;

@ManagedBean
@ViewScoped
public class UcitajStatistikeZaKorisnika implements Serializable {
    
    private boolean anketaModelPostoji=false;
    private Anketa poslednjaAnketaSaStatistikomZaKorisnika;
    private Statistika poslednjaStatistikaZaKorisnika, pretposlednjaStatistikaZaKorisnika;
    private ArrayList<Statistika> listaSvihStatistikaZaKorisnika;
    private ArrayList<Anketa> listaSvihStatistikaIzAnketaZaKorisnika;
    private PieChartModel modelZaPoslednjuStatistiku, modelZaDruguStatistiku;
    private BarChartModel modelPrvogPitanjaPoslednjeAnkete;

    public boolean isAnketaModelPostoji() {
        return anketaModelPostoji;
    }

    public void setAnketaModelPostoji(boolean anketaModelPostoji) {
        this.anketaModelPostoji = anketaModelPostoji;
    }

    public Anketa getPoslednjaAnketaSaStatistikomZaKorisnika() {
        return poslednjaAnketaSaStatistikomZaKorisnika;
    }

    public void setPoslednjaAnketaSaStatistikomZaKorisnika(Anketa poslednjaAnketaSaStatistikomZaKorisnika) {
        this.poslednjaAnketaSaStatistikomZaKorisnika = poslednjaAnketaSaStatistikomZaKorisnika;
    }

    public Statistika getPretposlednjaStatistikaZaKorisnika() {
        return pretposlednjaStatistikaZaKorisnika;
    }

    public void setPretposlednjaStatistikaZaKorisnika(Statistika pretposlednjaStatistikaZaKorisnika) {
        this.pretposlednjaStatistikaZaKorisnika = pretposlednjaStatistikaZaKorisnika;
    }

    public PieChartModel getModelZaDruguStatistiku() {
        return modelZaDruguStatistiku;
    }

    public void setModelZaDruguStatistiku(PieChartModel modelZaDruguStatistiku) {
        this.modelZaDruguStatistiku = modelZaDruguStatistiku;
    }

    public PieChartModel getModelZaPoslednjuStatistiku() {
        return modelZaPoslednjuStatistiku;
    }

    public void setModelZaPoslednjuStatistiku(PieChartModel modelZaPoslednjuStatistiku) {
        this.modelZaPoslednjuStatistiku = modelZaPoslednjuStatistiku;
    }

    public BarChartModel getModelPrvogPitanjaPoslednjeAnkete() {
        return modelPrvogPitanjaPoslednjeAnkete;
    }

    public void setModelPrvogPitanjaPoslednjeAnkete(BarChartModel modelPrvogPitanjaPoslednjeAnkete) {
        this.modelPrvogPitanjaPoslednjeAnkete = modelPrvogPitanjaPoslednjeAnkete;
    }

    public Statistika getPoslednjaStatistikaZaKorisnika() {
        return poslednjaStatistikaZaKorisnika;
    }

    public void setPoslednjaStatistikaZaKorisnika(Statistika poslednjaStatistikaZaKorisnika) {
        this.poslednjaStatistikaZaKorisnika = poslednjaStatistikaZaKorisnika;
    }

    public ArrayList<Statistika> getListaSvihStatistikaZaKorisnika() {
        return listaSvihStatistikaZaKorisnika;
    }

    public void setListaSvihStatistikaZaKorisnika(ArrayList<Statistika> listaSvihStatistikaZaKorisnika) {
        this.listaSvihStatistikaZaKorisnika = listaSvihStatistikaZaKorisnika;
    }

    public ArrayList<Anketa> getListaSvihStatistikaIzAnketaZaKorisnika() {
        return listaSvihStatistikaIzAnketaZaKorisnika;
    }

    public void setListaSvihStatistikaIzAnketaZaKorisnika(ArrayList<Anketa> listaSvihStatistikaIzAnketaZaKorisnika) {
        this.listaSvihStatistikaIzAnketaZaKorisnika = listaSvihStatistikaIzAnketaZaKorisnika;
    }

    public void ucitajStatistikeNaGlavnojStrani() {
        try {
            Connection conn = DriverManager.getConnection(db.db.connectionString, db.db.user, db.db.password);
            Statement stm = conn.createStatement();

            HttpSession sesija = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Korisnik korisnik = (Korisnik) sesija.getAttribute("korisnik");

            if (korisnik != null) {
                ResultSet statistikeZaKorisnika = stm.executeQuery("select idStatistike, nazivStatistike, metaStatistike, datumStatistike, naziviStavke, vrednostiStavke, nazivStatistike, stavkaStatistike from statistike s, ucestvuje_kreira uk, korisnik k where k.korisnickoIme='" + korisnik.getKorisnickoIme() + "' && k.idKorisnik=uk.idKorisnik && uk.idObjave=s.idObjave && s.arhiviranaStatistika='false' order by s.datumStatistike desc limit 1");

                while (statistikeZaKorisnika.next()) {
                    Statistika s = new Statistika();
                    s.setIdStatistike(statistikeZaKorisnika.getInt("idStatistike"));
                    s.setDatumKreiranja(statistikeZaKorisnika.getString("datumStatistike"));
                    s.setListaNazivaStavke(statistikeZaKorisnika.getString("naziviStavke"));
                    s.setListaVrednostiStavke(statistikeZaKorisnika.getString("vrednostiStavke"));
                    s.setMetaStatistike(statistikeZaKorisnika.getString("metaStatistike"));
                    s.setNazivStatistike(statistikeZaKorisnika.getString("nazivStatistike"));
                    s.setStavkaStatistike(statistikeZaKorisnika.getString("stavkaStatistike"));

                    poslednjaStatistikaZaKorisnika = s;
                }
            } else {
                ResultSet statistikeZaKorisnika = stm.executeQuery("select idStatistike, nazivStatistike, metaStatistike, datumStatistike, naziviStavke, vrednostiStavke, nazivStatistike, stavkaStatistike from statistike s, vidljivost v where s.arhiviranaStatistika='false' && v.nivoVidljivosti='Svi i gosti' && v.idVidljivost=s.idVidljivost order by s.datumStatistike desc limit 1");

                while (statistikeZaKorisnika.next()) {
                    Statistika s = new Statistika();
                    s.setIdStatistike(statistikeZaKorisnika.getInt("idStatistike"));
                    s.setDatumKreiranja(statistikeZaKorisnika.getString("datumStatistike"));
                    s.setListaNazivaStavke(statistikeZaKorisnika.getString("naziviStavke"));
                    s.setListaVrednostiStavke(statistikeZaKorisnika.getString("vrednostiStavke"));
                    s.setMetaStatistike(statistikeZaKorisnika.getString("metaStatistike"));
                    s.setNazivStatistike(statistikeZaKorisnika.getString("nazivStatistike"));
                    s.setStavkaStatistike(statistikeZaKorisnika.getString("stavkaStatistike"));

                    poslednjaStatistikaZaKorisnika = s;
                }
            }
            PieChartModel statistikaModel = new PieChartModel();

            Map<String, Integer> kolicinaKorisnikaPoStavkamaMapa = new HashMap();

            StringTokenizer vrednostiZaSvakuStavku = new StringTokenizer(poslednjaStatistikaZaKorisnika.getListaVrednostiStavke(), ",");
            StringTokenizer naziviSvakeStavke = new StringTokenizer(poslednjaStatistikaZaKorisnika.getListaNazivaStavke(), ",");

            int brojElemenata = vrednostiZaSvakuStavku.countTokens();

            for (int i = 1; i <= brojElemenata; i++) {
                kolicinaKorisnikaPoStavkamaMapa.put(naziviSvakeStavke.nextToken().trim(), Integer.parseInt(vrednostiZaSvakuStavku.nextToken().trim()));
            }

            for (Map.Entry<String, Integer> stavkeStatistike : kolicinaKorisnikaPoStavkamaMapa.entrySet()) {
                statistikaModel.set(stavkeStatistike.getKey(), stavkeStatistike.getValue());
            }

            statistikaModel.setTitle("");
            statistikaModel.setLegendPosition("w");
            statistikaModel.setShadow(true);
            statistikaModel.setExtender("pieCharts");
            
            modelZaPoslednjuStatistiku = statistikaModel;
            

            // Ucitavanje modela za anketu
            int idAnketa = 0;

            if (korisnik != null) {

                ResultSet idAnkete = stm.executeQuery("select idAnkete from ankete a, ucestvuje_kreira uk, korisnik k where k.korisnickoIme='" + korisnik.getKorisnickoIme() + "' && k.idKorisnik=uk.idKorisnik && a.idObjave=uk.idObjave && a.vidljivostRezultata='true' order by a.datumTrajanjaAnkete desc limit 1");
                
                while(idAnkete.next())
                idAnketa = idAnkete.getInt("idAnkete");
            } else {
                ResultSet idAnkete = stm.executeQuery("select idAnkete from ankete a, vidljivost v where a.vidljivostRezultata='true' && a.idVidljivost=v.idVidljivost && v.nivoVidljivosti='Svi i gosti' order by a.datumTrajanjaAnkete desc limit 1");
                
                while(idAnkete.next())
                idAnketa = idAnkete.getInt("idAnkete");
            }

            if (idAnketa != 0) {
                ResultSet idKorisnika = stm.executeQuery("select distinct idKorisnik from odgovoriankete where idAnkete=" + idAnketa);

                int brojKorisnika = 0;

                while (idKorisnika.next()) {
                    brojKorisnika++;
                }
                
                ResultSet anketa = stm.executeQuery("select nazivAnkete, idAnkete, metaAnkete from ankete where idAnkete=" + idAnketa);
                anketa.next();
                
                Anketa a = new Anketa();
                a.setNazivAnkete(anketa.getString("nazivAnkete"));
                a.setIdAnketa(anketa.getInt("idAnkete"));
                a.setMetaAnkete(anketa.getString("metaAnkete"));
                
                poslednjaAnketaSaStatistikomZaKorisnika = a;
                anketaModelPostoji = true;
                
                ResultSet statistikaPrvogPitanjaPoslednjeAnketeZaKorisnika = stm.executeQuery("select pitanje, idPitanjaAnkete, tipPitanja, ponudjeniOdgovori from pitanjaAnkete pa where idAnkete=" + idAnketa + " && (tipPitanja='int' || tipPitanja='daNe' || tipPitanja='ponudjeniodgovori') order by redniBrojPitanja limit 1");
                statistikaPrvogPitanjaPoslednjeAnketeZaKorisnika.next();

                String pitanje = statistikaPrvogPitanjaPoslednjeAnketeZaKorisnika.getString("pitanje");
                String tipPitanja = statistikaPrvogPitanjaPoslednjeAnketeZaKorisnika.getString("tipPitanja");
                int idPitanja = statistikaPrvogPitanjaPoslednjeAnketeZaKorisnika.getInt("idPitanjaAnkete");

                StringTokenizer ponudjeniOdgovoriZaPitanje = new StringTokenizer(statistikaPrvogPitanjaPoslednjeAnketeZaKorisnika.getString("ponudjeniOdgovori"), ",");

                ResultSet odgovoriNaPitanje = stm.executeQuery("select odgovoriAnkete from odgovoriAnkete oa where oa.idPitanjaAnkete=" + idPitanja);

                BarChartModel modelZaPitanje = new BarChartModel();

                if (tipPitanja.equals("ponudjeniOdgovori") || tipPitanja.equals("daNe")) {

                    ChartSeries chartPonudjeniOdgovori = new ChartSeries();
                    chartPonudjeniOdgovori.setLabel("Broj korisnika");

                    Map<String, Integer> odgovoriMapa = new HashMap();

                    while (ponudjeniOdgovoriZaPitanje.hasMoreTokens()) {
                        odgovoriMapa.put(ponudjeniOdgovoriZaPitanje.nextToken().trim(), 0);
                    }

                    while (odgovoriNaPitanje.next()) {
                        String odgovorNaPitanje = odgovoriNaPitanje.getString("odgovoriAnkete");
                        odgovoriMapa.put(odgovorNaPitanje, odgovoriMapa.get(odgovorNaPitanje) + 1);
                    }

                    for (Map.Entry<String, Integer> ponudjeniOdgovori : odgovoriMapa.entrySet()) {
                        chartPonudjeniOdgovori.set(ponudjeniOdgovori.getKey(), ponudjeniOdgovori.getValue());
                    }

                    modelZaPitanje.addSeries(chartPonudjeniOdgovori);
                    modelZaPitanje.setTitle(pitanje);
                    //model.setLegendPosition("ne");
                    //modelZaPitanje.setSeriesColors("#17BDB8");
                    modelZaPitanje.setExtender("barCharts");
                    
//                    Axis xAxis = modelZaPitanje.getAxis(AxisType.X);
//                    xAxis.setLabel("Odgovor");
                    Axis yAxis = modelZaPitanje.getAxis(AxisType.Y);
                    yAxis.setLabel("Broj korisnika");
                    yAxis.setMin(0);
                    yAxis.setMax(brojKorisnika);
                    
                    modelPrvogPitanjaPoslednjeAnkete = modelZaPitanje;

                } else if (tipPitanja.equals("int")) {

                    ChartSeries chartInt = new ChartSeries();
                    chartInt.setLabel("Broj korisnika");

                    Map<String, Integer> odgovoriMapa = new HashMap();

                    while (odgovoriNaPitanje.next()) {
                        String odgovorNaPitanje = odgovoriNaPitanje.getString("odgovoriAnkete");
                        odgovoriMapa.put(odgovorNaPitanje, odgovoriMapa.containsKey(odgovorNaPitanje) ? odgovoriMapa.get(odgovorNaPitanje) + 1 : 1);
                    }

                    for (Map.Entry<String, Integer> ponudjeniOdgovori : odgovoriMapa.entrySet()) {
                        chartInt.set(ponudjeniOdgovori.getKey(), ponudjeniOdgovori.getValue());
                    }

                    modelZaPitanje.addSeries(chartInt);
                    modelZaPitanje.setTitle(pitanje);
//                        modelZaPitanje.setLegendPosition("ne");
                    modelZaPitanje.setExtender("barCharts");
                    
                    Axis xAxis = modelZaPitanje.getAxis(AxisType.X);
//                    xAxis.setLabel("Odgovor");
                    Axis yAxis = modelZaPitanje.getAxis(AxisType.Y);
                    yAxis.setLabel("Broj korisnika");
                    yAxis.setMin(0);
                    yAxis.setMax(brojKorisnika);

                    modelPrvogPitanjaPoslednjeAnkete = modelZaPitanje;
                }
            } else {
                if (korisnik != null) {
                ResultSet statistikeZaKorisnika = stm.executeQuery("select idStatistike, nazivStatistike, metaStatistike, datumStatistike, naziviStavke, vrednostiStavke, nazivStatistike, stavkaStatistike from statistike s, ucestvuje_kreira uk, korisnik k where k.korisnickoIme='" + korisnik.getKorisnickoIme() + "' && k.idKorisnik=uk.idKorisnik && uk.idObjave=s.idObjave && s.arhiviranaStatistika='false' order by s.datumStatistike desc limit 2");
                statistikeZaKorisnika.next();
                
                while (statistikeZaKorisnika.next()) {
                    Statistika s = new Statistika();
                    s.setIdStatistike(statistikeZaKorisnika.getInt("idStatistike"));
                    s.setDatumKreiranja(statistikeZaKorisnika.getString("datumStatistike"));
                    s.setListaNazivaStavke(statistikeZaKorisnika.getString("naziviStavke"));
                    s.setListaVrednostiStavke(statistikeZaKorisnika.getString("vrednostiStavke"));
                    s.setMetaStatistike(statistikeZaKorisnika.getString("metaStatistike"));
                    s.setNazivStatistike(statistikeZaKorisnika.getString("nazivStatistike"));
                    s.setStavkaStatistike(statistikeZaKorisnika.getString("stavkaStatistike"));

                    pretposlednjaStatistikaZaKorisnika = s;
                }
            } else {
                ResultSet statistikeZaKorisnika = stm.executeQuery("select idStatistike, nazivStatistike, metaStatistike, datumStatistike, naziviStavke, vrednostiStavke, nazivStatistike, stavkaStatistike from statistike s, vidljivost v where s.arhiviranaStatistika='false' && v.nivoVidljivosti='Svi i gosti' && v.idVidljivost=s.idVidljivost order by s.datumStatistike desc limit 2");
                statistikeZaKorisnika.next();
                
                while (statistikeZaKorisnika.next()) {
                    Statistika s = new Statistika();
                    s.setIdStatistike(statistikeZaKorisnika.getInt("idStatistike"));
                    s.setDatumKreiranja(statistikeZaKorisnika.getString("datumStatistike"));
                    s.setListaNazivaStavke(statistikeZaKorisnika.getString("naziviStavke"));
                    s.setListaVrednostiStavke(statistikeZaKorisnika.getString("vrednostiStavke"));
                    s.setMetaStatistike(statistikeZaKorisnika.getString("metaStatistike"));
                    s.setNazivStatistike(statistikeZaKorisnika.getString("nazivStatistike"));
                    s.setStavkaStatistike(statistikeZaKorisnika.getString("stavkaStatistike"));

                    pretposlednjaStatistikaZaKorisnika = s;
                }
            }
            PieChartModel drugaStatistikaModel = new PieChartModel();

            Map<String, Integer> kolicinaKorisnikaPoStavkamaDrugaMapa = new HashMap();

            StringTokenizer drugeVrednostiZaSvakuStavku = new StringTokenizer(pretposlednjaStatistikaZaKorisnika.getListaVrednostiStavke(), ",");
            StringTokenizer drugiNaziviSvakeStavke = new StringTokenizer(pretposlednjaStatistikaZaKorisnika.getListaNazivaStavke(), ",");

            int drugiBrojElemenata = drugeVrednostiZaSvakuStavku.countTokens();

            for (int i = 1; i <= drugiBrojElemenata; i++) {
                kolicinaKorisnikaPoStavkamaDrugaMapa.put(drugiNaziviSvakeStavke.nextToken().trim(), Integer.parseInt(drugeVrednostiZaSvakuStavku.nextToken().trim()));
            }

            for (Map.Entry<String, Integer> stavkeStatistike : kolicinaKorisnikaPoStavkamaDrugaMapa.entrySet()) {
                drugaStatistikaModel.set(stavkeStatistike.getKey(), stavkeStatistike.getValue());
            }

//            drugaStatistikaModel.setTitle("Broj korisnika");
            drugaStatistikaModel.setLegendPosition("w");
            drugaStatistikaModel.setShadow(true);
            drugaStatistikaModel.setExtender("pieCharts");
            
            modelZaDruguStatistiku = drugaStatistikaModel;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UcitajStatistikeZaKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
