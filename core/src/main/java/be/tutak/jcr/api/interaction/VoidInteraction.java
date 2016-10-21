package be.tutak.jcr.api.interaction;

/**
 * Created by maarten on 28.09.16.
 */

@FunctionalInterface
public interface VoidInteraction<FACILITATOR_TYPE> {
    void interact(FACILITATOR_TYPE facilitator) throws Exception;
}