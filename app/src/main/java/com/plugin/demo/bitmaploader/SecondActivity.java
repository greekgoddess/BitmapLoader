package com.plugin.demo.bitmaploader;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jindingwei on 2019/7/17.
 */

public class SecondActivity extends Activity {
    private ListView listView;
    private TestAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);
        listView = findViewById(R.id.listview);

        mAdapter = new TestAdapter(SecondActivity.this);
        listView.setAdapter(mAdapter);
        initData();
    }


    public void initData() {
        List<String> list = new LinkedList<>();
        list.add("https://himg.baidu.com/sys/portraitm/item/fce7e7a88be799bde88cb6e79a84e88081e4b9a14f9d?timestamp=1563290020");
        list.add("https://himg.bdimg.com/sys/portrait/item/07f97fd1.jpg?time=8396");
        list.add("https://himg.baidu.com/sys/portraitm/item/54afe78cabe593a538373837d435?timestamp=1532846756");
        list.add("https://himg.baidu.com/sys/portraitm/item/c6a7e5a3b9e783a8e5ad903c58?timestamp=1560518896");
        list.add("https://himg.baidu.com/sys/portraitm/item/125de5b9b3e587a1e5bf83e781b5392f38?timestamp=1563081302");
        list.add("https://himg.baidu.com/sys/portraitm/item/dee8e59b9be6b5b7e4b8bae5aeb6e79a84e4baba3939dff4?timestamp=1562767281");
        list.add("https://himg.baidu.com/sys/portraitm/item/52cfe7baa2e585b5e5b08fe9989fe995bfc570?timestamp=1519896208");
        list.add("https://himg.baidu.com/sys/portraitm/item/90326c756665693030373030314f36?timestamp=0");
        list.add("https://himg.baidu.com/sys/portraitm/item/bf2b74696e67e5baad3831b0ad?timestamp=1563199326");
        list.add("https://himg.bdimg.com/sys/portrait/item/a0726b5b.jpg?time=5120");
        list.add("https://himg.baidu.com/sys/portraitm/item/26c3e997b9e997b9e79a84e79cbce6b3aae7ac91e4ba861c0d?timestamp=1562268268");
        list.add("https://himg.baidu.com/sys/portraitm/item/2622e5a4a9e8b58be7bb99e4b8aaf39c?timestamp=1562590678");
        list.add("https://himg.baidu.com/sys/portraitm/item/e61fe5b08fe894a1e5b08fe8be897df6?timestamp=1562297155");
        list.add("https://himg.baidu.com/sys/portraitm/item/9387e4b88de8a2abe4bda0e5bc95e8b5b7e6b3a8e6848f86f6?timestamp=1563360350");
        list.add("https://himg.baidu.com/sys/portraitm/item/488fe5beaee5beae38383631b27b?timestamp=1561593799");
        list.add("https://himg.baidu.com/sys/portraitm/item/9fb76d757368616f7975323031303226?timestamp=1561386059");
        list.add("https://himg.baidu.com/sys/portraitm/item/08ba565f4765536576656efc27?timestamp=1561917994");
        list.add("https://himg.baidu.com/sys/portraitm/item/a119e6b0b4e5ae9de5aeb6e5bc80e5bf83e99b85e584bf72f3?timestamp=1561625733");
        list.add("https://himg.baidu.com/sys/portraitm/item/dc91e588abe8afb4e68891e88ab1e5bf83e5a484e5a5b35f36?timestamp=1563278246");
        list.add("https://himg.baidu.com/sys/portraitm/item/4c10e4b9a6e4b88ae4b88ee4babab326?timestamp=1562853732");
        list.add("https://himg.baidu.com/sys/portraitm/item/65e9e8b791e5b1b1e5a4a7e4baba6c6f?timestamp=1563361336");
        list.add("https://himg.baidu.com/sys/portraitm/item/2116e4b985e788b1e99abee68ba5696346?timestamp=1560745937");
        mAdapter.setDatas(list);
    }
}
