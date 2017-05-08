package View;


import javafx.scene.control.*;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.util.WaitForAsyncUtils;


import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.hasText;


/**
 * PearPlanner
 * Created by Team BRONZE on 08/05/2017
 */
public class CreateAccountUITest extends TestFXBase {

final String CREATE_ACCOUT_LABEL = "#createAccountLabel";
final String FULL_NAME = "#salutation";




    @Test
    public void testLabel () {
       clickOn(FULL_NAME);
       clickOn(CREATE_ACCOUT_LABEL);
       //System.out.println("clicked!!!!");
       verifyThat(CREATE_ACCOUT_LABEL, NodeMatchers.isNotNull());

    }

}