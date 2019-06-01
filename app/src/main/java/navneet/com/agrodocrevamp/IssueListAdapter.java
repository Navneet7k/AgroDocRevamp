package navneet.com.agrodocrevamp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Navneet Krishna on 10/11/18.
 */
public class IssueListAdapter extends RecyclerView.Adapter<IssueListAdapter.IssueViewHolder> {

    class IssueViewHolder extends RecyclerView.ViewHolder {
        private final TextView issue_name,issue_desc,issue_symptom,issue_date;
//        private final Button delete_issue;
        private final RelativeLayout click_overlay;
        private final ImageView cancel_bt;

        private IssueViewHolder(View itemView) {
            super(itemView);
            issue_name = itemView.findViewById(R.id.issue_name);
            cancel_bt = itemView.findViewById(R.id.cancel_bt);
            issue_date = itemView.findViewById(R.id.issue_date);
            issue_desc = itemView.findViewById(R.id.issue_desc);
            issue_symptom = itemView.findViewById(R.id.issue_symptoms);
//            delete_issue = itemView.findViewById(R.id.delete_issue);
            click_overlay = itemView.findViewById(R.id.click_overlay);
        }
    }

    private final LayoutInflater mInflater;
    private List<IssueModel> mIssues;
    private onDeleteIssue onDeleteIssue;// Cached copy of words
    private int rowIndex=-1;

    IssueListAdapter(Context context, onDeleteIssue onDeleteIssue) {
        this.onDeleteIssue=onDeleteIssue;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public IssueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.issue_row, parent, false);
        return new IssueViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final IssueViewHolder holder, final int position) {

        if (mIssues != null) {
            IssueModel current = mIssues.get(position);
            holder.issue_name.setText("Issue : "+current.getIssueName());
            holder.issue_desc.setText("Description : "+current.getIssueDesc());
            holder.issue_symptom.setText("Symptoms : "+current.getIssueSymptoms());
            holder.issue_date.setText("Date : "+current.getIssueDate());
        } else {
            // Covers the case of data not being ready yet.
            holder.issue_name.setText("No Issue");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteIssue.onIssueSelected(holder.getAdapterPosition(),mIssues.get(position));
                rowIndex=position;
                notifyDataSetChanged();
            }
        });

        if(rowIndex==position) {
        holder.click_overlay.setVisibility(View.VISIBLE);
        } else {
            holder.click_overlay.setVisibility(View.GONE);
        }

        holder.cancel_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteIssue.onDeleteIssueClicked(mIssues.get(position));
            }
        });
    }

    void setIssues(List<IssueModel> issueModels){
        mIssues = issueModels;
        notifyDataSetChanged();
    }

    void removeIssue(IssueModel issueModel) {
        mIssues.remove(issueModel);
        notifyDataSetChanged();
    }
    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mIssues != null)
            return mIssues.size();
        else return 0;
    }
}
