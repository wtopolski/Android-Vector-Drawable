package com.github.wtopolski.androidvectordrawable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

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
        DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_custom);

        Bitmap alteredBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());

        Canvas canvas = new Canvas(alteredBitmap);
        canvas.drawBitmap(bitmap, 0, -8, new Paint());

        Paint paintText = new Paint();
        paintText.setAntiAlias(true);
        paintText.setTextAlign(Paint.Align.CENTER);
        paintText.setColor(ContextCompat.getColor(this, R.color.colorBackground));
        paintText.setTextSize((int) (7 * metrics.density));
        paintText.setTypeface(Typeface.DEFAULT_BOLD);
        canvas.drawText("JOHN", bitmap.getWidth() / 2f, bitmap.getHeight() / 1.1f, paintText);

        bitmap.recycle();

        return alteredBitmap;
    }
}
