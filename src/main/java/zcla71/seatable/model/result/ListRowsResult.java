package zcla71.seatable.model.result;

import java.util.Collection;

import zcla71.seatable.model.metadata.Row;

public class ListRowsResult {
    private Collection<Row> rows;

    public Collection<Row> getRows() {
        return rows;
    }

    public void setRows(Collection<Row> rows) {
        this.rows = rows;
    }
}
