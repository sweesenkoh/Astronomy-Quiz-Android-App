package com.example.astronomyquiz;


import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

class AstroData {

    public List<Chapter> data;

    public AstroData loadAstronomyData(Context context) {

        Gson gson = new Gson();
        String jsonString = "";
        try{
            jsonString = AssetJSONFile("astroquestionsdata.json",context);
        }catch (IOException e){
            e.printStackTrace();
        }

        AstroData json = gson.fromJson(jsonString, AstroData.class);
        return json;
    }

    public static String AssetJSONFile (String filename, Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open(filename);
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();
        return new String(formArray);
    }

    class Chapter {
        public String ChapterName;
        public List<Question> Questions;
    }

    class Question{
        public String QuestionStatement;
        public String Answer;
        public List<String> Options;
    }
};


