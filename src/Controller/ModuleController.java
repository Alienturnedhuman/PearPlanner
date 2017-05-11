package Controller;

import Model.Module;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Žilvinas on 11/05/2017.
 */
public class ModuleController implements Initializable
{
    private Module module;

    // Labels:
    @FXML private Label title;
    @FXML private Label organiser;
    @FXML private Label details;
    @FXML private Label assignments;
    @FXML private Label events;

    /**
     * Close this window
     */
    public void handleClose()
    {
        Stage stage = (Stage) this.title.getScene().getWindow();
        stage.close();
    }

    /**
     * Constructor for the ModuleController
     */
    public ModuleController(Module module)
    {
        this.module = module;
    }

    @Override public void initialize(URL location, ResourceBundle resources)
    {
        this.title.setText(this.module.getModuleCode() + " " + this.module.getName());
        this.organiser.setText("Organised by: \n" + this.module.getOrganiser().getFullName());
        this.details.setText(this.module.getDetails().getAsString());

        this.assignments.setText(this.module.getNoOfAssignments() + " assignments.");
        this.events.setText(this.module.getTimetable().size() + " events.");
    }
}