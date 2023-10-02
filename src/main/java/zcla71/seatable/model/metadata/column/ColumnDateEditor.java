package zcla71.seatable.model.metadata.column;

import java.util.Map;

import lombok.Data;
import zcla71.biblioteca.model.ForceErrorIfNotNull;

@Data
public class ColumnDateEditor { // Igual a ColorNumberEditor
    private ForceErrorIfNotNull key;
    private ForceErrorIfNotNull ref;
    private Map<String,ForceErrorIfNotNull> props;
    private ForceErrorIfNotNull _owner;
}
