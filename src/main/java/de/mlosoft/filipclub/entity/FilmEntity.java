package de.mlosoft.filipclub.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "film")
public class FilmEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "film_film_id_seq")
    @SequenceGenerator(name = "film_film_id_seq", sequenceName = "film_film_id_seq", allocationSize = 1)
    @Column(name = "film_id")
    private long filmId;
    private String name;
    private String type;

    @Column(name = "yt_link")
    private String ytLink;

    // TODO fix activation flag
    private int active;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

}
