package de.mlosoft.filipclub.error;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.mlosoft.filipclub.util.LogUtil;

@ControllerAdvice
public class ExceptionControllerAdvice {

    private static final Logger LOG = LogUtil.getLogger();

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorInfo exception(Exception e) {
        ErrorInfo info;

        if (e instanceof FilipClubException) {
            info = ((FilipClubException) e).getErrorInfo();
            if ("DB_ERROR".equals(info.getErrorCode())) {
                info.setErrorMessage("Database is not available");
                LOG.error("Database error");

            } else {
                info.setErrorMessage(ErrorCode.valueOf(info.errorCode).errorCode);
                info.setErrorSeverity("WARN");
            }
            info.addAdditionalInfoToErrorMessage();

        } else {
            LOG.error(e.getMessage(), e);
            info = new ErrorInfo();
            info.setErrorMessage(e.getMessage());
            info.setErrorCode(ErrorCode.UNKNOWN_ERROR.name());
        }
        return info;
    }

}