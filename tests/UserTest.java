import ParserEngine.PostData.Post;
import ParserEngine.UserData.Address;
import ParserEngine.UserData.Company;
import ParserEngine.UserData.GeoLocation;
import ParserEngine.UserData.User;
import org.junit.Assert;
import org.junit.Test;

public class UserTest {
    @Test(expected = IllegalArgumentException.class)
    public void addPostTest(){
        GeoLocation geo = new GeoLocation(-37.3159,-34.4618);
        Address address = new Address("street","suite","city","zipcode",geo);
        Company company = new Company("name","catchPhrase","bs");
        User user = new User(1,"name","username","e@mail.com",address,
                "123456789","www.google.com",company);
        user.addPost(null);
    }
}
