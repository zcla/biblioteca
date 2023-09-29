package zcla71.dao.seatable.config;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zcla71.dao.seatable.config.def.TableDef;

@NoArgsConstructor
@AllArgsConstructor
public class SeaTableBase {
    @Getter @Setter
    private String base_name;
    @Getter @Setter
    private SeaTableOptions options;
    @Getter @Setter
    private Collection<TableDef> tables;
}
