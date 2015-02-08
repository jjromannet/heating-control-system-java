package net.jjroman.homeautomation.heating.core.controler.impl;

import net.jjroman.homeautomation.heating.core.controler.ControlAction;

/**
 * Created by Jan on 08/02/2015.
 */
public class NopControlAction implements ControlAction {
    @Override
    public boolean execute() {
        return true;
    }
}
