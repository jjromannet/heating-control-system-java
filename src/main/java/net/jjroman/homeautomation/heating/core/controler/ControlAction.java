package net.jjroman.homeautomation.heating.core.controler;

import net.jjroman.homeautomation.heating.core.modules.LogicalModule;
import net.jjroman.homeautomation.heating.core.modules.ModuleState;

/**
 * Created by Jan on 08/02/2015.
 */
public interface ControlAction {
    boolean execute(LogicalModule targetModule);
    ModuleState getNewState();
}
