package com.xubo.linescrollview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xubo.linescrollviewlib.LineScrollView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    LineScrollView linelist_lsv;
    Button btn1;
    Button btn2;
    Button btn3;

    List<String> lineList = new ArrayList<String>();
    List<String> lineList2 = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linelist_lsv = findViewById(R.id.linelist_lsv);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        initData();
        linelist_lsv.setLines(lineList);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linelist_lsv.clear();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linelist_lsv.addLine("----增加滚动行----");
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linelist_lsv.setLines(lineList2);
            }
        });
    }

    private void initData() {
        lineList.add("手机号为1861****100用户已经中奖");
        lineList.add("手机号为1861****101用户已经中奖");
        lineList.add("手机号为1861****102用户已经中奖");
        lineList.add("手机号为1861****103用户已经中奖");
        lineList.add("手机号为1861****104用户已经中奖");
        lineList.add("手机号为1861****105用户已经中奖");
        lineList.add("手机号为1861****106用户已经中奖");
        lineList.add("手机号为1861****107用户已经中奖");
        lineList.add("手机号为1861****108用户已经中奖");
        lineList.add("手机号为1861****109用户已经中奖");
        lineList.add("手机号为1861****110用户已经中奖");

        lineList2.add("数据要变更啦--变更内容1");
        lineList2.add("数据要变更啦--变更内容2");
        lineList2.add("数据要变更啦--变更内容3");
        lineList2.add("数据要变更啦--变更内容4");
        lineList2.add("数据要变更啦--变更内容5");
        lineList2.add("数据要变更啦--变更内容6");
        lineList2.add("数据要变更啦--变更内容7");
    }
}
