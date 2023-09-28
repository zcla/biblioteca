package zcla71.seatable.model.result;

import java.util.Collection;

import zcla71.seatable.model.metadata.Row;
import lombok.Data;

@Data
public class ListRowsResult implements Result {
    private Collection<Row> rows;
}
