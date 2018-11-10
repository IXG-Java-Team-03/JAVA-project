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
			
			return String.format( "%4d. %-13s %-10s %-30s %-30s :   %-30s (%s.%s)%n", 
					record.getSequenceNumber(),					// sequence number
					df.format(d),								// time of logging
					"["+ record.getLevel().getName() + "]",		// logging level
					record.getLoggerName(),						// logger name
					Thread.currentThread().getName(),			// thread name
					super.formatMessage( record),				// logged message
					record.getSourceClassName(),				// class name
					record.getSourceMethodName()				// method name
					);			
		}
	}
	
	@Override
	public void setLevel( Level l) {
		Handler[] handlers = this.getHandlers();
		for( Handler h : handlers) {
			h.setLevel( l);		// set the handler level
		}
		super.setLevel( l);		// set the logger level

	}
	
	
	protected appLogger(String name, String resourceBundleName) {
		super(name, resourceBundleName);

		Handler ch = new ConsoleHandler();
		ch.setFormatter( new singleLineFormatter());
		this.addHandler( ch);

//		setLevel( Level.ALL);		// ----- debugging logging level
	}
	

}
