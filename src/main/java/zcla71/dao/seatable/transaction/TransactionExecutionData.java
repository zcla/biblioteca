package zcla71.dao.seatable.transaction;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class TransactionExecutionData {
    @Getter private TransactionOperation operation;

    public Map<String, String> getIds() {
        return operation.getIdMap();
    }
}
