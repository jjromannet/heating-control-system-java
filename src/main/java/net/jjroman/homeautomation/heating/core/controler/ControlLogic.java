package net.jjroman.homeautomation.heating.core.controler;

import net.jjroman.homeautomation.heating.core.modules.LogicalModule;

/**
 * Chain of responsibility implementation
 *
 * Created by Jan on 08/02/2015.
 */
public interface ControlLogic {
    ControlAction generateAction(LogicalModule logicalModule, EnvironmentSnapshot environmentSnapshot);
}
