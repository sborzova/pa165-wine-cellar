package cz.muni.fi.pa165.dao;

import cz.muni.fi.pa165.PersistenceSampleApplicationContext;
import cz.muni.fi.pa165.entity.Wine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author MarekScholtz
 * @version 2016.10.30
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class WineDaoTest {

    @Autowired
    private WineDao wineDao;
    private Wine veltlinskeZelene;
    private Wine muskatMoravsky;
    private Wine svatovavrinecke;

    @BeforeClass
    public void setup() {

        veltlinskeZelene = new Wine();
        veltlinskeZelene.setName("Veltlínske zelené");
        veltlinskeZelene.setVintage(Year.of(2014));
        veltlinskeZelene.setBatch("10/14");
        veltlinskeZelene.setPredicate("kabinetní víno");
        veltlinskeZelene.setPredicateEquivalent("suché");
        veltlinskeZelene.setDescription("Elegantní, svěží víno s lehkou aromatikou angreštu a zeleného pepře. Chuťový vjem je tvořen pikantní kyselinkou a kořenito-ovocnými tóny.");
        veltlinskeZelene.setNotes("20,0°ČNM");
        veltlinskeZelene.setAlcoholVolume(new BigDecimal(10.94));
        veltlinskeZelene.setResidualSugar(new BigDecimal(2.8));
        veltlinskeZelene.setAcidity(new BigDecimal(7.5));
        veltlinskeZelene.setGrapeSugarContent(new BigDecimal(0));

        muskatMoravsky = new Wine();
        muskatMoravsky.setName("Muškát moravský");
        muskatMoravsky.setVintage(Year.of(2015));
        muskatMoravsky.setBatch("1/14");
        muskatMoravsky.setPredicate("kabinetní víno");
        muskatMoravsky.setPredicateEquivalent("suché");
        muskatMoravsky.setDescription("Víno zlatavé barvy s ovocnou vůní citrusových plodů a muškátového oříšku. V chuti nabízí ovocné tóny grapefruitu a zralého citrónu. Ovocnou chuť provází příjemná kyselinka, díky níž je víno pikantní se suchým závěrem.");
        muskatMoravsky.setNotes("20,2°ČNM");
        muskatMoravsky.setAlcoholVolume(new BigDecimal(12));
        muskatMoravsky.setResidualSugar(new BigDecimal(0.7));
        muskatMoravsky.setAcidity(new BigDecimal(6.1));
        muskatMoravsky.setGrapeSugarContent(new BigDecimal(0));

        svatovavrinecke = new Wine();
        svatovavrinecke.setName("Svatovavřinecké");
        svatovavrinecke.setVintage(Year.of(2015));
        svatovavrinecke.setBatch("6/15");
        svatovavrinecke.setPredicate("pozdní sběr");
        svatovavrinecke.setPredicateEquivalent("suché");
        svatovavrinecke.setDescription("Jiskrné víno rubínových odstínů barvy. Kořenitá vůně višní a třešňové kůry. Zabalená v nádechu kouře z dubového dřeva. Chuť charakterní pevná, v níž se snoubí tóny višní, svěží kyselinky a příjemného třísla.");
        svatovavrinecke.setNotes("30,2°ČNM");
        svatovavrinecke.setAlcoholVolume(new BigDecimal(12));
        svatovavrinecke.setResidualSugar(new BigDecimal(6.2));
        svatovavrinecke.setAcidity(new BigDecimal(4.6));
        svatovavrinecke.setGrapeSugarContent(new BigDecimal(0));

        wineDao.createWine(muskatMoravsky);
        wineDao.createWine(svatovavrinecke);

    }

    @Test
    public void create() {
        wineDao.createWine(veltlinskeZelene);
        assertThat(wineDao.getWineById(veltlinskeZelene.getId())).isEqualToComparingFieldByField(veltlinskeZelene);
    }

    @Test
    public void update() {
        wineDao.createWine(muskatMoravsky);
        muskatMoravsky.setVintage(Year.of(2016));
        wineDao.updateWine(muskatMoravsky);
        assertThat(wineDao.getWineById(muskatMoravsky.getId())).isEqualToComparingFieldByField(muskatMoravsky);
    }

    @Test
    public void delete() {
        wineDao.deleteWine(muskatMoravsky);
        assertThat(wineDao.getWineById(muskatMoravsky.getId())).isNull();
    }

    @Test
    public void findAll() {
        List<Wine> found = wineDao.getAllWines();
        Assert.assertEquals(found.size(), 3);
    }

    @Test
    public void findByName() {
        List<Wine> found = wineDao.findByName("Veltlínske");
        Assert.assertEquals(found.size(), 1);
        Assert.assertEquals(found.get(0).getName(), "Veltlínske zelené");
    }

    @Test
    public void findByVintage() {
        List<Wine> found = wineDao.findByVintage(Year.of(2015));
        Assert.assertEquals(found.size(), 2);
        Assert.assertEquals(found.get(0).getVintage(), Year.of(2015));
        Assert.assertEquals(found.get(1).getVintage(), Year.of(2015));
    }

    @Test
    public void findByPredicate() {
        List<Wine> found = wineDao.findByPredicate("kabinetní");
        Assert.assertEquals(found.size(), 2);
        Assert.assertEquals(found.get(0).getPredicate(), "kabinetní víno");
        Assert.assertEquals(found.get(1).getPredicate(), "kabinetní víno");
    }

}
