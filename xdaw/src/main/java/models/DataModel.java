package models;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by kerem on 8/16/16.
 */
public class DataModel {
    private int nextId = 1;
    private Map posts = new HashMap<>();

    @Data
    class Post {
        private int id;
        private String title;
        private List categories;
        private String content;
    }

    public int createPost(String title, String content, List categories){
        int id = nextId++;
        Post post = new Post();
        post.setId(id);
        post.setTitle(title);
        post.setContent(content);
        post.setCategories(categories);
        posts.put(id, post);
        return id;
    }

    public List getAllPosts(){
        return (List) posts.keySet().stream().sorted().map((id) -> posts.get(id)).collect(Collectors.toList());
    }
}
