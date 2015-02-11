package net.jjroman.homeautomation.heating.core.controler.impl;

import net.jjroman.homeautomation.heating.core.controler.ControlAction;
import net.jjroman.homeautomation.heating.core.modules.LogicalModule;
import net.jjroman.homeautomation.heating.core.modules.ModuleState;

/**
 * Created by Jan on 08/02/2015.
 */
public class NopControlAction implements ControlAction {
    private final ModuleState currentState;
    public NopControlAction(ModuleState currentState){
        this.currentState = currentState;
    }
    @Override
    public boolean execute(LogicalModule targetModule) {
        if(targetModule == null) return false;
        if(targetModule.getCurrentStatus().equals(currentState)){
            return true;
        }
        return false;
    }

    @Override
    public ModuleState getNewState() {
        return currentState;
    }
}
