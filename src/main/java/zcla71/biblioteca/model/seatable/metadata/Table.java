package zcla71.biblioteca.model.seatable.metadata;

import java.util.Collection;

public class Table {
    private String _id;
    private String name;
    private Boolean is_header_locked;
    private Object header_settings; // TODO Object -> class
    private Object summary_configs; // TODO Object -> class
    private Collection<Column> columns;
    private Collection<Object> rows; // TODO Object -> class
    private ViewStructure view_structure;
    private Collection<View> views;
    private Object id_row_map; // TODO Object -> class

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

    public Boolean getIs_header_locked() {
        return is_header_locked;
    }

    public void setIs_header_locked(Boolean is_header_locked) {
        this.is_header_locked = is_header_locked;
    }

    public Object getHeader_settings() {
        return header_settings;
    }

    public void setHeader_settings(Object header_settings) {
        this.header_settings = header_settings;
    }

    public Object getSummary_configs() {
        return summary_configs;
    }

    public void setSummary_configs(Object summary_configs) {
        this.summary_configs = summary_configs;
    }

    public Collection<Column> getColumns() {
        return columns;
    }

    public void setColumns(Collection<Column> columns) {
        this.columns = columns;
    }

    public Collection<Object> getRows() {
        return rows;
    }

    public void setRows(Collection<Object> rows) {
        this.rows = rows;
    }

    public ViewStructure getView_structure() {
        return view_structure;
    }

    public void setView_structure(ViewStructure view_structure) {
        this.view_structure = view_structure;
    }

    public Collection<View> getViews() {
        return views;
    }

    public void setViews(Collection<View> views) {
        this.views = views;
    }

    public Object getId_row_map() {
        return id_row_map;
    }

    public void setId_row_map(Object id_row_map) {
        this.id_row_map = id_row_map;
    }
}
