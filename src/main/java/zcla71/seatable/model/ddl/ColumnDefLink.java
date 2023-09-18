package zcla71.seatable.model.ddl;

public class ColumnDefLink extends ColumnDef {
    private ColumnDataLink column_data;

    public ColumnDataLink getColumn_data() {
        return column_data;
    }

    public void setColumn_data(ColumnDataLink column_data) {
        this.column_data = column_data;
    }

    public ColumnDefLink() {
        super("link");
    }
}
