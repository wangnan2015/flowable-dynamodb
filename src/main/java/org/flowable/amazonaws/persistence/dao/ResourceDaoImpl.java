package org.flowable.amazonaws.persistence.dao;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.flowable.amazonaws.cfg.AwsProcessEngineConfiguration;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.repository.EngineResource;
import org.flowable.common.engine.impl.persistence.entity.Entity;
import org.flowable.engine.impl.persistence.entity.ResourceEntity;
import org.flowable.amazonaws.persistence.dao.model.DynamoDBModel;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

@Slf4j
public class ResourceDaoImpl extends AbstractAwsDataSourceDao {

    private static final String FLOWABLE_RESOURCE_BUCKET = "flowable-resource-nwwan";

    public ResourceDaoImpl(AwsProcessEngineConfiguration processEngineConfiguration) {
        super(processEngineConfiguration);
    }

    @Override
    protected Class<? extends DynamoDBModel> getDynamoDBModel() {
        return null;
    }

    @Override
    protected Entity mapToEntity(DynamoDBModel pojo) {
        return null;
    }

    /**
     * Bulk save objects
     * @param insertedObjects
     */
    @Override
    public void bulkSave(Map<String, Entity> insertedObjects) {
        for (Entity insertedObject: insertedObjects.values()) {
            save(insertedObject);
        }
    }

    /**
     * Save one resource to S3
     * @param insertedObject
     */
    @Override
    public void save(Entity insertedObject) {
        ResourceEntity resourceEntity = (ResourceEntity) insertedObject;
        InputStream inputStream = new ByteArrayInputStream(resourceEntity.getBytes());
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(resourceEntity.getBytes().length);
        try {
            amazonS3.putObject(FLOWABLE_RESOURCE_BUCKET, resourceEntity.getId(), inputStream, metadata);
        } catch (Exception e) {
            throw new FlowableException("Failed to upload resource to S3, id=" + resourceEntity.getId(), e);
        }
    }

    /**
     * Load resources from S3
     * @param resources
     * @return resources
     */
    public Map<String, EngineResource> load(Map<String, EngineResource> resources) {
        for (EngineResource resource: resources.values()) {
            ResourceEntity resourceEntity = (ResourceEntity) resource;
            try {
                S3Object s3Object = amazonS3.getObject(FLOWABLE_RESOURCE_BUCKET, resourceEntity.getId());
                resourceEntity.setBytes(IOUtils.toByteArray(s3Object.getObjectContent()));
            } catch (Exception e) {
                throw new FlowableException("Failed to download resource from S3, id=" + resourceEntity.getId(), e);
            }
        }
        return resources;
    }

}
