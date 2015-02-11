package net.jjroman.homeautomation.heating.core.controler.impl;

import net.jjroman.homeautomation.heating.core.StringPool;
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


    private ControlAction calculateAction(ModuleState initialModuleState, boolean moduleTurnedOn, double temperatureCurrent, double temperatureOff, double temperatureOn){

        LogicalModule module = new CoalBurnerModule();
        Map<LogicalModule, ModuleState> stateMap = new HashMap<>();
        stateMap.put(module, initialModuleState);
        Map<String, Boolean> booleanValues = new HashMap<>();
        booleanValues.put(StringPool.COALBURNER_MODULE_TURNED_ON, moduleTurnedOn);
        Map<String, Double> doubleValues = new HashMap<>();
        doubleValues.put(StringPool.COALBURNER_CURRENT_WATER_TEMPERATURE_KEY, temperatureCurrent);
        doubleValues.put(StringPool.COALBURNER_TURN_OFF_TEMPERATURE_KEY, temperatureOff);
        doubleValues.put(StringPool.COALBURNER_TURN_ON_TEMPERATURE_KEY, temperatureOn);

        EnvironmentSnapshot environmentSnapshot = new OneMinuteEnvironmentSnapshot(doubleValues, stateMap, booleanValues);

        CoalBurnerControlLogic coalBurnerControlLogic = new CoalBurnerControlLogic();
        return coalBurnerControlLogic.generateAction(module, environmentSnapshot);

    }

    @Test
    public void transitionOffStandby(){
        ControlAction controlAction =  calculateAction(
                CoalBurnerModule.OFF
                , true, 10.0, 60.0, 50.0);

        Assert.assertNotNull(controlAction);
        Assert.assertTrue(controlAction.getClass().isAssignableFrom(ChangeStateAction.class));
        Assert.assertEquals(controlAction.getNewState(), CoalBurnerModule.STANDBY);
    }

    @Test
    public void noTransitionOffOff(){
        ControlAction controlAction =  calculateAction(
                CoalBurnerModule.OFF
                , true, 10.0, 60.0, 50.0);

        Assert.assertNotNull(controlAction);
        Assert.assertTrue(controlAction.getClass().isAssignableFrom(NopControlAction.class));
        Assert.assertEquals(controlAction.getNewState(), CoalBurnerModule.OFF);
    }

    @Test
    public void transitionStandbyOff(){
        ControlAction controlAction =  calculateAction(
                CoalBurnerModule.STANDBY
                , false, 10.0, 60.0, 50.0);

        Assert.assertNotNull(controlAction);
        Assert.assertTrue(controlAction.getClass().isAssignableFrom(NopControlAction.class));
        Assert.assertEquals(controlAction.getNewState(), CoalBurnerModule.OFF);
    }

    @Test
    public void transitionActiveHeatingOff(){
        ControlAction controlAction =  calculateAction(
                CoalBurnerModule.ACTIVE_HEATING
                , false, 10.0, 60.0, 50.0);

        Assert.assertNotNull(controlAction);
        Assert.assertTrue(controlAction.getClass().isAssignableFrom(NopControlAction.class));
        Assert.assertEquals(controlAction.getNewState(), CoalBurnerModule.OFF);
    }

    @Test
    public void transitionStandbyActiveHeatingA(){
        ControlAction controlAction =  calculateAction(
                CoalBurnerModule.STANDBY
                , true, 45.0, 60.0, 45.1);

        Assert.assertNotNull(controlAction);
        Assert.assertTrue(controlAction.getClass().isAssignableFrom(NopControlAction.class));
        Assert.assertEquals(controlAction.getNewState(), CoalBurnerModule.ACTIVE_HEATING);
    }
    @Test
    public void transitionStandbyActiveHeatingB(){
        ControlAction controlAction =  calculateAction(
                CoalBurnerModule.STANDBY
                , true, 35.0, 60.0, 55);

        Assert.assertNotNull(controlAction);
        Assert.assertTrue(controlAction.getClass().isAssignableFrom(NopControlAction.class));
        Assert.assertEquals(controlAction.getNewState(), CoalBurnerModule.ACTIVE_HEATING);
    }

    @Test
    public void transitionActiveHeatingStandbyA(){
        ControlAction controlAction =  calculateAction(
                CoalBurnerModule.ACTIVE_HEATING
                , true, 61.0, 60.0, 55);

        Assert.assertNotNull(controlAction);
        Assert.assertTrue(controlAction.getClass().isAssignableFrom(NopControlAction.class));
        Assert.assertEquals(controlAction.getNewState(), CoalBurnerModule.STANDBY);
    }

    @Test
    public void transitionActiveHeatingStandbyB(){
        ControlAction controlAction =  calculateAction(
                CoalBurnerModule.ACTIVE_HEATING
                , true, 55.0, 50.0, 35);

        Assert.assertNotNull(controlAction);
        Assert.assertTrue(controlAction.getClass().isAssignableFrom(NopControlAction.class));
        Assert.assertEquals(controlAction.getNewState(), CoalBurnerModule.STANDBY);
    }

    @Test
    public void noTransitionActiveHeatingActiveHeating(){
        ControlAction controlAction =  calculateAction(
                CoalBurnerModule.ACTIVE_HEATING
                , true, 55.0, 60.0, 50.0);

        Assert.assertNotNull(controlAction);
        Assert.assertTrue(controlAction.getClass().isAssignableFrom(NopControlAction.class));
        Assert.assertEquals(controlAction.getNewState(), CoalBurnerModule.ACTIVE_HEATING);
    }
    @Test
    public void noTransitionStandbyStandby(){
        ControlAction controlAction =  calculateAction(
                CoalBurnerModule.STANDBY
                , true, 55.0, 60.0, 50.0);

        Assert.assertNotNull(controlAction);
        Assert.assertTrue(controlAction.getClass().isAssignableFrom(NopControlAction.class));
        Assert.assertEquals(controlAction.getNewState(), CoalBurnerModule.STANDBY);
    }
}
