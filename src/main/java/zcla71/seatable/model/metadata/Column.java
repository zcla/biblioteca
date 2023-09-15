package zcla71.seatable.model.metadata;

import java.util.Collection;

public class Column {
    private String key;
    private String type;
    private String name;
    private Boolean editable;
    private Integer width;
    private Boolean resizable;
    private Boolean draggable;
    private Object data; // TODO Object -> class
    private String permission_type;
    private Collection<Object> permitted_users; // TODO Object -> class
    private String edit_metadata_permission_type;
    private Collection<Object> edit_metadata_permitted_users; // TODO Object -> class
    private Object description; // TODO Object -> class

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Boolean getResizable() {
        return resizable;
    }

    public void setResizable(Boolean resizable) {
        this.resizable = resizable;
    }

    public Boolean getDraggable() {
        return draggable;
    }

    public void setDraggable(Boolean draggable) {
        this.draggable = draggable;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getPermission_type() {
        return permission_type;
    }

    public void setPermission_type(String permission_type) {
        this.permission_type = permission_type;
    }

    public Collection<Object> getPermitted_users() {
        return permitted_users;
    }

    public void setPermitted_users(Collection<Object> permitted_users) {
        this.permitted_users = permitted_users;
    }

    public String getEdit_metadata_permission_type() {
        return edit_metadata_permission_type;
    }

    public void setEdit_metadata_permission_type(String edit_metadata_permission_type) {
        this.edit_metadata_permission_type = edit_metadata_permission_type;
    }

    public Collection<Object> getEdit_metadata_permitted_users() {
        return edit_metadata_permitted_users;
    }

    public void setEdit_metadata_permitted_users(Collection<Object> edit_metadata_permitted_users) {
        this.edit_metadata_permitted_users = edit_metadata_permitted_users;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }
}
