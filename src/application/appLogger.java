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
		
		private final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		
		@Override
		public synchronized String format( LogRecord record) {
			
			Date d = new Date( record.getMillis());
			
			return String.format( "%-9s <%s>  %-28s :   %s%n", 
					"["+ record.getLevel().getName() + "]",
					df.format(d),
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
