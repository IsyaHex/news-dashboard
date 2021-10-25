package uz.task.domain;

import uz.task.constants.NewsStateEnum;

import java.util.ArrayList;
import java.util.List;

public class NewsFactory {

    public static News firstNews() {
        News news = new News();
        news.setId(1L);
        news.setDescription("Lorem ipsum test description One");
        news.setState(NewsStateEnum.UNVERIFIED.getValue());
        news.setUser(UserFactory.firstUser());
        return news;
    }

    public static News secondNews() {
        News news = new News();
        news.setId(2L);
        news.setDescription("Lorem ipsum test description Two");
        news.setState(NewsStateEnum.UNVERIFIED.getValue());
        news.setUser(UserFactory.secondUser());
        return news;
    }

    public static List<News> getNewsList() {
        List<News> list = new ArrayList<>();
        list.add(firstNews());
        list.add(secondNews());
        return list;
    }
}
