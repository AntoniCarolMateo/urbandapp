package cat.udl.urbandapp;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.test.rule.ActivityTestRule;

import cat.udl.urbandapp.dao.UserDAOImpl;
import cat.udl.urbandapp.network.RetrofitClientInstance;
import retrofit2.Retrofit;

import static org.junit.Assert.*;
@RunWith(AndroidJUnit4.class)
public class RegisterActivityInstrumentationTest {
@Rule
public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);
    private UserDAOImpl userDAO;
    public Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();


    @Before
    public void setUp() throws Exception {
    mActivityRule.getActivity();
    userDAO = retrofit.create(UserDAOImpl.class);
    mActivityRule.getActivity().getUserViewModel();
    }

    @Test
    public void testInvalidPassword(){
        Espresso.onView(ViewMatchers.withId(R.id.userpassword)).perform(ViewActions.typeText("12345678"));
        Espresso.onView(ViewMatchers.withId(R.id.register)).perform(ViewActions.click());

        //Espresso.onView(ViewMatchers.withId(R.id.errorMessage))
                //.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                //.check(ViewAssertions.matches(ViewMatchers.withText("Bad password")));

    }

    @After
    public void tearDown() throws Exception {
    }

}