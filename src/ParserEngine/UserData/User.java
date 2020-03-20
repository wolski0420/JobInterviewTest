package ParserEngine.UserData;

import ParserEngine.PostData.Post;

import java.util.ArrayList;

public class User {
    private UserID id;
    private String name;
    private String username;
    private String email;
    private Address address;
    private String phone;
    private String website;
    private Company company;
    private ArrayList<Post> posts;
    private int postCounter;

    public User(int id, String name, String username, String email,
                Address address, String phone, String website, Company company) {
        this.id = new UserID(id);
        this.name = name;
        this.username = username;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.website = website;
        this.company = company;
        this.postCounter = 0;
        this.posts = new ArrayList<>();
    }

    public void addPost(Post postToAdd){
        this.postCounter++;
        this.posts.add(postToAdd);
    }

    public int getNumberOfPosts(){
        return this.postCounter;
    }

    public UserID getID(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public Address getAddress(){
        return this.address;
    }
}
