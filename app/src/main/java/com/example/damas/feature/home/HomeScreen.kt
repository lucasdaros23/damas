package com.example.damas.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.damas.domain.model.Dialog
import com.example.damas.feature.components.GenericButton
import com.example.damas.feature.home.HomeUiEvent.ScreenEvent

@Composable
fun HomeScreen(
    modifier: Modifier,
    viewModel: HomeViewModel,
    showDialog: (Dialog) -> Unit,
    navigate: (String) -> Unit,
    navigateBack: () -> Unit,
) {
    Screen(
        modifier = modifier,
        onActionEvent = viewModel::onActionEvent
    )

    EventConsumer(
        viewModel = viewModel,
        showDialog = showDialog,
        navigate = navigate,
        navigateBack = navigateBack
    )
}

@Composable
private fun Screen(
    modifier: Modifier,
    onActionEvent: (HomeScreenAction) -> Unit,
    ) {
    val buttonsList = listOf(
        "jogar local",
        "jogar online",
        "jogar vs computador"
    )

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        buttonsList.forEachIndexed { index, text ->
            Spacer(Modifier.size(10.dp))
            GenericButton(
                text = text,
                onClick = { onActionEvent(HomeScreenAction.ListButtonClickedAction(index)) }
            )
        }
    }
}

@Composable
private fun EventConsumer(
    viewModel: HomeViewModel,
    showDialog: (Dialog) -> Unit,
    navigate: (String) -> Unit,
    navigateBack: () -> Unit
) = LaunchedEffect(Unit) {
    viewModel.uiEvent.collect { event ->
        when (event) {
            is ScreenEvent.Navigate -> navigate(event.route)
            is ScreenEvent.ShowDialog -> showDialog(event.dialog)
            ScreenEvent.NavigateBack -> navigateBack()
        }
    }
}

@Preview
@Composable
private fun ScreenPreview() {
    Screen(
        Modifier, {}
    )
}
