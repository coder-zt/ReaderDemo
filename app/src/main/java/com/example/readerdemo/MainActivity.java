package com.example.readerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.readerdemo.Reader.PageFactory;
import com.example.readerdemo.Reader.views.PageView;
import com.example.readerdemo.Reader.views.RegionSelectView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private List<LineTextView> mLineTextViews = new ArrayList<>();
    private PageView mP;
    private PageFactory mPageFactory;
    private Button mBtn;
    private RegionSelectView mRegionSelectView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    private void initView() {

        Point outSize = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(outSize);
        int x = outSize.x;
        int y = outSize.y;
        Log.d(TAG, "initView: " + y);
        mBtn = findViewById(R.id.btn);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPageFactory.getData();
            }
        });
        mRegionSelectView = findViewById(R.id.region_view);
        mP = (PageView)findViewById(R.id.page);
        mPageFactory = new PageFactory(mP, this);
        mP.setTouchListener(new PageView.TouchListener() {
            @Override
            public void longClick(int x, int y) {
                Log.d(TAG, "longClick: ----->(" + x + "," + y + ")");
                if (mRegionSelectView != null) {
                    mRegionSelectView.setVisibility(View.VISIBLE);
                    Log.d(TAG, "longClick: " + "mRegionSelectView is show");
                }
                String result =  mPageFactory.queryClickWord(x,y);
            }

            @Override
            public Boolean prePage() {
                mPageFactory.getPrePage();
                return true;
            }

            @Override
            public Boolean nextPage() {
                mPageFactory.getNextPage();
                return true;
            }

            @Override
            public void cancel() {

            }

            @Override
            public void click(int x, int y) {
                Log.d(TAG, "center: ----->(" + x + "," + y + ")");
                String result =  mPageFactory.queryClickWord(x,y);
                Toast.makeText(MainActivity.this,result, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
