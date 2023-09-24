package zcla71.seatable.model.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class ListRowsSqlParam {
    @Getter @Setter
    private String sql;
    @Getter @Setter
    private Boolean convert_keys;
}
