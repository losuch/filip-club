package de.mlosoft.filipclub.error;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ErrorInfo implements Serializable {

    private static final long serialVersionUID = -2199240474875513393L;

    String errorCode;
    String errorMessage;
    String errorSeverity; // i.e."WARN"
    String requestIdentifier;

    @JsonIgnore
    Map<String, String> additionalInfos; // NOSONAR

    public ErrorInfo() {
        additionalInfos = new HashMap<String, String>();
    }

    public ErrorInfo(String errorCode) {
        additionalInfos = new HashMap<String, String>();
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorSeverity() {
        return errorSeverity;
    }

    public void setErrorSeverity(String errorSeverity) {
        this.errorSeverity = errorSeverity;
    }

    public String getRequestIdentifier() {
        return requestIdentifier;
    }

    public void setRequestIdentifier(String requestIdentifier) {
        this.requestIdentifier = requestIdentifier;
    }

    public Map<String, String> getAdditionalInfo() {
        return additionalInfos;
    }

    public void setAdditionalInfos(Map<String, String> additionalInfos) {
        this.additionalInfos = additionalInfos;
    }

    public void setAdditionalInfo(String key, String value) {
        if (this.additionalInfos == null) {
            this.additionalInfos = new HashMap<String, String>();
        }
        this.additionalInfos.put(key, value);
    }

    /**
     * replace all occurrances for the keys within additional info with their
     * values for example: ErrorMessage is
     */
    public void addAdditionalInfoToErrorMessage() {
        if (getAdditionalInfo() != null) {
            Iterator<String> keys = getAdditionalInfo().keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                setErrorMessage(getErrorMessage().replaceAll(key, getAdditionalInfo().get(key)));
            }
        }
    }
}
