package net.jjroman.homeautomation.heating.core.controler;

import net.jjroman.homeautomation.heating.core.modules.LogicalModule;
import net.jjroman.homeautomation.heating.core.modules.ModuleState;

/**
 * Created by Jan on 08/02/2015.
 */
public interface EnvironmentSnapshot {
    // TODO: objectify
    Double getDoubleValue(String key);

    // TODO: create state object
    ModuleState getCurrentState(LogicalModule logicalModule);

    Boolean getBooleanValue(String key);
}
