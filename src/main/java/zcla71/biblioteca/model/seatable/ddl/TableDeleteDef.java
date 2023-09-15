package zcla71.biblioteca.model.seatable.ddl;

import zcla71.biblioteca.model.seatable.metadata.Table;

public class TableDeleteDef {
    private String table_name;

    public String getTable_name() {
        return table_name;
    }

    public TableDeleteDef(Table table) {
        this.table_name = table.getName();
    }
}
