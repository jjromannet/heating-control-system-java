package net.jjroman.homeautomation.heating.core.modules.impl;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import net.jjroman.homeautomation.heating.core.ExceptionHandler;
import net.jjroman.homeautomation.heating.core.controler.ConfigurationSnapshot;
import net.jjroman.homeautomation.heating.core.controler.EnvironmentSnapshot;
import net.jjroman.homeautomation.heating.core.controler.Measure;
import net.jjroman.homeautomation.heating.core.modules.CoalBurnerModuleConfiguration;
import net.jjroman.homeautomation.heating.core.modules.LogicalModule;
import net.jjroman.homeautomation.heating.core.modules.ModuleState;
import net.jjroman.homeautomation.heating.core.modules.UnknownModuleStateException;
import net.jjroman.homeautomation.heating.io.gpioexecutor.HardwareExecutor;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Jan on 08/02/2015.
 */
public class CoalBurnerModule implements LogicalModule {

    private final Pin fan;
    private final Pin coalDispenser;

    private Set<Measure> requiredMeasures = new HashSet<>();

    public static final ModuleState ACTIVE_HEATING = CoalBurnerModuleState.ACTIVE_HEATING;
    public static final ModuleState STANDBY = CoalBurnerModuleState.STANDBY;
    public static final ModuleState OFF = CoalBurnerModuleState.OFF;

    //private final ImmutableSet<ModuleState> availableStates;

    private final HardwareExecutor hardwareExecutor;

    private final AtomicReference<ModuleState> currentStateAtomicReference = new AtomicReference<>(OFF);

    private AtomicReference<EnvironmentSnapshot> environmentSnapshotAtomicReference = new AtomicReference<>();

    public CoalBurnerModule(HardwareExecutor hardwareExecutor, EnvironmentSnapshot initialEnvironmentSnapshot){

        this.hardwareExecutor = hardwareExecutor;
        this.fan = RaspiPin.GPIO_04;
        this.coalDispenser = RaspiPin.GPIO_00;

        ConfigurationSnapshot configurationSnapshot = initialEnvironmentSnapshot.getConfigurationSnapshot();
        if(configurationSnapshot == null || configurationSnapshot.getClass().isAssignableFrom(CoalBurnerModuleConfiguration.class)){
            // TODO
            //throw new Exception("Configuration for module not provided");
        }
        //((CoalBurnerModuleConfiguration)configurationSnapshot)
        //fan = hardwareExecutor.assignPin();
        //coalDispenser = pinFactory.getPin("coalDispenser");
        // TODO


        //availableStates = Sets.immutableEnumSet(ModuleState);


    }

    public void updateEnvironmentSnapshot(EnvironmentSnapshot environmentSnapshot){
        environmentSnapshotAtomicReference.set(environmentSnapshot);
    }

    @Override
    public synchronized boolean changeStatus(ModuleState from, ModuleState to) throws UnknownModuleStateException {
        if(from == null || !from.getClass().isAssignableFrom(CoalBurnerModuleState.class)
                || to == null || !to.getClass().isAssignableFrom(CoalBurnerModuleState.class) ){
            throw new UnknownModuleStateException("Unsupported state. CoalBurner can only handle CoalBurnerModuleState objects");
        }
        if(from.equals(to)){
            return false;
        }
        return currentStateAtomicReference.compareAndSet(from, to);
    }

    @Override
    public ModuleState getCurrentStatus() {
        return currentStateAtomicReference.get();
    }

    private void run(){
        int lastrun = 0;

        // TODO - read from config:
        int cfg_active_heating_fanHeadStart = 1;
        int cfg_active_heating_dispensingTime = 5;
        int cfg_active_heating_waitAfterCoalDispensed = 20;
        int cfg_standby_fanHeadStart = 1;
        int cfg_standby_dispensingTime = 10;
        int cfg_standby_waitAfterCoalDispensed = 120;

        try {
            while(true) {
                EnvironmentSnapshot localEnviromantSnapshot = environmentSnapshotAtomicReference.get();
                if(localEnviromantSnapshot == null){
                    TimeUnit.SECONDS.sleep(1);
                    continue;
                }
                switch ((CoalBurnerModuleState) currentStateAtomicReference.get()) {
                    case OFF:
                        TimeUnit.SECONDS.sleep(1);
                        lastrun = 0;
                        turnOff();
                        // TODO: Make sure water will not boil
                        break;
                    case STANDBY:
                        // LOGIC DEFINITION
                        TimeUnit.SECONDS.sleep(1);
                        lastrun++;
                        if (lastrun >= cfg_standby_waitAfterCoalDispensed) {
                            hardwareExecutor.changeGpioPinDigitalOutputState(fan, PinState.HIGH);
                            TimeUnit.SECONDS.sleep(cfg_standby_fanHeadStart);
                            hardwareExecutor.changeGpioPinDigitalOutputState(coalDispenser, PinState.HIGH);
                            TimeUnit.SECONDS.sleep(cfg_standby_dispensingTime);
                            hardwareExecutor.changeGpioPinDigitalOutputState(coalDispenser, PinState.LOW);
                            lastrun = 0;
                        }
                        // 1. Check when was last run to maintain the fire
                        // 2. maintenance run
                        // 3. reset timer

                        break;
                    case ACTIVE_HEATING:
                        // LOGIC DEFINITION
                        hardwareExecutor.changeGpioPinDigitalOutputState(fan, PinState.HIGH);
                        TimeUnit.SECONDS.sleep(cfg_active_heating_fanHeadStart);
                        hardwareExecutor.changeGpioPinDigitalOutputState(coalDispenser, PinState.HIGH);
                        TimeUnit.SECONDS.sleep(cfg_active_heating_dispensingTime);
                        hardwareExecutor.changeGpioPinDigitalOutputState(coalDispenser, PinState.LOW);
                        TimeUnit.SECONDS.sleep(cfg_active_heating_waitAfterCoalDispensed);
                        lastrun = 0;
                        break;
                }
            }
        } catch (InterruptedException e) {
            ExceptionHandler.handle(e);
        }finally {
            turnOff();
        }
    }

    private void turnOff(){
        hardwareExecutor.changeGpioPinDigitalOutputState(fan, PinState.LOW);
        hardwareExecutor.changeGpioPinDigitalOutputState(coalDispenser, PinState.LOW);
    }

    @Override
    public ImmutableSet<ModuleState> getAvailableStates(){
        // TODO
        return null;
    }

}
