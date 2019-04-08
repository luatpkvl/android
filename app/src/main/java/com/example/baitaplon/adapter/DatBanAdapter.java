package com.example.baitaplon.adapter;

        import android.content.Context;
        import android.support.annotation.NonNull;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.example.baitaplon.Interface.OnItemRVListener;
        import com.example.baitaplon.R;
        import com.example.baitaplon.struct_class.DatBan;

        import java.util.List;

public class DatBanAdapter extends RecyclerView.Adapter<DatBanAdapter.ViewHolder> {
    public static String ACTION_DEL = "actionDel";

    private Context context;
    private List<DatBan> datBanList;
    public DatBanAdapter(Context context, List<DatBan> datBanList) {
        this.context = context;
        this.datBanList = datBanList;
    }
    OnItemRVListener onItemRVListener;
    public void setOnItemRVListener(OnItemRVListener onItemRVListener) {
        this.onItemRVListener = onItemRVListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mondadat,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        DatBan datBan = datBanList.get(position);
        viewHolder.tv_tenmon.setText(datBan.getTen_mon());
        viewHolder.tv_soluong.setText(datBan.getSoluong()+"");
        viewHolder.tv_dongia.setText(datBan.getGia()+"");

    }

    @Override
    public int getItemCount() {
        return datBanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tenmon;
        TextView tv_soluong;
        TextView tv_dongia;
        ImageView img_del;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tv_tenmon = itemView.findViewById(R.id.tvnamemonan_chitiet);
            tv_soluong = itemView.findViewById(R.id.tvsoluong_chitiet);
            tv_dongia = itemView.findViewById(R.id.tvgiamonan_chitiet);
            itemView.findViewById(R.id.btndel_chitiet).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos =  getAdapterPosition();
                    if(onItemRVListener!=null){
                        onItemRVListener.onClick(pos,ACTION_DEL);
                    }
                }
            });
        }
    }
}
