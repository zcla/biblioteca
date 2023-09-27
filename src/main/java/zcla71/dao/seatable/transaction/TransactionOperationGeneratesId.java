package zcla71.dao.seatable.transaction;

import java.util.Map;

public interface TransactionOperationGeneratesId extends TransactionOperation {
    public abstract Map<String, String> getIdMap();
}
