import java.io.IOException;
import java.util.logging.*;

public class LoggerToFile implements BaseClass{
    private static final Logger logger = Logger.getLogger(LoggerToFile.class.getName());
    private static Handler fileHandler = null;
    static {
        try {
            fileHandler = new FileHandler("LoggerFile.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LoggerToFile(){
        fileHandler.setFormatter(new MyFormatter());
        logger.setUseParentHandlers(false);
        logger.addHandler(fileHandler);
    }

    public void run(String message) {
        logger.log(Level.INFO, message);
    }

    static class MyFormatter extends Formatter{
        @Override
        public String format(LogRecord record) {
            return record.getMessage() + "\n";
        }
    }
}
