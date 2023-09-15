package zcla71.biblioteca.model.seatable.ddl;

import java.util.Collection;

public class TableDef {
    private String table_name;
    private Collection<ColumnDef> columns;

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public Collection<ColumnDef> getColumns() {
        return columns;
    }

    public void setColumns(Collection<ColumnDef> columns) {
        this.columns = columns;
    }
}
