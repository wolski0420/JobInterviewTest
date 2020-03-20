package ParserEngine;

import ParserEngine.PostData.Post;
import ParserEngine.UserData.User;
import ParserEngine.UserData.UserID;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String args[]){
        try{
            // below you can paste your links to websites with JSON data in the same format like here in this program
            HashMap<UserID,User> users = Parser.parseUsers("https://jsonplaceholder.typicode.com/users");
            ArrayList<Post> posts = Parser.parsePosts("https://jsonplaceholder.typicode.com/posts");

            System.out.println("\n===============USERS===============");

            for(User user:users.values()){
                System.out.println(user.getName());
            }

            System.out.println("\n===============POSTS===============");

            for(Post post:posts){
                System.out.println(post.getTitle());
            }

            System.out.println("\n=============USERPOSTS=============");

            ArrayList<String> userPosts = Processor.countUserPosts(users,posts);
            for(String sentence: userPosts){
                System.out.println(sentence);
            }

            System.out.println("\n=============NON-UNIQUE=================");

            ArrayList<String> notUniquePostTitle = Processor.findNonUniqueTitles(posts);
            System.out.println("Number of non-unique titles: " + notUniquePostTitle.size());
            for(String sentence: notUniquePostTitle){
                System.out.println(sentence);
            }

            System.out.println("\n==============CLOSEST============");

            ArrayList<Pair<User,User>> closestUsers = Processor.findClosestUsers(users);
            for(Pair pair: closestUsers){
                User first = (User) pair.getKey();
                User second = (User) pair.getValue();
                System.out.println(first.getName() + " has the closest: " + second.getName());
            }
        } catch (Exception e){
            System.out.println(e.toString());
            System.exit(0);
        }
    }
}
