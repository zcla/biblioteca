package zcla71.biblioteca.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collection;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;

public class CsvDao<T> {
    private Class<T> classe;

    // Sei que não é muito elegante, mas foi o jeito mais fácil de conseguir saber qual é a classe T.
    public CsvDao(Class<T> classe) {
        this.classe = classe;
    }

    public Collection<T> read(File file) throws IllegalStateException, FileNotFoundException {
        return new CsvToBeanBuilder<T>(new FileReader(file))
            .withType(classe)
            .withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_SEPARATORS)
            .build()
            .parse();
    }
}
