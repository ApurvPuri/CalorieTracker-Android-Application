package com.example.a3;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MapQuest {
    private static final String API_KEY = "14X4Tku7GGZwNL9H4pGGrfTcadSH00SG";

    public static String searchParks(String keyword) {
        keyword = keyword.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";

        try {
            url = new URL("https://www.mapquestapi.com/search/v2/radius?origin=" + keyword + "&radius=5.0&maxMatches=10&ambiguities=allow&hostedData=mqap.ntpois|group_sic_code=?|799602&outFormat=json&key=" + API_KEY);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return textResult;
    }



}
