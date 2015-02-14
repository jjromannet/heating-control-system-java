package net.jjroman.homeautomation.heating.core.modules.impl;

import net.jjroman.homeautomation.heating.core.modules.ModuleState;

/**
 * Possible states for coal burner.
 * Created by Jan on 09/02/2015.
 */
public enum CoalBurnerModuleState implements ModuleState {
    ACTIVE_HEATING,
    STANDBY,
    OFF
}
