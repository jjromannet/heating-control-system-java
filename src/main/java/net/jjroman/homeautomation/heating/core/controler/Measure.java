package net.jjroman.homeautomation.heating.core.controler;

/**
 * Measure represents one element of external physical environments
 * that is being monitored and measured. It cana have one ore more physical
 * indicator but for the consumer of the object it needs to be transparent and
 * it should present only one value that is consider to be valid for the
 * lifetime of Measures snapshot which is one cycle of main loop.
 *
 * Created by Jan on 16/02/2015.
 */
public interface Measure {
}
