package com.sate7.sate7factorymode.data;

import android.content.Context;
import android.util.JsonReader;
import android.util.JsonToken;

import com.sate7.sate7factorymode.XLog;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

public class FactoryTestOptionJsonReader {
    public static final String TAG_TITLE = "title";
    public static final String TAG_FRAGMENT = "fragment";
    public static final String TAG_SHOW = "show";
    public static final String TAG_FULLSCREEN = "fullscreen";

    public static ArrayList<TestBean> read(Context context) throws IOException {
        ArrayList list = new ArrayList();
        InputStream stream = null;
        InputStreamReader inputStreamReader = null;
        JsonReader jsonReader = null;
        stream = context.getAssets().open("factory_test_options");
        inputStreamReader = new InputStreamReader(stream);
        jsonReader = new JsonReader(inputStreamReader);
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            list.add(readObject(jsonReader));
        }
        jsonReader.endArray();
        jsonReader.close();
        inputStreamReader.close();
        stream.close();
        XLog.d("read list == " + list);
        return list;
    }

    public static TestBean readObject(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        TestBean testBean = new TestBean();
        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if (name.equals(TAG_TITLE)) {
                testBean.setTitle(jsonReader.nextString());
            } else if (name.equals(TAG_FRAGMENT)) {
                testBean.setFragmentName(jsonReader.nextString());
            } else if (name.equals(TAG_SHOW)) {
                testBean.setShow(jsonReader.nextBoolean());
            } else if (name.equals(TAG_FULLSCREEN)) {
                testBean.setFullscreen(jsonReader.nextBoolean());
            } else {
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        XLog.d("readObject ... " + testBean);
        return testBean;
    }
}
