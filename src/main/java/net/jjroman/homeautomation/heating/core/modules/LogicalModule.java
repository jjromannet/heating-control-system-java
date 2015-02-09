package net.jjroman.homeautomation.heating.core.modules;

/**
 * Created by Jan on 08/02/2015.
 */
public interface LogicalModule {
    boolean changeStatus(ModuleState from, ModuleState to) throws UnknownModuleStateException;
    ModuleState getCurrentStatus();
}
