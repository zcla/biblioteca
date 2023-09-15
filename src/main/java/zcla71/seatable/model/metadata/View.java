package zcla71.seatable.model.metadata;

import java.util.Collection;

public class View {
    private String _id;
    private String name;
    private String type;
    private Object private_for; // TODO Object -> class
    private Boolean is_locked;
    private String row_height;
    private String filter_conjunction;
    private Collection<Object> filters; // TODO Object -> class
    private Collection<Object> sorts; // TODO Object -> class
    private Collection<Object> groupbys; // TODO Object -> class
    private Object colorbys; // TODO Object -> class
    private Collection<Object> hidden_columns; // TODO Object -> class
    private Collection<Object> rows; // TODO Object -> class
    private Object formula_rows; // TODO Object -> class
    private Object link_rows; // TODO Object -> class
    private Object summaries; // TODO Object -> class
    private Object colors; // TODO Object -> class
    private Object column_colors; // TODO Object -> class
    private Collection<Object> groups; // TODO Object -> class

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getPrivate_for() {
        return private_for;
    }

    public void setPrivate_for(Object private_for) {
        this.private_for = private_for;
    }

    public Boolean getIs_locked() {
        return is_locked;
    }

    public void setIs_locked(Boolean is_locked) {
        this.is_locked = is_locked;
    }

    public String getRow_height() {
        return row_height;
    }

    public void setRow_height(String row_height) {
        this.row_height = row_height;
    }

    public String getFilter_conjunction() {
        return filter_conjunction;
    }

    public void setFilter_conjunction(String filter_conjunction) {
        this.filter_conjunction = filter_conjunction;
    }

    public Collection<Object> getFilters() {
        return filters;
    }

    public void setFilters(Collection<Object> filters) {
        this.filters = filters;
    }

    public Collection<Object> getSorts() {
        return sorts;
    }

    public void setSorts(Collection<Object> sorts) {
        this.sorts = sorts;
    }

    public Collection<Object> getGroupbys() {
        return groupbys;
    }

    public void setGroupbys(Collection<Object> groupbys) {
        this.groupbys = groupbys;
    }

    public Object getColorbys() {
        return colorbys;
    }

    public void setColorbys(Object colorbys) {
        this.colorbys = colorbys;
    }

    public Collection<Object> getHidden_columns() {
        return hidden_columns;
    }

    public void setHidden_columns(Collection<Object> hidden_columns) {
        this.hidden_columns = hidden_columns;
    }

    public Collection<Object> getRows() {
        return rows;
    }

    public void setRows(Collection<Object> rows) {
        this.rows = rows;
    }

    public Object getFormula_rows() {
        return formula_rows;
    }

    public void setFormula_rows(Object formula_rows) {
        this.formula_rows = formula_rows;
    }

    public Object getLink_rows() {
        return link_rows;
    }

    public void setLink_rows(Object link_rows) {
        this.link_rows = link_rows;
    }

    public Object getSummaries() {
        return summaries;
    }

    public void setSummaries(Object summaries) {
        this.summaries = summaries;
    }

    public Object getColors() {
        return colors;
    }

    public void setColors(Object colors) {
        this.colors = colors;
    }

    public Object getColumn_colors() {
        return column_colors;
    }

    public void setColumn_colors(Object column_colors) {
        this.column_colors = column_colors;
    }

    public Collection<Object> getGroups() {
        return groups;
    }

    public void setGroups(Collection<Object> groups) {
        this.groups = groups;
    }
}
