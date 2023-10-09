package com.techelevator.Services;

import com.techelevator.dao.BandDao;
import com.techelevator.dao.SubgenreDao;
import com.techelevator.model.Band;
import com.techelevator.model.Subgenre;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubgenreService {

    private final SubgenreDao subgenreDao;
    private final BandDao bandDao;

    public SubgenreService(SubgenreDao subgenreDao, BandDao bandDao) {
        this.subgenreDao = subgenreDao;
        this.bandDao = bandDao;
    }

    public Subgenre getSubgenreById(int subgenreId) {
        return subgenreDao.getSubgenreById(subgenreId);
    }

    public Subgenre getSubgenreByName(String subgenreName) {
        return subgenreDao.getSubgenreByName(subgenreName);
    }

    public Subgenre addNewSubgenre(String subgenreName) {
        return subgenreDao.getSubgenreById(subgenreDao.addNewSubgenre(subgenreName));
    }

    public List<Band> getBandsBySubgenre(int subgenreId) {
        return subgenreDao.getBandsBySubgenre(subgenreId);
    }

    public Band linkBandToSubgenre(int subgenreId, int bandId) {
        return bandDao.getBandById(subgenreDao.linkBandToSubgenre(subgenreId, bandId));
    }
}
