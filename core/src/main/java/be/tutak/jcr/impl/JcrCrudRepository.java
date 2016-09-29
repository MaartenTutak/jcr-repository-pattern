package be.tutak.jcr.impl;

import be.tutak.jcr.api.CrudRepository;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private JcrInteraction interaction;

    /**
     * Default constructor - required by ???
     */
    public JcrCrudRepository() {
    }

    public JcrCrudRepository(JcrInteraction interaction) {
        this.interaction = interaction;
    }

    @Override
    public void create(Resource item) {
        //TODO
    }

    @Override
    public Optional<Resource> read(String id) {
        return interaction.interact(facilitator -> {
            return facilitator.getResource(id);
        });
    }

    @Override
    public List<Resource> read() {
        return Collections.emptyList();
    }

    @Override
    public void update(Resource item) {
        //TODO
    }

    @Override
    public void delete(String id) {
        interaction.interact(facilitator -> {
            Resource resource = facilitator.getResource(id);
            if(resource != null) {
                facilitator.delete(resource);
                facilitator.commit();
            } else {
                LOG.error("Resource '{}' does not exist.", id);
            }
        });
    }
}