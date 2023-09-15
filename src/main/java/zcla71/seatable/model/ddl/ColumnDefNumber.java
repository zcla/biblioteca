package zcla71.seatable.model.ddl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColumnDefNumber extends ColumnDef {
    private ColumnDataNumber column_data;

    @JsonIgnore // TODO Até na interface web do SeaTable isso dá erro. Retirar quando funcionar, rs.
    public ColumnDataNumber getColumn_data() {
        return column_data;
    }

    public void setColumn_data(ColumnDataNumber column_data) {
        this.column_data = column_data;
    }

    public ColumnDefNumber() {
        super("number");
    }
}
