package zcla71.seatable.model.metadata;

public class ColumnData {
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
    private Object array_data; // TODO Object -> class
    private String result_type;
    // number
    private String format;
    private String decimal;
    private String thousands;

    public String getDisplay_column_key() {
        return display_column_key;
    }

    public void setDisplay_column_key(String display_column_key) {
        this.display_column_key = display_column_key;
    }

    public Boolean getIs_internal() {
        return is_internal;
    }

    public void setIs_internal(Boolean is_internal) {
        this.is_internal = is_internal;
    }

    public String getLink_id() {
        return link_id;
    }

    public void setLink_id(String link_id) {
        this.link_id = link_id;
    }

    public String getTable_id() {
        return table_id;
    }

    public void setTable_id(String table_id) {
        this.table_id = table_id;
    }

    public String getOther_table_id() {
        return other_table_id;
    }

    public void setOther_table_id(String other_table_id) {
        this.other_table_id = other_table_id;
    }

    public Boolean getIs_multiple() {
        return is_multiple;
    }

    public void setIs_multiple(Boolean is_multiple) {
        this.is_multiple = is_multiple;
    }

    public Boolean getIs_row_form_view() {
        return is_row_form_view;
    }

    public void setIs_row_form_view(Boolean is_row_form_view) {
        this.is_row_form_view = is_row_form_view;
    }

    public String getView_id() {
        return view_id;
    }

    public void setView_id(String view_id) {
        this.view_id = view_id;
    }

    public String getArray_type() {
        return array_type;
    }

    public void setArray_type(String array_type) {
        this.array_type = array_type;
    }

    public Object getArray_data() {
        return array_data;
    }

    public void setArray_data(Object array_data) {
        this.array_data = array_data;
    }

    public String getResult_type() {
        return result_type;
    }

    public void setResult_type(String result_type) {
        this.result_type = result_type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getDecimal() {
        return decimal;
    }

    public void setDecimal(String decimal) {
        this.decimal = decimal;
    }

    public String getThousands() {
        return thousands;
    }

    public void setThousands(String thousands) {
        this.thousands = thousands;
    }
}
