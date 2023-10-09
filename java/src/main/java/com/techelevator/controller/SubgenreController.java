package com.techelevator.controller;

import com.techelevator.Services.SubgenreService;
import com.techelevator.dao.SubgenreDao;
import com.techelevator.model.Band;
import com.techelevator.model.Subgenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@PreAuthorize("isAuthenticated()")
public class SubgenreController {

    @Autowired
    private SubgenreDao subgenreDao;
    @Autowired
    private SubgenreService subgenreService;

    @RequestMapping(path = "/subgenre/{subgenreName}", method = RequestMethod.GET)
    public Subgenre getSubgenreByName(@PathVariable String subgenreName) {
        return subgenreService.getSubgenreByName(subgenreName);
    }

    @RequestMapping(path = "/bands/subgenre/{subgenreId}", method = RequestMethod.GET)
    public List<Band> getBandsBySubgenre(@PathVariable int subgenreId) {
        return subgenreService.getBandsBySubgenre(subgenreId);
    }

    @RequestMapping(path = "/subgenre/create", method = RequestMethod.POST)
    public Subgenre addNewSubgenre(@RequestBody String subgenreName) {
        return subgenreService.addNewSubgenre(subgenreName);
    }

    @RequestMapping(path = "/bands/{bandId}/addsubgenre", method = RequestMethod.POST)
    public Band linkSubgenreToBand(@PathVariable int bandId, @RequestBody int subgenreId ) {
        return subgenreService.linkBandToSubgenre(subgenreId, bandId);
    }
}
