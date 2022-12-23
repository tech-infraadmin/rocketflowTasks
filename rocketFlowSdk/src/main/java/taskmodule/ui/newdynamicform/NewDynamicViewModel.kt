package taskmodule.ui.newdynamicform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import taskmodule.TrackiSdkApplication
import taskmodule.data.DataManager
import taskmodule.data.model.request.OtpRequest
import taskmodule.data.model.response.config.Api
import taskmodule.data.network.APIError
import taskmodule.data.network.ApiCallback
import taskmodule.data.network.HttpManager
import taskmodule.ui.base.BaseSdkViewModel
import taskmodule.utils.ApiType
import taskmodule.utils.rx.AppSchedulerProvider
import taskmodule.utils.rx.SchedulerProvider
import java.io.File
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class NewDynamicViewModel (dataManager: DataManager, schedulerProvider: SchedulerProvider) :
        BaseSdkViewModel<NewDynamicNavigator>(dataManager, schedulerProvider) {

    private lateinit var httpManager: HttpManager


    fun executiveMap(position: Int,target: String , httpManager: HttpManager,rollId:String?) {
        this.httpManager = httpManager
        ExecutiveMap(position,target,rollId).hitApi()
    }

    private inner class ExecutiveMap(val position: Int,var target :String,var rollId:String?) : ApiCallback {

        override fun onResponse(result: Any?, error: APIError?) {
            navigator.handleExecutiveMapResponse(position, this, result, error)
        }

        override fun hitApi() {
            var apiMap=ApiType.valueOf(target)
            val api = TrackiSdkApplication.getApiMap()[apiMap]
            if(rollId!=null){
                var queryString="?roleIds="+ URLEncoder.encode(rollId, StandardCharsets.UTF_8.toString())
                var apiNew= Api()
                apiNew.url=api!!.url+queryString
                apiNew.name=api.name
                apiNew.timeOut=api!!.timeOut
                apiNew.cacheable=api!!.cacheable
                dataManager.executeMap(this@ExecutiveMap, httpManager, apiNew)

            }else{
                dataManager.executeMap(this@ExecutiveMap, httpManager, api)
            }
        }

        override fun isAvailable(): Boolean {
            return true
        }

        override fun onNetworkErrorClose() {
        }

        override fun onRequestTimeOut(callBack: ApiCallback) {
            navigator.showTimeOutMessage(callBack)
        }

        override fun onLogout() {
        }

    }

    fun getTaskData(httpManager: HttpManager, data:Any?) {
        this.httpManager = httpManager
        GetTaskData(data).hitApi()
    }

    private inner class GetTaskData(var data:Any?) : ApiCallback {

        override fun onResponse(result: Any?, error: APIError?) {
            navigator.handleGetTaskDataResponse(this, result, error)
        }

        override fun hitApi() {
            val api = TrackiSdkApplication.getApiMap()[ApiType.GET_TASK_DATA]
            dataManager.taskData(this, httpManager, data, api)
        }

        override fun isAvailable(): Boolean {
            return true
        }

        override fun onNetworkErrorClose() {
        }

        override fun onRequestTimeOut(callBack: ApiCallback) {
            navigator.showTimeOutMessage(callBack)
        }

        override fun onLogout() {
        }
    }

    fun uploadFileList(hashMap: HashMap<String, ArrayList<File>>, httpManager: HttpManager) {
        this.httpManager = httpManager
        UploadFiles(hashMap).hitApi()
    }

    inner class UploadFiles(val hashMap: HashMap<String, ArrayList<File>>) : ApiCallback {

        override fun onResponse(result: Any?, error: APIError?) {
            navigator.handleUploadFileResponse(this, result, error)
        }

        override fun hitApi() {
            val api = TrackiSdkApplication.getApiMap()[ApiType.UPLOAD_FILE]
            dataManager.uploadFiles(this@UploadFiles, httpManager, hashMap, api)
        }

        override fun isAvailable(): Boolean {
            return true
        }

        override fun onNetworkErrorClose() {
        }

        override fun onRequestTimeOut(callBack: ApiCallback) {
            navigator.showTimeOutMessage(callBack)
        }

        override fun onLogout() {
        }

    }

    fun verifyOtpServerRequest(otpRequest: OtpRequest, httpManager: HttpManager?) {
        this.httpManager = httpManager!!
        VerifyOtp(otpRequest).hitApi()
    }

    inner class VerifyOtp(val otpRequest: OtpRequest) : ApiCallback {

        override fun onResponse(result: Any?, error: APIError?) {
            navigator.handleUploadFileResponse(this, result, error)
        }

        override fun hitApi() {
            val api = TrackiSdkApplication.getApiMap()[ApiType.VERIFY_MOBILE]
            dataManager.verifyOtp(httpManager, this, otpRequest, api)
        }

        override fun isAvailable(): Boolean {
            return true
        }

        override fun onNetworkErrorClose() {
        }

        override fun onRequestTimeOut(callBack: ApiCallback) {
            navigator.showTimeOutMessage(callBack)
        }

        override fun onLogout() {
        }

    }


    internal class Factory(private val mDataManager: DataManager) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NewDynamicViewModel(mDataManager, AppSchedulerProvider()) as T
        }
    }


}