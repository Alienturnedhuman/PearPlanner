package View;


import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.junit.Test;


import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.hasText;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;


/**
 * PearPlanner
 * Created by Team BRONZE on 08/05/2017
 */
public class CreateAccountUITest extends FXBase {


    @Test
    public void testTextField () {
        TextField t = find("#salutation");
        TextField t1 = find("#full_name");
        TextField t3 = find("#account_no");
        TextField t4 = find("#email");
        Button b1 = find("#submit");

        clickOn(t).write("Mr");
        clickOn(t1).write("Andrei Odintsov");
        clickOn(t3).write("100125464");
        clickOn(t4).write("a1covker@gmail.com");


        verifyThat(b1, isEnabled());

    }




    @Test
    public void testTextFieldRegex () {
        TextField t = find("#salutation");
        TextField t1 = find("#full_name");
        TextField t3 = find("#account_no");
        TextField t4 = find("#email");
        Button b1 = find("#submit");


        clickOn(t).write("Mr");
        clickOn(t1).write("Andrei Odintsov");
        clickOn(t3).write("100125464");
        clickOn(t4).write("somethingnotemail");


        verifyThat(b1, isDisabled());


    }

}