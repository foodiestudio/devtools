package com.github.foodiestudio.devtools.internal.ui

import android.content.Context
import android.content.Intent
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ListItem
import androidx.compose.material.Scaffold
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.InsertDriveFile
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import okio.Path
import okio.Path.Companion.toOkioPath
import okio.Path.Companion.toPath
import java.io.File
import java.sql.Timestamp

@Composable
internal fun AppFilesScreen() {
    var navStack: List<BreadcrumbItem> by remember {
        mutableStateOf(listOf(BreadcrumbItem.Root))
    }

    BackHandler(navStack.size > 1) {
        navStack = navStack.toMutableList().apply {
            removeLast()
        }
    }

    val current by remember {
        derivedStateOf {
            navStack.last()
        }
    }

    val fileList: List<File> by remember {
        derivedStateOf {
            current.list()!!
        }
    }

    val state = rememberScaffoldState()
    val context = LocalContext.current

    Scaffold(scaffoldState = state, modifier = Modifier.statusBarsPadding(), topBar = {
        Column {
            TopAppBar(title = {
                Text(current.displayName)
            }, navigationIcon = {
                BackNavButton()
            })
            Breadcrumbs(Modifier, navStack) { item ->
                if (item != current) {
                    val index = navStack.indexOfFirst {
                        it == item
                    }
                    navStack = navStack.take(index + 1)
                }
            }
        }
    }, content = { paddingValues ->
        // 如果是根目录的话，
        if (current == BreadcrumbItem.Root) {
            AppStorage(Modifier.navigationBarsPadding()) { nav ->
                navStack = navStack.toMutableList().apply {
                    add(nav)
                }
            }
        } else {
            FileList(
                Modifier
                    .navigationBarsPadding()
                    .padding(paddingValues),
                fileList,
                onShare = {
                    val uri =
                        FileProvider.getUriForFile(
                            context,
                            context.packageName + ".fileprovider",
                            it
                        )
                    val mime: String =
                        context.contentResolver.getType(uri) ?: MimeTypeMap.getSingleton()
                            .getMimeTypeFromExtension(it.extension) ?: "*/*"

                    runCatching {
                        Intent().apply {
                            action = Intent.ACTION_SEND
                            type = mime
                            putExtra(Intent.EXTRA_STREAM, uri)
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            context.startActivity(this)
                        }
                    }.onFailure { err ->
                        Toast.makeText(context, err.message, Toast.LENGTH_SHORT).show()
                        err.printStackTrace()
                    }
                },
                onItemClick = {
                    if (it.isDirectory) {
                        navStack = navStack.toMutableList().apply {
                            add(BreadcrumbItem.Regular(it))
                        }
                    } else {
                        runCatching {
                            val uri =
                                FileProvider.getUriForFile(
                                    context,
                                    context.packageName + ".fileprovider",
                                    it
                                )
                            val mime: String =
                                context.contentResolver.getType(uri) ?: MimeTypeMap.getSingleton()
                                    .getMimeTypeFromExtension(it.extension) ?: "*/*"

                            Intent().apply {
                                action = Intent.ACTION_VIEW
                                setDataAndType(uri, mime)
                                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                context.startActivity(this)
                            }
                        }.onFailure { err ->
                            Toast.makeText(context, err.message, Toast.LENGTH_SHORT).show()
                            err.printStackTrace()
                        }
                    }
                })
        }
    })
}

@Composable
private fun BackNavButton() {
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    IconButton(onClick = { onBackPressedDispatcher?.onBackPressed() }) {
        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun AppStorage(modifier: Modifier, onItemClick: (BreadcrumbItem) -> Unit) {
    val context = LocalContext.current

    Column(modifier) {
        ListItem(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onItemClick(
                        BreadcrumbItem.Internal(context)
                    )
                },
            icon = {
                Icon(Icons.Filled.Folder, null)
            },
            text = {
                Text("Internal Storage")
            },
        )
        ListItem(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onItemClick(
                        BreadcrumbItem.External(context),
                    )
                },
            icon = {
                Icon(Icons.Filled.Folder, null)
            },
            text = {
                Text("External Storage")
            },
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun FileList(
    modifier: Modifier,
    fileList: List<File>,
    onItemClick: (File) -> Unit,
    onShare: (File) -> Unit
) {
    if (fileList.isEmpty()) {
        NoFiles(modifier)
    }
    LazyColumn(modifier) {
        items(fileList) {
            ListItem(
                modifier = Modifier.clickable {
                    onItemClick(it)
                },
                icon = {
                    when {
                        it.isDirectory -> if (it.list()?.isEmpty() == true) {
                            Icon(Icons.Filled.FolderOpen, null)
                        } else {
                            Icon(Icons.Filled.Folder, null)
                        }

                        else -> Icon(Icons.Filled.InsertDriveFile, null)
                    }
                },
                text = {
                    Text(it.name)
                },
                secondaryText = if (!it.isDirectory) {
                    @Composable {
                        Text(text = Timestamp(it.lastModified()).toString())
                    }
                } else null,
                trailing = if (!it.isDirectory) {
                    @Composable {
                        IconButton(onClick = { onShare(it) }) {
                            Icon(Icons.Filled.Share, "share")
                        }
                    }
                } else null
            )
        }
    }
}

@Composable
private fun NoFiles(modifier: Modifier) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = Icons.Default.InsertDriveFile,
                contentDescription = null,
                Modifier.size(148.dp)
            )
            Text(text = "No files")
        }
    }
}

private sealed class BreadcrumbItem(
    val displayName: String, val path: Path
) {
    open fun list(): List<File>? = path.toFile().listFiles()?.toList()

    class Regular(
        file: File
    ) : BreadcrumbItem(
        file.nameWithoutExtension, file.toOkioPath()
    )

    object Root : BreadcrumbItem(
        "App Storage", "/".toPath()
    ) {
        override fun list(): List<File>? = null
    }

    class Internal(private val context: Context) : BreadcrumbItem(
        "Internal", "/internal".toPath()
    ) {

        override fun list(): List<File> = listOf(
            context.filesDir, context.cacheDir
        )
    }

    class External(private val context: Context) : BreadcrumbItem(
        "External", "/external".toPath()
    ) {

        override fun list(): List<File> = listOf(
            context.getExternalFilesDir(null)!!, context.externalCacheDir!!
        )
    }
}


@Composable
private fun Breadcrumbs(
    modifier: Modifier, items: List<BreadcrumbItem>, onItemSelected: (BreadcrumbItem) -> Unit
) {
    var selectedIndex by remember(items) {
        mutableStateOf(items.lastIndex)
    }

    ScrollableTabRow(modifier = modifier.fillMaxWidth(),
        selectedTabIndex = selectedIndex,
        divider = {},
        tabs = {
            items.forEachIndexed { tabIndex, tab ->
                Tab(selected = selectedIndex == tabIndex, onClick = {
                    onItemSelected(tab)
                    selectedIndex = tabIndex
                }, text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = tab.displayName)
                        if (tabIndex != items.lastIndex) {
                            Icon(
                                imageVector = Icons.Default.ArrowRight,
                                contentDescription = null
                            )
                        }
                    }
                })
            }
        })
}
