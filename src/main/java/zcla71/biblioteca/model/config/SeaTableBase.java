package zcla71.biblioteca.model.config;

import java.util.Collection;

import zcla71.seatable.model.ddl.TableDef;

public class SeaTableBase {
    private String name;
    private Collection<TableDef> tables;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<TableDef> getTables() {
        return tables;
    }

    public void setTables(Collection<TableDef> tables) {
        this.tables = tables;
    }
}
