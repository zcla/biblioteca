package zcla71.seatable.model.param;

import zcla71.seatable.model.metadata.Row;

public class AddRowParam {
    private Row row;
    private String table_name;

    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }
}
