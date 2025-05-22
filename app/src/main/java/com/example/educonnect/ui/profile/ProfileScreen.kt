package com.example.educonnect.ui.profile

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.educonnect.R
import com.example.educonnect.ui.EduViewModelProvider
import com.example.educonnect.ui.authentication.LocalAuthState
import com.example.educonnect.ui.components.CustomAppBar
import com.example.educonnect.ui.navigation.NavigationDestination
import com.example.educonnect.ui.theme.EduConnectTheme

object ProfileDestination : NavigationDestination {
    override val route = "profile"
    override val titleRes = R.string.profile_title
}

@Composable
fun ProfileScreen(
    innerPadding : PaddingValues,
    viewModel: ProfileViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = EduViewModelProvider.Factory
    ),
    navigateToProfileEdits : (String) -> Unit,
    navigateToSettings : () -> Unit,
    navigateToLogin : () -> Unit,
) {
    val authState = LocalAuthState.current
    val uiState = viewModel.profileUiState.collectAsState()
    LaunchedEffect(authState.currentUserId) {
        viewModel.getCurrentUser(authState.currentUserId, authState.userRole)
    }

    if (!authState.isLoggedIn) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Vui lòng đăng nhập để tiếp tục",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 18.sp,
                color = Color.Black
            )
        }
        return
    }

    Scaffold(
        topBar = {
            CustomAppBar(
                title = stringResource(id = R.string.profile_title),
                hasActions = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp,
                        vertical = 16.dp
                    ),
            )
        }
    ) {
        when (authState.userRole) {
            "Giáo viên" ->
                uiState.value.currentMentor?.let { user ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = it.calculateTopPadding(),
                                bottom = it.calculateBottomPadding()
                            )
                            .padding(innerPadding)
                            .background(Color.White),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        ProfileAvatar(
                            userAvatar = user.avatarUrl,
                            userName = user.name
                        )

                        ProfileContent(
                            profile = ProfileItem.PROFILE,
                            onClick = { navigateToProfileEdits(user.teacherId) }
                        )
                        HorizontalDivider(
                            color = Color.Black.copy(
                                alpha = 0.1f
                            ),
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                        )


                        ProfileContent(
                            profile = ProfileItem.SETTING,
                            onClick = navigateToSettings
                        )
                        HorizontalDivider(
                            color = Color.Black.copy(
                                alpha = 0.1f
                            ),
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                        )


                        ProfileContent(
                            profile = ProfileItem.LOGOUT,
                            onClick = {
                                viewModel.logout()
                                navigateToLogin()
                            }
                        )
                        HorizontalDivider(
                            color = Color.Black.copy(
                                alpha = 0.1f
                            ),
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                        )

                    }
                }
            else ->
                uiState.value.currentStudent?.let { user ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = it.calculateTopPadding(),
                                bottom = it.calculateBottomPadding()
                            )
                            .padding(innerPadding)
                            .background(Color.White),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        ProfileAvatar(
                            userAvatar = user.avatarUrl,
                            userName = user.name
                        )

                        ProfileContent(
                            profile = ProfileItem.PROFILE,
                            onClick = { navigateToProfileEdits(user.studentId) }
                        )
                        HorizontalDivider(
                            color = Color.Black.copy(
                                alpha = 0.1f
                            ),
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                        )


                        ProfileContent(
                            profile = ProfileItem.SETTING,
                            onClick = navigateToSettings
                        )
                        HorizontalDivider(
                            color = Color.Black.copy(
                                alpha = 0.1f
                            ),
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                        )


                        ProfileContent(
                            profile = ProfileItem.LOGOUT,
                            onClick = {
                                viewModel.logout()
                                navigateToLogin()
                            }
                        )
                        HorizontalDivider(
                            color = Color.Black.copy(
                                alpha = 0.1f
                            ),
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                        )

                    }
                }
        } ?: run {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
private fun ProfileAvatar(
    userAvatar : String,
    userName : String
) {
    Column(
        modifier = Modifier.padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = userAvatar.ifBlank { R.drawable.person_crop_circle_fill_svgrepo_com }
            ),
            contentDescription = userName,
            modifier = Modifier.size(150.dp)
        )
        Text(
            userName,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun ProfileContent(
    profile : ProfileItem,
    onClick : () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 16.dp,
                horizontal = 20.dp
            )
            .background(Color.White)
            .clickable {
                onClick()
            },
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = profile.symbolIcon),
            contentDescription = profile.title,
            tint = Color(0xFF0961F5),
            modifier = Modifier.weight(0.1f)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                profile.title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                fontWeight = FontWeight.Thin
            )
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color(0xFF0961F5),
                modifier = Modifier.size(35.dp)
            )
        }

    }
}

enum class ProfileItem(
    @DrawableRes val symbolIcon : Int,
    val title : String,
) {
    PROFILE(
        title = "Your profile",
        symbolIcon = R.drawable.person_svgrepo_com_rounded
    ),
    SETTING(
        title = "Settings",
        symbolIcon = R.drawable.setting_2_svgrepo_com
    ),
    LOGOUT(
        title = "Log out",
        symbolIcon = R.drawable.logout_svgrepo_com
    )
}