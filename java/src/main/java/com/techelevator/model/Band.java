package com.techelevator.model;

import java.util.List;

public class Band {

    private int bandId;
    private String bandName;
    private String description;
    private Image image;
    private int genreId;
    private List<Subgenre> subgenres;

    public Band() {
    }

    public int getBandId() {
        return bandId;
    }

    public void setBandId(int bandId) {
        this.bandId = bandId;
    }

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Image getBandImage() {
        return image;
    }

    public void setBandImage(Image image) {
        this.image = image;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public List<Subgenre> getSubgenres() {
        return subgenres;
    }

    public void setSubgenres(List<Subgenre> subgenres) {
        this.subgenres = subgenres;
    }
}
