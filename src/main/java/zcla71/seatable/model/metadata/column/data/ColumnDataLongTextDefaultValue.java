package zcla71.seatable.model.metadata.column.data;

import java.util.Collection;

import lombok.Data;
import zcla71.biblioteca.model.ForceErrorIfNotNull;

@Data
public class ColumnDataLongTextDefaultValue {
    private String text;
    private String preview;
    private Collection<ForceErrorIfNotNull> images;
    private Collection<ForceErrorIfNotNull> links;
    private ColumnDataLongTextDefaultValueChecklist checklist;
}
