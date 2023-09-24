package zcla71.seatable.model.param;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import zcla71.utils.Utils;

public class ListRowsParam {
    public enum Direction {
        asc, desc
    };

    @Getter @Setter
    private String table_name;
    @Getter @Setter
    private String view_name;
    @Getter @Setter
    private Boolean convert_link_id;
    @Getter @Setter
    private String order_by;
    @Getter @Setter
    private Direction direction;
    @Getter @Setter
    private Integer start;
    @Getter @Setter
    private Integer limit;

    public ListRowsParam(String table_name) {
        this.table_name = table_name;
    }

    @JsonIgnore
    public String getUrlParams() {
        String separator = "?";
        String result = "";
        if (table_name != null) {
            result += separator + "table_name=" + Utils.encodeURIComponent(table_name);
            separator = "&";
        }
        if (view_name != null) {
            result += separator + "view_name=" + Utils.encodeURIComponent(view_name);
            separator = "&";
        }
        if (convert_link_id != null) {
            result += separator + "convert_link_id=" + Utils.encodeURIComponent(convert_link_id ? "true" : "false");
            separator = "&";
        }
        if (order_by != null) {
            result += separator + "order_by=" + Utils.encodeURIComponent(order_by);
            separator = "&";
        }
        if (direction != null) {
            result += separator + "direction=" + Utils.encodeURIComponent(direction.toString());
            separator = "&";
        }
        if (start != null) {
            result += separator + "start=" + Utils.encodeURIComponent(start.toString());
            separator = "&";
        }
        if (limit != null) {
            result += separator + "limit=" + Utils.encodeURIComponent(limit.toString());
            separator = "&";
        }
        return result;
    }
}
