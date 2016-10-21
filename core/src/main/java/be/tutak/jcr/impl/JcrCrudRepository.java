package be.tutak.jcr.impl;

import be.tutak.jcr.api.repository.CrudRepository;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.nodetype.NodeType;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by maarten on 28.09.16.
 */

@Service(CrudRepository.class)
@Component
public class JcrCrudRepository implements CrudRepository<Resource> {

    private static final Logger LOG = LoggerFactory.getLogger(JcrCrudRepository.class);

    @Reference
    private JcrInteraction<Resource> interaction;

    /**
     * Default constructor - required by ???
     */
    public JcrCrudRepository() {
    }

    public JcrCrudRepository(JcrInteraction<Resource> interaction) {
        this.interaction = interaction;
    }

    @Override
    public void create(Resource item) {
        interaction.forVoid(resourceResolver -> {
            String parentPath = ResourceUtil.getParent(item.getPath());
            Resource parentResource = ResourceUtil.getOrCreateResource(resourceResolver, parentPath, NodeType.NT_UNSTRUCTURED, NodeType.NT_UNSTRUCTURED, false);
            resourceResolver.create(parentResource, item.getName(), item.getValueMap());
        });
    }

    @Override
    public Optional<Resource> read(String id) {
        return interaction.forResult(resourceResolver -> resourceResolver.getResource(id));
    }

    @Override
    public List<Resource> read() {
        return Collections.emptyList();
    }

    @Override
    public void update(Resource item) {
        throw new RuntimeException("Method not implemented!");
    }

    @Override
    public void delete(String id) {
        interaction.forVoid(resourceResolver -> {
            Resource resource = resourceResolver.getResource(id);
            if(resource != null) {
                resourceResolver.delete(resource);
            } else {
                LOG.error("Resource '{}' does not exist.", id);
            }
        });
    }
}