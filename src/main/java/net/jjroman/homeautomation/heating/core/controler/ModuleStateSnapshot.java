package net.jjroman.homeautomation.heating.core.controler;

import net.jjroman.homeautomation.heating.core.modules.LogicalModule;
import net.jjroman.homeautomation.heating.core.modules.ModuleState;

/**
 * Created by Jan on 16/02/2015.
 */
public interface ModuleStateSnapshot {
    ModuleState getModuleState(LogicalModule logicalModule);
}
