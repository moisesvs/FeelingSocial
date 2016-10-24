package com.moisesvazquez.feelingsocial.feelingsocial.utils;

/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

import com.moisesvazquez.feelingsocial.feelingsocial.data.entity.TextList;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

/**
 * A number of String-specific utility methods for use by PMD or its IDE
 * plugins.
 */
public final class TweetUtil {

    /**
     * Return whether the non-null text arg starts with any of the prefix
     * values.
     *
     * @param listTweets list tweets
     * @return boolean
     */
    public static TextList toIntentTweet(List<Tweet> listTweets) {
        TextList result = null;
        if (listTweets != null) {
            result = new TextList(listTweets.size());
            if (listTweets != null) {
                for (Tweet tweet : listTweets) {
                    result.add(tweet.text);
                }
            }
        }
        return result;
    }
}