package datanapps.androidofflinecaching.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import datanapps.androidofflinecaching.R;
import datanapps.androidofflinecaching.network.users.model.User;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {


    private List<User> albumList = new ArrayList<>();

    private LayoutInflater inflater;


    public UserListAdapter(Context mContext) {

        inflater = LayoutInflater.from(mContext);
    }

    public  void setAlbumList(List<User> albumList){
        this.albumList = albumList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.layout_list_user, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        User user = albumList.get(position);

        Picasso.get().load(user.getAvatar()).into(holder.userIcon);

        holder.tvUserName.setText(user.getFirstName());

        holder.tvUserEmail.setText(user.getEmail());


    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout_list_user_name)
        TextView tvUserName;


        @BindView(R.id.layout_list_user_email)
        TextView tvUserEmail;


        @BindView(R.id.layout_list_user_icon)
        ImageView userIcon;


         public MyViewHolder(View view) {
            super(view);
             ButterKnife.bind(this, view);

        }
    }

}