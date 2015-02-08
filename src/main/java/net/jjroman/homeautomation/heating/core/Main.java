package net.jjroman.homeautomation.heating.core;

import net.jjroman.homeautomation.heating.core.controler.ControlLogic;
import net.jjroman.homeautomation.heating.core.controler.Controler;
import net.jjroman.homeautomation.heating.core.controler.impl.CoalBurnerControlLogic;
import net.jjroman.homeautomation.heating.core.controler.impl.HotWaterCylinderControlLogic;
import net.jjroman.homeautomation.heating.core.controler.impl.MainControler;
import net.jjroman.homeautomation.heating.core.modules.LogicalModule;
import net.jjroman.homeautomation.heating.core.modules.impl.CoalBurnerModule;
import net.jjroman.homeautomation.heating.core.modules.impl.HotWaterCylinderModule;

import java.util.ArrayList;
import java.util.List;

/**
 * Main class to run application
 *
 * Created by Jan on 08/02/2015.
 */
public class Main {
    public static void main(String[] args){
        System.out.print("Hello World!");

        List<ControlLogic> controlLogics= new ArrayList<ControlLogic>();
        List<LogicalModule> logicalModules = new ArrayList<LogicalModule>();

        controlLogics.add(new CoalBurnerControlLogic());
        controlLogics.add(new HotWaterCylinderControlLogic());

        logicalModules.add(new CoalBurnerModule());
        logicalModules.add(new HotWaterCylinderModule());

        Controler controler = new MainControler(logicalModules, controlLogics);

        // TODO: nice thread handling
        (new Thread(controler)).run();

     }
}
