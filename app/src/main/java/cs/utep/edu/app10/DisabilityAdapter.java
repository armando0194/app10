package cs.utep.edu.app10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class DisabilityAdapter extends RecyclerView.Adapter<DisabilityAdapter.ViewHolder> {
    Context ctx;
    DisabilityManager dm;

    public DisabilityAdapter(Context ctx){
        this.ctx = ctx;
        this.dm = new DisabilityManager();
    }
    public Context getContext(){
        return ctx;
    }

    /***
     * inflate the item layout and create the holder,
     */
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int pos) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View itemView = inflater.inflate(R.layout.disability, viewGroup, false);
        return new ViewHolder(itemView);
    }

    /**
     * Populate row with data from the model
     * @param viewHolder - row view
     * @param position   - row number
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Disability item = dm.get(position);
        // Set item views based on your views and data model
        Bitmap bm = BitmapFactory.decodeResource(ctx.getResources(),item.getResourceID());
        viewHolder.iconView.setImageBitmap(bm);
        viewHolder.iconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        viewHolder.likeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        viewHolder.dislikeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        viewHolder.reportView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }



    /**
     * @return - Returns the number of items
     */
    @Override
    public int getItemCount() {
        return dm.size();
    }

    /**
     * Adds an item to the recycler view
     */
    public void addItem(String name, String description, int resource){
        Disability temp = new Disability(name,description,resource);
        dm.add(temp);
        notifyItemInserted(dm.size()-1);
    }


    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder{
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView iconView;
        public ImageView likeView;
        public ImageView dislikeView;
        public ImageView reportView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            super(itemView);
            iconView = itemView.findViewById(R.id.accesible_icon);
            likeView = itemView.findViewById(R.id.like_icon);
            dislikeView = itemView.findViewById(R.id.dislike_icon);
            reportView = itemView.findViewById(R.id.report_icon);

        }


    }


}
