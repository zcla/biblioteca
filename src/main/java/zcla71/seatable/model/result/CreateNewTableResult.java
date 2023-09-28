package zcla71.seatable.model.result;

import java.util.Collection;

import lombok.Data;
import zcla71.biblioteca.model.ForceErrorIfNotNull;
import zcla71.seatable.model.metadata.View;
import zcla71.seatable.model.metadata.ViewStructure;

// MUITO parecida com zcla71.seatable.model.metadata.Table, mas dá problema com a propriedade "column" se usar herança.
@Data
public class CreateNewTableResult implements Result {
    private String _id;
    private String name;
    private Boolean is_header_locked;
    private ForceErrorIfNotNull header_settings;
    private ForceErrorIfNotNull summary_configs;
    private Collection<InsertColumnResult> columns;
    private Collection<ForceErrorIfNotNull> rows;
    private ViewStructure view_structure;
    private Collection<View> views;
    private ForceErrorIfNotNull id_row_map;
}
