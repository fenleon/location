package com.lightphone.location

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thelightphone.sdk.InitialScreen
import com.thelightphone.sdk.SealedLightActivity
import com.thelightphone.sdk.SimpleLightScreen
import com.thelightphone.sdk.ui.LightBarButton
import com.thelightphone.sdk.ui.LightIcon
import com.thelightphone.sdk.ui.LightIcons
import com.thelightphone.sdk.ui.LightText
import com.thelightphone.sdk.ui.LightTextVariant
import com.thelightphone.sdk.ui.LightTheme
import com.thelightphone.sdk.ui.LightThemeController
import com.thelightphone.sdk.ui.LightThemeTokens
import com.thelightphone.sdk.ui.LightTopBar
import com.thelightphone.sdk.ui.LightTopBarCenter
import com.thelightphone.sdk.ui.lightClickable

/**
 * AOSP's location settings activity — the screen `Settings.ACTION_LOCATION_SOURCE_SETTINGS`
 * resolves to (its exported LocationSettingsActivity). Tools can't build an
 * Intent themselves (plugin ban), so it's launched via the SDK's sanctioned
 * [SimpleLightScreen.startServerActivity], which takes a flattened component.
 */
private const val LOCATION_SETTINGS_COMPONENT =
    "com.android.settings/.Settings\$LocationSettingsActivity"

@InitialScreen
class LocationScreen(sealedActivity: SealedLightActivity) :
    SimpleLightScreen<Unit>(sealedActivity) {

    private var locationEnabled by mutableStateOf(false)

    override fun willShow() {
        // Refreshes on first show and every time we return from the AOSP
        // location screen (onResume → notifyWillShow), so the toggle is
        // current. Reads the framework switch from our own process via the
        // SDK client (LocationManager.isLocationEnabled — permission-free);
        // works on the emulator and the real device alike.
        locationEnabled = lightContext.locationEnabled
    }

    @Composable
    override fun Content() {
        val themeColors by LightThemeController.colors.collectAsState()
        LightTheme(colors = themeColors) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(LightThemeTokens.colors.background),
            ) {
                LightTopBar(
                    leftButton = LightBarButton.LightIcon(
                        icon = LightIcons.BACK,
                        onClick = { goBack() },
                        contentDescription = "Back to tools",
                    ),
                    center = LightTopBarCenter.Text("Location Services"),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .lightClickable { startServerActivity(LOCATION_SETTINGS_COMPONENT) }
                        .padding(horizontal = 24.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.Top,
                ) {
                    // Toggle sits immediately left of its action label,
                    // top-aligned so it lines up with the main label, not
                    // centered between the label and the caption.
                    Box(
                        modifier = Modifier.size(36.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        LightIcon(
                            icon = if (locationEnabled) {
                                LightIcons.TOGGLE_STATE_ON
                            } else {
                                LightIcons.TOGGLE_STATE_OFF
                            },
                            size = 2f,
                            contentDescription = if (locationEnabled) {
                                "Location services on"
                            } else {
                                "Location services off"
                            },
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Column {
                        LightText(
                            text = "Use Location",
                            variant = LightTextVariant.Heading,
                        )
                        LightText(
                            text = if (locationEnabled) "On" else "Off",
                            variant = LightTextVariant.Detail,
                            modifier = Modifier.padding(top = 2.dp),
                        )
                    }
                }
            }
        }
    }
}
