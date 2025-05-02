package com.example.educonnect.ui.information_form

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.educonnect.R
import com.example.educonnect.data.model.users.StudentProfile
import com.example.educonnect.ui.EduViewModelProvider
import com.example.educonnect.ui.authentication.LocalAuthState
import com.example.educonnect.ui.navigation.NavigationDestination
import com.example.educonnect.ui.signup.CustomTextField
import kotlinx.coroutines.launch
import java.time.LocalDate

object StudentInformationFormDestination : NavigationDestination {
//    const val userIdArg = "userId"
    override val titleRes = R.string.student_infor
    override val route = "student_information"
//    val routeWithArgs = "$route/{$userIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentInformationScreen(
    navigateToHomeScreen : () -> Unit,
    viewModel : StudentInformationViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = EduViewModelProvider.Factory
    )
) {
    val coroutineScope = rememberCoroutineScope()
    val uiState = viewModel.studentUiState.collectAsState()
    val authState = LocalAuthState.current

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
    var name by remember {
        mutableStateOf("")
    }
    var number by remember {
        mutableStateOf("")
    }
    var address by remember {
        mutableStateOf("")
    }
    var school by remember {
        mutableStateOf("")
    }
    var selectedGender by remember {
        mutableStateOf("")
    }
    var selectedMajor by remember {
        mutableStateOf("")
    }
    var expanded by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
//    var selectedDate = datePickerState.selectedDateMillis?.let {
//        convertMillisToDate(it)
//    } ?: ""
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    StudentInformationBody(
        name = name,
        number = number,
        school = school,
        address = address,
        selectedGender = selectedGender,
        onNameChange = {
            name = it
        },
        onNumberChange = {
            number = it
        },
        onSchoolChange = {
            school = it
        },
        onAddressChange = {
            address = it
        },
        onSelectedGender = {
            selectedGender = it
        },
        onMajorChange = {
            selectedMajor = it
        },
        expanded = expanded,
        selectedMajor = selectedMajor,
        onExpandedChange = {
            expanded = !expanded
        },
        onDismissRequest = {
            expanded = false
        },
        datePickerState = datePickerState,
        showDatePicker = showDatePicker,
        selectedDate = selectedDate,
        onDateChange = {
            selectedDate = it
        },
        onDateClick = {
            showDatePicker = !showDatePicker
        },
        onDateDismissRequest = {
            showDatePicker = false
        },
        onClick = {
            selectedMajor = it
            expanded = false
        },
        onSignUp = {
            coroutineScope.launch {
                viewModel.saveStudentProfile(
                    StudentProfile(
                        studentId = authState.currentUserId!!,
                        name = name,
                        number = number,
                        gender = selectedGender,
                        dateOfBirth = selectedDate!!,
                        address = address,
                        major = selectedMajor,
                        school = school
                    )
                )
            }
            if (uiState.value.isFilled) {
                navigateToHomeScreen()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentInformationBody(
    name : String,
    number : String,
    school : String,
    address : String,
    selectedGender : String,
    onNameChange : (String) -> Unit,
    onNumberChange : (String) -> Unit,
    onSchoolChange : (String) -> Unit,
    onAddressChange : (String) -> Unit,
    onSelectedGender : (String) -> Unit,
    onMajorChange : (String) -> Unit,
    showDatePicker: Boolean,
    datePickerState: DatePickerState,
    selectedDate : LocalDate?,
    onDateChange : (LocalDate?) -> Unit,
    onDateClick : () -> Unit,
    onDateDismissRequest : () -> Unit,
    expanded : Boolean,
    selectedMajor : String,
    onExpandedChange : (Boolean) -> Unit,
    onDismissRequest : () -> Unit,
    onClick : (String) -> Unit,
    onSignUp : () -> Unit
) {
    val isFormValid = name.isNotBlank() &&
            selectedDate != null &&
            number.isNotBlank() &&
            selectedGender.isNotEmpty() &&
            address.isNotBlank() &&
            selectedMajor.isNotEmpty() &&
            school.isNotBlank()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                stringResource(R.string.create),
                style = MaterialTheme.typography.headlineSmall,
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                stringResource(R.string.fill_infor),
                style = MaterialTheme.typography.titleSmall,
                color = Color.Gray,
                modifier = Modifier.padding(
                    vertical = 20.dp,
                    horizontal = 45.dp
                ),
                textAlign = TextAlign.Center
            )


            CustomTextField(
                title = R.string.name,
                value = name,
                onValueChange = onNameChange,
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
                selectedDate = selectedDate,
                onValueChange = onDateChange,

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
                value = number,
                onValueChange = onNumberChange,
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
                value = school,
                onValueChange = onSchoolChange,
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
                value = address,
                onValueChange = onAddressChange,
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
                            isSelected = selectedGender == gender.gender,
                            onGenderSelected = {
                                onSelectedGender(gender.gender)
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
                selectedMajor = selectedMajor,
                onExpandedChange = onExpandedChange,
                onValueChange = onMajorChange,
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
                enabled = isFormValid,
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
        }

    }
}





