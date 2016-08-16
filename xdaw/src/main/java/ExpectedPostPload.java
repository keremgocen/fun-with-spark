import lombok.Data;

import java.util.List;

/**
 * Created by kerem on 8/16/16.
 */
@Data
public class ExpectedPostPload {
    private String title;
    private List categories;
    private String content;

    public boolean isValid() {
        return title != null && !title.isEmpty() && !categories.isEmpty();
    }
}
