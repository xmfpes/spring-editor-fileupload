package com.kyunam.domain;

import java.util.Date;

import javax.persistence.Column;
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
public class Article {
	@Id
    @GeneratedValue
    Long id;
    String title;
    @Column(length = 100000000)
    String content;
    Date regDate;
}