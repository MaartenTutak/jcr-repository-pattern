package be.tutak.jcr.specs

import com.icfolson.aem.prosper.specs.ProsperSpec

/**
 * Created by maarten on 29.09.16.
 */
class ProductRepositorySpec extends ProsperSpec {

    def setupSpec() {
        nodeBuilder.etc {
            commerce {
                product {
                    "11111"("nt:unstructured", "sku": 11111)
                }
            }
        }
    }

    def "test"() {
        expect :
        session.nodeExists("/etc/commerce/product/11111") &&
        session.getNode("/etc/commerce/product/11111").hasProperty("sku")
    }
}