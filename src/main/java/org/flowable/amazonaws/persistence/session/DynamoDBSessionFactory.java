package org.flowable.amazonaws.persistence.session;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.flowable.amazonaws.cfg.AwsProcessEngineConfiguration;
import org.flowable.common.engine.impl.context.Context;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.common.engine.impl.interceptor.Session;
import org.flowable.common.engine.impl.interceptor.SessionFactory;
import org.flowable.common.engine.impl.persistence.cache.EntityCache;

@Slf4j
public class DynamoDBSessionFactory implements SessionFactory {

    @Getter
    private AwsProcessEngineConfiguration processEngineConfiguration;

    public DynamoDBSessionFactory(AwsProcessEngineConfiguration processEngineConfiguration) {
        this.processEngineConfiguration = processEngineConfiguration;
    }

    @Override
    public Class<?> getSessionType() {
        return DynamoDBSession.class;
    }

    @Override
    public Session openSession(CommandContext commandContext) {
        return new DynamoDBSession(this, Context.getCommandContext().getSession(EntityCache.class));
    }

}
