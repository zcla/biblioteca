package zcla71.seatable.model.ddl;

import zcla71.seatable.model.metadata.Table;

public class TableDeleteDef {
    private String table_name;

    public String getTable_name() {
        return table_name;
    }

    public TableDeleteDef(Table table) {
        this.table_name = table.getName();
    }
}
