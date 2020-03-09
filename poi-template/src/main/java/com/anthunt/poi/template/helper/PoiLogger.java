package com.anthunt.poi.template.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

public class PoiLogger implements Logger {

	private Logger logger;
	
	private PoiLogger(Class<?> loggingClass) {
		this.logger = LoggerFactory.getLogger(loggingClass);
	}
	
	public static PoiLogger getLogger(Class<?> loggingClass) {
		return new PoiLogger(loggingClass);
	}
	
	
	@Override
	public void debug(String arg0) {
		if(this.logger.isDebugEnabled() || this.logger.isTraceEnabled()) {
			this.logger.debug(arg0);
		}
	}

	@Override
	public void debug(String arg0, Object arg1) {
		if(this.logger.isDebugEnabled() || this.logger.isTraceEnabled()) {
			this.logger.debug(arg0, arg1);
		}
	}

	@Override
	public void debug(String arg0, Object... arg1) {
		if(this.logger.isDebugEnabled() || this.logger.isTraceEnabled()) {
			this.logger.debug(arg0, arg1);
		}
	}

	@Override
	public void debug(String arg0, Throwable arg1) {
		if(this.logger.isDebugEnabled() || this.logger.isTraceEnabled()) {
			this.logger.debug(arg0, arg1);
		}
	}

	@Override
	public void debug(Marker arg0, String arg1) {
		if(this.logger.isDebugEnabled(arg0) || this.logger.isTraceEnabled(arg0)) {
			this.logger.debug(arg0, arg1);
		}
	}

	@Override
	public void debug(String arg0, Object arg1, Object arg2) {
		if(this.logger.isDebugEnabled() || this.logger.isTraceEnabled()) {
			this.logger.debug(arg0, arg1, arg2);
		}
	}

	@Override
	public void debug(Marker arg0, String arg1, Object arg2) {
		if(this.logger.isDebugEnabled(arg0) || this.logger.isTraceEnabled(arg0)) {
			this.logger.debug(arg0, arg1, arg2);
		}
	}

	@Override
	public void debug(Marker arg0, String arg1, Object... arg2) {
		if(this.logger.isDebugEnabled(arg0) || this.logger.isTraceEnabled(arg0)) {
			this.logger.debug(arg0, arg1, arg2);
		}
	}

	@Override
	public void debug(Marker arg0, String arg1, Throwable arg2) {
		if(this.logger.isDebugEnabled(arg0) || this.logger.isTraceEnabled(arg0)) {
			this.logger.debug(arg0, arg1, arg2);
		}
	}

	@Override
	public void debug(Marker arg0, String arg1, Object arg2, Object arg3) {
		if(this.logger.isDebugEnabled(arg0) || this.logger.isTraceEnabled(arg0)) {
			this.logger.debug(arg0, arg1, arg2, arg3);
		}
	}

	@Override
	public void error(String arg0) {
		this.logger.error(arg0);
	}

	@Override
	public void error(String arg0, Object arg1) {
		this.logger.error(arg0, arg1);
	}

	@Override
	public void error(String arg0, Object... arg1) {
		this.logger.error(arg0, arg1);
	}

	@Override
	public void error(String arg0, Throwable arg1) {
		this.logger.error(arg0, arg1);
	}

	@Override
	public void error(Marker arg0, String arg1) {
		this.logger.error(arg0, arg1);
	}

	@Override
	public void error(String arg0, Object arg1, Object arg2) {
		this.logger.error(arg0, arg1, arg2);
	}

	@Override
	public void error(Marker arg0, String arg1, Object arg2) {
		this.logger.error(arg0, arg1, arg2);
	}

	@Override
	public void error(Marker arg0, String arg1, Object... arg2) {
		this.logger.error(arg0, arg1, arg2);
	}

	@Override
	public void error(Marker arg0, String arg1, Throwable arg2) {
		this.logger.error(arg0, arg1, arg2);
	}

	@Override
	public void error(Marker arg0, String arg1, Object arg2, Object arg3) {
		this.logger.error(arg0, arg1, arg2, arg3);
	}

	@Override
	public String getName() {
		return this.logger.getName();
	}

	@Override
	public void info(String arg0) {
		this.logger.info(arg0);
	}

	@Override
	public void info(String arg0, Object arg1) {
		this.logger.info(arg0, arg1);
	}

	@Override
	public void info(String arg0, Object... arg1) {
		this.logger.info(arg0, arg1);
	}

	@Override
	public void info(String arg0, Throwable arg1) {
		this.logger.info(arg0, arg1);
	}

	@Override
	public void info(Marker arg0, String arg1) {
		this.logger.info(arg0, arg1);
	}

	@Override
	public void info(String arg0, Object arg1, Object arg2) {
		this.logger.info(arg0, arg1, arg2);
	}

	@Override
	public void info(Marker arg0, String arg1, Object arg2) {
		this.logger.info(arg0, arg1, arg2);
	}

	@Override
	public void info(Marker arg0, String arg1, Object... arg2) {
		this.logger.info(arg0, arg1, arg2);
	}

	@Override
	public void info(Marker arg0, String arg1, Throwable arg2) {
		this.logger.info(arg0, arg1, arg2);
	}

	@Override
	public void info(Marker arg0, String arg1, Object arg2, Object arg3) {
		this.logger.info(arg0, arg1, arg2, arg3);
	}

	@Override
	public boolean isDebugEnabled() {
		return this.logger.isDebugEnabled();
	}

	@Override
	public boolean isDebugEnabled(Marker arg0) {
		return this.logger.isDebugEnabled(arg0);
	}

	@Override
	public boolean isErrorEnabled() {
		return this.logger.isErrorEnabled();
	}

	@Override
	public boolean isErrorEnabled(Marker arg0) {
		return this.logger.isErrorEnabled(arg0);
	}

	@Override
	public boolean isInfoEnabled() {
		return this.logger.isInfoEnabled();
	}

	@Override
	public boolean isInfoEnabled(Marker arg0) {
		return this.logger.isInfoEnabled(arg0);
	}

	@Override
	public boolean isTraceEnabled() {
		return this.logger.isTraceEnabled();
	}

	@Override
	public boolean isTraceEnabled(Marker arg0) {
		return this.logger.isTraceEnabled(arg0);
	}

	@Override
	public boolean isWarnEnabled() {
		return this.logger.isWarnEnabled();
	}

	@Override
	public boolean isWarnEnabled(Marker arg0) {
		return this.logger.isWarnEnabled(arg0);
	}

	@Override
	public void trace(String arg0) {
		if(this.logger.isTraceEnabled()) {
			this.logger.trace(arg0);
		}
	}

	@Override
	public void trace(String arg0, Object arg1) {
		if(this.logger.isTraceEnabled()) {
			this.logger.trace(arg0, arg1);
		}
	}

	@Override
	public void trace(String arg0, Object... arg1) {
		if(this.logger.isTraceEnabled()) {
			this.logger.trace(arg0, arg1);
		}
	}

	@Override
	public void trace(String arg0, Throwable arg1) {
		if(this.logger.isTraceEnabled()) {
			this.logger.trace(arg0, arg1);
		}
	}

	@Override
	public void trace(Marker arg0, String arg1) {
		if(this.logger.isTraceEnabled(arg0)) {
			this.logger.trace(arg0, arg1);
		}
	}

	@Override
	public void trace(String arg0, Object arg1, Object arg2) {
		if(this.logger.isTraceEnabled()) {
			this.logger.trace(arg0, arg1, arg2);
		}
	}

	@Override
	public void trace(Marker arg0, String arg1, Object arg2) {
		if(this.logger.isTraceEnabled(arg0)) {
			this.logger.trace(arg0, arg1, arg2);
		}
	}

	@Override
	public void trace(Marker arg0, String arg1, Object... arg2) {
		if(this.logger.isTraceEnabled(arg0)) {
			this.logger.trace(arg0, arg1, arg2);
		}
	}

	@Override
	public void trace(Marker arg0, String arg1, Throwable arg2) {
		if(this.logger.isTraceEnabled(arg0)) {
			this.logger.trace(arg0, arg1, arg2);
		}
	}

	@Override
	public void trace(Marker arg0, String arg1, Object arg2, Object arg3) {
		if(this.logger.isTraceEnabled(arg0)) {
			this.logger.trace(arg0, arg1, arg2, arg3);
		}
	}

	@Override
	public void warn(String arg0) {
		this.logger.warn(arg0);
	}

	@Override
	public void warn(String arg0, Object arg1) {
		this.logger.warn(arg0, arg1);
	}

	@Override
	public void warn(String arg0, Object... arg1) {
		this.logger.warn(arg0, arg1);
	}

	@Override
	public void warn(String arg0, Throwable arg1) {
		this.logger.warn(arg0, arg1);
	}

	@Override
	public void warn(Marker arg0, String arg1) {
		this.logger.warn(arg0, arg1);
	}

	@Override
	public void warn(String arg0, Object arg1, Object arg2) {
		this.logger.warn(arg0, arg1, arg2);
	}

	@Override
	public void warn(Marker arg0, String arg1, Object arg2) {
		this.logger.warn(arg0, arg1, arg2);
	}

	@Override
	public void warn(Marker arg0, String arg1, Object... arg2) {
		this.logger.warn(arg0, arg1, arg2);
	}

	@Override
	public void warn(Marker arg0, String arg1, Throwable arg2) {
		this.logger.warn(arg0, arg1, arg2);
	}

	@Override
	public void warn(Marker arg0, String arg1, Object arg2, Object arg3) {
		this.logger.warn(arg0, arg1, arg2, arg3);
	}

}
