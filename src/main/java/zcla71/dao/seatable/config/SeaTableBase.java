package zcla71.dao.seatable.config;

import java.util.Collection;

import lombok.Data;
import zcla71.dao.seatable.config.def.TableDef;

@Data
public class SeaTableBase {
    private String base_name;
    private SeaTableOptions options;
    private Collection<TableDef> tables;
}
