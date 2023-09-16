package zcla71.seatable.model.param;

import java.util.Collection;

import zcla71.seatable.model.metadata.Row;

public class AppendRowsParam {
    private Collection<Row> rows;
    private String table_name;

    public Collection<Row> getRows() {
        return rows;
    }

    public void setRows(Collection<Row> rows) {
        this.rows = rows;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }
}
