package zcla71.dao.seatable.config.def.column;

import lombok.Data;
import lombok.EqualsAndHashCode;
import zcla71.dao.seatable.config.def.column.data.ColumnDefDataLink;

@Data
@EqualsAndHashCode(callSuper=true)
public class ColumnDefLink extends ColumnDef {
    private ColumnDefDataLink column_data;

    public ColumnDefLink() {
        super("link");
    }
}
