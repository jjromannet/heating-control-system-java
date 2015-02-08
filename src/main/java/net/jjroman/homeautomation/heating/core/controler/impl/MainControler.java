package net.jjroman.homeautomation.heating.core.controler.impl;

import net.jjroman.homeautomation.heating.core.controler.ControlAction;
import net.jjroman.homeautomation.heating.core.controler.ControlLogic;
import net.jjroman.homeautomation.heating.core.controler.Controler;
import net.jjroman.homeautomation.heating.core.controler.EnvironmentSnapshot;
import net.jjroman.homeautomation.heating.core.modules.LogicalModule;
import org.omg.CORBA.Environment;

import java.util.Collections;
import java.util.List;

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
                boolean success = actionToPerform.execute();
            }

        }
    }

    private EnvironmentSnapshot createEnvironmentSnapshot(){

        return null;
    }

}
