package zcla71.seatable.model.param;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zcla71.dao.seatable.config.def.column.ColumnDef;
import zcla71.dao.seatable.config.def.column.ColumnDefLink;
import zcla71.dao.seatable.config.def.column.ColumnDefNumber;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class InsertColumnParam {
    @Getter @Setter
    private String table_name;
    @Getter @Setter
    private String column_name;
    @Getter @Setter
    private String column_type;
    @Getter @Setter
    private InsertColumnParamData column_data;

    public InsertColumnParam(String table_name, ColumnDef columnDef) {
        setTable_name(table_name);
        setColumn_type(columnDef.getColumn_type());
        setColumn_name(columnDef.getColumn_name());
        if (columnDef instanceof ColumnDefNumber cdn && cdn.getColumn_data() != null) {
            setColumn_data(new InsertColumnParamData());
            getColumn_data().put("format", cdn.getColumn_data().getFormat());
            getColumn_data().put("decimal", cdn.getColumn_data().getDecimal());
            getColumn_data().put("thousands", cdn.getColumn_data().getThousands());
        }
        if (columnDef instanceof ColumnDefLink cdl && cdl.getColumn_data() != null) {
            setColumn_data(new InsertColumnParamData());
            getColumn_data().put("table", cdl.getColumn_data().getTable());
            getColumn_data().put("other_table", cdl.getColumn_data().getOther_table());
        }
    }
}
