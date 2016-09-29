package be.tutak.jcr.specs

import be.tutak.jcr.api.CrudRepository
import be.tutak.jcr.example.entity.ProductEntity
import be.tutak.jcr.example.repository.ProductRepository
import be.tutak.jcr.impl.DefaultBucketStrategy
import be.tutak.jcr.impl.JcrCrudRepository
import be.tutak.jcr.impl.JcrInteraction
import com.icfolson.aem.prosper.specs.ProsperSpec
import org.apache.sling.api.resource.Resource
import org.apache.sling.api.resource.ResourceResolverFactory

/**
 * Created by maarten on 29.09.16.
 */
class ProductRepositorySpec extends ProsperSpec {

    def setupSpec() {
        nodeBuilder.etc {
            commerce {
                product {
                    "123456"("nt:unstructured", "sku": 11111)
                }
            }
        }
    }

    def "retrieve product 123456 via the ProductRepository" () {
        setup:
        def ResourceResolverFactory resourceResolverFactory = Mock(ResourceResolverFactory)
        def JcrInteraction interaction = new JcrInteraction(resourceResolverFactory);
        def CrudRepository<Resource> jcrRepo = new JcrCrudRepository(interaction);
        def CrudRepository<ProductEntity> productRepo = new ProductRepository(jcrRepo, new DefaultBucketStrategy());

        1 * resourceResolverFactory.getServiceResourceResolver(null) >> resourceResolver;

        when:
        Optional<ProductEntity> entity = productRepo.read("123456");

        then:
        entity.isPresent()
        entity.get().sku == "11111";
    }
}