package zcla71.seatable.model.param;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zcla71.seatable.model.metadata.Row;

@NoArgsConstructor
@AllArgsConstructor
public class AppendRowsParam {
    @Getter @Setter
    private Collection<Row> rows;
    @Getter @Setter
    private String table_name;
}
