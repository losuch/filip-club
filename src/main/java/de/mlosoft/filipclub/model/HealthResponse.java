package de.mlosoft.filipclub.model;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class HealthResponse implements Serializable {
    private String apiVersion;
    private String appName;
    private String message;
    private Date time;
}
