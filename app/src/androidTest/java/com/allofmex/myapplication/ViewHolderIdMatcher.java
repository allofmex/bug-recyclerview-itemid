package com.allofmex.myapplication;

import android.util.Log;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.matcher.BoundedMatcher;

import org.hamcrest.Description;

public class ViewHolderIdMatcher extends BoundedMatcher<RecyclerView.ViewHolder, Adapter.Vh> {

    final int mSearchedId;

    /**
     * Matches a ViewHolder with given itemId
     * @param searchedId itemId
     */
    ViewHolderIdMatcher(int searchedId) {
        super(Adapter.Vh.class);
        mSearchedId = searchedId;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(" ViewHoler with itemId ");
        description.appendValue(mSearchedId);
    }

    @Override
    protected boolean matchesSafely(Adapter.Vh viewHolder) {
        Log.d("ID_SEARCH", "View '"+((TextView) viewHolder.itemView).getText()+"': viewHolder.getItemId()="+viewHolder.getItemId());
        return mSearchedId == viewHolder.getItemId();
    }
}
