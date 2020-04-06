package org.flowable.amazonaws.persistence.datamanager;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.flowable.identitylink.service.impl.persistence.entity.IdentityLinkEntity;
import org.flowable.identitylink.service.impl.persistence.entity.IdentityLinkEntityImpl;
import org.flowable.identitylink.service.impl.persistence.entity.data.IdentityLinkDataManager;

import java.util.List;

/**
 * InMemoryIdentityLinkDataManager
 * The data manager implements IdentityLinkEntity's CRUD operations
 */
@Slf4j
public class InMemoryIdentityLinkDataManager implements IdentityLinkDataManager {

    @Override
    public List<IdentityLinkEntity> findIdentityLinksByTaskId(String taskId) {
        return Lists.newArrayList();
    }

    /**
     * Find identity links by processInstanceId (Not yet implemented)
     * @param processInstanceId
     * @return List<IdentityLinkEntity>
     */
    @Override
    public List<IdentityLinkEntity> findIdentityLinksByProcessInstanceId(String processInstanceId) {
        return Lists.newArrayList();
    }

    @Override
    public List<IdentityLinkEntity> findIdentityLinksByProcessDefinitionId(String processDefinitionId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<IdentityLinkEntity> findIdentityLinksByScopeIdAndType(String scopeId, String scopeType) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<IdentityLinkEntity> findIdentityLinksByScopeDefinitionIdAndType(String scopeDefinitionId, String scopeType) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<IdentityLinkEntity> findIdentityLinkByTaskUserGroupAndType(String taskId, String userId, String groupId, String type) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<IdentityLinkEntity> findIdentityLinkByProcessInstanceUserGroupAndType(String processInstanceId, String userId, String groupId, String type) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<IdentityLinkEntity> findIdentityLinkByProcessDefinitionUserAndGroup(String processDefinitionId, String userId, String groupId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<IdentityLinkEntity> findIdentityLinkByScopeIdScopeTypeUserGroupAndType(String scopeId, String scopeType, String userId, String groupId, String type) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<IdentityLinkEntity> findIdentityLinkByScopeDefinitionScopeTypeUserAndGroup(String scopeDefinitionId, String scopeType, String userId, String groupId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void deleteIdentityLinksByTaskId(String taskId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void deleteIdentityLinksByProcDef(String processDefId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void deleteIdentityLinksByProcessInstanceId(String processInstanceId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void deleteIdentityLinksByScopeIdAndScopeType(String scopeId, String scopeType) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void deleteIdentityLinksByScopeDefinitionIdAndScopeType(String scopeDefinitionId, String scopeType) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public IdentityLinkEntity create() {
        return new IdentityLinkEntityImpl();
    }

    @Override
    public IdentityLinkEntity findById(String entityId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void insert(IdentityLinkEntity entity) {
        log.debug("insert: {}", entity);
        //throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public IdentityLinkEntity update(IdentityLinkEntity entity) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void delete(IdentityLinkEntity entity) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }
}
