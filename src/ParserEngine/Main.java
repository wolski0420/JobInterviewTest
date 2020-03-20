package ParserEngine;

import ParserEngine.PostData.Post;
import ParserEngine.UserData.GeoLocation;
import ParserEngine.UserData.User;
import ParserEngine.UserData.UserID;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String args[]){
        HashMap<UserID,User> users = Parser.parseUsers("https://jsonplaceholder.typicode.com/users");
        ArrayList<Post> posts = Parser.parsePosts("https://jsonplaceholder.typicode.com/posts");

        for(User user:users.values()){
            System.out.println(user.getID().number);
        }

//        ArrayList<String> userPosts = Processor.countUserPosts(users,posts);

//        for(String sentence: userPosts){
//            System.out.println(sentence);
//        }

        ArrayList<String> notUniquePostTitle = Processor.findNonUniqueTitles(posts);
        System.out.println("Size: " + notUniquePostTitle.size());
        for(String sentence: notUniquePostTitle){
            System.out.println(sentence);
        }

        ArrayList<Pair<User,User>> closestUsers = Processor.findClosestUsers(users);
        for(Pair pair: closestUsers){
            User first = (User) pair.getKey();
            User second = (User) pair.getValue();
            System.out.println(first.getName() + " has the closest: " + second.getName());
        }
    }
}
