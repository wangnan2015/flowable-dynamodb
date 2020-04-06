package org.flowable.amazonaws.persistence.datamanager;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.event.EventLogEntry;
import org.flowable.engine.impl.persistence.entity.EventLogEntryEntity;
import org.flowable.engine.impl.persistence.entity.EventLogEntryEntityImpl;
import org.flowable.engine.impl.persistence.entity.data.EventLogEntryDataManager;

import java.util.List;

/**
 * InMemoryEventLogEntryDataManager
 * The data manager implements EventLogEntryEntity's CRUD operations
 */
@Slf4j
public class InMemoryEventLogEntryDataManager implements EventLogEntryDataManager {

    @Override
    public List<EventLogEntry> findAllEventLogEntries() {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<EventLogEntry> findEventLogEntries(long startLogNr, long pageSize) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<EventLogEntry> findEventLogEntriesByProcessInstanceId(String processInstanceId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void deleteEventLogEntry(long logNr) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public EventLogEntryEntity create() {
        return new EventLogEntryEntityImpl();
    }

    @Override
    public EventLogEntryEntity findById(String entityId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void insert(EventLogEntryEntity entity) {
        //LOG.info("Send Event[type={}, processInstanceId={}, executionId={}, taskId={}, timestamp={}, data={}]",
        //        entity.getType(), entity.getProcessInstanceId(), entity.getExecutionId(),
        //        entity.getTaskId(), entity.getTimeStamp(), new String(entity.getData()));
    }

    @Override
    public EventLogEntryEntity update(EventLogEntryEntity entity) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void delete(EventLogEntryEntity entity) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }
}
