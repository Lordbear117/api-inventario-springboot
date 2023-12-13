package com.bear.inventario.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "categoria")
public class Category {

  @Id @GeneratedValue(strategy = GenerationType.AUTO) private int id;

  @Column(length = 100, nullable = false) private String nombre;
}
