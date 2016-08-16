import lombok.Data;

/**
 * Validation class for user names
 * It can be expanded with more features such as regexp validation
 * Created by kerem on 8/16/16.
 */
@Data
public class ExpectedUserPayload {
    private String username;
    private String email;
    public boolean isValid() {
        return username != null && email != null; // etc.
    }
}
