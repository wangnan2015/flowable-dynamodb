package org.flowable.amazonaws.persistence.dao;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.flowable.amazonaws.cfg.AwsProcessEngineConfiguration;
import org.flowable.engine.impl.persistence.entity.*;
import org.flowable.job.service.impl.persistence.entity.JobEntityImpl;
import org.flowable.task.service.impl.persistence.entity.TaskEntityImpl;
import org.flowable.variable.service.impl.persistence.entity.VariableInstanceEntityImpl;

import java.util.Map;

@Getter
@Slf4j
public class AwsDataSourceFactory {

    public static final Class PROCESS_DEFINITION_ENTITY = ProcessDefinitionEntityImpl.class;
    public static final Class DEPLOYMENT_ENTITY = DeploymentEntityImpl.class;
    public static final Class RESOURCE_ENTITY = ResourceEntityImpl.class;
    public static final Class EXECUTION_ENTITY = ExecutionEntityImpl.class;
    public static final Class ACTIVITY_ENTITY = ActivityInstanceEntityImpl.class;
    public static final Class VARIABLE_ENTITY = VariableInstanceEntityImpl.class;
    public static final Class TASK_ENTITY = TaskEntityImpl.class;
    public static final Class JOB_ENTITY = JobEntityImpl.class;

    private ResourceDaoImpl resourceDao;
    private VariableDocumentDaoImpl variableDocumentDao;
    private ProcessDefinitionDaoImpl processDefinitionDao;
    private ProcessExecutionDaoImpl processExecutionDao;
    private TaskDaoImpl taskDao;
    private JobDaoImpl jobDao;

    private final Map ENTITY_TO_DAO_MAPPING;

    @SuppressWarnings("unchecked")
    public AwsDataSourceFactory(AwsProcessEngineConfiguration processEngineConfiguration) {
        resourceDao = new ResourceDaoImpl(processEngineConfiguration);
        variableDocumentDao = new VariableDocumentDaoImpl(processEngineConfiguration);
        processDefinitionDao = new ProcessDefinitionDaoImpl(processEngineConfiguration);
        processExecutionDao = new ProcessExecutionDaoImpl(processEngineConfiguration);
        taskDao = new TaskDaoImpl(processEngineConfiguration);
        jobDao = new JobDaoImpl(processEngineConfiguration);

        ENTITY_TO_DAO_MAPPING = ImmutableMap.builder()
                .put(PROCESS_DEFINITION_ENTITY, processDefinitionDao)
                .put(EXECUTION_ENTITY, processExecutionDao)
                .put(TASK_ENTITY, taskDao)
                .put(JOB_ENTITY, jobDao)
                .put(VARIABLE_ENTITY, variableDocumentDao)
                .put(RESOURCE_ENTITY, resourceDao)
                .build();
    }

    public AwsDataSourceDao getDao(Class<?> entityClass) {
        return (AwsDataSourceDao) ENTITY_TO_DAO_MAPPING.get(entityClass);
    }

}
