package zcla71.seatable.model.result;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class AppendRowsResult implements Result {
    @Getter @Setter
    private Integer inserted_row_count;
}
