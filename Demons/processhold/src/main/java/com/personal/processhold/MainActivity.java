package com.personal.processhold;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.iv_test);

        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0.2f);
        ColorMatrixColorFilter colorMatrixColorFilter = new ColorMatrixColorFilter(matrix);
        imageView.setColorFilter(colorMatrixColorFilter);
    }
}
