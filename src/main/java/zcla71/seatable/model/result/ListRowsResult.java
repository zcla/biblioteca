package zcla71.seatable.model.result;

import java.util.Collection;

import zcla71.seatable.model.metadata.Row;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class ListRowsResult implements Result {
    @Getter @Setter
    private Collection<Row> rows;
}
