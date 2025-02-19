package tn.hive.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import tn.hive.entities.Equipe;
import tn.hive.entities.Match;
import tn.hive.entities.Terrain;
import tn.hive.entities.Tournoi;
import tn.hive.services.TournoiService;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ModifierTournoiController {

    @FXML
    private Label tournoi_id_label;

    @FXML
    private DatePicker date_tournoi;

    @FXML
    private ComboBox<String> liste_type;

    @FXML
    private TextField nom_tournoi;

    @FXML
    private TextField trounoi_description;

    private int id_tournoi;

    private TournoiService tournoiService = new TournoiService();

    @FXML
    void annulerAjout(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheTournois.fxml"));
        try {
            Parent parent = loader.load();
            date_tournoi.getScene().setRoot(parent);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void confirmerModification(ActionEvent event) {
        try {
            Tournoi tournoi = new Tournoi(nom_tournoi.getText(), liste_type.getValue(), Date.valueOf(date_tournoi.getValue()), trounoi_description.getText());
            tournoiService.updateEntity(id_tournoi, tournoi);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Tournoi modifiée avec succès");
            alert.setContentText(tournoi.toString());
            alert.show();
            annulerAjout(event);
        }catch ( Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Match modifiée avec succès");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    public void setId_tournoi(int id_tournoi) {
        this.id_tournoi = id_tournoi;
        refreshModifierTournoi();
    }

    public void refreshModifierTournoi(){
        Tournoi tournoi = tournoiService.getTournoiById(id_tournoi);
        //titre
        tournoi_id_label.setText(String.valueOf(id_tournoi));

        //nom
        nom_tournoi.setText(tournoi.getNom_tournoi());


        //date
        date_tournoi.setValue(tournoi.getDate_tournoi().toLocalDate());

        //type
        liste_type.setItems(FXCollections.observableArrayList("Football", "Basketball", "Volley-ball", "Baseball", "Rugby", "Handball","Tennis", "Badminton", "Tennis de table", "Squash","Golf", "Bowling", "Bocce", "Croquet","Water-polo", "Dodgeball", "Sepak Takraw", "Lacrosse"));
        liste_type.setValue(tournoi.getType_tournoi());

        //description
        trounoi_description.setText(tournoi.getDescription_tournoi());
    }
}
