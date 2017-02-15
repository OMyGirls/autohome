package cn.lwtAR;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import cn.lwtAR.database.DataLocal;
import cn.lwtAR.entity.AREntity;
import cn.lwtAR.utils.ResourceUtil;

public class AboutUsActivity extends BaseActivity {


    private ImageView ivLeft;
    private TextView tv_left;
    private TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        ivLeft = (ImageView) findViewById(R.id.ivLeft);
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvInfo = (TextView) findViewById(R.id.tv_info);
        tvInfo.setText(ResourceUtil.getFromAssets(this, "text/about.txt"));
    }
}
