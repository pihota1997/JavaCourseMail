import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerToConsole implements BaseClass{
    private static Logger logger = Logger.getLogger(LoggerToConsole.class.getName());

    public void run(String message) {
        logger.log(Level.INFO, message);
    }
}
