package client.view;

import client.view.ViewHandler;
import client.viewModel.ProfileViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class ProfileViewController {
    @FXML
    private Label userName;
    @FXML
    private Label licenseNo;
    @FXML
    private Label actualFirstName;
    @FXML
    private Label actualSecondName;
    @FXML
    private TextField firstName;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField secondName;
    private ViewHandler viewHandler;
    private Region root;
    private ProfileViewModel viewModel;

    public ProfileViewController() {
        ///
    }

    public void init(ViewHandler viewHandler, ProfileViewModel viewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;

        userName.textProperty().bindBidirectional(viewModel.getUserNameProperty());
        licenseNo.textProperty().bindBidirectional(viewModel.getLicenseNoProperty());
        actualFirstName.textProperty().bindBidirectional(viewModel.getActualFirstNameProperty());
        actualSecondName.textProperty().bindBidirectional(viewModel.getActualSecondNameProperty());
        firstName.textProperty().bindBidirectional(viewModel.getFirstNameProperty());
        secondName.textProperty().bindBidirectional(viewModel.getSecondNameProperty());
        errorLabel.textProperty().bindBidirectional(viewModel.getErrorLabelProperty());

    }

    public Region getRoot() {
        return root;
    }

    public void reset() {
        try {
            viewModel.reset();
        } catch (RemoteException | SQLException ignored) {

        }
    }

    @FXML
    private void onChange() {
        viewModel.changeNames();
    }

    @FXML
    private void onChangeYourCar()
    {
        viewHandler.openView("ChangeCar");
    }

    @FXML
    private void onParkingLot()
    {
        viewHandler.openView("ParkingLotView");
    }

    @FXML
    private void onSupport()
    {
        viewHandler.openView("SupportView");
    }
}
