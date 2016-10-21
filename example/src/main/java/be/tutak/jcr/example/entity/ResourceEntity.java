package be.tutak.jcr.example.entity;

import org.apache.sling.api.resource.AbstractResource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;

import java.util.Map;

/**
 * Created by maarten on 19.10.16.
 */
public class ResourceEntity extends AbstractResource {

    private String path;
    private Map<String, Object> properties;

    public ResourceEntity(String path, Map<String, Object> properties) {
        this.path = path;
        this.properties = properties;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public ValueMap getValueMap() {
        return new ValueMapDecorator(properties);
    }

    @Override
    public String getResourceType() {
        return null;
    }

    @Override
    public String getResourceSuperType() {
        return null;
    }

    @Override
    public ResourceMetadata getResourceMetadata() {
        return null;
    }

    @Override
    public ResourceResolver getResourceResolver() {
        return null;
    }
}