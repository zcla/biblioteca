package zcla71.seatable.model.result;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class DeleteRowsResult implements Result {
    @Getter @Setter
    private Integer deleted_rows;
}
