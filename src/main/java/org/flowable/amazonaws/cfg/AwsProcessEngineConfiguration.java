package org.flowable.amazonaws.cfg;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.s3.AmazonS3;
import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.dozer.DozerBeanMapper;
import org.flowable.common.engine.impl.persistence.StrongUuidGenerator;
import org.flowable.amazonaws.persistence.datamanager.*;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.eventsubscription.service.EventSubscriptionServiceConfiguration;
import org.flowable.identitylink.service.IdentityLinkServiceConfiguration;
import org.flowable.job.service.JobServiceConfiguration;
import org.flowable.amazonaws.persistence.dao.AwsDataSourceFactory;
import org.flowable.amazonaws.persistence.session.DynamoDBSessionFactory;
import org.flowable.amazonaws.persistence.transaction.DynamoDBTransactionContextFactory;
import org.flowable.task.service.TaskServiceConfiguration;
import org.flowable.variable.service.VariableServiceConfiguration;

import java.util.List;

@Getter
public class AwsProcessEngineConfiguration extends StandaloneProcessEngineConfiguration {

    private AmazonDynamoDB amazonDynamoDB;
    private AmazonS3 amazonS3;
    private DynamoDBMapper dynamoDBMapper;
    private DozerBeanMapper beanMapper;
    private AwsDataSourceFactory awsDataSourceFactory;

    @Builder
    public AwsProcessEngineConfiguration(@NonNull AmazonDynamoDB amazonDynamoDB, @NonNull AmazonS3 amazonS3) {

        this.amazonDynamoDB = amazonDynamoDB;
        this.amazonS3 = amazonS3;

        this.usingRelationalDatabase = false;
        this.usingSchemaMgmt = false;
        this.disableIdmEngine = true;
        this.validateFlowable5EntitiesEnabled = false;
        this.performanceSettings.setValidateExecutionRelationshipCountConfigOnBoot(false);
        this.performanceSettings.setValidateTaskRelationshipCountConfigOnBoot(false);
        this.history = "none";
        this.isDbHistoryUsed = false;
        this.idGenerator = new StrongUuidGenerator();

        initBeanMapper();
    }

    private void initBeanMapper() {
        List<String> mappingFiles = Lists.newArrayList();
        mappingFiles.add("dozer-mappings.xml");
        beanMapper = new DozerBeanMapper();
        beanMapper.setMappingFiles(mappingFiles);
    }

    @Override
    public void initNonRelationalDataSource() {
        this.dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
        this.awsDataSourceFactory = new AwsDataSourceFactory(this);
        super.initNonRelationalDataSource();
    }

    @Override
    public void initSessionFactories() {
        this.customSessionFactories = Lists.newArrayList();
        this.customSessionFactories.add(new DynamoDBSessionFactory(this));
        super.initSessionFactories();
    }

    @Override
    public void initTransactionContextFactory() {
        this.transactionContextFactory = new DynamoDBTransactionContextFactory();
    }

    @Override
    public void initDataManagers() {
        this.deploymentDataManager = new DynamoDBDeploymentDataManager(this);
        this.processDefinitionDataManager = new DynamoDBProcessDefinitionDataManager(this);
        this.resourceDataManager = new DynamoDBResourceDataManager(this);
        this.executionDataManager = new DynamoDBExecutionDataManager(this);
        this.activityInstanceDataManager = new DynamoDBActivityInstanceDataManager(this);
        this.processDefinitionInfoDataManager = new InMemoryProcessDefinitionInfoDataManager();
        this.eventLogEntryDataManager = new InMemoryEventLogEntryDataManager();
    }

    @Override
    protected TaskServiceConfiguration instantiateTaskServiceConfiguration() {
        return super.instantiateTaskServiceConfiguration()
                .setTaskDataManager(new DynamoDBTaskDataManager(this));
    }

    @Override
    protected IdentityLinkServiceConfiguration instantiateIdentityLinkServiceConfiguration() {
        return super.instantiateIdentityLinkServiceConfiguration()
                .setIdentityLinkDataManager(new InMemoryIdentityLinkDataManager());
    }

    @Override
    protected VariableServiceConfiguration instantiateVariableServiceConfiguration() {
        return super.instantiateVariableServiceConfiguration()
                .setVariableInstanceDataManager(new DynamoDBVariableInstanceDataManager(this));
    }

    @Override
    protected JobServiceConfiguration instantiateJobServiceConfiguration() {
        return super.instantiateJobServiceConfiguration()
                .setJobDataManager(new DynamoDBJobDataManager(this))
                .setTimerJobDataManager(new InMemoryTimerJobDataManager())
                .setHistoryJobDataManager(new InMemoryHistoryJobDataManager())
                .setSuspendedJobDataManager(new InMemorySuspendedJobDataManager())
                .setDeadLetterJobDataManager(new InMemoryDeadLetterJobDataManager());
    }

    @Override
    protected EventSubscriptionServiceConfiguration instantiateEventSubscriptionServiceConfiguration() {
        return super.instantiateEventSubscriptionServiceConfiguration()
                .setEventSubscriptionDataManager(new InMemoryEventSubscriptionDataManager());
    }

}
