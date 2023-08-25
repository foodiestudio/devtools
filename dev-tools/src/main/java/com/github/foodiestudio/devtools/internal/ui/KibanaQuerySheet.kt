package com.github.foodiestudio.devtools.internal.ui

import android.util.Patterns
import android.view.KeyEvent.ACTION_DOWN
import android.webkit.URLUtil
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material.icons.filled.Web
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.github.foodiestudio.devtools.kibana.kibanaQuery

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun KibanaQuerySheet(modifier: Modifier) {
    val clipboardManager = LocalClipboardManager.current
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    var siteUrl by remember {
        mutableStateOf("https://")
    }
    var siteUrlIsValid by remember {
        mutableStateOf(true)
    }

    var index by remember {
        mutableStateOf("")
    }

    var filters by remember {
        mutableStateOf(emptyList<FilterParam>())
    }

    val fieldMatched by remember {
        derivedStateOf {
            siteUrl.isNotBlank() && index.isNotBlank()
        }
    }

    LazyColumn(
        modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp),
    ) {
        item {
            DragHandle()

            Box(
                Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = "Kibana Query",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .align(Alignment.CenterStart)
                )
                if (fieldMatched) {
                    Button(onClick = {
                        siteUrlIsValid =
                            URLUtil.isValidUrl(siteUrl) && Patterns.WEB_URL.matcher(siteUrl)
                                .matches()
                        if (!siteUrlIsValid) {
                            return@Button
                        }
                        runCatching {
                            val url = kibanaQuery(siteUrl, index) {
                                filters.filter { it.isNotBlank }.forEach {
                                    filter {
                                        it.key.trim() to it.value.trim()
                                    }
                                }
                            }
                            clipboardManager.setText(buildAnnotatedString {
                                append(url.toString())
                            })
                        }.onSuccess {
                            Toast.makeText(context, "Copied.", Toast.LENGTH_SHORT).show()
                        }
                    }) {
                        Icon(Icons.Default.ContentCopy, null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(text = "Copy")
                    }
                }
            }
        }

        item {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                readOnly = true,
                value = " ~ Last 24 Hours",
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                leadingIcon = {
                    Icon(Icons.Default.Timelapse, null)
                },
                label = {
                    Text(text = "Time Range")
                }, onValueChange = {
                    // TODO: pick a time range
                })
        }
        item {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        siteUrlIsValid = if (it.isFocused) {
                            true
                        } else {
                            URLUtil.isValidUrl(siteUrl) && Patterns.WEB_URL
                                .matcher(siteUrl)
                                .matches()
                        }
                    },
                singleLine = true,
                value = siteUrl,
                isError = !siteUrlIsValid,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                leadingIcon = {
                    Icon(Icons.Default.Web, null)
                },
                placeholder = {
                    Text(text = "https://kibana.com/")
                }, label = {
                    Text(text = "Site Url")
                }, onValueChange = {
                    siteUrl = it
                })
        }

        item {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .onPreviewKeyEvent {
                        if (it.key == Key.Tab && it.nativeKeyEvent.action == ACTION_DOWN) {
                            focusManager.moveFocus(FocusDirection.Down)
                            true
                        } else {
                            false
                        }
                    },
                leadingIcon = {
                    Icon(Icons.Default.Numbers, null)
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                singleLine = true,
                value = index, label = {
                    Text(text = "Index")
                }, onValueChange = {
                    index = it
                })
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Filters",
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 8.dp)
                )
            }
        }
        itemsIndexed(filters) { index, item ->
            FilterValueItem(item, onUpdate = {
                filters = filters.toMutableList().apply {
                    set(index, it)
                }
            }, onDelete = {
                filters = filters.toMutableList().apply {
                    removeAt(index)
                }
            })
        }
        item {
            OutlinedButton(
                onClick = {
                    filters = filters.toMutableList().apply {
                        add(FilterParam())
                    }
                },
                border = BorderStroke(
                    ButtonDefaults.OutlinedBorderSize,
                    MaterialTheme.colors.primary.copy(alpha = 0.6f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            ) {
                Icon(Icons.Default.AddCircle, null)
                Text(text = "New filter param", Modifier.padding(start = 4.dp))
            }
        }
    }
}

data class FilterParam(
    val key: String = "",
    val value: String = ""
) {
    val isNotBlank: Boolean get() = key.isNotBlank() && value.isNotBlank()
}

@Composable
private fun FilterValueItem(
    param: FilterParam,
    onUpdate: (FilterParam) -> Unit,
    onDelete: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .onPreviewKeyEvent {
                    if (it.nativeKeyEvent.action == ACTION_DOWN) {
                        focusManager.moveFocus(FocusDirection.Down)
                        true
                    } else {
                        false
                    }
                },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            value = param.key, label = {
                Text(text = "Key")
            }, onValueChange = {
                onUpdate(param.copy(key = it))
            })
        OutlinedTextField(
            modifier = Modifier
                .weight(1f),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            value = param.value, label = {
                Text(text = "Value")
            }, onValueChange = {
                onUpdate(param.copy(value = it))
            })
        IconButton(onClick = onDelete, modifier = Modifier.size(24.dp)) {
            Icon(
                imageVector = Icons.Default.RemoveCircleOutline,
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
        }
    }
}

@Composable
private fun DragHandle() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
        Spacer(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .background(Color.Gray, RoundedCornerShape(50))
                .size(32.dp, 4.dp)
        )
    }
}

