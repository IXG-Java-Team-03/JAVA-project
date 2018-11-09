package application;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;


public class appLogger extends Logger {
	

	private class singleLineFormatter extends Formatter {
		
		private final DateFormat df = new SimpleDateFormat("HH:mm:ss.SSS");
		
		@Override
		public synchronized String format( LogRecord record) {
			
			Date d = new Date( record.getMillis());
			
			return String.format( "%-13s %-10s %-30s %-30s :   %s%n", 
					df.format(d),
					"["+ record.getLevel().getName() + "]",
					record.getLoggerName(),
					Thread.currentThread().getName(),
					record.getMessage()
					);
		}
	}
	
	
	protected appLogger(String name, String resourceBundleName) {
		super(name, resourceBundleName);

		this.setLevel( Level.INFO);
		Handler ch = new ConsoleHandler();
		ch.setFormatter( new singleLineFormatter());
		this.addHandler( ch);
	}
	

}
