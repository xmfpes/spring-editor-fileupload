package com.kyunam.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

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
    
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="article_id")
    List<ArticleFiles> files;
}