package navneet.com.agrodocrevamp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

/**
 * Created by Navneet Krishna on 10/11/18.
 */
public class IssueViewModel extends AndroidViewModel {

    private IssueRepository mRepository;

    private LiveData<List<IssueModel>> mAllIssues;

    public IssueViewModel(Application application) {
        super(application);
        mRepository = new IssueRepository(application);
        mAllIssues = mRepository.getAllIssues();
    }

    LiveData<List<IssueModel>> getAllWords() {
        return mAllIssues;
    }

    public void insert(IssueModel issueModel) {
        mRepository.insert(issueModel);
    }

    public void delete(IssueModel issueModel) {
        mRepository.delete(issueModel);
    }

    public void update(IssueModel issueModel) {
        mRepository.update(issueModel);
    }
}
