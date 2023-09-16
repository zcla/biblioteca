package zcla71.seatable.model.param;

import zcla71.seatable.model.ddl.TableDef;

public class CreateNewTableParam extends TableDef {
    public CreateNewTableParam(TableDef tableDef) {
        super(tableDef.getTable_name(), tableDef.getColumns());
    }
}
