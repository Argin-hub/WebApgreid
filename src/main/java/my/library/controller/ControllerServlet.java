package my.library.controller;


import my.library.action.manager.Action;
import my.library.action.manager.ActionFactory;
import my.library.action.manager.ActionResult;
import my.library.action.manager.View;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ControllerServlet extends HttpServlet {

    private ActionFactory actionFactory;

    @Override
    public void init() throws ServletException {
        actionFactory = new ActionFactory();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Action action = actionFactory.getAction(req);
        ActionResult result = null;
        try {
            result = action.execute(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        View view = new View(req, resp);
        view.navigate(result);
    }
}