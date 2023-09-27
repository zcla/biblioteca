package zcla71.dao.seatable.transaction;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class TransactionExecutionData {
    @Getter @Setter
    private TransactionOperationGeneratesId operation;

    public Map<String, String> getIds() {
        return operation.getIdMap();
    }
}
