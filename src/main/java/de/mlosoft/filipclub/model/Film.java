package de.mlosoft.filipclub.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Film {

    private long filmId;

    @NotBlank
    private String name;
    private String type;

    @Column(name = "yt_link")
    private String ytLink;

    private boolean active;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Date createdAt;
}
