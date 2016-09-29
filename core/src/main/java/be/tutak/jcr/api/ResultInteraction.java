package be.tutak.jcr.api;

/**
 * Created by maarten on 28.09.16.
 */

@FunctionalInterface
public interface ResultInteraction<FACILITATOR_TYPE, RETURN_TYPE> {
    RETURN_TYPE interact(FACILITATOR_TYPE facilitator) throws Exception;
}