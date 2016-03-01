package net.suweya.androidmvp.model;

public class LoginModelImpl implements ILoginModel {

    @Override
    public boolean login(String userName, String passWord) {
        return "suweya".equals(userName) && "123".equals(passWord);
    }
}
