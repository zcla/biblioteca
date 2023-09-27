package zcla71.dao.seatable.transaction;

import java.util.Map;

public interface TransactionOperationRequiresId extends TransactionOperation {
    public abstract void applyIdMap(Map<String, String> idMap);
}
