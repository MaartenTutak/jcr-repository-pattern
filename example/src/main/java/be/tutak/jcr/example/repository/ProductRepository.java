package be.tutak.jcr.example.repository;

import be.tutak.jcr.api.repository.CrudRepository;
import be.tutak.jcr.api.strategy.BucketStrategy;
import be.tutak.jcr.example.builder.ResourceEntityBuilder;
import be.tutak.jcr.example.entity.ProductEntity;
import be.tutak.jcr.example.entity.ResourceEntity;
import be.tutak.jcr.impl.JcrInteraction;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by maarten on 28.09.16.
 */

@Service(CrudRepository.class)
@Component
public class ProductRepository implements CrudRepository<ProductEntity> {

    private static final String PRODUCT_ROOT_PATH = "/etc/commerce/product/";

    @Reference
    private JcrInteraction<ProductEntity> interaction;

    @Reference(target = "component.name=be.tutak.jcr.impl.JcrCrudRepository")
    private CrudRepository<Resource> repository;

    @Reference(target = "component.name=be.tutak.jcr.impl.DefaultBucketStrategy")
    private BucketStrategy bucketStrategy;

    /**
     * Default constructor - required by ???
     */
    public ProductRepository() {
    }

    public ProductRepository(JcrInteraction<ProductEntity> interaction, CrudRepository<Resource> repository, BucketStrategy bucketStrategy) {
        this.interaction = interaction;
        this.repository = repository;
        this.bucketStrategy = bucketStrategy;
    }

    @Override
    public void create(ProductEntity product) {
        String productPath = bucketStrategy.determinePath(PRODUCT_ROOT_PATH, product.getSku());
        ResourceEntity entity = new ResourceEntityBuilder()
                .withPath(productPath)
                .withProperty("sku", product.getSku())
                .withProperty("name", product.getName())
                .build();
        repository.create(entity);
    }

    @Override
    public Optional<ProductEntity> read(String id) {
        String productPath = bucketStrategy.determinePath(PRODUCT_ROOT_PATH, id);
        return interaction.forResult(resourceResolver -> {
            Resource productResource = resourceResolver.getResource(productPath);
            return productResource.adaptTo(ProductEntity.class);
        });
    }

    @Override
    public List<ProductEntity> read() {
        return interaction.forList(resourceResolver -> {
            List<ProductEntity> entities = new ArrayList<>();
            Resource productRoot = resourceResolver.getResource(PRODUCT_ROOT_PATH);
            addProducts(productRoot, entities);
            return entities;
        });
    }

    @Override
    public void update(ProductEntity product) {
        String productPath = bucketStrategy.determinePath(PRODUCT_ROOT_PATH, product.getSku());
        interaction.forVoid(resourceResolver -> {
            Resource productResource = resourceResolver.getResource(productPath);
            ModifiableValueMap valueMap = productResource.adaptTo(ModifiableValueMap.class);
            //assume that sku cannot be updated (unique identifier for a product)
            valueMap.put("name", product.getName());
        });
    }

    @Override
    public void delete(String id) {
        String productPath = bucketStrategy.determinePath(PRODUCT_ROOT_PATH, id);
        repository.delete(productPath);
    }

    private void addProducts(Resource currentResource, List<ProductEntity> entities) {
        //assume that a leaf node is a product node
        if(currentResource.hasChildren()) {
            for(Resource childResource : currentResource.getChildren()) {
                addProducts(childResource, entities);
            }
        } else {
            entities.add(currentResource.adaptTo(ProductEntity.class));
        }
    }
}