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
public class Product {

    private long productId;

    @NotBlank
    private String name;
    private String description;

    @Column(name = "images_url")
    private String imagesUrl;

    @NotBlank
    private int price;
    private int activ;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Date createdAt;
}
