package ParserEngine;

import ParserEngine.PostData.Post;
import ParserEngine.UserData.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/** this class is created to parse data in JSON from URL to needed objects **/
public class Parser {

    /** it is used only to read the content on web page **/
    private static String readContent(Reader reader) throws IOException {
        StringBuilder builder = new StringBuilder();
        int tmp;
        while ((tmp = reader.read()) != -1) {
            builder.append((char) tmp);
        }
        return builder.toString();
    }

    /** it gives us array of objects from given URL in JSON format **/
    private static JSONArray readJSONArrayFromURL(String url) throws IOException, IllegalArgumentException {
        if(url == null) throw new IllegalArgumentException("Given URL is null!");

        InputStream stream = new URL(url).openStream();
        InputStreamReader streamReader = new InputStreamReader(stream, Charset.forName("UTF-8"));
        BufferedReader reader = new BufferedReader(streamReader);
        String jsonText = readContent(reader);

        return new JSONArray(jsonText);
    }

    /** this method gets url to array of objects (users)
        and returns hashmap of users with ID as key **/
    public static HashMap<UserID, User> parseUsers(String url){
        HashMap<UserID,User> usersAL = new HashMap<>();
        JSONArray usersJSON = null;

        // trying to parse
        try {
            usersJSON = Parser.readJSONArrayFromURL(url);   // can throw exception
        }
        catch(Exception e){
            System.out.println(e.toString());
            System.exit(1);
        }

        // iterating on all users
        Iterator usersIterator = usersJSON.iterator();
        while(usersIterator.hasNext()){
            // getting all information about user in JSONObject
            JSONObject userData = (JSONObject)usersIterator.next();
            JSONObject address = userData.getJSONObject("address");
            JSONObject company = userData.getJSONObject("company");
            JSONObject geo = address.getJSONObject("geo");

            // inserting geo information for address
            GeoLocation readGeo = new GeoLocation(Double.parseDouble((String)geo.get("lat")), Double.parseDouble((String)geo.get("lng")));

            // inserting address information for user
            Address readAddress = new Address((String)address.get("street"),
                    (String)address.get("suite"), (String)address.get("city"),
                    (String)address.get("zipcode"),readGeo);

            // inserting company information for user
            Company readCompany = new Company((String)company.get("name"),
                    (String)company.get("catchPhrase"),(String)company.get("bs"));

            // inserting other data about user
            User readUser = new User((int)userData.get("id"),(String)userData.get("name"),
                    (String)userData.get("username"),(String)userData.get("email"), readAddress,
                    (String)userData.get("phone"),(String)userData.get("website"),readCompany);

            // inserting user to array
            usersAL.put(readUser.getID(),readUser);
        }

        return usersAL;
    }

    /** this method gets url to array of objects (posts)
        and returns an arraylist of posts **/
    public static ArrayList<Post> parsePosts(String url){
        ArrayList<Post> postsAL = new ArrayList<>();
        JSONArray postsJSON = null;

        // trying to parse
        try {
            postsJSON = Parser.readJSONArrayFromURL(url);   // can throw exception
        }
        catch(Exception e){
            System.out.println(e.toString());
            System.exit(1);
        }

        // iterating on all posts
        Iterator postsIterator = postsJSON.iterator();
        while(postsIterator.hasNext()){
            // getting all information about post
            JSONObject post = (JSONObject)postsIterator.next();

            // inserting data about post
            Post readPost = new Post((int)post.get("userId"),(int)post.get("id"),
                    (String)post.get("title"),(String)post.get("body"));

            // inserting to array
            postsAL.add(readPost);
        }

        return postsAL;
    }
}
