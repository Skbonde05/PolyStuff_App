package myapp.org.userapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SubjectListActivity extends AppCompatActivity {

    public static final String EXTRA_SUBJECT_KEY = "subject_list_key";

    private static class Config {
        int layoutId;
        String backClass;
        String cardMapJson;
        Config(int layoutId, String backClass, String cardMapJson) {
            this.layoutId = layoutId;
            this.backClass = backClass;
            this.cardMapJson = cardMapJson;
        }
    }

    private static final Map<String, Config> REGISTRY = new HashMap<>();
    static {
        REGISTRY.put("aap_cs", new Config(R.layout.aap_cs, "myapp.org.userapp.levels5", "{\"aap_mcq\":\"aap_mcq_main\"}"));
        REGISTRY.put("acn_cs", new Config(R.layout.acn_cs, "myapp.org.userapp.levels4b", "{\"acn_que\":\"acn_que_main\",\"acn_u3\":\"acn_u3_main\",\"acn_u5\":\"acn_u5_main\",\"acn_u2\":\"acn_u2_main\",\"acn_u4\":\"acn_u4_main\",\"acn_u1\":\"acn_u1_main\",\"acn_u6\":\"acn_u6_main\"}"));
        REGISTRY.put("cc_cs", new Config(R.layout.cc_cs, "myapp.org.userapp.levels5", "{\"cc_u6\":\"cc_u6_main\",\"cc_que\":\"cc_que_main\",\"cc_u2\":\"cc_u2_main\",\"cc_u1\":\"cc_u1_main\",\"cc_u3\":\"cc_u3_main\",\"cc_u5\":\"cc_u5_main\",\"cc_u4\":\"cc_u4_main\"}"));
        REGISTRY.put("ce_cs", new Config(R.layout.computingessentials, "myapp.org.userapp.Semesters", "{\"ce_u2\":\"ce_u2_main\",\"ce_u1\":\"ce_u1_main\",\"ce_u4\":\"ce_u4_main\",\"ce_u6\":\"ce_u6_main\",\"ce_u5\":\"ce_u5_main\",\"ce_u3\":\"ce_u3_main\"}"));
        REGISTRY.put("cg_cs", new Config(R.layout.cg_cs, "myapp.org.userapp.levels3", "{\"cg_u2\":\"cg_u2_main\",\"cg_u5\":\"cg_u5_main\",\"cg_u4\":\"cg_u4_main\",\"cg_que\":\"cg_que_main\",\"cg_u3\":\"cg_u3_main\",\"cg_u1\":\"cg_u1_main\"}"));
        REGISTRY.put("cms1_cs", new Config(R.layout.cms1_cs, "myapp.org.userapp.Semesters", "{\"cms1_u2\":\"cms1_u2_main\",\"cms1_u4\":\"cms1_u4_main\",\"cms1_u5\":\"cms1_u5_main\",\"cms1_u3\":\"cms1_u3_main\",\"cms1_u1\":\"cms1_u1_main\"}"));
        REGISTRY.put("cms2_cs", new Config(R.layout.cms2_cs, "myapp.org.userapp.Semesters", "{\"cms2_que\":\"cms2_que_main\",\"cms2_u4\":\"cms2_u4_main\"}"));
        REGISTRY.put("cn_cs", new Config(R.layout.cn_cs, "myapp.org.userapp.levels3", "{\"cn_u4\":\"cn_u4_main\",\"cn_u3\":\"cn_u3_main\",\"cn_que\":\"cn_que_main\",\"cn_u2\":\"cn_u2_main\",\"cn_u1\":\"cn_u1_main\",\"cn_u5\":\"cn_u5_main\"}"));
        REGISTRY.put("cphm_cs", new Config(R.layout.cphm_cs, "myapp.org.userapp.levels3", "{\"cphm_u5\":\"cphm_u5_main\",\"cphm_u4\":\"cphm_u4_main\",\"cphm_u1\":\"cphm_u1_main\",\"cphm_u6\":\"cphm_u6_main\",\"cphm_u3\":\"cphm_u3_main\",\"cphm_que\":\"cphm_que_main\",\"cphm_u2\":\"cphm_u2_main\"}"));
        REGISTRY.put("cs_cs", new Config(R.layout.cs_cs, "myapp.org.userapp.levels4b", "{\"cs_u4\":\"cs_u4_main\",\"cs_u5\":\"cs_u5_main\",\"cs_que\":\"cs_que_main\",\"cs_u2\":\"cs_u2_main\",\"cs_u1\":\"cs_u1_main\",\"cs_u3\":\"cs_u3_main\"}"));
        REGISTRY.put("dmi_cs", new Config(R.layout.dmi_cs, "myapp.org.userapp.levels5", "{\"dmi_que\":\"dmi_que_main\",\"dmi_u2\":\"dmi_u2_main\",\"dmi_u4\":\"dmi_u4_main\",\"dmi_u1\":\"dmi_u1_main\",\"dmi_u6\":\"dmi_u6_main\",\"dmi_u3\":\"dmi_u3_main\",\"dmi_u5\":\"dmi_u5_main\"}"));
        REGISTRY.put("ds_cs", new Config(R.layout.ds_cs, "myapp.org.userapp.levels3", "{\"ds_u2\":\"ds_u2_main\",\"ds_u1\":\"ds_u1_main\",\"ds_que\":\"ds_que_main\",\"ds_u4\":\"ds_u4_main\",\"ds_u3\":\"ds_u3_main\",\"ds_u6\":\"ds_u6_main\",\"ds_u5\":\"ds_u5_main\"}"));
        REGISTRY.put("edp_cs", new Config(R.layout.edp_cs, "myapp.org.userapp.levels4", "{\"edp_u2\":\"edp_u2_main\",\"edp_u1\":\"edp_u1_main\",\"edp_u3\":\"edp_u3_main\",\"edp_u4\":\"edp_u4_main\"}"));
        REGISTRY.put("ee_cs", new Config(R.layout.ee_cs, "myapp.org.userapp.levels2", "{\"ee_u1\":\"ee_u1_main\",\"ee_u5\":\"ee_u5_main\",\"ee_u3\":\"ee_u3_main\",\"ee_que\":\"ee_que_main\",\"ee_u2\":\"ee_u2_main\",\"ee_u4\":\"ee_u4_main\"}"));
        REGISTRY.put("foe_cs", new Config(R.layout.foe_cs, "myapp.org.userapp.levels2", "{\"foe_u2\":\"foe_u2_main\",\"foe_u5\":\"foe_u5_main\",\"foe_que\":\"foe_que_main\",\"foe_u3\":\"foe_u3_main\",\"foe_u1\":\"foe_u1_main\",\"foe_u4\":\"foe_u4_main\"}"));
        REGISTRY.put("html_cs", new Config(R.layout.html_cs, "myapp.org.userapp.levels2", "{\"html_u2\":\"html_u2_main\",\"html_u4\":\"html_u4_main\",\"html_u1\":\"html_u1_main\",\"html_u3\":\"html_u3_main\",\"html_u5\":\"html_u5_main\"}"));
        REGISTRY.put("ict_cs", new Config(R.layout.ict_cs, "myapp.org.userapp.levels2", "{\"fict_cmd\":\"fict_cmd_main\"}"));
        REGISTRY.put("im_cs", new Config(R.layout.im_cs, "myapp.org.userapp.levels4", "{\"im_u5\":\"im_u5_main\",\"im_u3\":\"im_u3_main\",\"im_u1\":\"im_u1_main\",\"im_u4\":\"im_u4_main\",\"im_u2\":\"im_u2_main\"}"));
        REGISTRY.put("jp1_cs", new Config(R.layout.jp1_cs, "myapp.org.userapp.levels3", "{\"jp1_que\":\"jp1_u1_main\",\"jp1_u2\":\"jp1_u1_main\",\"jp1_u1\":\"jp1_u1_main\",\"jp1_u6\":\"jp1_u1_main\",\"jp1_u3\":\"jp1_u1_main\",\"jp1_u5\":\"jp1_u1_main\",\"jp1_u4\":\"jp1_u1_main\"}"));
        REGISTRY.put("jp2_cs", new Config(R.layout.jp2_cs, "myapp.org.userapp.levels4b", "{\"jp2_u1\":\"jp2_u1_main\",\"jp2_u2\":\"jp2_u2_main\",\"jp2_u5\":\"jp2_u5_main\",\"jp2_u3\":\"jp2_u3_main\",\"jp2_u6\":\"jp2_u6_main\",\"jp2_que\":\"jp2_que_main\",\"jp2_u4\":\"jp2_u4_main\"}"));
        REGISTRY.put("linux_cs", new Config(R.layout.linux_cs, "myapp.org.userapp.levels2", "{\"linux_cmd\":\"linux_cmd_main\"}"));
        REGISTRY.put("m1_cs", new Config(R.layout.m1_cs, "myapp.org.userapp.Semesters", "{\"m1_tut\":\"m1_tut_main\"}"));
        REGISTRY.put("m2_cs", new Config(R.layout.m2_cs, "myapp.org.userapp.Semesters", "{\"m2_u1\":\"m2_u1_main\",\"m2_u2\":\"m2_u2_main\",\"m2_que\":\"m2_que_main\",\"m2_u5\":\"m2_u5_main\",\"m2_u4\":\"m2_u4_main\",\"m2_tut\":\"m2_tut_main\",\"m2_u3\":\"m2_u3_main\"}"));
        REGISTRY.put("m3_cs", new Config(R.layout.m3_cs, "myapp.org.userapp.levels2", "{\"m3_u1\":\"m3_u1_main\",\"m3_u2\":\"m3_u2_main\",\"m3_que\":\"m3_que_main\",\"m3_u3\":\"m3_u3_main\",\"m3_u4\":\"m3_u4_main\",\"m3_u5\":\"m3_u5_main\",\"m3_tut\":\"m3_tut_main\"}"));
        REGISTRY.put("oop_cs", new Config(R.layout.oop_cs, "myapp.org.userapp.levels3", "{\"oop_que\":\"oop_que_main\",\"oop_u5\":\"oop_u5_main\",\"oop_u3\":\"oop_u3_main\",\"oop_u6\":\"oop_u6_main\",\"oop_u1\":\"oop_u1_main\",\"oop_u4\":\"oop_u4_main\",\"oop_u2\":\"oop_u2_main\"}"));
        REGISTRY.put("os_cs", new Config(R.layout.os_cs, "myapp.org.userapp.levels3", "{\"os_que\":\"os_que_main\",\"os_u3\":\"os_u3_main\",\"os_u1\":\"os_u1_main\",\"os_u2\":\"os_u2_main\",\"os_u5\":\"os_u5_main\",\"os_u6\":\"os_u6_main\",\"os_u4\":\"os_u4_main\"}"));
        REGISTRY.put("pdtmp_cs", new Config(R.layout.pdtmp_cs, "myapp.org.userapp.levels3", "{\"dtmp_u2\":\"dtmp_u2_main\",\"dtmp_u5\":\"dtmp_u5_main\",\"dtmp_u6\":\"dtmp_u6_main\",\"dtmp_u4\":\"dtmp_u4_main\",\"dtmp_que\":\"dtmp_que_main\",\"dtmp_u3\":\"dtmp_u3_main\",\"dtmp_u1\":\"dtmp_u1_main\"}"));
        REGISTRY.put("physics_cs", new Config(R.layout.physics_cs, "myapp.org.userapp.Semesters", "{\"phy_u6\":\"phy_u6_main\",\"phy_u4\":\"phy_u4_main\",\"phy_u1\":\"phy_u1_main\",\"phy_u2\":\"phy_u2_main\",\"phy_u3\":\"phy_u3_main\",\"phy_u5\":\"phy_u5_main\"}"));
        REGISTRY.put("python_cs", new Config(R.layout.python_cs, "myapp.org.userapp.levels5", "{\"pwp_mcq\":\"pwp_mcq_main\"}"));
        REGISTRY.put("rdbms_cs", new Config(R.layout.rdbms_cs, "myapp.org.userapp.levels4b", "{\"rdbms_u2\":\"rdbms_u2_main\",\"rdbms_u4\":\"rdbms_u4_main\",\"rdbms_u6\":\"rdbms_u6_main\",\"rdbms_u5\":\"rdbms_u5_main\",\"rdbms_que\":\"rdbms_que_main\",\"rdbms_u1\":\"rdbms_u1_main\",\"rdbms_u3\":\"rdbms_u3_main\"}"));
        REGISTRY.put("set_cs", new Config(R.layout.set_cs, "myapp.org.userapp.levels4b", "{\"set_u2\":\"set_u2_main\",\"set_que\":\"set_que_main\",\"set_u6\":\"set_u6_main\",\"set_u5\":\"set_u5_main\",\"set_u4\":\"set_u4_main\",\"set_u1\":\"set_u1_main\",\"set_u3\":\"set_u3_main\"}"));
        REGISTRY.put("cms1_it", new Config(R.layout.cms1_it, "myapp.org.userapp.levels1_it", "{\"cms1_u2\":\"cms1_u2_main\",\"cms1_u4\":\"cms1_u4_main\",\"cms1_u5\":\"cms1_u5_main\",\"cms1_u3\":\"cms1_u3_main\",\"cms1_u1\":\"cms1_u1_main\"}"));
        REGISTRY.put("cms2_it", new Config(R.layout.cms2_it, "myapp.org.userapp.levels1_it", "{\"cms2_que\":\"cms2_que_main\",\"cms2_u4\":\"cms2_u4_main\"}"));
        REGISTRY.put("ct_it", new Config(R.layout.ct_it, "myapp.org.userapp.levels5_it", "{\"cc_u6\":\"cc_u6_main\",\"cc_que\":\"cc_que_main\",\"cc_u2\":\"cc_u2_main\",\"cc_u1\":\"cc_u1_main\",\"cc_u3\":\"cc_u3_main\",\"cc_u5\":\"cc_u5_main\",\"cc_u4\":\"cc_u4_main\"}"));
        REGISTRY.put("dbms_it", new Config(R.layout.dbms_it, "myapp.org.userapp.levels4b", "{\"rdbms_u2\":\"rdbms_u2_main\",\"rdbms_u4\":\"rdbms_u4_main\",\"rdbms_u6\":\"rdbms_u6_main\",\"rdbms_u5\":\"rdbms_u5_main\",\"rdbms_que\":\"rdbms_que_main\",\"rdbms_u1\":\"rdbms_u1_main\",\"rdbms_u3\":\"rdbms_u3_main\"}"));
        REGISTRY.put("dcn_it", new Config(R.layout.dcn_it, "myapp.org.userapp.levels3_it", "{\"cn_u4\":\"cn_u4_main\",\"cn_u3\":\"cn_u3_main\",\"cn_que\":\"cn_que_main\",\"cn_u2\":\"cn_u2_main\",\"cn_u1\":\"cn_u1_main\",\"cn_u5\":\"cn_u5_main\"}"));
        REGISTRY.put("ds_it", new Config(R.layout.ds_it, "myapp.org.userapp.levels3_it", "{\"ds_u2\":\"ds_u2_main\",\"ds_u1\":\"ds_u1_main\",\"ds_que\":\"ds_que_main\",\"ds_u4\":\"ds_u4_main\",\"ds_u3\":\"ds_u3_main\",\"ds_u6\":\"ds_u6_main\",\"ds_u5\":\"ds_u5_main\"}"));
        REGISTRY.put("edp_it", new Config(R.layout.edp_it, "myapp.org.userapp.levels4_it", "{\"edp_u2\":\"edp_u2_main\",\"edp_u1\":\"edp_u1_main\",\"edp_u3\":\"edp_u3_main\",\"edp_u4\":\"edp_u4_main\"}"));
        REGISTRY.put("ee_it", new Config(R.layout.ee_it, "myapp.org.userapp.levels2_it", "{\"ee_u1\":\"ee_u1_main\",\"ee_u5\":\"ee_u5_main\",\"ee_u3\":\"ee_u3_main\",\"ee_que\":\"ee_que_main\",\"ee_u2\":\"ee_u2_main\",\"ee_u4\":\"ee_u4_main\"}"));
        REGISTRY.put("foe_it", new Config(R.layout.foe_it, "myapp.org.userapp.levels2_it", "{\"foe_u2\":\"foe_u2_main\",\"foe_u5\":\"foe_u5_main\",\"foe_que\":\"foe_que_main\",\"foe_u3\":\"foe_u3_main\",\"foe_u1\":\"foe_u1_main\",\"foe_u4\":\"foe_u4_main\"}"));
        REGISTRY.put("ggt_it", new Config(R.layout.ggt_it, "myapp.org.userapp.levels5_it", "{\"cg_u2\":\"cg_u2_main\",\"cg_u5\":\"cg_u5_main\",\"cg_u4\":\"cg_u4_main\",\"cg_que\":\"cg_que_main\",\"cg_u3\":\"cg_u3_main\",\"cg_u1\":\"cg_u1_main\"}"));
        REGISTRY.put("html_it", new Config(R.layout.html_it, "myapp.org.userapp.levels2_it", "{\"html_u2\":\"html_u2_main\",\"html_u4\":\"html_u4_main\",\"html_u1\":\"html_u1_main\",\"html_u3\":\"html_u3_main\",\"html_u5\":\"html_u5_main\"}"));
        REGISTRY.put("ict_it", new Config(R.layout.ict_it, "myapp.org.userapp.levels2_it", "{\"fict_cmd\":\"fict_cmd_main\"}"));
        REGISTRY.put("im_it", new Config(R.layout.im_it, "myapp.org.userapp.levels4_it", "{\"im_u5\":\"im_u5_main\",\"im_u3\":\"im_u3_main\",\"im_u1\":\"im_u1_main\",\"im_u4\":\"im_u4_main\",\"im_u2\":\"im_u2_main\"}"));
        REGISTRY.put("iot_it", new Config(R.layout.iot_it, "myapp.org.userapp.levels4b_it", "{\"iot_ref\":\"iot_ref_main\"}"));
        REGISTRY.put("is_it", new Config(R.layout.is_it, "myapp.org.userapp.levels5_it", "{\"cs_u4\":\"cs_u4_main\",\"cs_u5\":\"cs_u5_main\",\"cs_que\":\"cs_que_main\",\"cs_u2\":\"cs_u2_main\",\"cs_u1\":\"cs_u1_main\",\"cs_u3\":\"cs_u3_main\"}"));
        REGISTRY.put("jp1_it", new Config(R.layout.jp1_it, "myapp.org.userapp.levels3", "{\"jp1_que\":\"jp1_que_main\",\"jp1_u2\":\"jp1_u2_main\",\"jp1_u1\":\"jp1_u1_main\",\"jp1_u6\":\"jp1_u6_main\",\"jp1_u3\":\"jp1_u3_main\",\"jp1_u5\":\"jp1_u5_main\",\"jp1_u4\":\"jp1_u4_main\"}"));
        REGISTRY.put("jp2_it", new Config(R.layout.jp2_it, "myapp.org.userapp.levels4b_it", "{\"jp2_u1\":\"jp2_u1_main\",\"jp2_u2\":\"jp2_u2_main\",\"jp2_u5\":\"jp2_u5_main\",\"jp2_u3\":\"jp2_u3_main\",\"jp2_u6\":\"jp2_u6_main\",\"jp2_que\":\"jp2_que_main\",\"jp2_u4\":\"jp2_u4_main\"}"));
        REGISTRY.put("linux_it", new Config(R.layout.linux_it, "myapp.org.userapp.levels2_it", "{\"linux_cmd\":\"linux_cmd_main\"}"));
        REGISTRY.put("m1_it", new Config(R.layout.m1_it, "myapp.org.userapp.levels1_it", "{\"m1_tut\":\"m1_tut_main\"}"));
        REGISTRY.put("m2_it", new Config(R.layout.m2_it, "myapp.org.userapp.levels1_it", "{\"m2_u1\":\"m2_u1_main\",\"m2_u2\":\"m2_u2_main\",\"m2_que\":\"m2_que_main\",\"m2_u5\":\"m2_u5_main\",\"m2_u4\":\"m2_u4_main\",\"m2_tut\":\"m2_tut_main\",\"m2_u3\":\"m2_u3_main\"}"));
        REGISTRY.put("m3_it", new Config(R.layout.m3_it, "myapp.org.userapp.levels2_it", "{\"m3_u1\":\"m3_u1_main\",\"m3_u2\":\"m3_u2_main\",\"m3_que\":\"m3_que_main\",\"m3_u3\":\"m3_u3_main\",\"m3_u4\":\"m3_u4_main\",\"m3_u5\":\"m3_u5_main\",\"m3_tut\":\"m3_tut_main\"}"));
        REGISTRY.put("ma_it", new Config(R.layout.ma_it, "myapp.org.userapp.levels3_it", "{\"ma_ref\":\"ma_ref_main\"}"));
        REGISTRY.put("nma_it", new Config(R.layout.nma_it, "myapp.org.userapp.levels4b_it", "{\"nma_ref\":\"nma_ref_main\"}"));
        REGISTRY.put("oop_it", new Config(R.layout.oop_it, "myapp.org.userapp.levels3_it", "{\"oop_que\":\"oop_que_main\",\"oop_u5\":\"oop_u5_main\",\"oop_u3\":\"oop_u3_main\",\"oop_u6\":\"oop_u6_main\",\"oop_u1\":\"oop_u1_main\",\"oop_u4\":\"oop_u4_main\",\"oop_u2\":\"oop_u2_main\"}"));
        REGISTRY.put("os_it", new Config(R.layout.os_it, "myapp.org.userapp.levels3_it", "{\"os_que\":\"os_que_main\",\"os_u3\":\"os_u3_main\",\"os_u1\":\"os_u1_main\",\"os_u2\":\"os_u2_main\",\"os_u5\":\"os_u5_main\",\"os_u6\":\"os_u6_main\",\"os_u4\":\"os_u4_main\"}"));
        REGISTRY.put("pdtmp_it", new Config(R.layout.pdtmp_it, "myapp.org.userapp.levels3_it", "{\"dtmp_u2\":\"dtmp_u2_main\",\"dtmp_u5\":\"dtmp_u5_main\",\"dtmp_u6\":\"dtmp_u6_main\",\"dtmp_u4\":\"dtmp_u4_main\",\"dtmp_que\":\"dtmp_que_main\",\"dtmp_u3\":\"dtmp_u3_main\",\"dtmp_u1\":\"dtmp_u1_main\"}"));
        REGISTRY.put("php_it", new Config(R.layout.php_it, "myapp.org.userapp.levels5", "{\"php_ref\":\"php_ref_main\"}"));
        REGISTRY.put("physics_it", new Config(R.layout.physics_it, "myapp.org.userapp.levels1_it", "{\"phy_u6\":\"phy_u6_main\",\"phy_u4\":\"phy_u4_main\",\"phy_u1\":\"phy_u1_main\",\"phy_u2\":\"phy_u2_main\",\"phy_u3\":\"phy_u3_main\",\"phy_u5\":\"phy_u5_main\"}"));
        REGISTRY.put("se_it", new Config(R.layout.se_it, "myapp.org.userapp.levels4b_it", "{\"set_u2\":\"set_u2_main\",\"set_u3\":\"set_u3_main\",\"set_que\":\"set_que_main\",\"set_u1\":\"set_u1_main\"}"));
        REGISTRY.put("st_it", new Config(R.layout.st_it, "myapp.org.userapp.levels4b_it", "{\"set_u5\":\"set_u5_main\",\"set_u6\":\"set_u6_main\",\"set_que\":\"set_que_main\",\"set_u4\":\"set_u4_main\"}"));
    }

    public static void launch(Context context, String key) {
        Config config = REGISTRY.get(key);
        if (config == null) {
            Toast.makeText(context, "Subject not found: " + key, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(context, SubjectListActivity.class);
        intent.putExtra("layout_id", config.layoutId);
        intent.putExtra("back_class", config.backClass);
        intent.putExtra("card_map", config.cardMapJson);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int layoutId = getIntent().getIntExtra("layout_id", -1);
        if (layoutId == -1) {
            Toast.makeText(this, "Layout ID missing", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        setContentView(layoutId);

        String backClassName = getIntent().getStringExtra("back_class");

        ImageButton backButton = findViewById(R.id.backButton);
        if (backButton != null && backClassName != null && !backClassName.isEmpty()) {
            backButton.setOnClickListener(v -> {
                try {
                    Class<?> backClass = Class.forName(backClassName);
                    Intent intent = new Intent(SubjectListActivity.this, backClass);
                    startActivity(intent);
                } catch (ClassNotFoundException e) {
                    finish();
                }
                finish();
            });
        }

        String cardMapJson = getIntent().getStringExtra("card_map");
        if (cardMapJson != null && !cardMapJson.isEmpty()) {
            try {
                JSONObject cardMap = new JSONObject(cardMapJson);
                Iterator<String> keys = cardMap.keys();
                while (keys.hasNext()) {
                    String viewIdName = keys.next();
                    int viewId = getResources().getIdentifier(viewIdName, "id", getPackageName());
                    if (viewId != 0) {
                        CardView cardView = findViewById(viewId);
                        if (cardView != null) {
                            String subjectKey = cardMap.getString(viewIdName);
                            cardView.setOnClickListener(v -> {
                                SubjectContentActivity.launch(SubjectListActivity.this, subjectKey);
                            });
                        }
                    }
                }
            } catch (JSONException e) {
                Toast.makeText(this, "Failed to load subjects", Toast.LENGTH_SHORT).show();
            }
        }
    }
}