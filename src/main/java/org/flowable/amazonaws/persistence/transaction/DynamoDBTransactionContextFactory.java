package org.flowable.amazonaws.persistence.transaction;

import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.impl.cfg.TransactionContext;
import org.flowable.common.engine.impl.cfg.TransactionContextFactory;
import org.flowable.common.engine.impl.interceptor.CommandContext;

@Slf4j
public class DynamoDBTransactionContextFactory implements TransactionContextFactory {

    @Override
    public TransactionContext openTransactionContext(CommandContext commandContext) {
        return new DynamoDBTransactionContext(commandContext);
    }
}
