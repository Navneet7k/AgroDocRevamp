package navneet.com.agrodocrevamp;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Navneet Krishna on 09/11/18.
 */
@Entity(tableName = "issue_table")
public class IssueModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "issue_name")
    private String issueName;
    private String issueDesc;
    private String issueSymptoms;
    private String issueState;
    private String issueResults;
    private String issueDate;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] extraImage;

    public IssueModel(String issueName, String issueDesc, String issueSymptoms) {
        this.issueName = issueName;
        this.issueDesc = issueDesc;
        this.issueSymptoms = issueSymptoms;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getIssueResults() {
        return issueResults;
    }

    public void setIssueResults(String issueResults) {
        this.issueResults = issueResults;
    }

    public String getIssueState() {
        return issueState;
    }

    public void setIssueState(String issueState) {
        this.issueState = issueState;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIssueName() {
        return issueName;
    }

    public String getIssueDesc() {
        return issueDesc;
    }

    public String getIssueSymptoms() {
        return issueSymptoms;
    }

    public int getId() {
        return id;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public byte[] getExtraImage() {
        return extraImage;
    }

    public void setExtraImage(byte[] extraImage) {
        this.extraImage = extraImage;
    }
}
