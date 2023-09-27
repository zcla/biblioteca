package zcla71.seatable.model.metadata;

import java.util.Collection;

import lombok.Data;

@Data
public class MetadataMetadata {
    private Collection<Table> tables;
    private Integer version;
    private Integer format_version;
}
