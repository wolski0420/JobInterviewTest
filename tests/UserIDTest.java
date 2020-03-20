import ParserEngine.UserData.UserID;
import org.junit.Assert;
import org.junit.Test;

public class UserIDTest {
    @Test
    public void equalsTest(){
        UserID u1 = new UserID(1);
        UserID u2 = null;
        Assert.assertFalse(u1.equals(u2));

        u2 = new UserID(2);
        Assert.assertFalse(u1.equals(u2));

        u2 = new UserID(1);
        Assert.assertTrue(u1.equals(u2));

        Assert.assertTrue(u1.equals(u1));
    }
}
