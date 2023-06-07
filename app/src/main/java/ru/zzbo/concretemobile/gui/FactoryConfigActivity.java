package ru.zzbo.concretemobile.gui;

import static ru.zzbo.concretemobile.utils.Constants.answer;
import static ru.zzbo.concretemobile.utils.Constants.tagListManual;
import static ru.zzbo.concretemobile.utils.Constants.tagListOptions;
import static ru.zzbo.concretemobile.utils.Constants.tagListMain;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.adapters.FragmentAdditionalOptionsAdapter;
import ru.zzbo.concretemobile.db.DBTags;
import ru.zzbo.concretemobile.protocol.profinet.collectors.DynamicTagBuilder;
import ru.zzbo.concretemobile.protocol.profinet.commands.CommandDispatcher;
import ru.zzbo.concretemobile.protocol.profinet.models.BlockMultiple;
import ru.zzbo.concretemobile.protocol.profinet.models.Tag;

public class FactoryConfigActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private TabLayout settingTabMenu;
    private FragmentAdditionalOptionsAdapter adapter;
    private FloatingActionButton nextBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factory_config);

        new Thread(() -> initAnswer()).start();

        tagListMain = new DBTags(getApplicationContext()).getTags("tags_main");
        tagListManual = new DBTags(getApplicationContext()).getTags("tags_manual");
        tagListOptions = new DBTags(getApplicationContext()).getTags("tags_options");

        viewPager2 = findViewById(R.id.view_pager);
        settingTabMenu = findViewById(R.id.tabLayout);
        nextBtn = findViewById(R.id.nextBtn);

        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new FragmentAdditionalOptionsAdapter(fragmentManager, getLifecycle());
        viewPager2.setAdapter(adapter);

        initActions();
    }

    private void initActions() {
        nextBtn.setOnClickListener(e -> super.onBackPressed());

        settingTabMenu.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                settingTabMenu.selectTab(settingTabMenu.getTabAt(position));
            }
        });

    }

    public void initAnswer() {
        CommandDispatcher commandDispatcher = new CommandDispatcher();

        List<Tag> getTagListAdditionalOptions = new DBTags(this).getTags("tags_additional_options");
        DynamicTagBuilder dynamicTagCollector = new DynamicTagBuilder(getTagListAdditionalOptions);
        dynamicTagCollector.buildSortedTags();

        List<BlockMultiple> tagIntAnswer = dynamicTagCollector.getTagIntAnswer();
        List<BlockMultiple> tagDIntAnswer = dynamicTagCollector.getTagDIntAnswer();
        List<BlockMultiple> tagRealAnswer = dynamicTagCollector.getTagRealAnswer();

        answer = new ArrayList<>();
        answer.addAll(commandDispatcher.readMultipleIntRegister(tagIntAnswer, getTagListAdditionalOptions));
        answer.addAll(commandDispatcher.readMultipleDIntRegister(tagDIntAnswer, getTagListAdditionalOptions));
        answer.addAll(commandDispatcher.readMultipleRealRegister(tagRealAnswer, getTagListAdditionalOptions));

    }

    @Override
    public void onBackPressed() {

    }
}