package com.example.kotlinmultiplatformproject.events.create.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.kotlinmultiplatformproject.categories.list.compose.CategoriesListView
import com.example.kotlinmultiplatformproject.common.ui.atoms.AppButton
import com.example.kotlinmultiplatformproject.common.ui.atoms.AppTextField
import com.example.kotlinmultiplatformproject.common.ui.atoms.BottomModalContainer
import com.example.kotlinmultiplatformproject.common.ui.atoms.TextPairButton
import com.example.kotlinmultiplatformproject.common.ui.calendar.compose.CalendarColors
import com.example.kotlinmultiplatformproject.common.ui.calendar.compose.DatePickerView
import com.example.kotlinmultiplatformproject.common.ui.calendar.model.CalendarDay
import com.example.kotlinmultiplatformproject.common.ui.theme.AppThemeProvider
import com.example.kotlinmultiplatformproject.di.DatePickerFactoryQualifier
import com.example.kotlinmultiplatformproject.di.getKoinInstance
import com.example.kotlinmultiplatformproject.events.create.CreateEventViewModel
import com.example.kotlinmultiplatformproject.events.model.SpendEvent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun CreateEventView(
    isExpand: Boolean,
    selectedDay: CalendarDay?,
    viewModel: CreateEventViewModel,
    createListener: (SpendEvent) -> Unit
) {

    val state by viewModel.state.collectAsState()
    var showDateDialog by remember { mutableStateOf(false) }
    var showCategoriesDialog by remember { mutableStateOf(false) }

    LaunchedEffect(isExpand) {
        if (isExpand) {
            viewModel.selectDate(selectedDay?.date)
        } else {
            viewModel.resetState()
        }

        viewModel.events.onEach { event ->
            when (event) {
                is CreateEventViewModel.Event.Finish -> createListener(event.spendEvent)
            }
        }.launchIn(this)
    }


    BottomModalContainer {
        TextPairButton(
            title = "category",
//            title = stringResource(MR.strings.category),
            buttonTitle = state.category.title.ifEmpty { "empty_category" },
            colorHex = state.category.colorHex.takeIf { it.isNotEmpty() }
        ) { showCategoriesDialog = true }

        TextPairButton(
            title = "date",
            buttonTitle = state.date.toString()
        ) { showDateDialog = true }

        AppTextField(
            value = state.title,
            placeholder = "spend_to",
            modifier = Modifier.fillMaxWidth()
        ) { viewModel.changeTitle(it) }
        AppTextField(
            value = state.note,
            placeholder = "note",
            modifier = Modifier.fillMaxWidth()
        ) { viewModel.changeNote(it) }

        AppTextField(
            value = state.cost.toString(),
            placeholder = "cost",
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        ) { viewModel.changeCost(it) }

        AppButton("save") {
            viewModel.finish()
        }
    }


    if (showCategoriesDialog) {
        Dialog(
            onDismissRequest = { showCategoriesDialog = false }
        ) {
            CategoriesListView(
                getKoinInstance(),
                modifier = Modifier.background(
                    AppThemeProvider.colors.surface,
                    RoundedCornerShape(16.dp)
                )
            ) { category ->
                showCategoriesDialog = false
                viewModel.selectCategory(category)
            }
        }
    }

    if (showDateDialog) {
        Dialog(onDismissRequest = { showDateDialog = false }) {
            DatePickerView(
                viewModel = getKoinInstance(DatePickerFactoryQualifier),
                colors = CalendarColors.default.copy(
                    colorAccent = AppThemeProvider.colors.accent,
                    colorOnSurface = AppThemeProvider.colors.onSurface,
                    colorSurface = AppThemeProvider.colors.surface
                )
            ) { day ->
                showDateDialog = false
                viewModel.selectDate(day.date)
            }
        }
    }

}









