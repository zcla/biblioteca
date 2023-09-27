package zcla71.seatable.model.metadata;

import java.util.Collection;

import lombok.Data;
import zcla71.biblioteca.model.ForceErrorIfNotNull;

@Data
public class ViewStructure {
    private Collection<ForceErrorIfNotNull> folders;
    private Collection<String> view_ids;
}
