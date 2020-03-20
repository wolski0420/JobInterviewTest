package ParserEngine;

import ParserEngine.PostData.Post;
import ParserEngine.UserData.GeoLocation;
import ParserEngine.UserData.User;
import ParserEngine.UserData.UserID;
import javafx.util.Pair;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

/** this class is created to process data **/
public class Processor {

    /** this is haversine formula which calculate distance on spherical earth (it ignores ellipsoidal effects) **/
    private static double countDistanceBetweenUsers(GeoLocation location1, GeoLocation location2) throws IllegalArgumentException{
        if(location1 == null || location2 == null) throw new IllegalArgumentException("One of the given argument is null!");

        // just preparing all required values
        double latitude1 = location1.getLat();
        double longitude1 = location1.getLng();
        double latitude2 = location2.getLat();
        double longitude2 = location2.getLng();
        double diffLat = Math.toRadians(latitude2 - latitude1);
        double diffLng = Math.toRadians(longitude2 - longitude1);
        latitude1 = Math.toRadians(latitude1);
        latitude2 = Math.toRadians(latitude2);
        double earthRadius = 6371;

        // counting the value under square root
        double underRoot = Math.pow(Math.sin(diffLat/2),2) + Math.pow(Math.sin(diffLng/2),2) *
                Math.cos(latitude1) * Math.cos(latitude2);

        if(underRoot < 0) throw new IllegalArgumentException("Value under root cannot be negative!");

        // returning right value
        return 2 * earthRadius * Math.asin(Math.sqrt(underRoot));
    }

    /** this method connect every post to author(user) by userID and count how many posts this specific user created **/
    public static ArrayList<String> countUserPosts(HashMap<UserID,User> users, ArrayList<Post> posts) throws IllegalArgumentException{
        if(users == null || posts == null) throw new IllegalArgumentException("One of the given argument is null!");

        // getting posts and their authors
        for(Post post:posts){
            User userToAddPost = users.get(new UserID(post.getUserId()));
            if(userToAddPost != null){
                try{
                    userToAddPost.addPost(post);
                }
                catch(IllegalArgumentException e){
                    System.out.println(e.toString());
                    System.exit(0);
                }
            }
        }

        // inserting information about authors and number of posts to array
        ArrayList<String> userPosts = new ArrayList<>();
        for(User user:users.values()){
            userPosts.add(user.getName() + " napisal(a) " + user.getNumberOfPosts() + " postow");
        }

        return userPosts;
    }

    /** this method looks for non-unique titles of posts and returns them **/
    public static ArrayList<String> findNonUniqueTitles(ArrayList<Post> posts) throws IllegalArgumentException{
        if(posts == null) throw new IllegalArgumentException("Given argument is null!");

        // getting titles of posts and counting number of them (hashmap has title as key and number of occurences as value)
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

        // inserting to array only these posts which have non-unique titles (number of occurences > 1)
        ArrayList<String> titlesToReturn = new ArrayList<>();
        for(Post post: posts){
            String title = post.getTitle();
            if(setOfTitles.containsKey(title) &&
                    !setOfTitles.get(title).equals(new BigInteger("1"))){
                titlesToReturn.add(title);
                // If I got next post with the same title, I would add it to returnList,
                // but I don't want to have this title several times so that's why I had
                // to write this line below - when I'm adding title, because counter > 1,
                // I set this counter to 1 what implicates that condition from above isn't executed
                setOfTitles.replace(title,new BigInteger("1"));
            }
        }

        return titlesToReturn;
    }

    /** this method looks for every user the closest user (the least distance) **/
    public static ArrayList<Pair<User,User>> findClosestUsers(HashMap<UserID,User> users){
        if(users == null) throw new IllegalArgumentException("Given argument is null!");

        ArrayList<Pair<User,User>> closestUsersToReturn = new ArrayList<>();

        // for every user...
        for(User user: users.values()){
            double minimumDistance = -1;
            User closestUser = null;

            // find user
            for(User userSought: users.values()){
                // who isn't himself/herself
                if(!user.getID().equals(userSought.getID())){
                    try{
                        // and has the least distance to current user
                        double distance = Processor.countDistanceBetweenUsers(                  // can throw exception
                                user.getAddress().getGeo(),userSought.getAddress().getGeo());
                        if((minimumDistance == -1 && closestUser == null) || distance <= minimumDistance){
                            minimumDistance = distance;
                            closestUser = userSought;
                        }
                    }
                    catch(IllegalArgumentException e){
                        System.out.println(e.toString());
                        System.exit(1);
                    }
                }
            }

            // second from the pair is the closest to the first one from the pair
            Pair<User,User> pairToAdd = new Pair<>(user,closestUser);
            closestUsersToReturn.add(pairToAdd);
        }

        return closestUsersToReturn;
    }
}
