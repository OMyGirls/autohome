package cn.lwtAR;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.arta.lib.adapter.BaseAdapterEntityViewManage;
import com.arta.lib.adapter.BaseEntityViewAdapter;
import com.arta.lib.adapter.DefaultBaseAdapterEntityViewManage;
import com.arta.lib.adapter.wrapper.AdapterViewEntityWrapper;
import com.arta.lib.widget.listener.OnEntityViewClickListener;

import java.util.ArrayList;
import java.util.List;

import cn.lwtAR.database.DataLocal;
import cn.lwtAR.entity.HelpEntity;
import cn.lwtAR.utils.ResourceUtil;

public class UserGuideActivity extends BaseActivity {


    private ImageView ivLeft;
    private TextView tv_left;
    private ListView lv_help;

    AdapterViewEntityWrapper<HelpEntity> entityWrapper;
    List<HelpEntity> helpEntityList = new ArrayList<HelpEntity>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide);

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
        lv_help = (ListView) findViewById(R.id.lv_help);

        helpEntityList.addAll(DataLocal.getHelpData(this));
        entityWrapper = new AdapterViewEntityWrapper<HelpEntity>(lv_help);
        entityWrapper.setAdapter(new BaseEntityViewAdapter<HelpEntity>(this, helpEntityList, viewManage));
        entityWrapper.setOnEntityViewClickListener(entityOnEntityViewClickListener);
    }
    BaseAdapterEntityViewManage<HelpEntity> viewManage = new DefaultBaseAdapterEntityViewManage<HelpEntity>(R.layout.item_ar_data_header) {
        @Override
        public void updateItemView(Context context, View view, final HelpEntity helpEntity, int i) {
            final TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvTitle.setText(helpEntity.getTitle());
        }
    };

    OnEntityViewClickListener<HelpEntity> entityOnEntityViewClickListener = new OnEntityViewClickListener<HelpEntity>() {
        @Override
        public void onEntityViewClick(AdapterView<?> adapterView, View view, HelpEntity helpEntity, int i) {
            Intent intent = new Intent(UserGuideActivity.this,UserGuideDetailActivity.class);
            intent.putExtra("a", helpEntity);
            startActivity(intent);
        }
    };
}
