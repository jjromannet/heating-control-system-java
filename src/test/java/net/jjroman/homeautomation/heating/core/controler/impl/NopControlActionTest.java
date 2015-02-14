package net.jjroman.homeautomation.heating.core.controler.impl;

import net.jjroman.homeautomation.heating.core.modules.LogicalModule;
import net.jjroman.homeautomation.heating.core.modules.impl.CoalBurnerModule;
import net.jjroman.homeautomation.heating.io.gpioexecutor.MockExecutor;
import org.junit.Assert;
import org.junit.Test;

/**
 * No Operation Control Action testing basic function which is :
 * Passed state is same as returned
 * Created by Jan on 11/02/2015.
 */
public class NopControlActionTest {

    @Test
    public void newActionHaveTargetStateSameAsPassedInContructor(){
        Assert.assertEquals((new NopControlAction(CoalBurnerModule.OFF)).getNewState(), CoalBurnerModule.OFF);
    }

    @Test
    public void ActionFailsIfCurrentStateMismatch(){
        LogicalModule logicalModule = new CoalBurnerModule(MockExecutor.INSTANCE);;

        Assert.assertFalse(new NopControlAction(CoalBurnerModule.STANDBY).execute(logicalModule));

    }

    @Test
    public void ActionSuceedIfCurrentStateMatches(){
        LogicalModule logicalModule = new CoalBurnerModule(MockExecutor.INSTANCE);

        Assert.assertTrue(new NopControlAction(CoalBurnerModule.OFF).execute(logicalModule));

    }

}
