package org.citizenGuesser.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {

    public List<String> getDepartments() {
        List<String> departments = new ArrayList<>();
        try {
            File file = new ClassPathResource("data/departements.csv").getFile();
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                departments.add(line);
            }
            bufferedReader.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return departments;
    }
}
