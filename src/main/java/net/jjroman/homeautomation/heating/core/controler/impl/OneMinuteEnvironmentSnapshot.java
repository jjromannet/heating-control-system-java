package net.jjroman.homeautomation.heating.core.controler.impl;

import net.jjroman.homeautomation.heating.core.controler.ConfigurationSnapshot;
import net.jjroman.homeautomation.heating.core.controler.EnvironmentSnapshot;
import net.jjroman.homeautomation.heating.core.controler.MeasuresSnapshot;
import net.jjroman.homeautomation.heating.core.controler.ModuleStateSnapshot;
import net.jjroman.homeautomation.heating.core.modules.LogicalModule;
import net.jjroman.homeautomation.heating.core.modules.ModuleState;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jan on 08/02/2015.
 */
public class OneMinuteEnvironmentSnapshot implements EnvironmentSnapshot {

    private final Map<String, Double> doubleValues;
    private final Map<LogicalModule, ModuleState> modulesStates;
    private final Map<String, Boolean> booleanValues;

    public OneMinuteEnvironmentSnapshot(Map<String, Double> doubleValues, Map<LogicalModule, ModuleState> modulesStates, Map<String, Boolean> booleanValues) {
        this.doubleValues = Collections.unmodifiableMap(doubleValues);
        this.modulesStates = Collections.unmodifiableMap(modulesStates);
        this.booleanValues = Collections.unmodifiableMap(booleanValues);
    }

    @Override
    public Double getDoubleValue(String key) {
        return doubleValues.get(key);
    }

    @Override
    public ModuleState getCurrentState(LogicalModule logicalModule) {
        return modulesStates.get(logicalModule);
    }

    @Override
    public Boolean getBooleanValue(String key) {
        return booleanValues.get(key);
    }

    @Override
    public ConfigurationSnapshot getConfigurationSnapshot() {
        // TODO
        return null;
    }

    @Override
    public MeasuresSnapshot getMeasuresSnapshot(){
        // TODO
        return null;
    }

    @Override
    public ModuleStateSnapshot getModuleStateSnapshot(){
        // TODO
        return null;
    }


}
