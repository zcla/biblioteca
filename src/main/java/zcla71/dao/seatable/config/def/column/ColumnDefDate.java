package zcla71.dao.seatable.config.def.column;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
import zcla71.dao.seatable.config.def.column.data.ColumnDefDataDate;

@Data
@EqualsAndHashCode(callSuper=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColumnDefDate extends ColumnDef {
    private ColumnDefDataDate column_data;

    public ColumnDefDate() {
        super("date");
    }
}
