package ParserEngine;

import org.json.JSONArray;

import java.util.ArrayList;

public class Main {
    public static void main(String args[]){
        ArrayList<User> users = Parser.parseUsers("https://jsonplaceholder.typicode.com/users");
        ArrayList<Post> posts = Parser.parsePosts("https://jsonplaceholder.typicode.com/posts");
    }
}
