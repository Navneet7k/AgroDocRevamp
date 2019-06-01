package navneet.com.agrodocrevamp;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Navneet Krishna on 10/11/18.
 */
@Dao
public interface IssueDao {

    @Insert
    void insert(IssueModel issueModel);

    @Update
    void update(IssueModel issueModel);

    @Delete
    void delete(IssueModel issueModel);

    @Query("DELETE FROM issue_table")
    void deleteAll();

    @Query("SELECT * FROM issue_table where id=:id")
    IssueModel getIssue(int id);

    @Query("SELECT * from issue_table ORDER BY id ASC")
    LiveData<List<IssueModel>> getAllIssues();
}
