package net.jjroman.homeautomation.heating.core.controler.impl;

import net.jjroman.homeautomation.heating.core.controler.EnvironmentSnapshot;
import net.jjroman.homeautomation.heating.core.modules.LogicalModule;
import org.omg.CORBA.Environment;

/**
 * Created by Jan on 08/02/2015.
 */
public class OneMinuteEnvironmentSnapshot implements EnvironmentSnapshot {

    @Override
    public Double getValue(String key) {
        // TODO
        return null;
    }

    @Override
    public Double getCurrentState(LogicalModule logicalModule) {
        // TODO
        return null;
    }
}
