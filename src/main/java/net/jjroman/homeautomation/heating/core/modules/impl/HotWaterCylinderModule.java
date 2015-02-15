package net.jjroman.homeautomation.heating.core.modules.impl;

import com.google.common.collect.ImmutableSet;
import net.jjroman.homeautomation.heating.core.modules.LogicalModule;
import net.jjroman.homeautomation.heating.core.modules.ModuleState;
import net.jjroman.homeautomation.heating.core.modules.UnknownModuleStateException;

/**
 * Created by Jan on 08/02/2015.
 */
public class HotWaterCylinderModule implements LogicalModule {
    public static final ModuleState UNSPECIFIED = new HotWaterCylinderModuleState();
    @Override
    public boolean changeStatus(ModuleState from, ModuleState to) throws UnknownModuleStateException {
        //TODO
        return false;
    }

    @Override
    public ModuleState getCurrentStatus() {
        //TODO
        return UNSPECIFIED;
    }

    @Override
    public ImmutableSet<ModuleState> getAvailableStates(){
        // TODO
        return null;
    }
}
