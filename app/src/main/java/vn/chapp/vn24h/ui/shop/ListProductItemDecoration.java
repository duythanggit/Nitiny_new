package vn.chapp.vn24h.ui.shop;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import vn.chapp.vn24h.utils.ViewUtils;

public class ListProductItemDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int itemPosition = parent.getChildAdapterPosition(view);

        outRect.top = itemPosition == 0 || itemPosition == 1 ? ViewUtils.dpToPx(15) : 0;
        outRect.bottom = ViewUtils.dpToPx(15);
        outRect.left = ViewUtils.dpToPx(itemPosition % 2 == 0 ? 10 : 5);
        outRect.right = ViewUtils.dpToPx(itemPosition % 2 == 0 ? 10 : 6);

    }
}
