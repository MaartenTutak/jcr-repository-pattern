package be.tutak.jcr.api;

/**
 * Created by maarten on 28.09.16.
 */
public interface BucketStrategy {
    String determinePath(String rootPath, String id);
}