package zcla71.dao.seatable.config.def.column;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColumnDefText extends ColumnDef {
    public ColumnDefText() {
        super("text");
    }
}
