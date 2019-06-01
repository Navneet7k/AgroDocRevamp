package navneet.com.agrodocrevamp;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by Navneet Krishna on 10/11/18.
 */
public class IssueRepository {

    private IssueDao mIssueDao;
    private LiveData<List<IssueModel>> mAllIssues;

    IssueRepository(Application application) {
        IssueRoomDatabase db = IssueRoomDatabase.getDatabase(application);
        mIssueDao = db.issueDao();
        mAllIssues = mIssueDao.getAllIssues();
    }

    LiveData<List<IssueModel>> getAllIssues() {
        return mAllIssues;
    }


    public void insert (IssueModel issueModel) {
        new insertAsyncTask(mIssueDao).execute(issueModel);
    }

    public void update (IssueModel issueModel) {
        new updateAsyncTask(mIssueDao).execute(issueModel);
    }

    public void delete (IssueModel issueModel) {
        new deleteAsyncTask(mIssueDao).execute(issueModel);
    }

    private static class insertAsyncTask extends AsyncTask<IssueModel, Void, Void> {

        private IssueDao mAsyncTaskDao;

        insertAsyncTask(IssueDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final IssueModel... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<IssueModel, Void, Void> {

        private IssueDao mAsyncTaskDao;

        updateAsyncTask(IssueDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final IssueModel... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<IssueModel, Void, Void> {

        private IssueDao mAsyncTaskDao;

        deleteAsyncTask(IssueDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final IssueModel... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}

