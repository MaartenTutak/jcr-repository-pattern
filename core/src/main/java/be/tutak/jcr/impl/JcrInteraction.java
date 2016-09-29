package be.tutak.jcr.impl;

import be.tutak.jcr.api.ResultInteraction;
import be.tutak.jcr.api.VoidInteraction;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Created by maarten on 28.09.16.
 */

@Service(JcrInteraction.class)
@Component
public class JcrInteraction {

    private static final Logger LOG = LoggerFactory.getLogger(JcrInteraction.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    /**
     * Default constructor - required by ???
     */
    public JcrInteraction() {
    }

    public JcrInteraction(ResourceResolverFactory resourceResolverFactory) {
        this.resourceResolverFactory = resourceResolverFactory;
    }

    public Optional<Resource> interact(ResultInteraction<ResourceResolver, Resource> interaction) {
        Optional<Resource> resource;

        try(ResourceResolver resourceResolver = obtainResourceResolver()) {
            resource = Optional.of(interaction.interact(resourceResolver));
        } catch (Exception e) {
            LOG.error("Error during JCR interaction", e);
            resource = Optional.empty();
        }

        return resource;
    }

    public void interact(VoidInteraction<ResourceResolver> interaction) {
        try(ResourceResolver resourceResolver = obtainResourceResolver()) {
            interaction.interact(resourceResolver);
        } catch (Exception e) {
            LOG.error("Error during JCR interaction", e);
        }
    }

    private ResourceResolver obtainResourceResolver() throws LoginException {
        return resourceResolverFactory.getServiceResourceResolver(null);
    }
}