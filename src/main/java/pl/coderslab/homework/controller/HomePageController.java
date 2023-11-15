package pl.coderslab.homework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.homework.dao.ArticleDao;
import pl.coderslab.homework.model.Article;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomePageController {
    @Autowired
    ArticleDao articleDao;
    @GetMapping("/")
    @ResponseBody
    public String home(){
        List<Article> articles =  articleDao.findLast5();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return articles.stream()
                .map(a->String.format("<br>Tytul: %s <br>  Data dodania: %s <br> Zawartosc: %s <br><br>", a.getTitle(), a.getCreated().format(dateTimeFormatter), a.getContent()))
                .collect(Collectors.toList())
                .toString();

    }
}
