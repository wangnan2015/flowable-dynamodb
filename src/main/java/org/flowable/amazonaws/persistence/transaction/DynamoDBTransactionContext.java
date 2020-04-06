package org.flowable.amazonaws.persistence.transaction;

import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.impl.cfg.TransactionContext;
import org.flowable.common.engine.impl.cfg.TransactionListener;
import org.flowable.common.engine.impl.cfg.TransactionPropagation;
import org.flowable.common.engine.impl.cfg.TransactionState;
import org.flowable.common.engine.impl.context.Context;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandConfig;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.common.engine.impl.interceptor.CommandExecutor;
import org.flowable.amazonaws.persistence.session.DynamoDBSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class DynamoDBTransactionContext implements TransactionContext {

    private CommandContext commandContext;
    private DynamoDBSession dynamoDBSession;
    private Map<TransactionState, List<TransactionListener>> stateTransactionListeners;

    public DynamoDBTransactionContext(CommandContext commandContext) {
        this.commandContext = commandContext;
        this.dynamoDBSession = commandContext.getSession(DynamoDBSession.class);
    }

    @Override
    public void commit() {
        log.debug("firing event committing...");
        fireTransactionEvent(TransactionState.COMMITTING, false);

        log.debug("committing the dynamodb session...");
        dynamoDBSession.commit();

        log.debug("firing event committed...");
        fireTransactionEvent(TransactionState.COMMITTED, true);
    }

    @Override
    public void rollback() {
        dynamoDBSession.rollback();
    }

    @Override
    public void addTransactionListener(TransactionState transactionState, TransactionListener transactionListener) {
        log.debug("addTransactionListener[transactionState={}, transactionListener={}]", transactionState, transactionListener);
        if (stateTransactionListeners == null) {
            stateTransactionListeners = new HashMap<>();
        }
        List<TransactionListener> transactionListeners = stateTransactionListeners.get(transactionState);
        if (transactionListeners == null) {
            transactionListeners = new ArrayList<>();
            stateTransactionListeners.put(transactionState, transactionListeners);
        }
        transactionListeners.add(transactionListener);
    }

    private void fireTransactionEvent(TransactionState transactionState, boolean executeInNewContext) {
        if (stateTransactionListeners == null) {
            return;
        }
        final List<TransactionListener> transactionListeners = stateTransactionListeners.get(transactionState);
        if (transactionListeners == null) {
            return;
        }

        if (executeInNewContext) {
            CommandExecutor commandExecutor = Context.getCommandContext().getCurrentEngineConfiguration().getCommandExecutor();
            CommandConfig commandConfig = new CommandConfig(false, TransactionPropagation.REQUIRES_NEW);
            commandExecutor.execute(commandConfig, new Command<Void>() {
                @Override
                public Void execute(CommandContext commandContext) {
                    executeTransactionListeners(transactionListeners, commandContext);
                    return null;
                }
            });
        } else {
            executeTransactionListeners(transactionListeners, commandContext);
        }
    }

    private void executeTransactionListeners(List<TransactionListener> transactionListeners, CommandContext commandContext) {
        for (TransactionListener transactionListener : transactionListeners) {
            transactionListener.execute(commandContext);
        }
    }

}
