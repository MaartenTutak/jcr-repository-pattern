package be.tutak.jcr.example.builder;

import be.tutak.jcr.example.entity.ResourceEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by maarten on 19.10.16.
 */
public class ResourceEntityBuilder {

    private String path;
    private Map<String, Object> properties = new HashMap<>();

    public ResourceEntityBuilder withPath(String path) {
        this.path = path;
        return this;
    }

    public ResourceEntityBuilder withProperty(String key, Object value) {
        this.properties.put(key, value);
        return this;
    }

    public ResourceEntity build() {
        return new ResourceEntity(path, properties);
    }
}