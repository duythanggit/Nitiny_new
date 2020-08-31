package vn.chapp.vn24h.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class SectionedViewHolder extends RecyclerView.ViewHolder {

  private PositionDelegate positionDelegate;

  public SectionedViewHolder(View itemView) {
    super(itemView);
  }

  void setPositionDelegate(PositionDelegate positionDelegate) {
    this.positionDelegate = positionDelegate;
  }

  protected ItemCoord getRelativePosition() {
    return positionDelegate.relativePosition(getAdapterPosition());
  }

  protected boolean isHeader() {
    return positionDelegate.isHeader(getAdapterPosition());
  }

  protected boolean isFooter() {
    return positionDelegate.isFooter(getAdapterPosition());
  }

  interface PositionDelegate {
    ItemCoord relativePosition(int absolutePosition);

    boolean isHeader(int absolutePosition);

    boolean isFooter(int absolutePosition);
  }
}
