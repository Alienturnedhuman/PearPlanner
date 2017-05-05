package Controller;

import Model.Account;
import Model.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.stage.Stage;

import javax.xml.soap.Text;

/**
 * Created by Zilvinas on 04/05/2017.
 */
public class AccountController
{
    @FXML private TextField account_no;
    @FXML private TextField salutation;
    @FXML private TextField full_name;
    @FXML private TextField email;
    @FXML private CheckBox fam_last;
    @FXML private Button submit;

    private Account account;
    private boolean success = false;

    public Account getAccount()
    {
        return account;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public void handleCreate()
    {
        this.fam_last.requestFocus();
    }

    public void handleChange()
    {
        if (Person.validSalutation(this.salutation.getText().trim()) &&
            Person.validName(this.full_name.getText().trim()) &&
            (this.email.getText().trim().isEmpty() || Person.validEmail(this.email.getText().trim())) &&
            !account_no.getText().trim().isEmpty())

            this.submit.setDisable(false);
    }

    public void validateSalutation()
    {
        if (!Person.validSalutation(this.salutation.getText().trim()))
        {
            this.salutation.setStyle("-fx-text-box-border:red;");
            this.submit.setDisable(true);
        }
        else
        {
            this.salutation.setStyle("");
            this.handleChange();
        }
    }

    public void validateName()
    {
        if (!Person.validName(this.full_name.getText().trim()))
        {
            this.full_name.setStyle("-fx-text-box-border:red;");
            this.submit.setDisable(true);
        }
        else
        {
            this.full_name.setStyle("");
            this.handleChange();
        }
    }

    public void validateEmail()
    {
        if (this.email.getText().trim().isEmpty() || Person.validEmail(this.email.getText().trim()))
        {
            this.email.setStyle("");
            this.handleChange();
        }
        else
        {
            this.email.setStyle("-fx-text-box-border:red;");
            this.submit.setDisable(true);
        }
    }

    public void validateNumber()
    {
        if (account_no.getText().trim().isEmpty())
        {
            this.account_no.setStyle("-fx-text-box-border:red;");
            this.submit.setDisable(true);
        }
        else
        {
            this.account_no.setStyle("");
            this.handleChange();
        }
    }

    public void handleSubmit()
    {
        Person p = new Person(this.salutation.getText().trim(), this.full_name.getText().trim(),
                this.fam_last.isSelected());

        if (!this.email.getText().trim().isEmpty())
            p.setEmail(this.email.getText().trim());

        this.account = new Account(p, this.account_no.getText().trim());
        this.success = true;
        Stage stage = (Stage) this.submit.getScene().getWindow();
        stage.close();
    }

    public void handleQuit()
    {
        Stage stage = (Stage) this.submit.getScene().getWindow();
        stage.close();
    }
}