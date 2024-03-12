package de.mlosoft.filipclub.entity;

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
@Table(name = "product_type")
public class ProductTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_type_type_id_seq")
    @SequenceGenerator(name = "product_type_type_id_seq", sequenceName = "product_type_type_id_seq", allocationSize = 1)
    @Column(name = "type_id")
    private long typeId;

    @Column(name = "type_name")
    private String typeName;
}