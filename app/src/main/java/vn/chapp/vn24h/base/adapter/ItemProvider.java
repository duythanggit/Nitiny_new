package vn.chapp.vn24h.base.adapter;

import android.support.annotation.RestrictTo;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
interface ItemProvider {

  int getSectionCount();

  int getItemCount(int sectionIndex);

  boolean showHeadersForEmptySections();

  boolean showFooters();
}
