package zcla71.biblioteca.model.config;

import zcla71.dao.config.SeaTableBase;

public class Config { // TODO Tá no lugar errado. É do pacote biblioteca.
    private SeaTableBase biblioteca;

    public SeaTableBase getBiblioteca() {
        return biblioteca;
    }

    public void setBiblioteca(SeaTableBase biblioteca) {
        this.biblioteca = biblioteca;
    }
}
