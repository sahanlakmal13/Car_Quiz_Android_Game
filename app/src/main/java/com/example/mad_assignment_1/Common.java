package com.example.mad_assignment_1;

import android.widget.ImageView;

public class Common {

    static int[] carRefs = {R.drawable.audi1,R.drawable.audi2,R.drawable.audi3,
            R.drawable.bentley1,    R.drawable.bentley2,
            R.drawable.lamborghini1,R.drawable.lamborghini2,R.drawable.lamborghini3,
            R.drawable.ferrari1,    R.drawable.ferrari2,R.drawable.ferrari3,R.drawable.ferrari4,
            R.drawable.porsche1,    R.drawable.porsche2,R.drawable.porsche3,R.drawable.porsche4,R.drawable.porsche5,R.drawable.porsche6,
            R.drawable.jaguar1,     R.drawable.jaguar2,R.drawable.jaguar3,R.drawable.jaguar4,R.drawable.jaguar5,
            R.drawable.dodge1,      R.drawable.dodge2,R.drawable.dodge3,R.drawable.dodge4,R.drawable.dodge5,R.drawable.dodge6,R.drawable.dodge7,
            R.drawable.chevrolet1,  R.drawable.chevrolet2,R.drawable.chevrolet3,R.drawable.chevrolet4,R.drawable.chevrolet5,
            R.drawable.bmw1,        R.drawable.bmw2,R.drawable.bmw3,R.drawable.bmw4,R.drawable.bmw5};

    public static String getSelectedCarName(int[] carRefs, int n, ImageView imageView){

        String carPath =imageView.getResources().getResourceName(carRefs[n]);
        String[] df = carPath.split("/",2);
        String carImageName = df[1];
        return carImageName.replaceAll("[0-9]", "");
    }
}
