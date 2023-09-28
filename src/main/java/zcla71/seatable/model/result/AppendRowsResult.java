package zcla71.seatable.model.result;

import lombok.Data;

@Data
public class AppendRowsResult implements Result {
    private Integer inserted_row_count;
}
