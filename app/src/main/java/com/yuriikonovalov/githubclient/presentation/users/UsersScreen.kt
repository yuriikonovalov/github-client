package com.yuriikonovalov.githubclient.presentation.users

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.yuriikonovalov.githubclient.R
import com.yuriikonovalov.githubclient.domain.entities.User
import com.yuriikonovalov.githubclient.presentation.common.Divider
import com.yuriikonovalov.githubclient.presentation.common.LoadingInitialDataError
import com.yuriikonovalov.githubclient.presentation.common.LoadingInitialDataIndicator
import com.yuriikonovalov.githubclient.presentation.common.LoadingNextPageError
import com.yuriikonovalov.githubclient.presentation.common.LoadingNextPageIndicator
import com.yuriikonovalov.githubclient.presentation.common.RefreshingDataIndicator

@Composable
fun UsersScreen(
    onUserClick: (login: String) -> Unit,
    viewModel: UsersScreenViewModel = hiltViewModel()
) {
    val users = viewModel.state.collectAsLazyPagingItems()

    UsersScreenContent(
        modifier = Modifier.fillMaxSize(),
        users = users,
        onUserClick = onUserClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UsersScreenContent(
    modifier: Modifier,
    users: LazyPagingItems<User>,
    onUserClick: (login: String) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(
                modifier = Modifier.fillMaxWidth(),
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding
        ) {
            RefreshingDataIndicator(
                modifier = Modifier.fillMaxWidth(),
                // Visible when:
                // 1. ongoing refreshing from remote;
                // 2. cache items are displayed.
                visible = users.loadState.refresh is LoadState.Loading && users.itemCount > 0
            )

            items(users.itemCount) { index ->
                val user = users[index]
                if (user != null)
                    UserListItem(
                        modifier = Modifier.fillMaxWidth(),
                        login = user.login.value,
                        avatarUrl = user.avatar.url,
                        onClick = { onUserClick(user.login.value) }
                    )
            }


            LoadingNextPageIndicator(
                modifier = Modifier.fillMaxWidth(),
                // Visible when: a next page is being loaded.
                visible = users.loadState.append is LoadState.Loading,
                textResId = R.string.users_screen_loading_next_page
            )

            LoadingNextPageError(
                modifier = Modifier.fillMaxWidth(),
                visible = users.loadState.append is LoadState.Error,
                message = if (users.loadState.append is LoadState.Error) {
                    (users.loadState.append as LoadState.Error).error.message
                } else {
                    null
                },
                onRetryClick = users::refresh
            )

            LoadingInitialDataIndicator(
                visible = users.loadState.refresh is LoadState.Loading && users.itemCount == 0
            )

            LoadingInitialDataError(
                visible = users.loadState.refresh is LoadState.Error && users.itemCount == 0,
                message = if (users.loadState.refresh is LoadState.Error && users.itemCount == 0) {
                    (users.loadState.refresh as LoadState.Error).error.message
                } else {
                    null
                },
                onRetryClick = users::refresh
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    modifier: Modifier,
    scrollBehavior: TopAppBarScrollBehavior
) {
    Column(modifier = modifier) {
        CenterAlignedTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            scrollBehavior = scrollBehavior,
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_github),
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(R.string.users_screen_first_title),
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                    )

                    Text(
                        text = stringResource(R.string.users_screen_second_title),
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1
                    )

                }
            }
        )
        Divider()
    }
}


@Composable
private fun UserListItem(
    modifier: Modifier,
    login: String,
    avatarUrl: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.LightGray),
            model = avatarUrl,
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(R.string.cd_user_avatar),
            loading = {
                CircularProgressIndicator(strokeWidth = 1.dp)
            }
        )
        Text(
            text = login,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}