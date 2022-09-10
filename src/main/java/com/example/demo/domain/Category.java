package com.example.demo.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Categories")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Category {

	
	@Id
	@GeneratedValue
	public Long id;
	public String categoryName;
	
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "catId", referencedColumnName = "id")
	public List<Item> itemsList;
	 
}
