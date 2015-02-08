package net.jjroman.homeautomation.heating.core.controler;

import net.jjroman.homeautomation.heating.core.modules.LogicalModule;

/**
 * Created by Jan on 08/02/2015.
 */
public interface EnvironmentSnapshot {
    // TODO: objectify
    Double getValue(String key);

    // TODO: create state object
    Double getCurrentState(LogicalModule logicalModule);
}
