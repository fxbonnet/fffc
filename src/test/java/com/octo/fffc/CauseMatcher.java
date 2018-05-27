package com.octo.fffc;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Provides a wrapper for the {@link TypeSafeMatcher} to help match the underlying cause for the exception
 *
 * @author anoop.shiralige
 * @version 1.0.0
 * @since 1.0.0
 */
public class CauseMatcher extends TypeSafeMatcher<Throwable> {

    private final Class<? extends Throwable> type;
    private final String expectedMessage;

    public CauseMatcher(Class<? extends Throwable> type, String expectedMessage) {
        this.type = type;
        this.expectedMessage = expectedMessage;
    }

    @Override
    protected boolean matchesSafely(Throwable item) {
        return item.getClass().isAssignableFrom(type)
                && item.getMessage().contains(expectedMessage);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("expects type ")
                .appendValue(type)
                .appendText(" and a message ")
                .appendValue(expectedMessage);
    }
}
