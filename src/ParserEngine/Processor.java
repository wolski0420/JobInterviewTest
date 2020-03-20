package ParserEngine;

import ParserEngine.PostData.Post;
import ParserEngine.UserData.User;
import ParserEngine.UserData.UserID;

import java.util.ArrayList;
import java.util.HashMap;

public class Processor {
    public static ArrayList<String> countUserPosts(HashMap<UserID,User> users, ArrayList<Post> posts){
        // exception for null

        for(Post post:posts){
            users.get(new UserID(post.getUserId())).addPost(post);
        }

        ArrayList<String> userPosts = new ArrayList<>();

        for(User user:users.values()){
            userPosts.add(user.getName() + " napisal(a) " + user.getNumberOfPosts() + " postow");
        }

        return userPosts;
    }
}
