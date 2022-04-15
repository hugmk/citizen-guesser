package org.citizenGuesser.service;

import org.citizenGuesser.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class CityAssigner {

    private final static int easyModeMaxCitizens = 40000;

    @Value("${api.geo.url}")
    private String apiGeoUrl;

    @Autowired
    private DataService dataService;

    public City getRandomCity(HashMap<String, List<String>> pickedCities, boolean hardMode) {
        List<City> cityList;
        String department;
        City randomCity = null;

        do {
            department = getRandomDepartment();
            final String requestUrl = apiGeoUrl
                    + "departements/"
                    + department
                    + "/communes?fields=nom,codesPostaux,population&format=json&geometry=centre";

            RestTemplate restTemplate = new RestTemplate();
            City[] response = restTemplate.getForEntity(requestUrl, City[].class).getBody();
            cityList = new ArrayList<>(Arrays.asList(response));
            cityList.removeIf(city -> (city.getPopulation() == 0));
            if(!hardMode) {
                cityList.removeIf(city -> (city.getPopulation() < easyModeMaxCitizens));
            }
        } while (cityList.size() == pickedCities.get(department).size());

        while(randomCity == null || pickedCities.get(department).contains(randomCity.getCode())) {
            randomCity = getCityFromList(cityList);
        }

        return randomCity;
    }

    private City getCityFromList(List<City> cities) {
        Random rand = new Random();
        int randomIndex = rand.nextInt(cities.size());

        return cities.get(randomIndex);
    }

    private String getRandomDepartment() {
        List<String> departments = dataService.getDepartments();

        Random rand = new Random();
        int randomIndex = rand.nextInt(departments.size());

        return departments.get(randomIndex);
    }
}
