package zcla71.seatable.model.ddl;

import java.util.Collection;

public class TableDef {
    private String table_name;
    private Collection<ColumnDef> columns;

    public TableDef() {
    }

    public TableDef(String table_name, Collection<ColumnDef> columns) {
        this.table_name = table_name;
        this.columns = columns;
    }

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
