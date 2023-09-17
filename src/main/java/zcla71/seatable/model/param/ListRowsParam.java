package zcla71.seatable.model.param;

import com.fasterxml.jackson.annotation.JsonIgnore;

import zcla71.utils.Utils;

public class ListRowsParam {
    public enum Direction {
        asc, desc
    };

    private String table_name;
    private String view_name;
    private Boolean convert_link_id;
    private String order_by;
    private Direction direction;
    private Integer start;
    private Integer limit;

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getView_name() {
        return view_name;
    }

    public void setView_name(String view_name) {
        this.view_name = view_name;
    }

    public Boolean getConvert_link_id() {
        return convert_link_id;
    }

    public void setConvert_link_id(Boolean convert_link_id) {
        this.convert_link_id = convert_link_id;
    }

    public String getOrder_by() {
        return order_by;
    }

    public void setOrder_by(String order_by) {
        this.order_by = order_by;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

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
