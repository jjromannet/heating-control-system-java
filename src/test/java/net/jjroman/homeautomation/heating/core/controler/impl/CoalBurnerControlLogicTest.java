package net.jjroman.homeautomation.heating.core.controler.impl;

import net.jjroman.homeautomation.heating.core.controler.ControlAction;
import net.jjroman.homeautomation.heating.core.controler.EnvironmentSnapshot;
import net.jjroman.homeautomation.heating.core.modules.LogicalModule;
import net.jjroman.homeautomation.heating.core.modules.ModuleState;
import net.jjroman.homeautomation.heating.core.modules.impl.CoalBurnerModule;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jan on 10/02/2015.
 */
public class CoalBurnerControlLogicTest {




    @Test
    public void turnModuleOnWhenEnabled(){
        LogicalModule module = new CoalBurnerModule();

        Map<LogicalModule, ModuleState> stateMap = new HashMap<>();
        stateMap.put(module, CoalBurnerModule.STANDBY);

        EnvironmentSnapshot environmentSnapshot = new OneMinuteEnvironmentSnapshot(null, stateMap, null);

        CoalBurnerControlLogic coalBurnerControlLogic = new CoalBurnerControlLogic();
        ControlAction controlAction =  coalBurnerControlLogic.generateAction(module, environmentSnapshot);
        Assert.assertNull(controlAction);
        Assert.assertFalse(controlAction.getClass().isAssignableFrom(ChangeStateAction.class));

    }


}
