package zcla71.seatable.model.ddl.columndef;

import zcla71.seatable.model.ddl.columndef.data.ColumnDefDataLink;

public class ColumnDefLink extends ColumnDef {
    private ColumnDefDataLink column_data;

    public ColumnDefDataLink getColumn_data() {
        return column_data;
    }

    public void setColumn_data(ColumnDefDataLink column_data) {
        this.column_data = column_data;
    }

    public ColumnDefLink() {
        super("link");
    }
}
