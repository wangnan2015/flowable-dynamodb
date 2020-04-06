package org.flowable.amazonaws.persistence.datamanager;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.impl.persistence.cache.CachedEntityMatcher;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.amazonaws.persistence.query.DBQueryExpression;
import org.flowable.amazonaws.persistence.query.FindVariablesByExecutionId;
import org.flowable.variable.service.impl.persistence.entity.VariableInstanceEntity;
import org.flowable.variable.service.impl.persistence.entity.VariableInstanceEntityImpl;
import org.flowable.variable.service.impl.persistence.entity.data.VariableInstanceDataManager;
import org.flowable.variable.service.impl.persistence.entity.data.impl.cachematcher.VariableInstanceByExecutionIdMatcher;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * DynamoDBVariableInstanceDataManager
 * The data manager implements VariableInstanceEntity's CRUD operations
 */
@Slf4j
public class DynamoDBVariableInstanceDataManager extends AbstractDynamoDBDataManager<VariableInstanceEntity> implements VariableInstanceDataManager {

    private DBQueryExpression findVariablesByExecutionId = new FindVariablesByExecutionId();
    private DBQueryExpression deleteVariableInstancesByExecutionId = new FindVariablesByExecutionId();

    private CachedEntityMatcher<VariableInstanceEntity> variableInstanceByExecutionIdMatcher = new VariableInstanceByExecutionIdMatcher();

    public DynamoDBVariableInstanceDataManager(ProcessEngineConfigurationImpl processEngineConfiguration) {
        super(processEngineConfiguration);
    }

    /**
     * Find variables by taskId (Not yet implemented)
     * @param taskId
     * @return List<VariableInstanceEntity>
     */
    @Override
    public List<VariableInstanceEntity> findVariableInstancesByTaskId(String taskId) {
        return Lists.newArrayList();
    }

    @Override
    public List<VariableInstanceEntity> findVariableInstancesByTaskIds(Set<String> taskIds) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    /**
     * Find variables by executionId
     * @param executionId
     * @return List<VariableInstanceEntity>
     */
    @Override
    public List<VariableInstanceEntity> findVariableInstancesByExecutionId(String executionId) {
        log.debug("findVariableInstancesByExecutionId[executionId={}]", executionId);
        return findMany(findVariablesByExecutionId, executionId, variableInstanceByExecutionIdMatcher);
        //return getDynamoDBSession().getVariablesByExecutionId(executionId);
    }

    @Override
    public List<VariableInstanceEntity> findVariableInstancesByExecutionIds(Set<String> executionIds) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public VariableInstanceEntity findVariableInstanceByExecutionAndName(String executionId, String variableName) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<VariableInstanceEntity> findVariableInstancesByExecutionAndNames(String executionId, Collection<String> names) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public VariableInstanceEntity findVariableInstanceByTaskAndName(String taskId, String variableName) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<VariableInstanceEntity> findVariableInstancesByTaskAndNames(String taskId, Collection<String> names) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<VariableInstanceEntity> findVariableInstanceByScopeIdAndScopeType(String scopeId, String scopeType) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public VariableInstanceEntity findVariableInstanceByScopeIdAndScopeTypeAndName(String scopeId, String scopeType, String variableName) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<VariableInstanceEntity> findVariableInstancesByScopeIdAndScopeTypeAndNames(String scopeId, String scopeType, Collection<String> variableNames) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<VariableInstanceEntity> findVariableInstanceBySubScopeIdAndScopeType(String subScopeId, String scopeType) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public VariableInstanceEntity findVariableInstanceBySubScopeIdAndScopeTypeAndName(String subScopeId, String scopeType, String variableName) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<VariableInstanceEntity> findVariableInstancesBySubScopeIdAndScopeTypeAndNames(String subScopeId, String scopeType, Collection<String> variableNames) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void deleteVariablesByTaskId(String taskId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    /**
     * Delete variables by executionId
     * @param executionId
     */
    @Override
    public void deleteVariablesByExecutionId(String executionId) {
        log.debug("deleteVariablesByExecutionId[executionId={}]", executionId);
        bulkDelete(deleteVariableInstancesByExecutionId, executionId);
    }

    @Override
    public void deleteByScopeIdAndScopeType(String scopeId, String scopeType) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public VariableInstanceEntity create() {
        VariableInstanceEntityImpl variableInstanceEntity = new VariableInstanceEntityImpl();
        variableInstanceEntity.setRevision(0); // For backwards compatibility, variables / HistoricVariableUpdate assumes revision 0 for the first time
        return variableInstanceEntity;
    }

    @Override
    public Class<? extends VariableInstanceEntity> getManagedEntityClass() {
        return VariableInstanceEntityImpl.class;
    }

    @Override
    public VariableInstanceEntity findById(String entityId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    //@Override
    //public void insert(VariableInstanceEntity entity) {
    //    throw new UnsupportedOperationException("Unsupported Operation!");
    //}

    @Override
    public VariableInstanceEntity update(VariableInstanceEntity entity) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    //@Override
    //public void delete(VariableInstanceEntity entity) {
    //    throw new UnsupportedOperationException("Unsupported Operation!");
    //}
}
