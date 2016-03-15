package net.suweya.androidmvp.presenter;

import net.suweya.androidmvp.base.BaseModel;
import net.suweya.androidmvp.base.BasePresenter;
import net.suweya.androidmvp.entities.DataEntity;
import net.suweya.androidmvp.model.MainModel;
import net.suweya.androidmvp.ui.interfaces.IMainView;

/**
 * MainPresenter
 */
public class MainPresenter extends BasePresenter<IMainView> {

    private static final int DEFAULT_START_PAGE_INDEX = 1;

    private MainModel mMainModel;

    public MainPresenter() {
        mMainModel = new MainModel();
    }

    public void refreshData() {
        loadData(DEFAULT_START_PAGE_INDEX, true);
    }

    public void loadNextPageData(int pageIndex) {
        loadData(pageIndex, false);
    }

    private void loadData(int pageIndex, final boolean refresh) {
        final IMainView view = getView();
        if (view != null) {
            mMainModel.getDataByPage(pageIndex, new BaseModel.OnResponseCallback<DataEntity>() {
                @Override
                public void onComplete() {
                    if (refresh) {
                        view.hideRefreshLoading();
                    }
                }

                @Override
                public void onSuccess(DataEntity response) {
                    if (refresh) {
                        view.onRefreshSuccess(response);
                    } else {
                        view.onDataLoadSuccess(response);
                    }
                }

                @Override
                public void onError(String message) {
                    view.onDataLoadError(message);
                }
            });
        }
    }

    @Override
    public void onCancel() {
        mMainModel.cancel();
    }
}
