package com.example.a3;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class NationalNutrientDb {
    private static final String API_KEY = "pBld5N6rfugHdQtuuqxW17OcyDgGL9gui3QvVqYY";
    //private static final String SEARCH_ID_cx = "009082434492505824041:vjdxlaqlvvg";

    public static String searchFood(String keyword) {
        keyword = keyword.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter = "";
        try {
            url = new URL("https://api.nal.usda.gov/ndb/search/?format=json&q=" + keyword + "&sort=n&max=25&offset=0&api_key=" + API_KEY );
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

    public static String getFoodId(String result) {
        String foodId = null;
        try {

                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONObject("list").getJSONArray("item");
                if (jsonArray != null && jsonArray.length() > 0) {
                    foodId = jsonArray.getJSONObject(0).getString("ndbno");


            }
        } catch (Exception e) {
            e.printStackTrace();
            foodId = "0";
        }
        return foodId;
    }

    public static String searchFoodNutrition(String keyword) {
        keyword = keyword.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter = "";

        try {
            url = new URL("https://api.nal.usda.gov/ndb/reports/?ndbno=" + keyword + "&type=f&format=json&api_key=" + API_KEY );
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


    public static String getFoodCalorieAmount(String result) {
        String  foodCalorieAmount = null;
        try {


            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONObject("report").getJSONObject("food").getJSONArray("nutrients");
            if (jsonArray != null && jsonArray.length() > 0) {
                foodCalorieAmount = jsonArray.getJSONObject(0).getJSONArray("measures").getJSONObject(0).getString("value");

            }
        } catch (Exception e) {
            e.printStackTrace();
            foodCalorieAmount = "0";
        }
        return  foodCalorieAmount;
    }

    public static String getFoodServingUnit(String result) {
        String  foodServingUnit = null;
        try {


            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONObject("report").getJSONObject("food").getJSONArray("nutrients");
            if (jsonArray != null && jsonArray.length() > 0) {
                foodServingUnit = jsonArray.getJSONObject(0).getJSONArray("measures").getJSONObject(0).getString("label");

            }
        } catch (Exception e) {
            e.printStackTrace();
            foodServingUnit = "NO INFO FOUND";
        }
        return  foodServingUnit;
    }

    public static String getFoodFatAmount(String result) {
        String  foodFatAmount = null;
        try {


            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONObject("report").getJSONObject("food").getJSONArray("nutrients");
            if (jsonArray != null && jsonArray.length() > 0) {
                foodFatAmount = jsonArray.getJSONObject(2).getJSONArray("measures").getJSONObject(0).getString("value");

            }
        } catch (Exception e) {
            e.printStackTrace();
            foodFatAmount = "0";
        }
        return  foodFatAmount;
    }

    public static String getFoodStdServingAmount(String result) {
        String  foodFoodStdServingAmount = null;
        try {


            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONObject("report").getJSONObject("food").getJSONArray("nutrients");
            if (jsonArray != null && jsonArray.length() > 0) {
                foodFoodStdServingAmount = jsonArray.getJSONObject(0).getJSONArray("measures").getJSONObject(0).getString("qty");

            }
        } catch (Exception e) {
            e.printStackTrace();
            foodFoodStdServingAmount = "0";
        }
        return  foodFoodStdServingAmount;
    }

}
