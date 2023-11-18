package de.mlosoft.filipclub.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LogUtil {

    private static final Logger LOG = LoggerFactory.getLogger(Logger.class);

    private LogUtil() {
    }

    /**
     * Get logger for calling class without an explicit reference to that class.
     * <br>
     * In case of an unexpected stack on some exotic platform, get logger for
     * {@link com.lhsystems.cms.mops.util.LogUtil} <br>
     * Usage: <br>
     * 
     * <pre>
     * import LogUtil;
     * import org.slf4j.Logger;
     * 
     * public final class MyClass {
     * 
     *   private (static) final Logger logger = LogUtil.getLogger(); // instead of getLogger(MyClass.class);
     *   ...
     * }
     * </pre>
     * 
     * @return Logger instance
     */
    public static synchronized Logger getLogger() {

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        for (int i = 1; i < stackTrace.length; i++) {

            String className = stackTrace[i].getClassName();

            if (LogUtil.class.getCanonicalName().equals(className)) {

                if (i + 1 < stackTrace.length) {
                    return LoggerFactory.getLogger(stackTrace[i + 1].getClassName());
                }

                LOG.error("Unexpected end of stack:");
                for (StackTraceElement ste : stackTrace) {
                    LOG.error(ste.toString());
                }

                return LoggerFactory.getLogger(className);
            }
        }

        LOG.error("Unexpected stack:");
        for (StackTraceElement ste : stackTrace) {
            LOG.error(ste.toString());
        }

        return LoggerFactory.getLogger(LogUtil.class.getCanonicalName());
    }
}