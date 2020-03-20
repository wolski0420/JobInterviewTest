package ParserEngine.UserData;

import java.util.Objects;

public class UserID {
    public int number;

    public UserID(int number){
        this.number = number;
    }

    // hashmap necessary methods

    @Override
    public int hashCode(){
        return Objects.hash(this.number);
    }

    @Override
    public boolean equals(Object other){
        if(other == null) return false;
        if(other == this) return true;
        if(!(other instanceof UserID)) return false;
        UserID secondID = (UserID) other;
        return this.number == secondID.number;
    }
}
