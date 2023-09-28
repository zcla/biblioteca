package zcla71.seatable.model.metadata;

import lombok.Data;
import zcla71.biblioteca.model.ForceErrorIfNotNull;

@Data
public class ColumnData {
    // TODO Herança
    // link
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
    // number
    private String format;
    private String decimal;
    private String thousands;
    // ? (apareceram quando adicionei um campo Number com um "Data processing" do tipo "Calculated accumulated value" atrelado a ele)
    private Integer precision;
    private Boolean enable_precision;
    private Boolean enable_fill_default_value;
    private Boolean enable_check_format;
    private Integer format_min_value;
    private Integer format_max_value;
}
