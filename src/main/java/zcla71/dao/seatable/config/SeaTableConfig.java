package zcla71.dao.seatable.config;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class SeaTableConfig {
    @Getter @Setter
    private Collection<SeaTableBase> bases;
}
