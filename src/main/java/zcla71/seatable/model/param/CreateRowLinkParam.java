package zcla71.seatable.model.param;

public class CreateRowLinkParam {
    private String table_name;
    private String other_table_name;
    private String link_id;
    private String table_row_id;
    private String other_table_row_id;

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getOther_table_name() {
        return other_table_name;
    }

    public void setOther_table_name(String other_table_name) {
        this.other_table_name = other_table_name;
    }

    public String getLink_id() {
        return link_id;
    }

    public void setLink_id(String link_id) {
        this.link_id = link_id;
    }

    public String getTable_row_id() {
        return table_row_id;
    }

    public void setTable_row_id(String table_row_id) {
        this.table_row_id = table_row_id;
    }

    public String getOther_table_row_id() {
        return other_table_row_id;
    }

    public void setOther_table_row_id(String other_table_row_id) {
        this.other_table_row_id = other_table_row_id;
    }
}
