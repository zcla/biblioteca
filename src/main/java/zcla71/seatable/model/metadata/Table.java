package zcla71.seatable.model.metadata;

import java.util.Collection;

import lombok.Data;
import zcla71.biblioteca.model.ForceErrorIfNotNull;
import zcla71.seatable.model.metadata.column.Column;

@Data
public class Table {
    private String _id;
    private String name;
    private Boolean is_header_locked;
    private ForceErrorIfNotNull header_settings;
    private ForceErrorIfNotNull summary_configs;
    private Collection<Column> columns;
    private Collection<ForceErrorIfNotNull> rows;
    private ViewStructure view_structure;
    private Collection<View> views;
    private ForceErrorIfNotNull id_row_map;
}
