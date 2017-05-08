package View;


import org.junit.Test;


import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.hasText;


/**
 * PearPlanner
 * Created by Team BRONZE on 08/05/2017
 */
public class CreateAccountUITest extends FXBase {

final String CREATE_ACCOUT_LABEL = "#createAccountLabel";




    @Test
    public void testLabel () {
       verifyThat(CREATE_ACCOUT_LABEL, hasText("Create Account"));

    }

}