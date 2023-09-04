package com.example.withpetapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.withpetapp.AreaList;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    TextView cafeText, attractionsText, activityText;
    EditText editText;
    String urlStr = "https://www.pettravel.kr/api/listArea.do?page=1&pageBlock=300&areaCode=";
    String Nm;
    String category;
    String address;
    String name;
    String tel;
    int isCafe, isActivity, isAttractions = 0;

    static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cafeText = findViewById(R.id.cafe);
        attractionsText = findViewById(R.id.attractions);
        activityText = findViewById(R.id.activity);
        editText = findViewById(R.id.editText);

        Button button = findViewById(R.id.searchBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Nm = editText.getText().toString();
                switch (Nm){
                    case"춘천시": case"춘천":
                        Nm = "AC01";
                        makeRequest();
                        break;
                    case"원주시": case"원주":
                        Nm="AC02";
                        makeRequest();
                        break;
                    case"강릉시": case"강릉":
                        Nm="AC03";
                        makeRequest();
                        break;
                    case"동해시": case"동해":
                        Nm="AC04";
                        makeRequest();
                        break;
                    case"태백시": case"태백":
                        Nm="AC05";
                        makeRequest();
                        break;
                    case"속초시": case"속초":
                        Nm="AC06";
                        makeRequest();
                        break;
                    case"삼척시": case"삼척":
                        Nm="AC07";
                        makeRequest();
                        break;
                    case"홍천군": case"홍천":
                        Nm="AC08";
                        makeRequest();
                        break;
                    case"횡성군": case"횡성":
                        Nm="AC09";
                        makeRequest();
                        break;
                    case"영월군": case"영월":
                        Nm="AC10";
                        makeRequest();
                        break;
                    case"평창군": case"평창":
                        Nm="AC11";
                        makeRequest();
                        break;
                    case"정선군": case"정선":
                        Nm="AC12";
                        makeRequest();
                        break;
                    case"철원군": case"철원":
                        Nm="AC13";
                        makeRequest();
                        break;
                    case"화천군": case"화천":
                        Nm="AC14";
                        makeRequest();
                        break;
                    case"양구군": case"양구":
                        Nm="AC15";
                        makeRequest();
                        break;
                    case"인제군": case"인제":
                        Nm="AC16";
                        makeRequest();
                        break;
                    case"고성군": case"고성":
                        Nm="AC17";
                        makeRequest();
                        break;
                    case"양양군": case"양양":
                        Nm="AC18";
                        makeRequest();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "지역명을 제대로 입력해주세요!", Toast.LENGTH_LONG).show();
                }
                cafeText.setText("");
                activityText.setText("");
                attractionsText.setText("");
            }
        });
        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    private void makeRequest() {
        StringRequest request = new StringRequest(Request.Method.GET, urlStr+Nm, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.substring(1, response.length()-1);
                processResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //println("에러 : " + error);
            }
        });
        request.setShouldCache(false);
        requestQueue.add(request);
        //println("요청 보냄");
    }

    private void processResponse(String response) {
        Gson gson = new Gson();
        AreaList areaList = gson.fromJson(response, AreaList.class);

        for (int i = 0; i < areaList.resultList.size(); i++) {
            category = areaList.resultList.get(i).partName;
            name = areaList.resultList.get(i).title;
            address = areaList.resultList.get(i).address;
            tel = areaList.resultList.get(i).tel;
            if (category.equals("식음료")) {
                println("cafe", " < " + name + " > " + '\n' + "➡" + " 주소 : " + address + ", tel : " + tel + '\n');
                isCafe++;
            }
            if (category.equals("체험")) {
                println("activity", " < " + name + " > " + '\n' + "➡" + " 주소 : " + address + ", tel : " + tel + '\n');
                isActivity++;
            }
            if (category.equals("관광지")) {
                println("attractions", " < " + name + " > " + '\n' + "➡" + " 주소 : " + address + ", tel : " + tel + '\n');
                isAttractions++;
            }
        }
        if(isCafe == 0) println("cafe", "No data");
        if(isActivity == 0) println("activity", "No data");
        if(isAttractions == 0) println("attractions", "No data");
        else{
            isCafe = 0;
            isActivity = 0;
        }
    }

    private void println(final String str, String str1) {
        switch (str){
            case "cafe":
                cafeText.append(str1 + '\n');
                break;
            case "attractions":
                attractionsText.append(str1 + '\n');
                break;
            case "activity":
                activityText.append(str1 + '\n');
                break;
        }
    }
}