package com.example.educonnect.ui.profile

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.educonnect.R
import com.example.educonnect.data.model.users.StudentProfile
import com.example.educonnect.ui.EduViewModelProvider
import com.example.educonnect.ui.authentication.LocalAuthState
import com.example.educonnect.ui.components.CustomAppBar
import com.example.educonnect.ui.information_form.DateOfBirthTextField
import com.example.educonnect.ui.information_form.Gender
import com.example.educonnect.ui.information_form.GenderSelection
import com.example.educonnect.ui.information_form.SpecializationDropdown
import com.example.educonnect.ui.navigation.NavigationDestination
import com.example.educonnect.ui.signup.CustomTextField
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

object ProfileEditDestination : NavigationDestination {
    override val route = "profile_edit"
    override val titleRes = R.string.profile_edit_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditScreen(
    onNavigateBack : () -> Unit,
    innerPadding : PaddingValues,
    viewModel: ProfileEditViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = EduViewModelProvider.Factory
    )
) {
    val coroutineScope = rememberCoroutineScope()

    var expanded by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    val authState = LocalAuthState.current
    LaunchedEffect(authState.currentUserId) {
        authState.currentUserId?.let { userId ->
            viewModel.setCurrentUserId(userId)
        }
    }

    if (!authState.isLoggedIn) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
        return
    }

    Scaffold(
        topBar = {
            CustomAppBar(
                title = stringResource(id = R.string.profile_edit_title),
                hasActions = false,
                navigationOnClick = onNavigateBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp,
                        vertical = 16.dp
                    ),
            )
        }
    ) {
        ProfileEditBody(
            uiState = viewModel.userProfileUiState,
            onProfileChange = viewModel::updateUiState,
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
            onDismissRequest = {
                expanded = false
            },
            datePickerState = datePickerState,
            showDatePicker = showDatePicker,
            onDateClick = {
                showDatePicker = !showDatePicker
            },
            onDateDismissRequest = {
                showDatePicker = false
            },
            onClick = {
                expanded = false
            },
            onSignUp = {
                coroutineScope.launch {
                    viewModel.updateUserProfile()
                }
            },
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                )
                .padding(innerPadding)

        )
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileEditBody(
    uiState: ProfileEditUiState,
    onProfileChange: (StudentProfile) -> Unit,
    showDatePicker: Boolean,
    datePickerState: DatePickerState,
    onDateClick: () -> Unit,
    onDateDismissRequest: () -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    onClick: (String) -> Unit,
    onSignUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            if (uiState.currentUser != null) {
                ProfileTextField(
                    currentUser = uiState.currentUser,
                    onValueChange = onProfileChange,
                    onDateClick = onDateClick,
                    onDateDismissRequest = onDateDismissRequest,
                    expanded = expanded,
                    showDatePicker = showDatePicker,
                    datePickerState = datePickerState,
                    onExpandedChange = onExpandedChange,
                    onDismissRequest = onDismissRequest,
                    onClick = onClick
                )
                TextButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 16.dp
                        ),
                    colors = ButtonColors(
                        containerColor = Color(0xFF0961F5),
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFF0961F5),
                        disabledContentColor = Color.White
                    ),
                    enabled = uiState.isFormValid,
                    onClick = onSignUp
                ) {
                    Text(
                        stringResource(R.string.to_home),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(
                            vertical = 8.dp
                        )
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Người dùng không tồn tại",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileTextField(
    currentUser : StudentProfile,
    onValueChange : (StudentProfile) -> Unit,
    onDateClick : () -> Unit,
    onDateDismissRequest : () -> Unit,
    expanded : Boolean,
    showDatePicker: Boolean,
    datePickerState: DatePickerState,
    onExpandedChange : (Boolean) -> Unit,
    onDismissRequest : () -> Unit,
    onClick : (String) -> Unit,
) {
    ProfileAvatar(
        userAvatar = currentUser.avatarUrl,
        userName = currentUser.name,
        onClick = {

        }
    )

    CustomTextField(
        title = R.string.name,
        value = currentUser.name,
        onValueChange = {
            onValueChange(
                currentUser.copy(
                    name = it
                )
            )
        },
        visualTransformation = VisualTransformation.None,
        placeholder = {
            Text(
                stringResource(R.string.john_wick),
                style = MaterialTheme.typography.titleSmall,
                color = Color.Gray,
            )
        },
        leadingIcon = {
            Icon(
                Icons.Filled.Person,
                contentDescription = stringResource(R.string.name),
                tint = Color.Black.copy(
                    alpha = 0.7f
                )
            )
        },
        trailingIcon = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 24.dp,
            )
    )

    DateOfBirthTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 16.dp
            ),
        title = R.string.dob,
        selectedDate = currentUser.dateOfBirth,
        onValueChange = {
            onValueChange(
                currentUser.copy(
                    dateOfBirth = it!!
                )
            )
        },

        visualTransformation = VisualTransformation.None,
        placeholder = {
            Text(
                stringResource(id = R.string.dob),
                style = MaterialTheme.typography.titleSmall,
                color = Color.Gray,
            )
        },
        leadingIcon = {
            Icon(
                Icons.Filled.DateRange,
                contentDescription = stringResource(R.string.email),
                tint = Color.Black.copy(
                    alpha = 0.7f
                )
            )
        },
        trailingIcon = {
            IconButton(onClick = onDateClick) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Select date"
                )
            }
        },
        showDatePicker = showDatePicker,
        onDismissRequest = onDateDismissRequest,
        datePickerState = datePickerState
    )

    CustomTextField(
        title = R.string.number,
        value = currentUser.number,
        onValueChange = {
            onValueChange(
                currentUser.copy(
                    number = it
                )
            )
        },
        placeholder = {
            Text(
                stringResource(R.string.number),
                style = MaterialTheme.typography.titleSmall,
                color = Color.Gray
            )
        },
        leadingIcon = {
            Icon(
                Icons.Rounded.Call,
                contentDescription = stringResource(R.string.number)
            )
        },
        trailingIcon = { },
        visualTransformation = VisualTransformation.None,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 24.dp,
            )
    )

    CustomTextField(
        title = R.string.school,
        value = currentUser.school,
        onValueChange = {
            onValueChange(
                currentUser.copy(
                    school = it
                )
            )
        },
        visualTransformation = VisualTransformation.None,
        placeholder = {
            Text(
                stringResource(R.string.school_ex),
                style = MaterialTheme.typography.titleSmall,
                color = Color.Gray,
            )
        },
        leadingIcon = {
            Icon(
                Icons.Filled.Home,
                contentDescription = stringResource(R.string.school),
                tint = Color.Black.copy(
                    alpha = 0.7f
                )
            )
        },
        trailingIcon = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 24.dp,
            )
    )

    CustomTextField(
        title = R.string.address,
        value = currentUser.address,
        onValueChange = {
            onValueChange(
                currentUser.copy(
                    address = it
                )
            )
        },
        visualTransformation = VisualTransformation.None,
        placeholder = {
            Text(
                stringResource(R.string.address),
                style = MaterialTheme.typography.titleSmall,
                color = Color.Gray,
            )
        },
        leadingIcon = {
            Icon(
                Icons.Filled.LocationOn,
                contentDescription = stringResource(R.string.address),
                tint = Color.Black.copy(
                    alpha = 0.7f
                )
            )
        },
        trailingIcon = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 24.dp,
            )
    )

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 16.dp,
            ),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            "Bạn là:",
            style = MaterialTheme.typography.titleSmall,
            color = Color.Black.copy(
                alpha = 0.7f
            ),
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            for (gender in Gender.entries) {
                GenderSelection(
                    gender = gender,
                    isSelected = currentUser.gender == gender.gender,
                    onGenderSelected = {
                        onValueChange(
                            currentUser.copy(
                                gender = gender.gender
                            )
                        )
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }

    SpecializationDropdown(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 16.dp
            ),
        title = R.string.major,
        expanded = expanded,
        selectedMajor = currentUser.major,
        onExpandedChange = onExpandedChange,
        onValueChange = {
            onValueChange(
                currentUser.copy(
                    major = it
                )
            )
        },
        trailingIcon = {
            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
        },
        onDismissRequest = onDismissRequest,
        placeholder = {
            Text(
                stringResource(id = R.string.select_major),
                style = MaterialTheme.typography.titleSmall,
                color = Color.Gray
            )
        },
        onClick = onClick
    )
}

@Composable
private fun ProfileAvatar(
    @DrawableRes userAvatar : Int,
    userName : String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(vertical = 12.dp)
            .clickable {

            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            contentAlignment = Alignment.BottomEnd
        ) {
            Image(
                painter = painterResource(id = userAvatar),
                contentDescription = userName,
                modifier = Modifier.size(150.dp)
            )
            Box(
                modifier = Modifier
                    .size(35.dp)
                    .offset(x = (-15).dp, y = (-10).dp)
                    .background(
                        color = Color(0xFF0961F5),
                        shape = RoundedCornerShape(100.dp)
                    )
                    .clip(RoundedCornerShape(100.dp))
                    .border(1.dp, Color.White, RoundedCornerShape(100.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.edit_write_alt_svgrepo_com),
                    tint = Color.White,
                    contentDescription = "Edit",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Text(
            userName,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}
