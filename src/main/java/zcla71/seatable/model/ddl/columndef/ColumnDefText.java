package zcla71.seatable.model.ddl.columndef;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColumnDefText extends ColumnDef {
    public ColumnDefText() {
        super("text");
    }
}
