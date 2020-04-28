package cat.udl.urbandapp.utils;

import org.junit.Test;

import java.io.IOException;

import cat.udl.urbandapp.viewmodel.UserViewModel;

import static org.junit.Assert.*;

public class LoginUtilsTest {

    @Test
    public void isValidPassword() {
        String password = "Mmmmmmmm88";
        assertTrue("La contrasenya es vàlida" ,LoginUtils.isValidPassword(password));

    }

    @Test
    public void isNotValidPassword() throws IOException {
        String password = "ahir1234";
        assertFalse("La contrasenya no és vàlida",LoginUtils.isValidPassword(password));
    }

    @Test
    public void isValidPhone() throws IOException {
        String phone = "607689879";
        assertTrue("f", LoginUtils.isValidPhone(phone));
    }

    @Test
    public void isNotValidPhone() throws IOException{
        String phone = "607689";
        assertFalse("", LoginUtils.isValidPhone(phone));
    }
    @Test
    public void Check_EnviemApi_Valid() throws IOException {
        UserViewModel viewModel;


    }

}