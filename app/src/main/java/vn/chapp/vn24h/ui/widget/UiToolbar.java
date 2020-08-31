package vn.chapp.vn24h.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.chapp.vn24h.R;

public class UiToolbar extends ConstraintLayout implements BaseWidget {


    private View v;

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.ivActionRight)
    ImageView ivActionRight;
    private OnToolbarWithBackClickListener onToolbarWithCloseClick;

    public UiToolbar(Context context) {
        super(context);
        init(context, null, -1);
    }

    public UiToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, -1);
    }

    @Override
    public void init(Context context, AttributeSet attrs, int defStyleAttr) {

        v = LayoutInflater.from(context).inflate(R.layout.ui_toolbar, this, true);
        ButterKnife.bind(this, v);

        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.ToolbarStyleable);
        try {
            String title = t.getString(R.styleable.ToolbarStyleable_tsTitle);
            if (!TextUtils.isEmpty(title)) tvTitle.setText(title);
            boolean enableBack = t.getBoolean(R.styleable.ToolbarStyleable_tsEnableBack,true);
            ivBack.setVisibility(enableBack ? VISIBLE : GONE);
        } finally {
            t.recycle();
        }
    }

    public void setTitle(String title){
        if (!TextUtils.isEmpty(title)) tvTitle.setText(title);
    }

    public String getTitle(){
        return tvTitle.getText().toString();
    }

    public void setStyleTitle(int gravity){
        tvTitle.setGravity(gravity);
    }

    public void setEnableBack(Boolean isEnableBack){
        ivBack.setVisibility(isEnableBack ? VISIBLE : INVISIBLE);
    }

    public boolean getEnableBack(){
        return ivBack.getVisibility() == VISIBLE;
    }

    public void setActionRight(boolean enable, int icon){
        ivActionRight.setVisibility(enable ? VISIBLE : INVISIBLE);
        if (icon != -1) {
            ivActionRight.setImageDrawable(ResourcesCompat.getDrawable(getResources(),icon,null));
        }
    }

    @OnClick(R.id.ivBack)
    public void onBackClickListener(View v){
        if (onToolbarWithCloseClick != null) onToolbarWithCloseClick.onToolbarBackClick();
    }

    @OnClick(R.id.ivActionRight)
    public void onActionRightClick(View v){
        if (onToolbarWithCloseClick != null) onToolbarWithCloseClick.onToolbarActionRightClick();
    }

    public interface OnToolbarWithBackClickListener {
        void onToolbarBackClick();
        void onToolbarActionRightClick();
    }

    public void setOnToolbarWithCloseClick(OnToolbarWithBackClickListener onToolbarWithCloseClick) {
        this.onToolbarWithCloseClick = onToolbarWithCloseClick;
    }
}
