package com.example.deliver;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

public class ChaController  {

    @FXML
    private Button changBtn;
    public void changeScene() {
           try {
                 Parent nextScene
                    = FXMLLoader.load(getClass().getResource("chabulid.fxml"));
                  Scene scene = new Scene(nextScene);

                  Stage primaryStage = (Stage) changBtn.getS
          } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
