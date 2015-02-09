package net.jjroman.homeautomation.heating.core;

import net.jjroman.homeautomation.heating.core.controler.impl.CoalBurnerControlLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Jan on 09/02/2015.
 */
public class ExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CoalBurnerControlLogic.class);

    public static void handle(Throwable th){
        logger.error("ExceprionHandler handled exception.", th);
    }
}
