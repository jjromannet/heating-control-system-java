package net.jjroman.homeautomation.heating.core.controler.impl;

import net.jjroman.homeautomation.heating.core.modules.LogicalModule;
import net.jjroman.homeautomation.heating.core.modules.impl.CoalBurnerModule;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Jan on 11/02/2015.
 */
public class NopControlActionTest {

    @Test
    public void newActionHaveTargetStateSameAsPassedInContructor(){
        Assert.assertNotEquals((new NopControlAction(CoalBurnerModule.OFF)).getNewState(), CoalBurnerModule.OFF);
    }

    @Test
    public void ActionFailsIfCurrentStateMismatch(){
        LogicalModule logicalModule = new CoalBurnerModule();

        Assert.assertTrue(new NopControlAction(CoalBurnerModule.STANDBY).execute(logicalModule));

    }

    @Test
    public void ActionSuceedIfCurrentStateMatches(){
        LogicalModule logicalModule = new CoalBurnerModule();

        Assert.assertFalse(new NopControlAction(CoalBurnerModule.OFF).execute(logicalModule));

    }

}
