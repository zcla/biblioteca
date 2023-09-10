package zcla71.biblioteca.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collection;

import org.springframework.http.MediaType;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;

import zcla71.biblioteca.model.LibibCsv;

@RestController
@RequestMapping(produces={ MediaType.APPLICATION_JSON_VALUE })
public class Importa {
    @GetMapping(value="/importa/libib")
    public Collection<LibibCsv> libibImporta() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:libib/library.csv");
        Collection<LibibCsv> result = new CsvToBeanBuilder<LibibCsv>(new FileReader(file))
            .withType(LibibCsv.class)
            .withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_SEPARATORS)
            .build()
            .parse();
        return result;
    }
}
