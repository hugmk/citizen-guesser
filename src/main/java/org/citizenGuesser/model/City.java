package org.citizenGuesser.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class City {
    private String nom;
    private int population;
    private String code;
    private String[] codesPostaux;

    public City() {
    }

    public String getNom() {
        return nom;
    }

    public int getPopulation() {
        return population;
    }

    public String getCode() {
        return code;
    }

    public String[] getCodesPostaux() {
        return codesPostaux;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCodesPostaux(String[] codesPostaux) {
        this.codesPostaux = codesPostaux;
    }

    public String getFormattedPopulation() {
        return String.format("%, d \n", population);
    }

    public String getMapSource() {
        return "https://www.google.com/maps/embed/v1/place?key=AIzaSyC_jBQ_3VNiydl0Sfhdvfd-7YTOhJkBam8&q="
                + nom + " "
                + getCodePostal()
                + "&zoom=7";
    }

    public String getCodePostal() {
        if(code.equals("75056")) { // don't add postal to url for Paris
            return "";
        }
        return codesPostaux[0];
    }
}
