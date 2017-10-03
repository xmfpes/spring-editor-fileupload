package com.kyunam.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class ArticleFiles {
	@Id
	@GeneratedValue
	private Long id;
	
	String fileName;
	String filePath;

}