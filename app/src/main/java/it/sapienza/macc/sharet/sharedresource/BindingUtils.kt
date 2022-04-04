package it.sapienza.macc.sharet.sharedresourcecalendar

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import it.sapienza.macc.sharet.R
import it.sapienza.macc.sharet.database.SharedResourceEntity
import it.sapienza.macc.sharet.domain.SharedResource

@BindingAdapter("sharedResourceImage")
fun ImageView.setSharedResourceImage(item: SharedResource?) {
    item?.let {
        setImageResource(R.drawable.ic_baseline_whatshot_24)
    }
}

@BindingAdapter("sharedResourceName")
fun TextView.setSharedResourceName(item: SharedResource?) {
    item?.let {
        text = item.name
    }
}

@BindingAdapter("addUserImage")
fun ImageView.setUserImage(item: SharedResource?) {
    item?.let {
        setImageResource(R.drawable.ic_baseline_person_add_alt_1_24)
    }
}


@BindingAdapter("deleteResourceImage")
fun ImageView.deleteResourceImage(item: SharedResource?) {
    item?.let {
        setImageResource(R.drawable.ic_baseline_delete_24)
    }
}