package net.jjroman.homeautomation.heating.core.controler.impl;

import net.jjroman.homeautomation.heating.core.controler.ControlAction;
import net.jjroman.homeautomation.heating.core.controler.ControlLogic;
import net.jjroman.homeautomation.heating.core.controler.Controler;
import net.jjroman.homeautomation.heating.core.controler.EnvironmentSnapshot;
import net.jjroman.homeautomation.heating.core.modules.LogicalModule;
import net.jjroman.homeautomation.heating.core.modules.ModuleState;
import org.omg.CORBA.Environment;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jan on 08/02/2015.
 */
public class MainControler implements Controler{
    private final List<LogicalModule> modules;
    private final List<ControlLogic> controlLogics;

    public MainControler(List<LogicalModule> modules, List<ControlLogic> controlLogics) {
        this.modules = Collections.unmodifiableList(modules);
        this.controlLogics = Collections.unmodifiableList(controlLogics);
    }

    public void run(){
        while(true){
            // TODO: error handling etc,
            // TODO: thread pause
            // TODO: app clean stop
            mainLoopStep();
        }
    }

    private void mainLoopStep(){
        EnvironmentSnapshot environmentSnapshot = createEnvironmentSnapshot();

        for(LogicalModule module : modules){
            for(ControlLogic currentLogic : controlLogics) {
                ControlAction actionToPerform = currentLogic.generateAction(module, environmentSnapshot);
                boolean success = actionToPerform.execute(module);
            }

        }
    }

    private EnvironmentSnapshot createEnvironmentSnapshot(){
        // Mocking ...
        Map<String, Double> doubleValues = new HashMap<>();
        Map<LogicalModule, ModuleState> modulesStates = new HashMap<>();
        Map<String, Boolean> booleanValues = new HashMap<>();

        doubleValues.put("coalburner.current_water_temperature", 30.00);
        doubleValues.put("coalburner.turn_on_water_temperature", 55.00);
        doubleValues.put("coalburner.turn_off_water_temperature", 60.00);
        booleanValues.put("coalburner.module_turned_on", true);

        for(LogicalModule module : modules){
            modulesStates.put(module, module.getCurrentStatus());
        }


        return new OneMinuteEnvironmentSnapshot(doubleValues, modulesStates, booleanValues);
    }

}
