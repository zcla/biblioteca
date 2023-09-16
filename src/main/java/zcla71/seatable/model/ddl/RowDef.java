package zcla71.seatable.model.ddl;

import java.util.Map;

public class RowDef {
    private Map<String, String> row;
    private String table_name;

    public Map<String, String> getRow() {
        return row;
    }

    public void setRow(Map<String, String> row) {
        this.row = row;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }
}
