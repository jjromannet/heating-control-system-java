package net.jjroman.homeautomation.heating.core.modules.impl;

import net.jjroman.homeautomation.heating.core.controler.EnvironmentSnapshot;
import net.jjroman.homeautomation.heating.core.controler.impl.ChangeStateAction;
import net.jjroman.homeautomation.heating.core.controler.impl.OneMinuteEnvironmentSnapshot;
import net.jjroman.homeautomation.heating.core.modules.LogicalModule;
import net.jjroman.homeautomation.heating.core.modules.ModuleState;
import net.jjroman.homeautomation.heating.io.gpioexecutor.MockExecutor;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

/**
 * Testing basic state changes of coal burner
 * Created by Jan on 09/02/2015.
 */
public class CoalBurnerModuleTest {

    private EnvironmentSnapshot environmentSnapshot = new OneMinuteEnvironmentSnapshot(new HashMap<String, Double>(), new HashMap<LogicalModule, ModuleState>(), new HashMap<String, Boolean>());

    @Test
    public void moduleStatesPopulated(){
        Assert.assertNotNull("OFF should be populated", CoalBurnerModule.OFF);
        Assert.assertEquals(CoalBurnerModule.OFF, CoalBurnerModuleState.OFF);
        Assert.assertNotNull("ACTIVE_HEATING should be populated", CoalBurnerModule.ACTIVE_HEATING);
        Assert.assertEquals(CoalBurnerModule.ACTIVE_HEATING, CoalBurnerModuleState.ACTIVE_HEATING);
        Assert.assertNotNull("STANDBY should be populated", CoalBurnerModule.STANDBY);
        Assert.assertEquals(CoalBurnerModule.STANDBY, CoalBurnerModuleState.STANDBY);
    }

    @Test
    public void moduleIsByDefaultOff(){
        CoalBurnerModule coalBurnerModule = new CoalBurnerModule(MockExecutor.INSTANCE, environmentSnapshot);
        Assert.assertSame("By default module state should be OFF", CoalBurnerModule.OFF, coalBurnerModule.getCurrentStatus());
    }
    @Test
    public void switchReturnsFalseForSameState(){
        CoalBurnerModule coalBurnerModule = new CoalBurnerModule(MockExecutor.INSTANCE, environmentSnapshot);
        ChangeStateAction changeStateAction = new ChangeStateAction(CoalBurnerModule.OFF, CoalBurnerModule.OFF);
        Assert.assertFalse(changeStateAction.execute(coalBurnerModule));
        changeStateAction = new ChangeStateAction(CoalBurnerModule.ACTIVE_HEATING, CoalBurnerModule.ACTIVE_HEATING);
        Assert.assertFalse(changeStateAction.execute(coalBurnerModule));
        changeStateAction = new ChangeStateAction(CoalBurnerModule.STANDBY, CoalBurnerModule.STANDBY);
        Assert.assertFalse(changeStateAction.execute(coalBurnerModule));
    }
    @Test
    public void stateChangedCorrectly(){
        ChangeStateAction changeStateAction;
        CoalBurnerModule coalBurnerModule;
        ModuleState from,to;

        coalBurnerModule = new CoalBurnerModule(MockExecutor.INSTANCE, environmentSnapshot);
        from = CoalBurnerModule.OFF;
        to = CoalBurnerModule.STANDBY;

        changeStateAction = new ChangeStateAction(from, to);
        Assert.assertTrue(changeStateAction.execute(coalBurnerModule));
        Assert.assertEquals(coalBurnerModule.getCurrentStatus(), to);

        from = CoalBurnerModule.STANDBY;
        to = CoalBurnerModule.ACTIVE_HEATING;
        changeStateAction = new ChangeStateAction(from, to);
        Assert.assertTrue(changeStateAction.execute(coalBurnerModule));
        Assert.assertEquals(coalBurnerModule.getCurrentStatus(), to);

        from = CoalBurnerModule.ACTIVE_HEATING;
        to = CoalBurnerModule.OFF;
        changeStateAction = new ChangeStateAction(from, to);
        Assert.assertTrue(changeStateAction.execute(coalBurnerModule));
        Assert.assertEquals(coalBurnerModule.getCurrentStatus(), to);

        //coalBurnerModule = new CoalBurnerModule(MockExecutor.INSTANCE);
        from = CoalBurnerModule.OFF;
        to = CoalBurnerModule.ACTIVE_HEATING;

        changeStateAction = new ChangeStateAction(from, to);
        Assert.assertTrue(changeStateAction.execute(coalBurnerModule));
        Assert.assertEquals(coalBurnerModule.getCurrentStatus(), to);

        from = CoalBurnerModule.ACTIVE_HEATING;
        to = CoalBurnerModule.STANDBY;

        changeStateAction = new ChangeStateAction(from, to);
        Assert.assertTrue(changeStateAction.execute(coalBurnerModule));
        Assert.assertEquals(coalBurnerModule.getCurrentStatus(), to);

        from = CoalBurnerModule.STANDBY;
        to = CoalBurnerModule.OFF;

        changeStateAction = new ChangeStateAction(from, to);
        Assert.assertTrue(changeStateAction.execute(coalBurnerModule));
        Assert.assertEquals(coalBurnerModule.getCurrentStatus(), to);


    }

}
