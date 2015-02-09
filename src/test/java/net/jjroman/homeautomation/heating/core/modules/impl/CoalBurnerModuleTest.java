package net.jjroman.homeautomation.heating.core.modules.impl;

import net.jjroman.homeautomation.heating.core.controler.impl.ChangeStateAction;
import net.jjroman.homeautomation.heating.core.modules.ModuleState;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Jan on 09/02/2015.
 */
public class CoalBurnerModuleTest {
    @Test
    public void moduleStatesPopulated(){
        Assert.assertNotNull("OFF should be populated", CoalBurnerModule.OFF);
        Assert.assertNotNull("ACTIVE_HEATING should be populated", CoalBurnerModule.ACTIVE_HEATING);
        Assert.assertNotNull("STANDBY should be populated", CoalBurnerModule.STANDBY);
    }

    @Test
    public void moduleIsByDefaultOff(){
        CoalBurnerModule coalBurnerModule = new CoalBurnerModule();
        Assert.assertSame("By default module state should be OFF", CoalBurnerModule.OFF, coalBurnerModule.getCurrentStatus());
    }
    @Test
    public void switchReturnsFalseForSameState(){
        CoalBurnerModule coalBurnerModule = new CoalBurnerModule();
        ChangeStateAction changeStateAction = new ChangeStateAction(CoalBurnerModule.OFF, CoalBurnerModule.OFF, coalBurnerModule);
        Assert.assertFalse(changeStateAction.execute());
        changeStateAction = new ChangeStateAction(CoalBurnerModule.ACTIVE_HEATING, CoalBurnerModule.ACTIVE_HEATING, coalBurnerModule);
        Assert.assertFalse(changeStateAction.execute());
        changeStateAction = new ChangeStateAction(CoalBurnerModule.STANDBY, CoalBurnerModule.STANDBY, coalBurnerModule);
        Assert.assertFalse(changeStateAction.execute());
    }
    @Test
    public void stateChangedCorrectly(){
        ChangeStateAction changeStateAction;
        CoalBurnerModule coalBurnerModule;
        ModuleState from,to;

        coalBurnerModule = new CoalBurnerModule();
        from = CoalBurnerModule.OFF;
        to = CoalBurnerModule.STANDBY;

        changeStateAction = new ChangeStateAction(from, to, coalBurnerModule);
        Assert.assertTrue(changeStateAction.execute());
        Assert.assertEquals(coalBurnerModule.getCurrentStatus(), to);

        coalBurnerModule = new CoalBurnerModule();
        from = CoalBurnerModule.OFF;
        to = CoalBurnerModule.ACTIVE_HEATING;

        changeStateAction = new ChangeStateAction(from, to, coalBurnerModule);
        Assert.assertTrue(changeStateAction.execute());
        Assert.assertEquals(coalBurnerModule.getCurrentStatus(), to);


    }

}
