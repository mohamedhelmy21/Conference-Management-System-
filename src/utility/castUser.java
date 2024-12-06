package utility;

import domain.User;

public class castUser {
    public static <T extends User> T castUser(User user, Class<T> type) {
        if (type.isInstance(user)) {
            return type.cast(user);
        } else {
            throw new IllegalArgumentException("User is not of type: " + type.getSimpleName());
        }
    }
}
