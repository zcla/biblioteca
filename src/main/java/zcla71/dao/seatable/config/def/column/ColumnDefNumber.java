package zcla71.dao.seatable.config.def.column;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
import zcla71.dao.seatable.config.def.column.data.ColumnDefDataNumber;

@Data
@EqualsAndHashCode(callSuper=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColumnDefNumber extends ColumnDef {
    private ColumnDefDataNumber column_data;

    public ColumnDefNumber() {
        super("number");
    }
}
