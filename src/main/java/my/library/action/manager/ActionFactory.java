package my.library.action.manager;


import my.library.action.post.DeleteProfileAction;
import my.library.action.post.LoginAction;
import my.library.action.post.RegisterAction;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ActionFactory {

    private Map<String, Action> actions;

    public void init() {
        actions = new HashMap<>();

        actions.put("GET/welcome", new ShowPageAction("welcome"));
        actions.put("GET/register", new ShowPageAction("register"));
        actions.put("GET/main", new ShowPageAction("main"));

        actions.put("POST/login", new LoginAction());
        actions.put("POST/register", new RegisterAction());
        actions.put("POST/deleteProfile", new DeleteProfileAction());
    }

    public Action getAction(HttpServletRequest request) {
        if (actions == null) {
            init();
        }
        return actions.get(request.getMethod() + request.getPathInfo());
    }
}
