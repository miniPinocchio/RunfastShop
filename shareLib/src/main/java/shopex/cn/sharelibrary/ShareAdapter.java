package shopex.cn.sharelibrary;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by p on 2016/6/29.
 */
public class ShareAdapter extends BaseAdapter {
    private static String[] shareNames = new String[] {"微信朋友圈", "微信好友", "QQ空间","QQ好友","新浪微博","复制链接"};

    private int[] shareIcons = new int[] {R.drawable.pengyouquan_normal,R.drawable.weixin_login, R.drawable.qzone_normal, R.drawable.qq_normal,R.drawable.weibo_login,R.drawable.link_allshare_normal};

    private int[] shareIconspressed = new int[] {R.drawable.pengyouquan_pressed,R.drawable.weixin_pressed, R.drawable.qzone_pressed, R.drawable.qq_pressed,R.drawable.sina_pressed,R.drawable.link_allshare_pressed};
    private LayoutInflater inflater;

    public ShareAdapter(Context context)
    {
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return shareNames.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = inflater.inflate(R.layout.share_item, null);
        }
        final ImageView shareIcon = (ImageView) convertView.findViewById(R.id.share_icon);
        TextView shareTitle = (TextView) convertView.findViewById(R.id.share_title);
        shareIcon.setImageResource(shareIcons[position]);
        shareTitle.setText(shareNames[position]);

        return convertView;
    }
}
