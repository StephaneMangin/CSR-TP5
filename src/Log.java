import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;	 
import java.util.Date;
import java.util.logging.LogRecord;


public class Log {

	private Object obj;
	private static Logger log = Logger.getLogger("CSR_TP5");
	private static ConsoleHandler ch = new ConsoleHandler(); 
	private static Formatter format = new Formatter() {

	    @Override
	    public String format(LogRecord record) {
	        return new Date(record.getMillis()) + " "
	        		+ record.getLoggerName() + " ["
	        		+ record.getLevel() + "] "
	                + record.getMessage() + "\n";
	    }
	};

	protected Log(Object obj) {
		this.obj = obj;
		ch.setFormatter(format);
		log.addHandler(ch);
		setLevel(Level.ALL);
	}
	
	public void setLevel(Level level) {
		log.setLevel(level);
	}
	
	public void log(Level level, String msg) {
		//log.log(level, obj.toString() + " >>> " + msg);
		if (log.isLoggable(level)) {
			try {
				System.out.println(Date.class.newInstance().toString() + " [" + level.toString() + "] " + obj.toString() + " >>> " + msg);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void severe(String msg) {
		log(Level.SEVERE, msg);
	}

	public void warning(String msg) {
		log(Level.WARNING, msg);
	}

	public void info(String msg) {
		log(Level.INFO, msg);
	}

	public void config(String msg) {
		log(Level.CONFIG, msg);
	}

	public void fine(String msg) {
		log(Level.FINE, msg);
	}

	public void finer(String msg) {
		log(Level.FINER, msg);
	}

	public void finest(String msg) {
		log(Level.FINEST, msg);
	}
}
