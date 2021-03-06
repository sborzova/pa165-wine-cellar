package cz.muni.fi.pa165.controllers;

import cz.muni.fi.pa165.rest.ApiUris;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MarekScholtz
 * @version 2016.12.15
 */

@RestController
public class MainController {

    /**
     * The main entry point of the REST API
     * Provides access to all the resources with links to resource URIs
     *
     * @return resources uris
     */
    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Map<String, String> getResources() {

        Map<String, String> resourcesMap = new HashMap<>();

        resourcesMap.put("winelists_uri", ApiUris.ROOT_URI_WINELISTS);

        return Collections.unmodifiableMap(resourcesMap);

    }
}
