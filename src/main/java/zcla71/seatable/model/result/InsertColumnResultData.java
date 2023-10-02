package zcla71.seatable.model.result;

import lombok.Data;

// MUITO parecida com zcla71.seatable.model.metadata.column.data.ColumnData, mas dá problema se usar herança.
@Data
public class InsertColumnResultData {
    private String display_column_key;
    private Boolean is_internal;
    private String link_id;
    private String table_id;
    private String other_table_id;
    private Boolean is_multiple;
    private Boolean is_row_form_view;
    private String view_id;
    private String format;
}
