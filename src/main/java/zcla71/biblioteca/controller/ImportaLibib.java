package zcla71.biblioteca.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collection;

import org.springframework.http.MediaType;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opencsv.bean.CsvToBeanBuilder;

import zcla71.biblioteca.model.LibibData;

@RestController
public class ImportaLibib {
    @GetMapping(value="/libib/importa", produces={ MediaType.APPLICATION_JSON_VALUE })
    public Collection<LibibData> libibImporta() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:libib/library.csv");
        Collection<LibibData> result = new CsvToBeanBuilder<LibibData>(new FileReader(file))
            .withType(LibibData.class)
            .build()
            .parse();
        return result;
    }
}
