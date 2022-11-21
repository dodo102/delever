package com.example.deliver;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ChattingRoomController implements Initializable {

    @FXML
    private Label chattingRoom;

    @FXML
    private TextField chatInput;

    @FXML
    private Button inviteGO;

    @FXML
    private TableView<ChatVO> chatView;

    @FXML
    private TableColumn<ChatVO, String> nameCol;

    @FXML
    private TableColumn<ChatVO, String> chatContentsCol;

    @FXML
    private TableColumn<ChatVO, Time> timeCol;

    LocalTime now = LocalTime.now();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH시 mm분 ss초");

    String formatedNow = now.format(formatter);

    ObservableList<ChatVO> chatList = FXCollections.observableArrayList();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDate1();
    }

    public void loadDate1() {
        select();
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        chatContentsCol.setCellValueFactory(new PropertyValueFactory<>("chatContent"));
        timeCol.setCellValueFactory((new PropertyValueFactory<>("chatTime")));
    }

    public void select() {
        DBUtil db = new DBUtil();
        Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            chatList.clear();
            pstmt = conn.prepareStatement("SELECT name, chatContents, chatTime FROM chat WHERE chattingRoomName = ?");
            pstmt.setString(1, chattingRoom.getText());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                chatList.add(new ChatVO(rs.getString("name"), rs.getString("chatContent"), rs.getTime("chatTime")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        chatView.setItems(chatList);
    }

    public void send() {
        DBUtil db = new DBUtil();
        Connection conn = db.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement("INSERT INTO chat VALUES (?,?,?,?,?)");
            pstmt.setString(1, chattingRoom.getText());
            // pstmt.setString(2, id);
            // pstmt.setString(3, name);
            pstmt.setString(4, chatInput.getText());
            pstmt.setTime(5, Time.valueOf(formatedNow));
            pstmt.executeUpdate();
            select();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void inviteFriendGO() {
        try {
            Parent nextScene = FXMLLoader.load(getClass().getResource("inviteFriend.fxml"));
            Scene scene = new Scene(nextScene);
            Stage primaryStage = (Stage) inviteGO.getScene().getWindow();
            primaryStage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void search() {
        String clientId = "3vbAs3kmMdj7Lr7HZNHK"; //애플리케이션 클라이언트 아이디
        String clientSecret = "cSen26uwDO"; //애플리케이션 클라이언트 시크릿


        String text = null;
        try {
            text = URLEncoder.encode("그린팩토리", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패", e);
        }


        String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text;    // JSON 결과
        //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // XML 결과


        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL, requestHeaders);


        System.out.println(responseBody);
    }


    private static String get(String apiUrl, Map<String, String> requestHeaders) {
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }


            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 오류 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }


    private static HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }


    private static String readBody(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }
}
