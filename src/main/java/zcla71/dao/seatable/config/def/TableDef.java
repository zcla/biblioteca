package zcla71.dao.seatable.config.def;

import java.util.Collection;

import lombok.Data;
import zcla71.dao.seatable.config.def.column.ColumnDef;

@Data
public class TableDef {
    private String table_name;
    private Collection<ColumnDef> columns;
}
