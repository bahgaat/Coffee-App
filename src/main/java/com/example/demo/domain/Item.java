package com.example.demo.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Items")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Item implements Serializable{

	public static final long serialVersionUID = -2228784815938588107L;
	
	@Id
	@GeneratedValue
	public Long itemId;
	public Long price;
	public Long quantity;
	public String name;
	public Long catId;
	
	@Override
	public String toString() {
		return "Item [itemId=" + itemId + ", price=" + price + ", quantity=" + quantity + ", name=" + name + ", catId="
				+ catId + "]";
	}
	
	
}
