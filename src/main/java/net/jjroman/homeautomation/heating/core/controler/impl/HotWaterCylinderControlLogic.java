package net.jjroman.homeautomation.heating.core.controler.impl;

import net.jjroman.homeautomation.heating.core.controler.ControlAction;
import net.jjroman.homeautomation.heating.core.controler.ControlLogic;
import net.jjroman.homeautomation.heating.core.controler.EnvironmentSnapshot;
import net.jjroman.homeautomation.heating.core.modules.LogicalModule;

/**
 * Created by Jan on 08/02/2015.
 */
public class HotWaterCylinderControlLogic implements ControlLogic {
    @Override
    public ControlAction generateAction(LogicalModule logicalModule, EnvironmentSnapshot environmentSnapshot) {
        return new NopControlAction(logicalModule.getCurrentStatus());
    }
}
