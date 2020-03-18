package cat.udl.urbandapp.services;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import cat.udl.urbandapp.dao.IUserDAO;
import cat.udl.urbandapp.dao.UserDAOImpl;

public class UserServiceImpl implements UserServiceI {

    private IUserDAO userDAO = new UserDAOImpl();


    @Override
    public void registerUser(JsonObject userJson) {
        userDAO.registerUser(userJson);
    }
}
