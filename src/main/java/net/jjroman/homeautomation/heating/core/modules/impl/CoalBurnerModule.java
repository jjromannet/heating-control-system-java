package net.jjroman.homeautomation.heating.core.modules.impl;

import net.jjroman.homeautomation.heating.core.modules.LogicalModule;
import net.jjroman.homeautomation.heating.core.modules.ModuleState;
import net.jjroman.homeautomation.heating.core.modules.UnknownModuleStateException;
import net.jjroman.homeautomation.heating.io.gpioexecutor.HardwareExecutor;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Jan on 08/02/2015.
 */
public class CoalBurnerModule implements LogicalModule {

    public static final ModuleState ACTIVE_HEATING = CoalBurnerModuleState.ACTIVE_HEATING;
    public static final ModuleState STANDBY = CoalBurnerModuleState.STANDBY;
    public static final ModuleState OFF = CoalBurnerModuleState.OFF;

    private final HardwareExecutor hardwareExecutor;

    //private ModuleState currentState = OFF;
    private final AtomicReference<ModuleState> currentStateAtomicReference = new AtomicReference<>(OFF);
    public CoalBurnerModule(HardwareExecutor hardwareExecutor){
        this.hardwareExecutor = hardwareExecutor;
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

    // TODO
    private void run(){
        try {
            switch ((CoalBurnerModuleState)currentStateAtomicReference.get()){
                case OFF:
                    // SET all outputs to off
                    break;
                case STANDBY:
                    // LOGIC DEFINITION
                    // 1. Check when was last run to maintain the fire
                    // 2. maintenance run
                    // 3. reset timer
                        TimeUnit.SECONDS.sleep(1);
                    break;
                case ACTIVE_HEATING:
                    // TODO:
                    // LOGIC DEFINITION
                    // 1. Start FAN - if it is not started already
                    // 2. Wait for 'fan headstart' seconds
                    // 3. Start Coal Dispenser
                    // 4. Stop Coal Dispenser .. CRITICAL !
                    // 5. Wait for 'after coal dispensed' seconds
                    // 6. Start again - if we are still on Active heating
                    TimeUnit.SECONDS.sleep(1);
                    break;
            }
        } catch (InterruptedException e) {
            // TODO
            e.printStackTrace();
        }finally {
            // TODO turn everything off
        }
    }
}
