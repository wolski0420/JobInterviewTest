import ParserEngine.PostData.Post;
import ParserEngine.Processor;
import ParserEngine.UserData.*;
import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class ProcessorTest {
    private GeoLocation geo = new GeoLocation(-30.3159,-44.4618);
    private Address address = new Address("street","suite","city","zipcode",geo);
    private Company company = new Company("name","catchPhrase","bs");
    private User user = new User(1,"name","username","e@mail.com",address,
            "123456789","www.google.com",company);

    private GeoLocation geo2 = new GeoLocation(-34.4618,-47.8179);
    private Address address2 = new Address("street2","suite2","city2","zipcode2",geo2);
    private Company company2 = new Company("name2","catchPhrase2","bs2");
    private User user2 = new User(2,"name2","username2","e2@mail.com",address2,
            "123456789","www.google2.com",company2);

    private GeoLocation geo3 = new GeoLocation(-38.4618,-49.8179);
    private Address address3 = new Address("street3","suite3","city3","zipcode3",geo3);
    private Company company3 = new Company("name3","catchPhrase3","bs3");
    private User user3 = new User(3,"name3","username3","e3@mail.com",address3,
            "123456789","www.google3.com",company3);

    private Post post1 = new Post(1,1,"title","body");
    private Post post2 = new Post(1,2,"title2","body2");
    private Post post3 = new Post(2,3,"title","body3");
    private Post post4 = new Post(3,4,"title4","body4");

    private HashMap<UserID,User> users = new HashMap<>();
    private ArrayList<Post> posts = new ArrayList<>();

    /** tests **/

    @Test(expected = IllegalArgumentException.class)
    public void countUsersPostsTestException1(){
        Processor.countUserPosts(null,null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void countUsersPostsTestException2(){
        HashMap<UserID,User> users = new HashMap<>();
        Processor.countUserPosts(users,null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void countUsersPostsTestException3(){
        ArrayList<Post> posts = new ArrayList<>();
        Processor.countUserPosts(null,posts);
    }

    @Test
    public void countUsersPostsTest(){
        users.put(user.getID(),user);
        users.put(user2.getID(),user2);
        users.put(user3.getID(),user3);
        posts.add(post1);
        posts.add(post2);
        posts.add(post3);
        posts.add(post4);

        Processor.countUserPosts(users,posts);

        Assert.assertEquals(2,users.get(new UserID(1)).getNumberOfPosts());
        Assert.assertEquals(1,users.get(new UserID(2)).getNumberOfPosts());
        Assert.assertEquals(1,users.get(new UserID(3)).getNumberOfPosts());
        Assert.assertNotEquals(0,users.get(new UserID(1)).getNumberOfPosts());
        Assert.assertNotEquals(0,users.get(new UserID(2)).getNumberOfPosts());
        Assert.assertNotEquals(0,users.get(new UserID(3)).getNumberOfPosts());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findNonUniqueTitlesTestException(){
        Processor.findNonUniqueTitles(null);
    }

    @Test
    public void findNonUniqueTitlesTest(){
        posts.add(post1);
        posts.add(post2);
        posts.add(post3);
        posts.add(post4);

        ArrayList<String> sentences = Processor.findNonUniqueTitles(posts);

        Assert.assertEquals(1,sentences.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findClosestUsersTestException(){
        Processor.findClosestUsers(null);
    }

    @Test
    public void findClosestUsersTest(){
        users.put(user.getID(),user);
        users.put(user2.getID(),user2);
        users.put(user3.getID(),user3);

        ArrayList<Pair<User,User>> closestUsers = Processor.findClosestUsers(users);

        for(Pair pair:closestUsers){
            User userK = (User)pair.getKey();
            User userV = (User)pair.getValue();
            if(userK.getID().number == 1){
                Assert.assertEquals(userK,user);
                Assert.assertEquals(userV,user2);
            }
            else if(userK.getID().number == 2){
                Assert.assertEquals(userK,user2);
                Assert.assertEquals(userV,user3);
            }
            else if(userK.getID().number == 3){
                Assert.assertEquals(userK,user3);
                Assert.assertEquals(userV,user2);
            }
        }
    }
}
