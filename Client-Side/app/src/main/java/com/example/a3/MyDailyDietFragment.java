package com.example.a3;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.os.AsyncTask;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;


import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyDailyDietFragment extends Fragment {
    private View vDailyDiet;
    private Spinner sFoodCategory, sFood;
    private TextView tvSelectFoodCategory, tvFoodHint, tvAddFood, addFoodFeedback;
    private EditText etAddNewFoodName;
    private String categories;
    private String selectedCategory;
    private boolean isSpinnerTouched = false;
    private String foods = "";
    private ArrayAdapter<String> spinnerAdapter, spinnerAdapterFood;
    private EditText etAddFood;
    private Button bAddFood;
    private TextView tvAddFoodFeedback;
    private String foodName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { // Set Variables and listeners
        vDailyDiet = inflater.inflate(R.layout.fragment_my_daily_diet, container, false);
        etAddFood = (EditText) vDailyDiet.findViewById(R.id.etAddNewFoodName);
        bAddFood = (Button) vDailyDiet.findViewById(R.id.bAddFood);
        tvAddFoodFeedback = (TextView) vDailyDiet.findViewById(R.id.addFoodFeedback);

        categories = ((MainActivity) getActivity()).getCategories();
        String[] details = categories.split(" ");
        List<String> listCategory = new ArrayList<String>();
        for (int i = 0; i < details.length; i++) {
            listCategory.add(details[i]);
        }

        sFoodCategory = (Spinner) vDailyDiet.findViewById(R.id.sFoodCategory);
        sFood = (Spinner) vDailyDiet.findViewById(R.id.sFoodUnderSpecificCategory);

        spinnerAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listCategory);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sFoodCategory.setAdapter(spinnerAdapter);
        spinnerAdapterFood = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item);
        spinnerAdapterFood.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sFood.setAdapter(spinnerAdapterFood);
        sFoodCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedActivity = parent.getItemAtPosition(position).toString();
                if (selectedActivity != null) {
                    Toast.makeText(parent.getContext(), " Food Item selected is: " + selectedActivity, Toast.LENGTH_LONG).show();
                    selectedCategory = selectedActivity;
                    FoodAsyncTask food = new FoodAsyncTask();
                    food.execute(selectedActivity);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        sFood.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isSpinnerTouched = true;
                return false;
            }
        });


        sFood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!isSpinnerTouched) return;
                String selectedActivity = parent.getItemAtPosition(position).toString();
                if (selectedActivity != null) {
                    Toast.makeText(parent.getContext(), " You consumed: " + selectedActivity, Toast.LENGTH_LONG).show();


                    PostAsyncTaskFindFoodId postAsyncTaskFindFoodId = new PostAsyncTaskFindFoodId();
                    postAsyncTaskFindFoodId.execute(selectedActivity);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        bAddFood.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                foodName = etAddFood.getText().toString();
                SearchAsyncTask searchAsyncTask = new SearchAsyncTask();
                searchAsyncTask.execute(foodName);
                SearchImageAsyncTask searchImageAsyncTask = new SearchImageAsyncTask();
                searchImageAsyncTask.execute(foodName);
                SearchFoodIdAsyncTask searchFoodIdAsyncTask = new SearchFoodIdAsyncTask();
                searchFoodIdAsyncTask.execute(foodName);

            }
        });


        return vDailyDiet;
    }

    private class FoodAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String allFood = restClient.findNamesByCategory(params[0]);
            allFood = allFood.substring(1, allFood.length() - 1);
            allFood = allFood.replaceAll("[,]", "");
            return allFood;
        }

        @Override
        protected void onPostExecute(String result) {
            List<String> listFood = new ArrayList<String>();
            String[] detailsFood;
            if (!result.isEmpty()) {
                detailsFood = result.split(" ");

                for (int i = 0; i < detailsFood.length; i++) {
                    listFood.add(detailsFood[i]);
                }
            }
            spinnerAdapterFood.clear();
            spinnerAdapterFood.addAll(listFood);

        }
    }

    private class PostAsyncTaskFindFoodId extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return restClient.findFoodId(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {

            /*SharedPreferences foodIdCount = getActivity().getSharedPreferences("consumtion_id", Context.MODE_PRIVATE);
                SharedPreferences.Editor spEditor = foodIdCount.edit();
                spEditor.putInt("consumtion_id", 6);
                spEditor.apply();*/


            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("consumtion_id", Context.MODE_PRIVATE);
            int consumtionId = sharedPreferences.getInt("consumtion_id", 0);
            consumtionId++;
            SharedPreferences.Editor spEditor2 = sharedPreferences.edit();
            spEditor2.putInt("consumtion_id", consumtionId);
            spEditor2.apply();
            final Calendar myCalendar = Calendar.getInstance();

            Date consumptionDate = myCalendar.getTime();

            String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String sConsumtionDate = simpleDateFormat.format(consumptionDate);

            int userId = ((MainActivity) getActivity()).getTheUserId();

            PostAsyncTaskAddConsumption postAsyncTaskAddConsumption = new PostAsyncTaskAddConsumption();
            postAsyncTaskAddConsumption.execute(Integer.toString(consumtionId), sConsumtionDate, Integer.toString(1),
                    result, Integer.toString(userId));

        }
    }

    private class PostAsyncTaskAddConsumption extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date consumptionDate = null;
            try {
                consumptionDate = sdf.parse(params[1]);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            NewFood newFood = new NewFood(Integer.valueOf(params[3]));
            NewUser newUser = new NewUser(Integer.valueOf(params[4]));

            NewConsumption newConsumption = new NewConsumption(Integer.valueOf(params[0]), consumptionDate, Integer.valueOf(params[2]),
                    newFood, newUser);

            restClient.createNewConsumption(newConsumption);
            return "Food consumed";
        }

        @Override
        protected void onPostExecute(String response) {
            TextView tvFeedback = (TextView) vDailyDiet.findViewById(R.id.foodConsumedFeedback);
            tvFeedback.setText(response);
        }
    }


    private class SearchAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return SearchGoogleAPI.search(params[0], new String[]{"num"}, new String[]{"1"});
        }

        @Override
        protected void onPostExecute(String result) {
            TextView tv = (TextView) vDailyDiet.findViewById(R.id.addFoodFeedback);
            tv.setText(SearchGoogleAPI.getSnippet(result));
        }
    }

    private class SearchFoodIdAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return NationalNutrientDb.searchFood(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            String foodId = NationalNutrientDb.getFoodId(result);
            SearchFoodNutritionAsyncTask searchFoodNutritionAsyncTask = new SearchFoodNutritionAsyncTask();
            searchFoodNutritionAsyncTask.execute(foodId);
        }
    }

    private class SearchFoodNutritionAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return NationalNutrientDb.searchFoodNutrition(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            int foodCalorieAmount = (int) Double.parseDouble(NationalNutrientDb.getFoodCalorieAmount(result));
            String foodServingUnit = NationalNutrientDb.getFoodServingUnit(result);
            int foodFatAmount = (int) Double.parseDouble(NationalNutrientDb.getFoodFatAmount(result));
            int foodFoodStdServingAmount = (int) Double.parseDouble(NationalNutrientDb.getFoodStdServingAmount(result));
            if (foodFoodStdServingAmount < 1)
                foodFoodStdServingAmount = 1;

              /*SharedPreferences foodIdCount = getActivity().getSharedPreferences("food_id", Context.MODE_PRIVATE);
                SharedPreferences.Editor spEditor = foodIdCount.edit();
                spEditor.putInt("food_id", 20);
                spEditor.apply();*/

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("food_id", Context.MODE_PRIVATE);
            int foodId = sharedPreferences.getInt("food_id", 0);
            foodId++;
            SharedPreferences.Editor spEditor2 = sharedPreferences.edit();
            spEditor2.putInt("food_id", foodId);
            spEditor2.apply();


            PostAsyncTaskAddFood postAsyncTaskAddFood = new PostAsyncTaskAddFood();
            postAsyncTaskAddFood.execute(foodName, selectedCategory, Integer.toString(foodCalorieAmount), foodServingUnit,
                    Integer.toString(foodFatAmount),
                    Integer.toString(foodFoodStdServingAmount), Integer.toString(foodId));


        }
    }

    private class PostAsyncTaskAddFood extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            String name = params[0].replace(" ", "-");
            NewFood newFood = new NewFood(name, params[1], (int) Integer.valueOf(params[2]),
                    params[3], Integer.valueOf(params[4]),BigDecimal.valueOf(1) /* BigDecimal.valueOf(Integer.valueOf(params[5]))*/, Integer.valueOf(params[6]));

            restClient.createNewFood(newFood);
            return "New food added";
        }

        @Override
        protected void onPostExecute(String response) {
            TextView tvFeedback = (TextView) vDailyDiet.findViewById(R.id.tvAddFoodServerFeedback);
            tvFeedback.setText(response);
        }
    }


    private class SearchImageAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return SearchGoogleAPI.searchImage(params[0], new String[]{"num"}, new String[]{"1"});
        }

        @Override
        protected void onPostExecute(String result) {

            ImageView ivFood = (ImageView) vDailyDiet.findViewById(R.id.imageView);
            String imageLink = SearchGoogleAPI.getImageLink(result);

            new DownloadImageTask(ivFood).execute(imageLink);

        }
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bmp = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bmp;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}