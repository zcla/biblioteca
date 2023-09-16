package zcla71.seatable.model.param;

import zcla71.seatable.model.metadata.Table;

public class DeleteTableParam {
    private String table_name;

    public String getTable_name() {
        return table_name;
    }

    public DeleteTableParam(Table table) {
        this.table_name = table.getName();
    }
}
