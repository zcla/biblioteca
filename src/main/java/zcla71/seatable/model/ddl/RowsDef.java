package zcla71.seatable.model.ddl;

import java.util.Collection;
import java.util.Map;

public class RowsDef {
    private Collection<Map<String, String>> rows;
    private String table_name;

    public Collection<Map<String, String>> getRows() {
        return rows;
    }

    public void setRows(Collection<Map<String, String>> rows) {
        this.rows = rows;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }
}
