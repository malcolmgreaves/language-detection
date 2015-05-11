package com.cybozu.labs.langdetect;

import com.cybozu.labs.langdetect.util.LangProfile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for {@link Detector} and {@link DetectorFactory}
 *
 * @author Nakatani Shuyo
 */
public class DetectorTest {

    private static final String TRAINING_EN = "a a a b b c c d e";
    private static final String TRAINING_FR = "a b b c c c d d d";
    private static final String TRAINING_JA = "\u3042 \u3042 \u3042 \u3044 \u3046 \u3048 \u3048";
    private static final String JSON_LANG1 = "{\"freq\":{\"A\":3,\"B\":6,\"C\":3,\"AB\":2,\"BC\":1,\"ABC\":2,\"BBC\":1,\"CBA\":1},\"n_words\":[12,3,4],\"name\":\"lang1\"}";
    private static final String JSON_LANG2 = "{\"freq\":{\"A\":6,\"B\":3,\"C\":3,\"AA\":3,\"AB\":2,\"ABC\":1,\"ABA\":1,\"CAA\":1},\"n_words\":[12,5,3],\"name\":\"lang2\"}";

    private DetectorFactory detectorFact;

    @Before
    public void setUp() throws Exception {
        detectorFact = new DetectorFactory();

        LangProfile profile_en = new LangProfile("en");
        for (String w : TRAINING_EN.split(" "))
            profile_en.add(w);
        detectorFact.addProfile(profile_en, 0, 3);

        LangProfile profile_fr = new LangProfile("fr");
        for (String w : TRAINING_FR.split(" "))
            profile_fr.add(w);
        detectorFact.addProfile(profile_fr, 1, 3);

        LangProfile profile_ja = new LangProfile("ja");
        for (String w : TRAINING_JA.split(" "))
            profile_ja.add(w);
        detectorFact.addProfile(profile_ja, 2, 3);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testDetector1() throws LangDetectException {
        Detector detect = detectorFact.create();
        detect.append("a");
        assertEquals(detect.detect(), "en");
    }

    @Test
    public final void testDetector2() throws LangDetectException {
        Detector detect = detectorFact.create();
        detect.append("b d");
        assertEquals(detect.detect(), "fr");
    }

    @Test
    public final void testDetector3() throws LangDetectException {
        Detector detect = detectorFact.create();
        detect.append("d e");
        assertEquals(detect.detect(), "en");
    }

    @Test
    public final void testDetector4() throws LangDetectException {
        Detector detect = detectorFact.create();
        detect.append("\u3042\u3042\u3042\u3042a");
        assertEquals(detect.detect(), "ja");
    }

    @Test
    public final void testLangList() throws LangDetectException {
        List<String> langList = detectorFact.getLangList();
        assertEquals(langList.size(), 3);
        assertEquals(langList.get(0), "en");
        assertEquals(langList.get(1), "fr");
        assertEquals(langList.get(2), "ja");
    }

    @Test(expected = UnsupportedOperationException.class)
    public final void testLangListException() throws LangDetectException {
        List<String> langList = detectorFact.getLangList();
        langList.add("hoge");
        //langList.add(1, "hoge");
    }

    @Test
    public final void testFactoryFromJsonString() throws LangDetectException {
        detectorFact.clear();
        ArrayList<String> profiles = new ArrayList<String>();
        profiles.add(JSON_LANG1);
        profiles.add(JSON_LANG2);
        detectorFact.loadProfile(profiles);
        List<String> langList = detectorFact.getLangList();
        assertEquals(langList.size(), 2);
        assertEquals(langList.get(0), "lang1");
        assertEquals(langList.get(1), "lang2");
    }
}