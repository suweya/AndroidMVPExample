package net.suweya.androidmvp.ui.interfaces;

import net.suweya.androidmvp.entities.DataEntity;

/**
 * IMainView
 */
public interface IMainView {

    void hideRefreshLoading();

    void onRefreshSuccess(DataEntity entity);

    void onDataLoadSuccess(DataEntity entity);

    void onRefreshError(String message);

    void onDataLoadError(String message);
}
