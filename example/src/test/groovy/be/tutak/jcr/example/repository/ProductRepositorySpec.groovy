package be.tutak.jcr.example.repository

import be.tutak.jcr.api.repository.CrudRepository
import be.tutak.jcr.example.entity.ProductEntity
import be.tutak.jcr.impl.DefaultBucketStrategy
import be.tutak.jcr.impl.JcrCrudRepository
import be.tutak.jcr.impl.JcrInteraction
import com.icfolson.aem.prosper.specs.ProsperSpec
import org.apache.sling.api.resource.Resource
import org.apache.sling.api.resource.ResourceResolverFactory
import spock.lang.Shared

import static org.apache.jackrabbit.JcrConstants.NT_UNSTRUCTURED

/**
 * Created by maarten on 29.09.16.
 */
class ProductRepositorySpec extends ProsperSpec {

    @Shared
    CrudRepository<ProductEntity> productRepository;

    def setupSpec() {
        slingContext.addModelsForPackage("be.tutak.jcr.example.entity")

        def ResourceResolverFactory resourceResolverFactory = slingContext.getService(ResourceResolverFactory);
        def JcrInteraction interaction = new JcrInteraction(resourceResolverFactory);
        def CrudRepository<Resource> jcrRepo = new JcrCrudRepository(interaction);
        productRepository = new ProductRepository(interaction, jcrRepo, new DefaultBucketStrategy())
    }

    def "retrieve product 111"() {
        setup:
        nodeBuilder.etc { commerce { product { "111"(NT_UNSTRUCTURED, "sku": 111) } } }

        when:
        Optional<ProductEntity> entity = productRepository.read("111")

        then:
        entity.isPresent()
        entity.get().sku == "111"
    }

    def "create product 222"() {
        when:
        productRepository.create(new ProductEntity("222", "OSGi in Action"))

        then:
        assertNodeExists("/etc/commerce/product/222", [sku: "222", name: "OSGi in Action"] as Map)
    }

    def "delete product 333"() {
        setup:
        nodeBuilder.etc { commerce { product { "333"(NT_UNSTRUCTURED, "sku": 333) } } }

        when:
        productRepository.delete("333")

        then:
        !session.nodeExists("/etc/commerce/product/555")
    }

    def "retrieve all products"() {
        setup:
        nodeBuilder.etc {
            commerce {
                product {
                    "444"(NT_UNSTRUCTURED, "sku": 444)
                    "555"(NT_UNSTRUCTURED, "sku": 555)
                }
            }
        }

        when:
        List<ProductEntity> entities = productRepository.read();

        then:
        entities.containsAll([new ProductEntity("444", ""), new ProductEntity("555", "")] as List)
    }

    def "update product 666"() {
        setup:
        nodeBuilder.etc { commerce { product { "666"(NT_UNSTRUCTURED, "sku": 666, "name": "Clean Code") } } }

        when:
        productRepository.update(new ProductEntity("666", "GOOS"))

        then:
        assertNodeExists("/etc/commerce/product/666", [name: "GOOS"] as Map)
    }
}