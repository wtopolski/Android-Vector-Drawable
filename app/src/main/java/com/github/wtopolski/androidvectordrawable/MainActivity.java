package com.github.wtopolski.androidvectordrawable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.content.res.AppCompatResources;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView image = (ImageView) findViewById(R.id.paint);
        image.setImageBitmap(generateIcon());
    }

    public void onSelectMe(View view) {
        CheckBox checkbox = (CheckBox) view;
        Toast.makeText(MainActivity.this, "" + checkbox.isChecked(), Toast.LENGTH_SHORT).show();
    }

    private Bitmap generateIcon() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();

        int fontSize = 16;
        int imageSize = (int) (64 * metrics.density);
        Bitmap bitmap = Bitmap.createBitmap(imageSize, imageSize, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Drawable vectorDrawable = AppCompatResources.getDrawable(getApplicationContext(), R.drawable.ic_custom);

        if (vectorDrawable != null) {
            int resize = (int) (fontSize * metrics.density);
            vectorDrawable.setBounds(resize/2, 0, canvas.getWidth() - resize/2, canvas.getHeight() - resize);
            vectorDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.RED);
        }

        Paint paintText = new Paint();
        paintText.setAntiAlias(true);
        paintText.setTextAlign(Paint.Align.CENTER);
        paintText.setColor(ContextCompat.getColor(this, R.color.colorBackground));
        paintText.setTextSize((int) (fontSize * metrics.density));
        paintText.setTypeface(Typeface.DEFAULT_BOLD);
        canvas.drawText("JOHN", bitmap.getWidth() / 2f, bitmap.getHeight() - 2*(metrics.density), paintText);

        return bitmap;
    }
}
