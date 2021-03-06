package cz.muni.fi.pa165.controller;

import cz.muni.fi.pa165.dto.packing.PackingDto;
import cz.muni.fi.pa165.dto.price.PriceDto;
import cz.muni.fi.pa165.dto.price.PricePackingDto;
import cz.muni.fi.pa165.dto.wine.WineCreateDto;
import cz.muni.fi.pa165.dto.wine.WineDto;
import cz.muni.fi.pa165.dto.wineList.WineListDto;
import cz.muni.fi.pa165.facade.PackingFacade;
import cz.muni.fi.pa165.facade.PriceFacade;
import cz.muni.fi.pa165.facade.WineFacade;
import cz.muni.fi.pa165.facade.WineListFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michaela Bamburova on 16/12/2016.
 */
@Controller
@RequestMapping("/wines")
public class WineController {

    @Inject
    private WineFacade wineFacade;

    @Inject
    private PackingFacade packingFacade;

    @Inject
    private PriceFacade priceFacade;

    @Inject
    private WineListFacade wineListFacade;

    @RequestMapping(value = {"", "/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("wines", wineFacade.findAllWines());
        model.addAttribute("wineLists", wineListFacade.findAllWineLists());
        return "wines/index";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newWine(Model model) {

        model.addAttribute("wineCreate", new WineDto());
        return "wines/new";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        WineDto wineDto = wineFacade.findWineById(id);

        try {
            wineFacade.deleteWine(id);
            redirectAttributes.addFlashAttribute("alert_success", "Wine \"" + wineDto.getName() + "\" was deleted.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("alert_danger", "Wine \"" + wineDto.getName() + "\" is in tasting or some error occured" +
                "tickets!");
        }
        return "redirect:" + uriBuilder.path("/wines/index").toUriString();
    }

    @RequestMapping(value = "add/{id}/{listId}", method = RequestMethod.GET)
    public String addToWineList(@PathVariable long id, @PathVariable long listId, UriComponentsBuilder uriBuilder,
                                RedirectAttributes redirectAttributes) {
        WineDto wine = wineFacade.findWineById(id);
        WineListDto wineListDto = wineListFacade.findWineListById(listId);
        try {
            wineListFacade.addWine(wineListDto, wine);
        } catch (UnsupportedOperationException e) {
            redirectAttributes.addFlashAttribute("alert_danger", "Tasting ticket \"" + wineListDto.getName() + "\" already contains wine \""
                + wine.getName() + "\"!");
            return "redirect:" + uriBuilder.path("/wines/index").toUriString();
        }

        redirectAttributes.addFlashAttribute("alert_success", "Wine \"" + wine.getName() + "\" was added to tasting ticket \""
            + wineListDto.getName() + "\".");
        return "redirect:" + uriBuilder.path("/wines/index").toUriString();
    }

    @RequestMapping(value = "remove/{id}/{wineId}", method = RequestMethod.GET)
    public String removeFromWineList(@PathVariable long id, @PathVariable long wineId,
                                     UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {

        WineDto wine = wineFacade.findWineById(wineId);
        WineListDto wineListDto = wineListFacade.findWineListById(id);
        wineListFacade.removeWine(wineListDto, wine);

        redirectAttributes.addFlashAttribute("alert_info", "Wine \"" + wine.getName() + "\" was removed from tasting ticket \""
            + wineListDto.getName() + "\".");
        return "redirect:" + uriBuilder.path("/winelists/view/" + wineListDto.getId()).toUriString();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("wineCreate") WineCreateDto formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {

        if (bindingResult.hasErrors()) {
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
            }
            return "wines/new";
        }
        wineFacade.createWine(formBean);

        redirectAttributes.addFlashAttribute("alert_success", "Wine \"" + formBean.getName() + "\" was created.");
        return "redirect:" + uriBuilder.path("/wines/index").toUriString();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable long id, Model model) {
        WineDto wineDto = wineFacade.findWineById(id);

        model.addAttribute("pricePackings", pricesOfWine(id));
        model.addAttribute("wineUpdate", wineDto);
        return "wines/update";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("wineUpdate") WineDto formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        if (bindingResult.hasErrors()) {
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
            }
            return "wines/update";
        }
        wineFacade.updateWine(formBean);

        redirectAttributes.addFlashAttribute("alert_info", "Wine \"" + formBean.getName() + "\" was updated.");
        return "redirect:" + uriBuilder.path("/wines/index").toUriString();
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable long id, Model model) {

        model.addAttribute("wine", wineFacade.findWineById(id));
        model.addAttribute("pricePackings", pricesOfWine(id));
        return "wines/detail";
    }

    private List<PricePackingDto> pricesOfWine(Long wineId) {
        List<PricePackingDto> pricePackingDtos = new ArrayList<>();
        for (PackingDto packingDto : packingFacade.findPackingsByWine(wineFacade.findWineById(wineId))) {
            for (PriceDto priceDto : priceFacade.findPricesByPacking(packingDto)) {
                PricePackingDto pricePackingDto = new PricePackingDto();
                pricePackingDto.setPackingDto(packingDto);
                pricePackingDto.setPriceDto(priceDto);
                pricePackingDtos.add(pricePackingDto);
            }
        }
        return pricePackingDtos;
    }

}
