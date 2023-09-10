package zcla71.biblioteca.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;

import org.springframework.http.MediaType;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zcla71.biblioteca.dao.CsvDao;
import zcla71.biblioteca.model.LibibCsv;

@RestController
@RequestMapping(produces={ MediaType.APPLICATION_JSON_VALUE })
public class Importa {
    @GetMapping(value="/importa/libib")
    public Collection<LibibCsv> libibImporta() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:libib/library.csv");
        CsvDao<LibibCsv> dao = new CsvDao<LibibCsv>(LibibCsv.class);
        Collection<LibibCsv> result = dao.read(file);
        return result;
    }
}
