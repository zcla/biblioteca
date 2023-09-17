package zcla71.seatable.model.param;

import java.util.Collection;

public class DeleteRowsParam {
    private String table_name;
    private Collection<String> row_ids;

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public Collection<String> getRow_ids() {
        return row_ids;
    }

    public void setRow_ids(Collection<String> row_ids) {
        this.row_ids = row_ids;
    }
}
