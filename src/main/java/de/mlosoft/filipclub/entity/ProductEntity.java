package de.mlosoft.filipclub.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_product_id_seq")
    @SequenceGenerator(name = "product_product_id_seq", sequenceName = "product_product_id_seq", allocationSize = 1)
    @Column(name = "product_id")
    private long productId;
    private String name;
    private String description;

    @Column(name = "images_url")
    private String imagesUrl;
    private int price;

    // TODO fix activation flag
    private int active;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "type_id")
    private ProductTypeEntity productType;

}
