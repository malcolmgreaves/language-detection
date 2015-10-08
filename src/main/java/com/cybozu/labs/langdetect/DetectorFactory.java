package com.cybozu.labs.langdetect;

import com.cybozu.labs.langdetect.util.LangProfile;
import net.arnx.jsonic.JSON;
import net.arnx.jsonic.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Language Detector Factory Class
 *
 * WARNING: HIGHLY MUTABLE CLASS. USE CAUTION IN CONCURRENT and DISTRIBUTED USE CASES!
 *
 * This class manages an initialization and constructions of {@link Detector}.
 *
 * Before using language detection library,
 * load profiles with {@link DetectorFactory#loadProfile(String)} method
 * and set initialization parameters.
 *
 * When the language detection,
 * construct Detector instance via {@link DetectorFactory#create()}.
 * See also {@link Detector}'s sample code.

 * <ul>
 * <li>4x faster improvement based on Elmer Garduno's code. Thanks!</li>
 * </ul>
 *
 * @author Nakatani Shuyo
 * @author Malcolm Greaves (editor)
 * @see Detector
 */
public class DetectorFactory implements Serializable {

    private Map<String, double[]> wordLangProbMap;

    private List<String> langlist;

    private long seed;

    public DetectorFactory() {
        this(
                new HashMap<String, double[]>(),
                new ArrayList<String>(),
                Double.doubleToLongBits((Math.random() * 10000))
        );
    }

    public DetectorFactory(
            final Map<String, double[]> wordLangProbMap,
            final ArrayList<String> langlist,
            final long seed
    ) {
        this.wordLangProbMap = wordLangProbMap;
        this.langlist = langlist;
        this.seed = seed;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(final long seed) {
        this.seed = seed;
    }

    /**
     * The languages that this detector factory knows about.
     *
     * @return Unmodifiable list.
     */
    public List<String> getLangList() {
        return Collections.unmodifiableList(langlist);
    }

    /**
     * The language to word-probability mapping.
     *
     * @return Unmodifiable map.
     */
    public Map<String, double[]> getWordLangProbMap() {
        return Collections.unmodifiableMap(wordLangProbMap);
    }


    /**
     * Load profiles from specified directory.
     * This method must be called once before language detection.
     *
     * @param profileDirectory profile directory path
     * @throws LangDetectException Can't open profiles(error code = {@link ErrorCode#FileLoadError})
     *                             or profile's format is wrong (error code = {@link ErrorCode#FormatError})
     */
    public void loadProfile(final String profileDirectory) throws LangDetectException {
        loadProfile(new File(profileDirectory));
    }

    /**
     * Load profiles from specified directory.
     * This method must be called once before language detection.
     *
     * @param profileDirectory profile directory path
     * @throws LangDetectException Can't open profiles(error code = {@link ErrorCode#FileLoadError})
     *                             or profile's format is wrong (error code = {@link ErrorCode#FormatError})
     */
    public void loadProfile(final File profileDirectory) throws LangDetectException {
        final File[] listFiles = profileDirectory.listFiles();
        if (listFiles == null)
            throw new LangDetectException(ErrorCode.NeedLoadProfileError, "Not found profile: " + profileDirectory);

        final int langsize = listFiles.length;
        int index = 0;
        for (final File file : listFiles) {
            if (file.getName().startsWith(".") || !file.isFile())
                continue;

            FileInputStream is = null;
            try {
                is = new FileInputStream(file);
                final LangProfile profile = JSON.decode(is, LangProfile.class);
                addProfile(profile, index, langsize);
                ++index;

            } catch (JSONException e) {
                throw new LangDetectException(ErrorCode.FormatError, "profile format error in '" + file.getName() + "'");

            } catch (IOException e) {
                throw new LangDetectException(ErrorCode.FileLoadError, "can't open '" + file.getName() + "'");

            } finally {
                try {
                    if (is != null) is.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     * Load profiles from specified directory.
     * This method must be called once before language detection.
     *
     * @param json_profiles all json-encoded profiles
     * @throws LangDetectException Can't open profiles(error code = {@link ErrorCode#FileLoadError})
     *                             or profile's format is wrong (error code = {@link ErrorCode#FormatError})
     */
    public void loadProfile(final List<String> json_profiles) throws LangDetectException {
        final int langsize = json_profiles.size();
        if (langsize < 2)
            throw new LangDetectException(ErrorCode.NeedLoadProfileError, "Need more than 2 profiles");

        int index = 0;
        for (final String json : json_profiles) {
            try {
                final LangProfile profile = JSON.decode(json, LangProfile.class);
                addProfile(profile, index, langsize);
                ++index;
            } catch (JSONException e) {
                throw new LangDetectException(ErrorCode.FormatError, "profile format error");
            }
        }
    }

    /**
     * @param profile  profile
     * @param index    index
     * @param langsize language size
     * @throws LangDetectException if there's a problem
     */
    public void addProfile(
            final LangProfile profile,
            final int index,
            final int langsize) throws LangDetectException {

        final String lang = profile.name;
        if (langlist.contains(lang)) {
            throw new LangDetectException(ErrorCode.DuplicateLangError, "duplicate the same language profile");
        }
        langlist.add(lang);

        for (final String word : profile.freq.keySet()) {
            if (!wordLangProbMap.containsKey(word)) {
                wordLangProbMap.put(word, new double[langsize]);
            }
            final int length = word.length();
            if (length >= 1 && length <= 3) {
                final double prob = profile.freq.get(word).doubleValue() / profile.n_words[length - 1];
                wordLangProbMap.get(word)[index] = prob;
            }
        }
    }

    /**
     * Clear loaded language profiles (reinitialization to be available)
     */
    public void clear() {
        langlist.clear();
        wordLangProbMap.clear();
    }

    /**
     * Construct Detector instance
     *
     * @return Detector instance
     * @throws LangDetectException if there's a problem
     */
    public Detector create() throws LangDetectException {
        return createDetector();
    }

    /**
     * Construct Detector instance with smoothing parameter
     *
     * @param alpha smoothing parameter (default value = 0.5)
     * @return Detector instance
     * @throws LangDetectException if there's a problem
     */
    public Detector create(final double alpha) throws LangDetectException {
        final Detector detector = createDetector();
        detector.setAlpha(alpha);
        return detector;
    }

    private Detector createDetector() throws LangDetectException {
        if (langlist.isEmpty())
            throw new LangDetectException(ErrorCode.NeedLoadProfileError, "need to load profiles");
        return new Detector(this);
    }

}
