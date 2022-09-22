package com.example.opgg_test.presenter.adapter

import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.opgg_test.R
import com.example.opgg_test.presenter.MainUiState

@BindingAdapter("uiState")
fun TextView.setUiStateForLoading(uiState: MainUiState) {
    visibility = if (uiState.isLoading || uiState.isError) {
        View.VISIBLE
    } else {
        View.GONE
    }

    text = if (uiState.isError) {
        "에러가 발생하였습니다."
    } else {
        "잠시만 기다려주시기 바랍니다."
    }
}

@BindingAdapter("uiState")
fun AppCompatButton.isError(uiState: MainUiState) {
    visibility = if (uiState.isError) {
        View.VISIBLE
    } else {
        View.GONE
    }
}


@BindingAdapter("uiState")
fun ProgressBar.setUiStateForLoading(uiState: MainUiState) {
    visibility = if (uiState.isLoading) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("uiState")
fun RecyclerView.setUiStateForLoading(uiState: MainUiState) {
    visibility = if (uiState.isLoading) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter(value = ["wins", "losses"])
fun TextView.setWinLose(win: Int, losses: Int) {
    text = "${win}승 ${losses}패"
}

@BindingAdapter(
    requireAll = false,
    value = ["kill", "death", "assist", "darkgray","isTypeDouble"]
)
fun TextView.setKda(
    kill: Double,
    death: Double,
    assist: Double,
    darkgray: Boolean,
    isTypeDouble: Boolean = false
) {
    val stringBuilder = SpannableStringBuilder()
    stringBuilder.append(
        setTextColor(
            if(isTypeDouble){
                kill.toString()
            } else {
                kill.toInt().toString()
            },
            context.resources.getColor(
                if (darkgray) {
                    R.color.dark_grey
                } else {
                    R.color.charcoal_grey
                }, null
            )
        )
    )
    stringBuilder.append(" / ")
    stringBuilder.append(
        setTextColor(
            if(isTypeDouble){
                death.toString()
            } else {
                death.toInt().toString()
            },
            context.resources.getColor(R.color.darkish_pink, null)
        )
    )
    stringBuilder.append(" / ")
    stringBuilder.append(
        setTextColor(
            if(isTypeDouble){
                assist.toString()
            } else {
                assist.toInt().toString()
            },
            context.resources.getColor(
                if (darkgray) {
                    R.color.dark_grey
                } else {
                    R.color.charcoal_grey
                }, null
            )
        )
    )
    text = stringBuilder
}

@BindingAdapter(
    requireAll = false,
    value = ["kda", "winper"]
)
fun TextView.setKda(
    kda: String,
    winper: String,
) {
    val stringBuilder = SpannableStringBuilder()
    stringBuilder.append(setTextColor(kda, context.resources.getColor(R.color.azure, null)))
    stringBuilder.append(setTextColor(" (", context.resources.getColor(R.color.darkish_pink, null)))
    stringBuilder.append(
        setTextColor(
            winper,
            context.resources.getColor(R.color.darkish_pink, null)
        )
    )
    stringBuilder.append(setTextColor(")", context.resources.getColor(R.color.darkish_pink, null)))
    text = stringBuilder
}


@BindingAdapter(
    requireAll = false,
    value = ["champWinPer"]
)
fun TextView.setChampPer(
    champWinPer: Int
) {
    val stringBuilder = SpannableStringBuilder()
    val color = when (champWinPer) {
        in 0..50 -> {
            context.resources.getColor(R.color.dark_grey, null)
        }
        in 50..75 -> {
            context.resources.getColor(R.color.periwinkle, null)
        }
        else -> {
            context.resources.getColor(R.color.darkish_pink, null)
        }
    }
    stringBuilder.append(setTextColor(champWinPer.toString(), color))
    stringBuilder.append(setTextColor("%", color))
    text = stringBuilder
}


@BindingAdapter(
    "isWin"
)
fun TextView.isWin(isWin: Boolean) {
    text = if (isWin) {
        "승"
    } else {
        "패"
    }
}

@BindingAdapter(
    "scorebadge"
)
fun TextView.isShowScoreBadge(scoreBadge: String) {
    when (scoreBadge) {
        "ACE" -> {
            text = scoreBadge
            background = resources.getDrawable(R.drawable.bg_ace_badge, null)
            visibility = View.VISIBLE
        }
        "MVP" -> {
            text = scoreBadge
            background = resources.getDrawable(R.drawable.bg_mvp_badge, null)
            visibility = View.VISIBLE
        }
        else -> {
            visibility = View.GONE
        }
    }
}

@BindingAdapter(
    "contributionForKillRate"
)
fun TextView.setKillRate(contributionForKillRate: String) {
    text = "킬관여 $contributionForKillRate"
}

@BindingAdapter(
    "multikillbadge"
)
fun TextView.setMultikillBadge(isMultiKill: String) {
    if (isMultiKill.isNotEmpty()) {
        text = isMultiKill
        visibility = View.VISIBLE
    } else {
        visibility = View.GONE
    }
}

@BindingAdapter(
    "gameLength"
)
fun TextView.setGameLength(gameLength: Long) {
    val minutes = gameLength / 1000 / 60
    val seconds = gameLength / 1000 % 60
    text = "$minutes:$seconds"
}

@BindingAdapter(
    "createDate"
)
fun TextView.setCompareTheTime(createDate: Long) {
    val currentTime = System.currentTimeMillis() / 1000
    val m: Long = (currentTime - createDate) / 60 % 60
    text = "${m}분 전"
}


@BindingAdapter(
    requireAll = false,
    value = ["imageUrl", "circleCrop"]
)
fun ImageView.setRoundImageFromUrl(
    imageUrl: String?,
    circleCrop: Boolean,
) {
    if (imageUrl != null) {
        Glide.with(context).clear(this)
        Glide.with(this).load(imageUrl).let { request ->
            if (circleCrop) {
                request.circleCrop()
            }
            request.placeholder(
                resources.getDrawable(
                    com.google.android.material.R.drawable.mtrl_ic_error,
                    null
                )
            )
            request.error(
                resources.getDrawable(
                    com.google.android.material.R.drawable.ic_mtrl_chip_close_circle,
                    null
                )
            )
                .fallback(
                    resources.getDrawable(
                        com.google.android.material.R.drawable.ic_mtrl_chip_close_circle,
                        null
                    )
                )
            request.into(this)
        }
    }
}

@BindingAdapter(
    requireAll = false,
    value = ["itemImgUrl", "circleCrop"]
)
fun ImageView.setItemImage(
    itemImgUrl: String?,
    circleCrop: Boolean,
) {
    if (itemImgUrl != null) {
        clipToOutline = true
        Glide.with(context).clear(this)
        Glide.with(this).load(itemImgUrl).let { request ->
            if (circleCrop) {
                request.circleCrop()
            }
            request.placeholder(
                resources.getDrawable(
                    com.google.android.material.R.drawable.mtrl_ic_error,
                    null
                )
            )
            request.error(
                resources.getDrawable(
                    com.google.android.material.R.drawable.ic_mtrl_chip_close_circle,
                    null
                )
            )
                .fallback(
                    resources.getDrawable(
                        com.google.android.material.R.drawable.ic_mtrl_chip_close_circle,
                        null
                    )
                )
            request.into(this)
        }
    }
}

@BindingAdapter(
    requireAll = false,
    value = ["position"]
)
fun ImageView.setPosition(
    position: String?,
) {
    Glide.with(this).load(
        when (position) {
            "TOP" -> {
                resources.getDrawable(R.drawable.icon_lol_top, null)
            }
            "JNG" -> {
                resources.getDrawable(R.drawable.icon_lol_jng, null)
            }
            "MID" -> {
                resources.getDrawable(R.drawable.icon_lol_mid, null)
            }
            "ADC" -> {
                resources.getDrawable(R.drawable.icon_lol_bot, null)
            }
            "SUP" -> {
                resources.getDrawable(R.drawable.icon_lol_sup, null)
            }
            else -> {
                ""
            }
        }
    ).let { request ->
        request.into(this)
    }
}


@BindingAdapter(
    "isWin"
)
fun View.isWin(isWin: Boolean) {
    setBackgroundColor(
        if (isWin) {
            context.resources.getColor(R.color.soft_blue, null)
        } else {
            context.resources.getColor(R.color.darkish_pink, null)
        }
    )
}

fun setTextColor(value: String, color: Int): SpannableString {
    val spannable = SpannableString(value)
    spannable.setSpan(
        ForegroundColorSpan(color),
        0,
        value.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return spannable
}
