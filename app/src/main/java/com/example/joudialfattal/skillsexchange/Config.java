package com.example.joudialfattal.skillsexchange;


public class Config {

        public static String[] skills;
        public static String[] ids;


        public static final String GET_URL = "http://skillsexchangecyprus.com/SEC/mainList.php";
        public static final String JSON_ID = "id";
        public static final String JSON_NAME = "skill";
        public static final String TAG_JSON_ARRAY="result";

        public Config(int i) {
            skills = new String[i];
            ids = new String[i];
        }

        }
