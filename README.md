See https://github.com/android/android-test/issues/991#issuecomment-944168584

Starting recyclerview:1.2.0, ViewHolder bound in RecyclerViewActions::itemsMatching are updated incomplete. 
ViewHolder.getItemId() will always be NO_ID if used in Matcher.