package org.insbaixcamp.gratitude.journal.daily.tools;

import org.insbaixcamp.gratitude.journal.daily.model.Quote;

public interface QuoteCallback {
    void onCallback(Quote quote);
    void onError(String error);
}
