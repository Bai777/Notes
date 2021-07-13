package com.example.notes.data;

import com.example.notes.R;

import java.util.Random;

public class PictureIndexConverter {
    private static Random random = new Random();
    private static Object syncObj = new Object();

    private static int[] picIndex = {
            R.drawable.ak_74,
            R.drawable.beretta,
            R.drawable.aps,
            R.drawable.gsh_18,
            R.drawable.pps_1,
            R.drawable.svd,
            R.drawable.vss,
    };

    public static int randomPictureIndex(){
        synchronized (syncObj){
            return random.nextInt(picIndex.length);
        }
    }

    public static int getPictureByIndex(int index){
        if (index < 0 || index >= picIndex.length){
            index = 0;
        }
        return picIndex[index];
    }

    public static int getIndexByPicture(int picture){
        for(int i = 0; i < picIndex.length; i++){
            if (picIndex[i] == picture){
                return i;
            }
        }
        return 0;
    }


}
