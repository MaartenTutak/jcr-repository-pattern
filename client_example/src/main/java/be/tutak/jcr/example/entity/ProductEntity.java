package be.tutak.jcr.example.entity;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;

/**
 * Created by maarten on 28.09.16.
 */

@Model(adaptables = Resource.class)
public class ProductEntity {

    @Inject
    private String sku;

    public ProductEntity(String sku) {
        this.sku = sku;
    }

    public String getSku() {
        return sku;
    }
}