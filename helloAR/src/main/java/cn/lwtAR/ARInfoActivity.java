package cn.lwtAR;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.lwtAR.database.DataLocal;
import cn.lwtAR.entity.AREntity;

public class ARInfoActivity extends BaseActivity {

    private ImageView iv_info;
    private TextView tvCenter;
    private TextView tv_title;
    private TextView tv_info;

    private ImageView ivLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arinfo);
        iv_info = (ImageView) findViewById(R.id.iv_info);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_info = (TextView) findViewById(R.id.tv_info);

        ivLeft = (ImageView) findViewById(R.id.ivLeft);
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvCenter = (TextView) findViewById(R.id.tvCenter);
        tvCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        int i = getIntent().getIntExtra("a",0);
        AREntity arEntity = DataLocal.getARData().get(i);
        iv_info.setBackgroundResource(arEntity.getImageBody());
        tv_title.setText(arEntity.getTitle());
        tv_info.setText(arEntity.getBody());
    }
}
