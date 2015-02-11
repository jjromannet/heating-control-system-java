package net.jjroman.homeautomation.heating.core.modules.impl;

import net.jjroman.homeautomation.heating.core.modules.LogicalModule;
import net.jjroman.homeautomation.heating.core.modules.ModuleState;
import net.jjroman.homeautomation.heating.core.modules.UnknownModuleStateException;

/**
 * Created by Jan on 08/02/2015.
 */
public class CoalBurnerModule implements LogicalModule {

    public static final ModuleState ACTIVE_HEATING = new CoalBurnerModuleState();
    public static final ModuleState STANDBY = new CoalBurnerModuleState();
    public static final ModuleState OFF = new CoalBurnerModuleState();

    private ModuleState currentState = OFF;
    public CoalBurnerModule(){

    }

    @Override
    public synchronized boolean changeStatus(ModuleState from, ModuleState to) throws UnknownModuleStateException {
        if(from == null || !from.getClass().isAssignableFrom(CoalBurnerModuleState.class)
                || to == null || !to.getClass().isAssignableFrom(CoalBurnerModuleState.class) ){
            throw new UnknownModuleStateException("Unsupported state. CoalBurner can only handle CoalBurnerModuleState objects");
        }
        if(from.equals(to)){
            return false;
        }
        if(currentState.equals(from)){
            // TODO ... ALL CONTROL LOGIC
            currentState = to;
            return true;
        }else{
            return false;
        }
    }

    @Override
    public synchronized ModuleState getCurrentStatus() {
        return currentState;
    }
}
