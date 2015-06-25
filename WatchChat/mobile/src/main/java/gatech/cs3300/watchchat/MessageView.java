package gatech.cs3300.watchchat;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MessageView extends FrameLayout {

    private static int ReceivedColor = 0xffdddddd;
    private static int SentColor = 0xff0074d9;

    //
    private FrameLayout mContentView;
    private TextView mMessageView;

    private TextView mTimestamp;
    private ImageView mAvatar;
    private TextView mAuthor;

    private Message mMessage;

    // Initializer
    public MessageView(Context context) {
        super(context);

        mContentView = new FrameLayout(context);
        mMessageView = new TextView(context);
        mTimestamp = new TextView(context);
        mAvatar = new ImageView(context);
        mAuthor = new TextView(context);

        mContentView.setPadding(10, 10, 10, 10);
        mMessageView.setTextSize(20);

        mAvatar.setImageDrawable(getResources().getDrawable(R.drawable.ic_user));

        addView(mContentView);
        mContentView.addView(mMessageView);
        addView(mAvatar);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //super.onLayout(changed, l, t, r, b);

        if (mMessage == null) {
            return;
        }
        int avatarWidth = 100;

        int padding = Math.max(getPaddingLeft(), 10);

        l = 0;
        t = 0;
        b = getMeasuredHeight();
        r = getMeasuredWidth();

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

        mMessageView.setText("  " + message.content);
        if (mMessage.received) {
            mContentView.setBackgroundResource(R.drawable.message_received_background);
            mMessageView.setTextColor(0xff000000);
        } else {
            mContentView.setBackgroundResource(R.drawable.message_sent_background);
            mMessageView.setTextColor(0xffffffff);
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

        int contentWidth = widthMeasureSpec - (leftPadding * 3 + 150 + 20);

        mMessageView.setMaxWidth(contentWidth);
        mContentView.measure(widthMeasureSpec, heightMeasureSpec);
        int height = Math.max(mContentView.getMeasuredHeight(), 175);

        setMeasuredDimension(widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }

}
