package org.flowable.amazonaws.persistence.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionWriteRequest;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.flowable.amazonaws.cfg.AwsProcessEngineConfiguration;
import org.flowable.common.engine.impl.persistence.entity.Entity;
import org.flowable.amazonaws.persistence.dao.model.DynamoDBModel;
import org.flowable.amazonaws.persistence.dao.model.Variable;
import org.flowable.amazonaws.persistence.dao.model.VariableDocument;
import org.flowable.variable.service.impl.persistence.entity.VariableInstanceEntity;
import org.flowable.variable.service.impl.persistence.entity.VariableInstanceEntityImpl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class VariableDocumentDaoImpl extends AbstractAwsDataSourceDao {

    public VariableDocumentDaoImpl(AwsProcessEngineConfiguration processEngineConfiguration) {
        super(processEngineConfiguration);
    }

    @Override
    protected Class<? extends DynamoDBModel> getDynamoDBModel() {
        return VariableDocument.class;
    }

    @Override
    protected Entity mapToEntity(DynamoDBModel pojo) { return null; }

    /**
     * Transactional insert objects
     * @param insertedObjects
     * @param transactionWriteRequest
     */
    @Override
    public void trxInsert(Map<String, Entity> insertedObjects, TransactionWriteRequest transactionWriteRequest) {
        // Get the first variable
        VariableInstanceEntity var = (VariableInstanceEntity) insertedObjects.values().iterator().next();

        // Create or update the variable document
        VariableDocument doc = dynamoDBMapper.load(VariableDocument.class, var.getProcessInstanceId());
        if (doc == null) {
            List<Variable> variables = insertedObjects.values().stream()
                    .map(v -> beanMapper.map(v, Variable.class))
                    .collect(Collectors.toList());
            doc = VariableDocument.builder()
                    .id(var.getProcessInstanceId())
                    .executionId(var.getExecutionId())
                    .variables(variables)
                    .build();
            transactionWriteRequest.addPut(doc);

        } else {
            Map<String, Variable> variables = doc.getVariables().stream()
                    .collect(Collectors.toMap(v -> v.getName(), p -> p));
            Map<String, Variable> newVariables = insertedObjects.values().stream()
                    .map(v -> beanMapper.map(v, Variable.class))
                    .collect(Collectors.toMap(v -> v.getName(), p -> p));
            // Merge the variables
            variables.putAll(newVariables);
            doc.setVariables(variables.values().stream()
                    .collect(Collectors.toList()));
            transactionWriteRequest.addUpdate(doc);
        }
    }

    /**
     * Query by expression
     * @param queryExpression
     * @return variables
     */
    @SuppressWarnings("unchecked")
    @Override
    public List queryList(DynamoDBQueryExpression queryExpression) {
        VariableDocument doc = (VariableDocument) dynamoDBMapper.query(VariableDocument.class, queryExpression)
                .stream()
                .findFirst()
                .orElse(null);
        if (doc != null) {
            List<VariableInstanceEntity> variables = doc.getVariables().stream()
                    .map(v -> beanMapper.map(v, VariableInstanceEntityImpl.class))
                    .collect(Collectors.toList());
            // Fill processInstanceId and executionId
            for (VariableInstanceEntity var: variables) {
                var.setProcessInstanceId(doc.getId());
                var.setExecutionId(doc.getExecutionId());
            }
            return variables;
        }
        return Lists.newArrayList();
    }

    /**
     * Transactional delete objects
     * @param deletedObjects
     * @param transactionWriteRequest
     */
    @Override
    public void trxDelete(Map<String, Entity> deletedObjects, TransactionWriteRequest transactionWriteRequest) {
        VariableInstanceEntity var = (VariableInstanceEntity) deletedObjects.values().iterator().next();
        VariableDocument req = VariableDocument.builder()
                .id(var.getProcessInstanceId())
                .build();
        transactionWriteRequest.addDelete(req);
    }

}
