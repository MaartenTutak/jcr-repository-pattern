package be.tutak.jcr.impl;

import be.tutak.jcr.api.interaction.ResultInteraction;
import be.tutak.jcr.api.interaction.VoidInteraction;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by maarten on 28.09.16.
 */

@Service(JcrInteraction.class)
@Component
public class JcrInteraction<T> {

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

    public Optional<T> forResult(ResultInteraction<ResourceResolver, T> interaction) {
        Optional<T> result;

        try(ResourceResolver resourceResolver = obtainResourceResolver()) {
            result = Optional.ofNullable(interaction.interact(resourceResolver));
        } catch (Exception e) {
            LOG.error("Error during JCR interaction", e);
            result = Optional.empty();
        }

        return result;
    }

    public List<T> forList(ResultInteraction<ResourceResolver, List<T>> interaction) {
        List<T> resultList;

        try (ResourceResolver resourceResolver = obtainResourceResolver()){
            resultList = interaction.interact(resourceResolver);
        } catch (Exception e) {
            LOG.error("Error during JCR interaction", e);
            resultList = Collections.emptyList();
        }

        return resultList;
    }

    public void forVoid(VoidInteraction<ResourceResolver> interaction) {
        try(ResourceResolver resourceResolver = obtainResourceResolver()) {
            interaction.interact(resourceResolver);
            if(resourceResolver.hasChanges()) resourceResolver.commit();
        } catch (Exception e) {
            LOG.error("Error during JCR interaction", e);
        }
    }

    private ResourceResolver obtainResourceResolver() throws LoginException {
        return resourceResolverFactory.getServiceResourceResolver(null);
    }
}