package zcla71.dao.config;

import java.util.Collection;

import zcla71.seatable.model.ddl.TableDef;

public class SeaTableBase {
    private String base_name;
    private Collection<TableDef> tables;

    public String getBase_name() {
        return base_name;
    }

    public void setBase_name(String base_name) {
        this.base_name = base_name;
    }

    public Collection<TableDef> getTables() {
        return tables;
    }

    public void setTables(Collection<TableDef> tables) {
        this.tables = tables;
    }
}
