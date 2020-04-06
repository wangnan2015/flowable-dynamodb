package org.flowable.amazonaws.persistence.datamanager;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.flowable.eventsubscription.api.EventSubscription;
import org.flowable.eventsubscription.service.impl.EventSubscriptionQueryImpl;
import org.flowable.eventsubscription.service.impl.persistence.entity.CompensateEventSubscriptionEntity;
import org.flowable.eventsubscription.service.impl.persistence.entity.EventSubscriptionEntity;
import org.flowable.eventsubscription.service.impl.persistence.entity.MessageEventSubscriptionEntity;
import org.flowable.eventsubscription.service.impl.persistence.entity.SignalEventSubscriptionEntity;
import org.flowable.eventsubscription.service.impl.persistence.entity.data.EventSubscriptionDataManager;

import java.util.List;

/**
 * InMemoryEventSubscriptionDataManager
 * The data manager implements EventSubscriptionEntity's CRUD operations
 */
@Slf4j
public class InMemoryEventSubscriptionDataManager implements EventSubscriptionDataManager {

    @Override
    public MessageEventSubscriptionEntity createMessageEventSubscription() {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public SignalEventSubscriptionEntity createSignalEventSubscription() {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public CompensateEventSubscriptionEntity createCompensateEventSubscription() {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public long findEventSubscriptionCountByQueryCriteria(EventSubscriptionQueryImpl eventSubscriptionQueryImpl) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<EventSubscription> findEventSubscriptionsByQueryCriteria(EventSubscriptionQueryImpl eventSubscriptionQueryImpl) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<MessageEventSubscriptionEntity> findMessageEventSubscriptionsByProcessInstanceAndEventName(String processInstanceId, String eventName) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<SignalEventSubscriptionEntity> findSignalEventSubscriptionsByEventName(String eventName, String tenantId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<SignalEventSubscriptionEntity> findSignalEventSubscriptionsByProcessInstanceAndEventName(String processInstanceId, String eventName) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<SignalEventSubscriptionEntity> findSignalEventSubscriptionsByScopeAndEventName(String scopeId, String scopeType, String eventName) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<SignalEventSubscriptionEntity> findSignalEventSubscriptionsByNameAndExecution(String name, String executionId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<EventSubscriptionEntity> findEventSubscriptionsByExecutionAndType(String executionId, String type) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<EventSubscriptionEntity> findEventSubscriptionsByProcessInstanceAndActivityId(String processInstanceId, String activityId, String type) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    /**
     * Find event subscriptions by executionId (Not yet implemented)
     * @param executionId
     * @return List<EventSubscriptionEntity>
     */
    @Override
    public List<EventSubscriptionEntity> findEventSubscriptionsByExecution(String executionId) {
        return Lists.newArrayList();
    }

    @Override
    public List<EventSubscriptionEntity> findEventSubscriptionsBySubScopeId(String subScopeId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    /**
     * Find event subscriptions by type and processDefinitionId (Not yet implemented)
     * @param type
     * @param processDefinitionId
     * @param tenantId
     * @return List<EventSubscriptionEntity>
     */
    @Override
    public List<EventSubscriptionEntity> findEventSubscriptionsByTypeAndProcessDefinitionId(String type, String processDefinitionId, String tenantId) {
        return Lists.newArrayList();
    }

    @Override
    public List<EventSubscriptionEntity> findEventSubscriptionsByName(String type, String eventName, String tenantId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<EventSubscriptionEntity> findEventSubscriptionsByNameAndExecution(String type, String eventName, String executionId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public MessageEventSubscriptionEntity findMessageStartEventSubscriptionByName(String messageName, String tenantId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void updateEventSubscriptionTenantId(String oldTenantId, String newTenantId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void deleteEventSubscriptionsForProcessDefinition(String processDefinitionId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void deleteEventSubscriptionsByExecutionId(String executionId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void deleteEventSubscriptionsForScopeIdAndType(String scopeId, String scopeType) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public EventSubscriptionEntity create() {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public EventSubscriptionEntity findById(String entityId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void insert(EventSubscriptionEntity entity) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public EventSubscriptionEntity update(EventSubscriptionEntity entity) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void delete(EventSubscriptionEntity entity) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }
}
