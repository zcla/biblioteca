package zcla71.seatable.model.param;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsertColumnParam {
    private String table_name;
    private String column_name;
    private String column_type;
    private InsertColumnParamData column_data;

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getColumn_name() {
        return column_name;
    }

    public void setColumn_name(String column_name) {
        this.column_name = column_name;
    }

    public String getColumn_type() {
        return column_type;
    }

    public void setColumn_type(String column_type) {
        this.column_type = column_type;
    }

    public InsertColumnParamData getColumn_data() {
        return column_data;
    }

    public void setColumn_data(InsertColumnParamData column_data) {
        this.column_data = column_data;
    }
}
