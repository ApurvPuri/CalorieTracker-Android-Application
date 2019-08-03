package com.example.a3;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class restClient {
    private static final String BASE_URL = "http://10.0.2.2:8080/Assignment1/webresources/";

    public static String authenticate(String userName, String passwordHash) {
        final String methodPath = "a1.credentials/authenticate/" + userName + "/" + passwordHash; //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = ""; //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath); //open the connection
            conn = (HttpURLConnection) url.openConnection(); //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to GET
            conn.setRequestMethod("GET"); //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json"); //Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the password stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String findUserId(String userName) {
        final String methodPath = "a1.credentials/findUserId/" + userName; //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = ""; //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath); //open the connection
            conn = (HttpURLConnection) url.openConnection(); //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to GET
            conn.setRequestMethod("GET"); //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json"); //Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the password stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String findFirstName(String userId) {
        final String methodPath = "a1.users/findFirstName/" + userId; //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = ""; //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath); //open the connection
            conn = (HttpURLConnection) url.openConnection(); //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to GET
            conn.setRequestMethod("GET"); //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json"); //Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the password stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String findAddress(String userId) {
        final String methodPath = "a1.users/findAddress/" + userId; //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = ""; //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath); //open the connection
            conn = (HttpURLConnection) url.openConnection(); //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to GET
            conn.setRequestMethod("GET"); //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json"); //Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the password stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }


    public static void createNewUser(NewUser newUser) { //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath = "a1.users/";
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").create();
            String jsonUser = gson.toJson(newUser);


            url = new URL(BASE_URL + methodPath); //open the connection
            conn = (HttpURLConnection) url.openConnection(); //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to POST
            conn.setRequestMethod("POST"); //set the output to true
            conn.setDoOutput(true); //set length of the data you want to send
            conn.setFixedLengthStreamingMode(jsonUser.getBytes().length); //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json"); //Send the POST out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(jsonUser);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static String findFoodId(String foodName) {
        final String methodPath = "a1.food/findIdByName/" + foodName; //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = ""; //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath); //open the connection
            conn = (HttpURLConnection) url.openConnection(); //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to GET
            conn.setRequestMethod("GET"); //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json"); //Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the password stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static void createNewConsumption(NewConsumption newConsumption) { //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath = "a1.consumption/";

        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").create();
            String jsonConsumption = gson.toJson(newConsumption);


            url = new URL(BASE_URL + methodPath); //open the connection
            conn = (HttpURLConnection) url.openConnection(); //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to POST
            conn.setRequestMethod("POST"); //set the output to true
            conn.setDoOutput(true); //set length of the data you want to send
            conn.setFixedLengthStreamingMode(jsonConsumption.getBytes().length); //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json"); //Send the POST out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(jsonConsumption);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static void createNewFood(NewFood newFood) { //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath = "a1.food/";
        try {


            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").create();
            String jsonFood = gson.toJson(newFood);


            url = new URL(BASE_URL + methodPath); //open the connection
            conn = (HttpURLConnection) url.openConnection(); //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to POST
            conn.setRequestMethod("POST"); //set the output to true
            conn.setDoOutput(true); //set length of the data you want to send
            conn.setFixedLengthStreamingMode(jsonFood.getBytes().length); //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json"); //Send the POST out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(jsonFood);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static void createNewCredential(NewCredentials newCredentials) { //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath = "a1.credentials/";
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").create();
            String jsonCredential = gson.toJson(newCredentials);
            url = new URL(BASE_URL + methodPath); //open the connection
            conn = (HttpURLConnection) url.openConnection(); //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to POST
            conn.setRequestMethod("POST"); //set the output to true
            conn.setDoOutput(true); //set length of the data you want to send
            conn.setFixedLengthStreamingMode(jsonCredential.getBytes().length); //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json"); //Send the POST out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(jsonCredential);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static String findAllCategories() {
        final String methodPath = "a1.food/findAllCategories/" ; //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = ""; //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath); //open the connection
            conn = (HttpURLConnection) url.openConnection(); //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to GET
            conn.setRequestMethod("GET"); //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json"); //Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the password stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String findNamesByCategory(String category) {
        final String methodPath = "a1.food/findNamesByCategory/" + category; //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = ""; //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath); //open the connection
            conn = (HttpURLConnection) url.openConnection(); //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to GET
            conn.setRequestMethod("GET"); //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json"); //Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the password stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String findReport(Integer userId, String date) { //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath = "a1.report/findGetReport/" + userId + "/" + date;

        String textResult = ""; //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath); //open the connection
            conn = (HttpURLConnection) url.openConnection(); //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to GET
            conn.setRequestMethod("GET"); //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json"); //Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the password stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String getTotalCalConsumed(String result) {
        String  totalCalConsumed = null;
        try {


            JSONArray jsonArray = new JSONArray(result);
            if (jsonArray != null && jsonArray.length() > 0) {
                totalCalConsumed = jsonArray.getJSONObject(0).getString("Total calories consumed");

            }
        } catch (Exception e) {
            e.printStackTrace();
            totalCalConsumed = "0";
        }
        return  totalCalConsumed;
    }

    public static String getTotalCalBurned(String result) {
        String  totalCalBurned = null;
        try {


            JSONArray jsonArray = new JSONArray(result);
            if (jsonArray != null && jsonArray.length() > 0) {
                totalCalBurned = jsonArray.getJSONObject(0).getString("Total calories burned");

            }
        } catch (Exception e) {
            e.printStackTrace();
            totalCalBurned = "0";
        }
        return  totalCalBurned;
    }

    public static String getRemainingCal(String result) {
        String  remainingCal = null;
        try {


            JSONArray jsonArray = new JSONArray(result);
            if (jsonArray != null && jsonArray.length() > 0) {
                remainingCal = jsonArray.getJSONObject(0).getString("Remaining calories");

            }
        } catch (Exception e) {
            e.printStackTrace();
            remainingCal = "0";
        }
        return  remainingCal;
    }


    public static String findReportPerDay(Integer userId, String dateStart, String dateEnd) { //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath = "a1.report/findGetReportForADurationPerDay/" + userId + "/" + dateStart + "/" + dateEnd;

        String textResult = ""; //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath); //open the connection
            conn = (HttpURLConnection) url.openConnection(); //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to GET
            conn.setRequestMethod("GET"); //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json"); //Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the password stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }


    public static String findCalConsumed(Integer userId, String date) {
        final String methodPath = "a1.consumption/findCalconsumedOnADay/" + userId + "/" + date; //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = ""; //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath); //open the connection
            conn = (HttpURLConnection) url.openConnection(); //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to GET
            conn.setRequestMethod("GET"); //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json"); //Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the password stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String findCalBurnedPerStep(Integer userId) {
        final String methodPath = "a1.users/findCalBurnedPerStep/" + userId; //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = ""; //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath); //open the connection
            conn = (HttpURLConnection) url.openConnection(); //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to GET
            conn.setRequestMethod("GET"); //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json"); //Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the password stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String findCalBurnedAtRest(Integer userId) {
        final String methodPath = "a1.users/findCalBurnedAtRestString/" + userId; //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = ""; //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath); //open the connection
            conn = (HttpURLConnection) url.openConnection(); //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to GET
            conn.setRequestMethod("GET"); //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json"); //Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the password stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }


    public static void createNewReport(NewReport newReport) { //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath = "a1.report/";

        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").create();
            String jsonConsumption = gson.toJson(newReport);


            url = new URL(BASE_URL + methodPath); //open the connection
            conn = (HttpURLConnection) url.openConnection(); //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to POST
            conn.setRequestMethod("POST"); //set the output to true
            conn.setDoOutput(true); //set length of the data you want to send
            conn.setFixedLengthStreamingMode(jsonConsumption.getBytes().length); //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json"); //Send the POST out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(jsonConsumption);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }


    public static String getEmailStatus(String email) {
        final String methodPath = "a1.users/getEmailStatus/" + email; //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = ""; //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath); //open the connection
            conn = (HttpURLConnection) url.openConnection(); //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to GET
            conn.setRequestMethod("GET"); //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json"); //Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the password stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String getUserNameStatus(String userName) {
        final String methodPath = "a1.credentials/getUserNameStatus/" + userName; //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = ""; //Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath); //open the connection
            conn = (HttpURLConnection) url.openConnection(); //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to GET
            conn.setRequestMethod("GET"); //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json"); //Read the response
            Scanner inStream = new Scanner(conn.getInputStream()); //read the password stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }


}
