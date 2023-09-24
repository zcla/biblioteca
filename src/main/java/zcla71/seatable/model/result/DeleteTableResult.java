package zcla71.seatable.model.result;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class DeleteTableResult implements Result {
    @Getter @Setter
    private Boolean success;
}
