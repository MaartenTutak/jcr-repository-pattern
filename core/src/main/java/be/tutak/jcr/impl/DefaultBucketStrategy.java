package be.tutak.jcr.impl;

import be.tutak.jcr.api.strategy.BucketStrategy;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

/**
 * Created by maarten on 28.09.16.
 */

@Service(BucketStrategy.class)
@Component
public class DefaultBucketStrategy implements BucketStrategy {

    @Override
    public String determinePath(String rootPath, String id) {
        return rootPath + id;
    }
}