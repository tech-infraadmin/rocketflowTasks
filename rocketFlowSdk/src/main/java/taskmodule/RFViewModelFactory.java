package taskmodule;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import taskmodule.data.DataManager;
import taskmodule.ui.base.BaseSdkViewModel;
import taskmodule.utils.rx.SchedulerProvider;

public class RFViewModelFactory implements ViewModelProvider.Factory {
    private final DataManager mDataManager;
    private final SchedulerProvider mSchedulerProvider;


    public RFViewModelFactory(DataManager dataManager, SchedulerProvider schedulerProvider) {
        mDataManager = dataManager;
        mSchedulerProvider = schedulerProvider;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new BaseSdkViewModel<>(mDataManager, mSchedulerProvider);
    }
}
