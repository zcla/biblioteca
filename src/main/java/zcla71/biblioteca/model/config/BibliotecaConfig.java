package zcla71.biblioteca.model.config;

import zcla71.dao.seatable.SeaTableConfig;

public class BibliotecaConfig {
    private LibibConfig libib;
    private SeaTableConfig seaTable;

    public LibibConfig getLibib() {
        return libib;
    }

    public void setLibib(LibibConfig libib) {
        this.libib = libib;
    }

    public SeaTableConfig getSeaTable() {
        return seaTable;
    }

    public void setSeaTable(SeaTableConfig seaTable) {
        this.seaTable = seaTable;
    }
}
