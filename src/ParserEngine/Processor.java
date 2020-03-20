package ParserEngine;

import ParserEngine.PostData.Post;
import ParserEngine.UserData.GeoLocation;
import ParserEngine.UserData.User;
import ParserEngine.UserData.UserID;
import javafx.util.Pair;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class Processor {
    private static double countDistanceBetweenUsers(GeoLocation location1, GeoLocation location2){
        double latitude1 = location1.getLat();
        double longitude1 = location1.getLng();
        double latitude2 = location2.getLat();
        double longitude2 = location2.getLng();
        double diffLat = Math.toRadians(latitude2 - latitude1);
        double diffLng = Math.toRadians(longitude2 - longitude1);
        latitude1 = Math.toRadians(latitude1);
        latitude2 = Math.toRadians(latitude2);
        double earthRadius = 6371;

        double underRoot = Math.pow(Math.sin(diffLat/2),2) + Math.pow(Math.sin(diffLng/2),2) *
                Math.cos(latitude1) * Math.cos(latitude2);

        return 2 * earthRadius * Math.asin(Math.sqrt(underRoot));
    }

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

    public static ArrayList<String> findNonUniqueTitles(ArrayList<Post> posts){
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

    public static ArrayList<Pair<User,User>> findClosestUsers(HashMap<UserID,User> users){
        ArrayList<Pair<User,User>> closestUsersToReturn = new ArrayList<>();

        for(User user: users.values()){
            double minimumDistance = -1;
            User closestUser = null;

            for(User userSought: users.values()){
                if(!user.getID().equals(userSought.getID())){
                    double distance = Processor.countDistanceBetweenUsers(
                            user.getAddress().getGeo(),userSought.getAddress().getGeo());
                    if((minimumDistance == -1 && closestUser == null) || distance <= minimumDistance){
                        minimumDistance = distance;
                        closestUser = userSought;
                    }
                }
            }

            Pair<User,User> pairToAdd = new Pair<>(user,closestUser);
            closestUsersToReturn.add(pairToAdd);
        }

        return closestUsersToReturn;
    }
}
