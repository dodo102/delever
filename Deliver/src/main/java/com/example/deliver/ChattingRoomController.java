package com.example.deliver;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ChattingRoomController implements Initializable {

    @FXML
    private TableView<ChatVO> chatView;

    @FXML
    private TableColumn<ChatVO, String> nameCol;

    @FXML
    private TableColumn<ChatVO, String> chatContentsCol;

    ObservableList<ChatVO> chatList = FXCollections.observableArrayList();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDate1();
    }

    public void loadDate1() {
        select();
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        chatContentsCol.setCellValueFactory(new PropertyValueFactory<>("chatContents"));
    }

    public void select() {
        DBUtil db = new DBUtil();
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            chatList.clear();
            pstmt = conn.prepareStatement("SELECT name, chatContents FROM chat WHERE chattingRoomName = ");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                chatList.add(new ChatVO(rs.getString("name"), rs.getString("chatContents")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        chatView.setItems(chatList);
    }
}
