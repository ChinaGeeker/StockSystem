package example.ruanjian.stocksystem.log;

import org.apache.log4j.Level;

import de.mindpipe.android.logging.log4j.LogConfigurator;
import example.ruanjian.stocksystem.application.StockSystemApplication;
import example.ruanjian.stocksystem.utils.StockSystemConstant;


public class Logger {

    private static Logger _instance;

    private org.apache.log4j.Logger _log;

    public static Logger getLoggerInstance()
    {
        if (_instance == null)
        {
            _instance = new Logger();
        }
        return _instance;
    }

    private Logger()
    {
        LogConfigurator logConfigurator = new LogConfigurator();
        logConfigurator.setFileName(StockSystemApplication.getInstance().getLogPath());
        logConfigurator.setRootLevel(Level.DEBUG);
        logConfigurator.setLevel("org.apache", Level.ERROR);
        logConfigurator.setFilePattern("%d %-5p [%c{2}]-[%L] %m%n");
        logConfigurator.setMaxFileSize(1024 * 1024 * 5);
        logConfigurator.setImmediateFlush(true);
        logConfigurator.configure();
        _log = org.apache.log4j.Logger.getLogger(StockSystemConstant.LOG_PREFIX_NAME);
    }


    public void error(Object message, Throwable t)
    {
        if (t == null)
        {
            _log.error(message);
            return;
        }
        _log.error(message, t);
    }

    public void debug(Object message, Throwable t)
    {
        if (t == null)
        {
            _log.debug(message);
            return;
        }
        _log.debug(message, t);
    }


    public void info(Object message, Throwable t)
    {
        if (t == null)
        {
            _log.info(message);
            return;
        }
        _log.info(message, t);
    }


    public void warn(Object message, Throwable t)
    {
        if (t == null)
        {
            _log.warn(message);
            return;
        }
        _log.warn(message, t);
    }


}
