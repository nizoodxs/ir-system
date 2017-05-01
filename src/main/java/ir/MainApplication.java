/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ir;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;

/**
 *
 * @author nischal
 */
 
public final class MainApplication extends Application {
 
    private final Desktop desktop = Desktop.getDesktop();
    public Extractor type = new Extractor();
 
    @Override
    public void start(final Stage stage) {
        stage.setTitle("E discovery application");
 
        final FileChooser fileChooser = new FileChooser();
 
        final Button openButton = new Button("Choose a file...");
        final Button openMultipleButton = new Button("Choose multiplefiles...");
        final TextArea actiontarget = new TextArea();
        final TextArea metadataTextArea = new TextArea();
        metadataTextArea.setEditable(false);
        actiontarget.setEditable(false);
 
        openButton.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    File file = fileChooser.showOpenDialog(stage);
                    if (file != null) {
                        try {
                            String fileType = type.fileType(file);
//                            c=content , m=metadata , 
                            String fileContent = type.parseFile(file);
                            HashMap<String, String> metadataMap = new HashMap<>();
                            metadataMap = type.getMetadata(file);
                            actiontarget.setText(fileType + ": \n" + fileContent);
                            Iterator it = metadataMap.entrySet().iterator();
                            StringBuilder completeMeta = new StringBuilder();
                            while (it.hasNext()) {
                                Map.Entry pair = (Map.Entry)it.next();
                                completeMeta.append("\n").append(pair.getKey()).append(" = ").append(pair.getValue());
//                                System.out.println(pair.getKey() + " = " + pair.getValue());
                                it.remove(); // avoids a ConcurrentModificationException
                            }
                            metadataTextArea.setText(completeMeta.toString());
//                            System.out.println(fileType);
//                        openFile(file);
                        } catch (Exception ex) {
                            Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
 
       /* openMultipleButton.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    List<File> list =
                        fileChooser.showOpenMultipleDialog(stage);
//                    if (list != null) {
//                        for (File file : list) {
//                            openFile(file);
//                        }
//                    }
               
            });
        */
 
 
        final GridPane inputGridPane = new GridPane();
        inputGridPane.setPrefSize(800,600);
 
        GridPane.setConstraints(openButton, 5, 5);
        GridPane.setConstraints(openMultipleButton, 6, 5);
        GridPane.setConstraints(actiontarget, 5, 10);
        GridPane.setConstraints(metadataTextArea, 5, 15);
        inputGridPane.setHgap(6);
        inputGridPane.setVgap(6);
        inputGridPane.getChildren().addAll(openButton, openMultipleButton,actiontarget,metadataTextArea);
 
        final Pane rootGroup = new VBox(12);
        rootGroup.getChildren().addAll(inputGridPane);
        rootGroup.setPadding(new Insets(12, 12, 12, 12));
 
        stage.setScene(new Scene(rootGroup));
        stage.show();
    }
 
    public static void main(String[] args) {
        Application.launch(args);
    }

    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(MainApplication.class.getName()).log(
                    Level.SEVERE, null, ex
                );
        }
    }

}