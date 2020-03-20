package ParserEngine;

import ParserEngine.PostData.Post;
import ParserEngine.UserData.User;
import ParserEngine.UserData.UserID;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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

    public static ArrayList<String> notUniquePostNames(ArrayList<Post> posts){
        HashMap<String, BigInteger> setOfTitles = new HashMap<>();
        for(Post post: posts){
            String title = post.getTitle();
            if(setOfTitles.containsKey(title)){
                BigInteger incrementedNumber = setOfTitles.get(title).add(new BigInteger("1"));
                setOfTitles.replace(title,incrementedNumber);
            }
            else{
                setOfTitles.put(title,new BigInteger("1"));
            }
        }

        ArrayList<String> titlesToReturn = new ArrayList<>();
        for(Post post: posts){
            String title = post.getTitle();
            if(setOfTitles.containsKey(title) &&
                    !setOfTitles.get(title).equals(new BigInteger("1"))){
                titlesToReturn.add(title);
            }
        }

        return titlesToReturn;
    }
}
