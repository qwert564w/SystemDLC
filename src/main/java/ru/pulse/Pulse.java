package ru.pulse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public final class Pulse {
    private static final Logger LOGGER = LoggerFactory.getLogger("systemdlc-cosmetics");
    private Pulse() {}
    public static Logger getLOGGER() { return LOGGER; }
}
