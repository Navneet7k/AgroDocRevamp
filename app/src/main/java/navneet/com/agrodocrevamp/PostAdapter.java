package navneet.com.agrodocrevamp;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;


/**
 * Created by navneet on 19/03/18.
 */

public class
PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private ArrayList<TimeLineModel> records = new ArrayList<>();
    private Context context;
    private TimelineInterface timelineInterface;


    public PostAdapter(ArrayList<TimeLineModel> records, Context context,TimelineInterface timelineInterface) {
        this.context = context;
        this.records = records;
        this.timelineInterface=timelineInterface;
    }

    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timeline_line_padding, parent, false);
        return new PostAdapter.ViewHolder(view,viewType);
    }

    public void setRecords(ArrayList<TimeLineModel> records) {
        this.records=records;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }

    @Override
    public void onBindViewHolder(PostAdapter.ViewHolder holder, final int position) {

        TimeLineModel timeLineModel = records.get(position);

        if (timeLineModel.getStatus() == OrderStatus.INACTIVE) {
            holder.status.setVisibility(View.VISIBLE);
            holder.status.setText("SCAN");
            holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(context, R.drawable.ic_marker_inactive, android.R.color.darker_gray));
        } else if (timeLineModel.getStatus() == OrderStatus.ACTIVE) {
            holder.status.setVisibility(View.VISIBLE);
            holder.status.setText("RE-SCAN");
            holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(context, R.drawable.ic_marker_active, R.color.colorPrimary));
        } else {
            holder.status.setVisibility(View.VISIBLE);
            holder.status.setText("COMPLETED - CLICK TO VIEW ANALYSIS");
            holder.mTimelineView.setMarker(ContextCompat.getDrawable(context, R.drawable.ic_marker), ContextCompat.getColor(context, R.color.colorPrimary));
        }
        holder.title.setText(timeLineModel.getMessage());
        holder.status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timelineInterface.onScanClicked();
            }
        });
        holder.control_measure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timelineInterface.onControlMeasures(timeLineModel.getMessage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return (records!=null? records.size():0);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title,status,control_measure;
        private TimelineView mTimelineView;

        public ViewHolder(View view, int viewType) {
            super(view);

            mTimelineView = (TimelineView) view.findViewById(R.id.time_marker);
            mTimelineView.initLine(viewType);
            title = (TextView) view.findViewById(R.id.text_timeline_title);
            status = (TextView)view.findViewById(R.id.text_timeline_date);
            control_measure = (TextView)view.findViewById(R.id.control_measure);
        }
    }
}
