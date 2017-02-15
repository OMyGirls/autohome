package cn.lwtAR;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.lwtAR.entity.HelpEntity;
import cn.lwtAR.utils.ResourceUtil;

public class UserGuideDetailActivity extends BaseActivity {


    private ImageView ivLeft;
    private TextView tvCenter;
    private TextView tv_title;
    private TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide_detail);

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

        tv_title = (TextView) findViewById(R.id.tv_info);
        tvInfo = (TextView) findViewById(R.id.tv_info);

        if(getIntent().getSerializableExtra("a") == null) return;
        HelpEntity helpEntity = (HelpEntity) getIntent().getSerializableExtra("a");
        tv_title.setText(helpEntity.getTitle());
        tvInfo.setText(helpEntity.getText());
    }
}
