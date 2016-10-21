package be.tutak.jcr.example.entity;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by maarten on 28.09.16.
 */

@Model(adaptables = Resource.class)
public class ProductEntity {

    private String sku;
    private String name;

    @Inject
    public ProductEntity(@Named("sku") String sku, @Optional @Named("name") String name) {
        this.sku = sku;
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductEntity that = (ProductEntity) o;

        return sku.equals(that.sku);

    }

    @Override
    public int hashCode() {
        return sku.hashCode();
    }
}