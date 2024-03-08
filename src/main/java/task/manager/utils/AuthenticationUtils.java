package task.manager.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;
import task.manager.security.UserDetailsImpl;

@UtilityClass
public class AuthenticationUtils {

    public static Long getLoggedUserId() {
        UserDetailsImpl loggedInUser = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loggedInUser.getId();
    }
}
