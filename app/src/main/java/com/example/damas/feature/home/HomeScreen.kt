package com.example.damas.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.damas.core.getResourceProvider
import com.example.damas.domain.model.Dialog
import com.example.damas.feature.components.GenericButton
import com.example.damas.feature.components.dialog.ScreenDialog
import com.example.damas.feature.home.HomeUiEvent.ScreenEvent
import com.example.damas.resources.CheckersStrings

@Composable
fun HomeScreen(
    modifier: Modifier,
    viewModel: HomeViewModel,
    navigate: (String) -> Unit,
    navigateBack: () -> Unit,
) {
    Screen(
        modifier = modifier,
        uiState = viewModel.uiState,
        onActionEvent = viewModel::onActionEvent
    )

    EventConsumer(
        viewModel = viewModel,
        navigate = navigate,
        navigateBack = navigateBack
    )
}

@Composable
private fun Screen(
    modifier: Modifier,
    uiState: HomeUiState,
    onActionEvent: (HomeScreenAction) -> Unit,
    ) {
    val presentation by uiState.presentation.collectAsStateWithLifecycle()
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        presentation.buttons.forEachIndexed { index, text ->
            Spacer(Modifier.size(10.dp))
            GenericButton(
                text = text,
                onClick = { onActionEvent(HomeScreenAction.ListButtonClickedAction(index)) }
            )
        }
    }
    HomeScreenDialog(dialog = presentation.activeDialog)
}

@Composable
private fun EventConsumer(
    viewModel: HomeViewModel,
    navigate: (String) -> Unit,
    navigateBack: () -> Unit
) = LaunchedEffect(Unit) {
    viewModel.uiEvent.collect { event ->
        when (event) {
            is ScreenEvent.Navigate -> navigate(event.route)
            ScreenEvent.NavigateBack -> navigateBack()
        }
    }
}

@Composable
private fun HomeScreenDialog(dialog: Dialog?) {
    dialog?.let { ScreenDialog(dialog = dialog) }
}

@Preview
@Composable
private fun ScreenPreview() {
    val strings = CheckersStrings(resourceProvider = getResourceProvider())
    Screen(
        Modifier, HomeUiState(strings).apply {  }, {},
    )
}
