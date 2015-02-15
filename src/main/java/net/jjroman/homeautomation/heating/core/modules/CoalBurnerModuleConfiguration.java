package net.jjroman.homeautomation.heating.core.modules;

/**
 * Created by Jan on 14/02/2015.
 */
public interface CoalBurnerModuleConfiguration {
    int getConfigValueActiveHeatingFanHeadStart();
    int getConfigValueActiveHeatingCoalDispension();
    int getConfigValueActiveHeatingPauseAfterCoalDispension();

    int getConfigValueStandbyFanHeadStart();
    int getConfigValueStandbyCoalDispension();
    int getConfigValueStandbyPauseAfterCoalDispension();

    int getFanPin();
    int getCoalDispenserPin();

}
