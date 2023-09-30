package zcla71.dao.seatable.config.def.column;

import com.fasterxml.jackson.annotation.JsonInclude;

import zcla71.dao.seatable.config.def.column.data.ColumnDefDataNumber;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColumnDefNumber extends ColumnDef {
    private ColumnDefDataNumber column_data;

    public ColumnDefDataNumber getColumn_data() {
        return column_data;
    }

    public void setColumn_data(ColumnDefDataNumber column_data) {
        this.column_data = column_data;
    }

    public ColumnDefNumber() {
        super("number");
    }
}