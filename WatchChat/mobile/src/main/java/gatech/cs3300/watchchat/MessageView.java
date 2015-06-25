package gatech.cs3300.watchchat;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MessageView extends ViewGroup {

    private static int ReceivedColor = 0xffdddddd;
    private static int SentColor = 0xff0074d9;

    //
    private TextView mContentView;
    private TextView mTimestamp;
    private ImageView mAvatar;
    private TextView mAuthor;

    private Message mMessage;

    // Initializer
    public MessageView(Context context) {
        super(context);

        mContentView = new TextView(context);
        mTimestamp = new TextView(context);
        mAvatar = new ImageView(context);
        mAuthor = new TextView(context);

        addView(mContentView);
        addView(mAvatar);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
       if (mMessage == null) {
           return;
       }

        int avatarWidth = 50;

       int padding = getPaddingLeft();

       if (mMessage.received) {
           mAvatar.layout(l + padding, b - (avatarWidth + padding), l + padding + avatarWidth, b - padding);
           mContentView.layout(l + 2 * padding + avatarWidth, t + padding, r - padding, b - padding);
       } else {
           mContentView.layout(l + padding, t + padding, r - (2 * padding + avatarWidth), b - padding);
           mAvatar.layout(r - (avatarWidth + padding), b - (avatarWidth + padding), r - padding, b - padding);
       }
    }

    // Properties
    public void setMessage(Message message) {
        mMessage = message;

        mAvatar.setBackgroundColor(0xff000000);
        mContentView.setText(message.content);
        if (mMessage.received) {
            mContentView.setBackgroundColor(MessageView.ReceivedColor);
        } else {
            mContentView.setBackgroundColor(MessageView.SentColor);
        }

        requestLayout();
        invalidate();
    }

    // Layout

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mMessage == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        int leftPadding = getPaddingLeft();

        int contentWidth = widthMeasureSpec - (leftPadding * 3 + 50);

        mContentView.setMaxWidth(contentWidth);
        int height = mContentView.getMeasuredHeight();

        setMeasuredDimension(widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(400, MeasureSpec.EXACTLY));
    }

}
