package com.example.kotlinmultiplatformproject.root.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kotlinmultiplatformproject.common.ui.theme.AppThemeProvider
import com.example.kotlinmultiplatformproject.root.model.AppTab
import com.example.kotlinmultiplatformproject.root.model.BottomBarItem

@Composable
fun BoxScope.RootBottomBar(
    selectedTab: AppTab,
    clickOnTab: (AppTab) -> Unit
) {
    Row(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .background(
                AppThemeProvider.colors.surface,
                shape = RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp)
            )
            .padding(8.dp)
            .fillMaxWidth ()
    ) {
        BottomBarItem.getItems().forEach { item ->
            BottomBarItemView(item, item.appTab == selectedTab) {
                clickOnTab(item.appTab)
            }
        }
    }
}

@Composable
fun RowScope.BottomBarItemView(
    bottomBarItem: BottomBarItem,
    isSelected: Boolean,
    clickOnTab: () -> Unit
) {
    val foreground = if(isSelected) AppThemeProvider.colors.accent else AppThemeProvider.colors.onSurface
    Column(
        modifier = Modifier
            .weight(1f)
            .padding(4.dp)
            .clickable {
                clickOnTab()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(bottomBarItem.title, color = foreground)
    }
}