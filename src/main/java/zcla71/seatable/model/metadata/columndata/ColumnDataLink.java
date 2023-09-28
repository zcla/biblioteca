package zcla71.seatable.model.metadata.columndata;

import lombok.Data;
import lombok.EqualsAndHashCode;
import zcla71.biblioteca.model.ForceErrorIfNotNull;

@Data
@EqualsAndHashCode(callSuper=true)
public class ColumnDataLink extends ColumnData {
    private String display_column_key;
    private Boolean is_internal;
    private String link_id;
    private String table_id;
    private String other_table_id;
    private Boolean is_multiple;
    private Boolean is_row_form_view;
    private String view_id;
    private String array_type;
    private ForceErrorIfNotNull array_data;
    private String result_type;
}
