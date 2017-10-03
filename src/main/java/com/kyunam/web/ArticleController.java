package com.kyunam.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.kyunam.aws.S3Manager;
import com.kyunam.domain.Article;
import com.kyunam.domain.ArticleFiles;
import com.kyunam.domain.ArticleRepository;
@Controller
@RequestMapping("/article")
public class ArticleController {
	@Autowired
	ArticleRepository articleRepository;
	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("articles", articleRepository.findAll());
		return "/article/list";
	}
	@GetMapping("/write")
	public String writeForm() {
		return "/article/form";
	}
	@PostMapping("/write")
	public String write(String title, String content, List<MultipartFile> uploadFiles) throws IOException {  
	  
			S3Manager s3manager = new S3Manager();
			List<ArticleFiles> list = new ArrayList<ArticleFiles>();
			for(MultipartFile file : uploadFiles) {
				ArticleFiles articleFile = new ArticleFiles();
				articleFile.setFileName(file.getOriginalFilename());
				articleFile.setFilePath(s3manager.saveUploadedFiles(file));
				list.add(articleFile);
			}
	    
			Article article = new Article();
			article.setTitle(title);
			article.setContent(content);
			article.setFiles(list);
	    article.setRegDate(new Date());
	    
	    articleRepository.save(article);
	    return "redirect:/article/list";
	}
	@GetMapping("/articleDetail/{id}")
	public String detail(@PathVariable Long id, Model model) {
		model.addAttribute("article",articleRepository.findOne(id));
		return "/article/articleDetail";
	}
	@GetMapping("/delete/{id}")
	public String deleteArticle(@PathVariable Long id) {
		articleRepository.delete(id);
		return "redirect:/article/list";
	}
}