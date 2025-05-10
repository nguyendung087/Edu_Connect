package com.example.educonnect.ui.information_form

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.educonnect.R
import com.example.educonnect.data.LocalCourseCategoryData
import com.example.educonnect.data.model.users.TeacherProfile
import com.example.educonnect.ui.EduViewModelProvider
import com.example.educonnect.ui.authentication.LocalAuthState
import com.example.educonnect.ui.navigation.NavigationDestination
import com.example.educonnect.ui.signup.CustomTextField
import com.example.educonnect.ui.signup.SignupUiState
import com.example.educonnect.ui.signup.SignupViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

object InformationFormDestination : NavigationDestination {
//    const val userIdArg = "userId"
    override val titleRes = R.string.mentor_infor
    override val route = "mentor_information"
//    val routeWithArgs = "$route/{$userIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformationScreen(
    navigateToHomeScreen : () -> Unit,
    viewModel : InformationFormViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = EduViewModelProvider.Factory
    )
) {
    val coroutineScope = rememberCoroutineScope()
    val uiState = viewModel.teacherProfileState.collectAsState()
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


    InformationBody(
        name = name,
        number = number,
        selectedGender = selectedGender,
        onNameChange = {
            name = it
        },
        onNumberChange = {
            number = it
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
                viewModel.saveTeacherProfile(
                    TeacherProfile(
                        teacherId = authState.currentUserId!!,
                        name = name,
                        gender = selectedGender,
                        dateOfBirth = selectedDate!!,
                        number = number,
                        specialization = selectedMajor
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
fun InformationBody(
    name : String,
    number : String,
    selectedGender : String,
    onNameChange : (String) -> Unit,
    onNumberChange : (String) -> Unit,
//    onDateOfBirthChange : (String) -> Unit,
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
            selectedMajor.isNotEmpty()

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpecializationDropdown(
    @StringRes title : Int,
    expanded : Boolean,
    selectedMajor : String,
    onExpandedChange : (Boolean) -> Unit,
    onValueChange : (String) -> Unit,
    placeholder : @Composable () -> Unit,
    trailingIcon : @Composable () -> Unit,
    onDismissRequest : () -> Unit,
    onClick : (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            stringResource(title),
            style = MaterialTheme.typography.titleSmall,
            color = Color.Black.copy(
                alpha = 0.7f
            ),
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = onExpandedChange
        ) {
            TextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                placeholder = placeholder,
                singleLine = true,
                value = selectedMajor,
                onValueChange = onValueChange,
                trailingIcon = trailingIcon,
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = onDismissRequest
            ) {
                LocalCourseCategoryData.courseCategories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category.first) },
                        onClick = {
                            onClick(category.first)
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateOfBirthTextField(
    @StringRes title : Int,
    selectedDate: LocalDate?,
    onValueChange: (LocalDate?) -> Unit,
    visualTransformation: VisualTransformation,
    placeholder: @Composable () -> Unit,
    leadingIcon: @Composable () -> Unit,
    trailingIcon: @Composable (() -> Unit)?,
    showDatePicker : Boolean,
    onDismissRequest : () -> Unit,
    datePickerState : DatePickerState,
    modifier: Modifier = Modifier
) {
    val displayDate = selectedDate?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) ?: ""
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            stringResource(title),
            style = MaterialTheme.typography.titleSmall,
            color = Color.Black.copy(
                alpha = 0.7f
            ),
        )
        TextField(
            value = displayDate,
            singleLine = true,
            onValueChange = {},
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            visualTransformation = visualTransformation,
            trailingIcon = trailingIcon,
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFF4F6F9),
                disabledTextColor = Color.Gray,
                focusedContainerColor = Color(0xFFF4F6F9),
                errorLabelColor = Color.Red.copy(
                    alpha = 0.2f
                ),
                unfocusedTextColor = Color.Gray,
                focusedTextColor = Color.Black.copy(
                    alpha = 0.8f
                ),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth()
        )
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = onDismissRequest,
                confirmButton = {
                    TextButton(onClick = {
                        val date = datePickerState.selectedDateMillis?.let {
                            Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
                        }
                        onValueChange(date)
                        onDismissRequest()
                    }) {
                        Text("Okay")
                    }
                },
                dismissButton = {
                    TextButton(onClick = onDismissRequest) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}

@Composable
fun GenderSelection(
    gender: Gender,
    isSelected : Boolean,
    onGenderSelected : () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = if (isSelected) gender.backgroundColor else Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onGenderSelected,
            colors = RadioButtonDefaults.colors(
                selectedColor = Color.White.copy(
                    alpha = 0.8f
                ),
                unselectedColor = Color.Black.copy(
                    alpha = 0.8f
                )
            ),
            modifier = Modifier
                .weight(1.2f)
                .padding(
                    vertical = 12.dp,
                    horizontal = 8.dp
                ),
        )
        Icon(
            painter = painterResource(gender.icon),
            contentDescription = gender.gender,
            tint = Color.Unspecified,
            modifier = Modifier
                .weight(1.2f)

        )
        Text(
            gender.gender,
            style = MaterialTheme.typography.titleSmall,
            color = if (isSelected) Color.White else Color.Black.copy(
                alpha = 0.5f
            ),
            modifier = Modifier
                .weight(2f)
                .padding(horizontal = 8.dp)
        )
    }
}

enum class Gender(
    val gender: String,
    @DrawableRes val icon: Int,
    val backgroundColor: Color
) {
    MALE(
        gender = "Nam",
        icon = R.drawable.male_programmer_upper_body_svgrepo_com,
        backgroundColor = Color(0xFF0961F5)
    ),
    FEMALE(
        gender = "Nữ",
        icon = R.drawable.female_programmer_upper_body_svgrepo_com,
        backgroundColor = Color(0xFFFFA800)
    )
}