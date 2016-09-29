package be.tutak.jcr.example.repository;

import be.tutak.jcr.api.BucketStrategy;
import be.tutak.jcr.api.CrudRepository;
import be.tutak.jcr.example.entity.ProductEntity;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;

import java.util.List;
import java.util.Optional;

/**
 * Created by maarten on 28.09.16.
 */

@Service(CrudRepository.class)
@Component
public class ProductRepository implements CrudRepository<ProductEntity> {

    private static final String PRODUCT_ROOT_PATH = "/etc/commerce/product/";

    @Reference(target = "component.name=be.tutak.jcr.impl.JcrCrudRepository")
    private CrudRepository<Resource> repository;

    @Reference(target = "component.name=be.tutak.jcr.impl.DefaultBucketStrategy")
    private BucketStrategy bucketStrategy;

    @Override
    public void create(ProductEntity item) {

    }

    @Override
    public Optional<ProductEntity> read(String id) {
        Optional<ProductEntity> productEntity;
        String itemPath = bucketStrategy.determinePath(PRODUCT_ROOT_PATH, id);
        Optional<Resource> productResource = repository.read(itemPath);

        //TODO Sling model validation

        if(productResource.isPresent()) {
            productEntity = Optional.of(productResource.get().adaptTo(ProductEntity.class));
        } else {
            productEntity = Optional.empty();
        }

        return productEntity;
    }

    @Override
    public List<ProductEntity> read() {
        return null;
    }

    @Override
    public void update(ProductEntity item) {

    }

    @Override
    public void delete(String id) {

    }
}