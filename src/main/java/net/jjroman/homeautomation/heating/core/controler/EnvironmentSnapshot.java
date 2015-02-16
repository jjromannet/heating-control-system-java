package net.jjroman.homeautomation.heating.core.controler;

import net.jjroman.homeautomation.heating.core.modules.LogicalModule;
import net.jjroman.homeautomation.heating.core.modules.ModuleState;

/**
 * Enviroment Snapshot implementation have to be immutable.
 * Environment snapshot represent state to be processed in next cycle.
 * Program runs in cycles. It is crucial to ensure values are immutable
 * for time of one cycle.
 * Created by Jan on 08/02/2015.
 */
public interface EnvironmentSnapshot {
    // TODO: objectify
    Double getDoubleValue(String key);

    // TODO: create state object
    ModuleState getCurrentState(LogicalModule logicalModule);

    Boolean getBooleanValue(String key);

    ConfigurationSnapshot getConfigurationSnapshot();

    MeasuresSnapshot getMeasuresSnapshot();

    ModuleStateSnapshot getModuleStateSnapshot();
}
