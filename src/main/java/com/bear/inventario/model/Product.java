package com.bear.inventario.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "producto")
public class Product {

  @Id @GeneratedValue(strategy = GenerationType.AUTO) private int id;

  @Column(length = 100, nullable = false) private String nombre;

  @Column(columnDefinition = "TEXT") private String descripcion;

  @Column(nullable = false, precision = 10) private double precio;

  @Column(nullable = false) private int cantidad;

  @ManyToOne
  @JoinColumn(name = "categoria_id", referencedColumnName = "id")
  private Category category;
}
