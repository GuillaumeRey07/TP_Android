package io.gresse.hugo.tp3;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.curioustechizen.ago.RelativeTimeTextView;

import java.util.List;

/**
 * Display chat messages
 * <p>
 * Created by Hugo Gresse on 26/11/2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private Listener mListener;
    private List<Message> mData;
    static final int TYPE_SENT = 1;
    static final int TYPE_RECEIVED = 2;

    public MessageAdapter(Listener listener, List<Message> data) {
        mListener = listener;
        mData = data;
    }

    public void setData(List<Message> data) {
        mData = data;
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //System.out.println("viewtype " + getItemViewType(viewType));
        View view = null;
        if (getItemViewType(viewType) == TYPE_RECEIVED){
            System.out.println("TYPE_RECEIVED");
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_messages_received, parent, false);
        }else{
            System.out.println("TYPE_SENT");
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_messages_sent, parent, false);

        }

        //view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_messages_sent, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position){
        //System.out.println("LEMAL " + mData.get(position).userEmail + "   " + NamePickerActivity.leMail);
        if (mData.get(position).userEmail == NamePickerActivity.leMail){
            return TYPE_SENT;
        }else{
            return TYPE_RECEIVED;
        }

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mUserImageView;
        TextView  mUserTextView;
        TextView  mContentTextView;
        RelativeTimeTextView mRelativeTimeTextView;

        ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            mUserImageView = itemView.findViewById(R.id.userImageView);
            mUserTextView = itemView.findViewById(R.id.userTextView);
            mContentTextView = itemView.findViewById(R.id.contentTextView);
            mRelativeTimeTextView = itemView.findViewById(R.id.timeStampView);
        }

        void setData(Message message) {
            mUserTextView.setText(message.userName + ": ");
            //  EDIT
            mContentTextView.setText(message.content);
            mRelativeTimeTextView.setReferenceTime(message.timestamp);
            //  EDIT
            if (!TextUtils.isEmpty(message.userEmail)) {
                Glide
                        .with(mUserImageView.getContext())
                        .load(Constant.GRAVATAR_PREFIX + Utils.md5(message.userEmail))
                        .apply(RequestOptions.circleCropTransform())
                        .into(mUserImageView);
            } else {
                mUserImageView.setImageResource(R.color.colorAccent);
            }
        }

        @Override
        public void onClick(View view) {
            mListener.onItemClick(getAdapterPosition(), mData.get(getAdapterPosition()));
        }

    }

    public interface Listener {
        void onItemClick(int position, Message message);
    }
}
