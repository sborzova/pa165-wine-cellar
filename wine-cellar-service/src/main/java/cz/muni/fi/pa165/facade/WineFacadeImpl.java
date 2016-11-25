package cz.muni.fi.pa165.facade;

import cz.muni.fi.pa165.dto.WineDto;
import cz.muni.fi.pa165.entity.Wine;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.WineService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

/**
 * @author MarekScholtz
 * @version 2016.11.06
 */
public class WineFacadeImpl implements WineFacade {

    @Autowired
    private WineService wineService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public Long createWine(WineDto wineDto) {
        Wine wine = new Wine();
        wine.setName(wineDto.getName());
        wine.setVintage(wineDto.getVintage());
        wine.setBatch(wineDto.getBatch());
        wine.setPredicate(wineDto.getPredicate());
        wine.setPredicateEquivalent(wineDto.getPredicateEquivalent());
        wine.setDescription(wineDto.getDescription());
        wine.setNotes(wineDto.getNotes());
        wine.setAlcoholVolume(wineDto.getAlcoholVolume());
        wine.setResidualSugar(wineDto.getResidualSugar());
        wine.setAcidity(wineDto.getAcidity());
        wine.setGrapeSugarContent(wineDto.getGrapeSugarContent());
        wineService.create(wine);
        return wine.getId();
    }

    @Override
    public WineDto findWineById(Long id) {
        if (wineService.findWineById(id) == null) {
            throw new NoResultException();
        }
        return beanMappingService.mapTo(wineService.findWineById(id), Wine.class);
    }

    @Override
    public List<WineDto> findAllWines() {
        return null;
    }

    @Override
    public void updateWine(Long wineId) {
        Wine mappedWine = beanMappingService.mapTo(WineDto, Wine.class);
        wineService.updateWine(mappedWine);
    }

    @Override
    public void deleteWine(Long wineId) {
        Wine mappedWine = beanMappingService.mapTo(WineDto, Wine.class);
        wineService.deleteWine(mappedWine);
    }

    @Override
    public List<WineDto> findWinesByName(String name) {
        if (wineService.findWineById(id) == null) {
            throw new NoResultException();
        }
        return beanMappingService.mapTo(wineService.findWineByName(name), Wine.class);
    }

    @Override
    public List<WineDto> findWinesByVintage(Year vintage) {
        if (wineService.findWinesByVintage(id) == null) {
            throw new NoResultException();
        }
        return beanMappingService.mapTo(wineService.findWinesByVintage(vintage), Wine.class);
    }

    @Override
    public WineDto findWineByBatch(String batch) {
        if (wineService.findWineById(id) == null) {
            throw new NoResultException();
        }
        return beanMappingService.mapTo(wineService.findWineById(id), Wine.class);
    }

    @Override
    public List<WineDto> findWinesByPredicate(String predicate) {
        return null;
    }

    @Override
    public List<WineDto> findWinesByPredicateEquivalent(String predicateEquivalent) {
        return null;
    }

    @Override
    public List<WineDto> findWinesByDescription(String description) {
        return null;
    }

    @Override
    public List<WineDto> findWinesByNotes(String notes) {
        return null;
    }

    @Override
    public List<WineDto> findWinesByAlcoholVolume(BigDecimal alcoholVolume) {
        return null;
    }

    @Override
    public List<WineDto> findWinesByResidualSugar(BigDecimal residualSugar) {
        return null;
    }

    @Override
    public List<WineDto> findWinesByAcidity(BigDecimal acidity) {
        return null;
    }

    @Override
    public List<WineDto> findWinesByGrapeSugarContent(BigDecimal grapeSugarContent) {
        return null;
    }

    @Override
    public List<WineDto> findWinesBetweenYears(Year from, Year to) {
        return null;
    }

    @Override
    public void addWinePackage(Long wine, Long packing) {

    }

    @Override
    public void removeWinePackage(Long wine, Long packing) {

    }
}
