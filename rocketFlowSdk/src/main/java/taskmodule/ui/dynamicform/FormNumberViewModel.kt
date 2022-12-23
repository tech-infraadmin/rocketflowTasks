package taskmodule.ui.dynamicform

import androidx.databinding.ObservableField
import taskmodule.data.model.response.config.FormData

/**
 * Created by rahul on 1/4/19
 */
class FormNumberViewModel(val formData: FormData) {
    var enteredValue = ObservableField("")
    val title = ObservableField("")
    val hint = ObservableField("")
    val length = ObservableField(20)

    init {
        if (formData.enteredValue != null) {
            enteredValue.set(formData.enteredValue)
        }
        if (formData.title != null) {
            title.set(formData.title)
        }
        if (formData.placeHolder != null) {
            hint.set(formData.placeHolder)
        }
        if (formData.maxLength != 0) {
            length.set(formData.maxLength)
        }
    }
}