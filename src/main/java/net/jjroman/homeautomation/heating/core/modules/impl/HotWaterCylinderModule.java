package net.jjroman.homeautomation.heating.core.modules.impl;

import net.jjroman.homeautomation.heating.core.modules.LogicalModule;
import net.jjroman.homeautomation.heating.core.modules.ModuleState;
import net.jjroman.homeautomation.heating.core.modules.UnknownModuleStateException;

/**
 * Created by Jan on 08/02/2015.
 */
public class HotWaterCylinderModule implements LogicalModule {

    @Override
    public boolean changeStatus(ModuleState from, ModuleState to) throws UnknownModuleStateException {
        //TODO
        return false;
    }

    @Override
    public ModuleState getCurrentStatus() {
        //TODO
        return null;
    }
}
