package ParserEngine;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public class Parser {
    private static String readContent(Reader reader) throws IOException {
        StringBuilder builder = new StringBuilder();
        int tmp;
        while ((tmp = reader.read()) != -1) {
            builder.append((char) tmp);
        }
        return builder.toString();
    }

    private static JSONArray readJSONArrayFromURL(String url) throws IOException {
        InputStream stream = new URL(url).openStream();
        JSONArray array = null;
        try{
            InputStreamReader streamReader = new InputStreamReader(stream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(streamReader);
            String jsonText = readContent(reader);
            array = new JSONArray(jsonText);

        } catch(Exception e){
            e.printStackTrace();
        }
        return array;
    }

    public static ArrayList<User> parseUsers(String url){
        ArrayList<User> usersAL = new ArrayList<>();
        JSONArray usersJSON = null;

        try {
            usersJSON = Parser.readJSONArrayFromURL(url);
        } catch(Exception e){
            e.printStackTrace();
        }

        Iterator usersIterator = usersJSON.iterator();
        while(usersIterator.hasNext()){
            JSONObject userData = (JSONObject)usersIterator.next();

            JSONObject address = userData.getJSONObject("address");
            JSONObject company = userData.getJSONObject("company");
            JSONObject geo = address.getJSONObject("geo");

            GeoLocation readGeo = new GeoLocation(Double.parseDouble((String)geo.get("lat")), Double.parseDouble((String)geo.get("lng")));
            Address readAddress = new Address((String)address.get("street"),
                    (String)address.get("suite"), (String)address.get("city"),
                    (String)address.get("zipcode"),readGeo);
            Company readCompany = new Company((String)company.get("name"),
                    (String)company.get("catchPhrase"),(String)company.get("bs"));
            User readUser = new User((int)userData.get("id"),(String)userData.get("name"),
                    (String)userData.get("username"),(String)userData.get("email"), readAddress,
                    (String)userData.get("phone"),(String)userData.get("website"),readCompany);

            usersAL.add(readUser);
        }

        return usersAL;
    }

    public static ArrayList<Post> parsePosts(String url){
        ArrayList<Post> postsAL = new ArrayList<>();
        JSONArray postsJSON = null;

        try {
            postsJSON = Parser.readJSONArrayFromURL(url);
        } catch(Exception e){
            e.printStackTrace();
        }

        Iterator postsIterator = postsJSON.iterator();
        while(postsIterator.hasNext()){
            JSONObject post = (JSONObject)postsIterator.next();

            Post readPost = new Post((int)post.get("userId"),(int)post.get("id"),
                    (String)post.get("title"),(String)post.get("body"));

            postsAL.add(readPost);
        }

        return postsAL;
    }
}
