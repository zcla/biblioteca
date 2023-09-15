package zcla71.seatable.model.metadata;

import java.util.Collection;

public class MetadataMetadata {
    private Collection<Table> tables;
    private Integer version;
    private Integer format_version;

    public Collection<Table> getTables() {
        return tables;
    }

    public void setTables(Collection<Table> tables) {
        this.tables = tables;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getFormat_version() {
        return format_version;
    }

    public void setFormat_version(Integer format_version) {
        this.format_version = format_version;
    }
}
