package org.flowable.amazonaws.persistence.datamanager;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionInfoEntity;
import org.flowable.engine.impl.persistence.entity.data.ProcessDefinitionInfoDataManager;

/**
 * InMemoryProcessDefinitionInfoDataManager
 * The data manager implements ProcessDefinitionInfoEntity's CRUD operations
 */
@Slf4j
public class InMemoryProcessDefinitionInfoDataManager implements ProcessDefinitionInfoDataManager {

    /**
     * Find process definition info by processDefinitionId (Not yet implemented)
     * @param processDefinitionId
     * @return ProcessDefinitionInfoEntity
     */
    @Override
    public ProcessDefinitionInfoEntity findProcessDefinitionInfoByProcessDefinitionId(String processDefinitionId) {
        return null;
    }

    @Override
    public ProcessDefinitionInfoEntity create() {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public ProcessDefinitionInfoEntity findById(String entityId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void insert(ProcessDefinitionInfoEntity entity) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public ProcessDefinitionInfoEntity update(ProcessDefinitionInfoEntity entity) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void delete(ProcessDefinitionInfoEntity entity) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }
}
