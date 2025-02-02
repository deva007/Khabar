package com.example.khabar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.khabar.ui.theme.YellowMellow

@Composable
fun OnboardingScreen(navController: NavController) {
    var nextClickCount by remember { mutableIntStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(YellowMellow), // Yellow background
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            // Content for the onboarding screen
            Spacer(modifier = Modifier.height(32.dp))
            if(nextClickCount == 0) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .padding(8.dp)
                        .wrapContentWidth()
                        .background(Color.Black),
                ) {
                    Text(
                        text = " Current Balance ",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = YellowMellow,
                    )
                }


                Text(
                    text = "F£$€kɆ",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = TextUnit(12f, TextUnitType.Sp),
                    color = Color.Black
                )
            }  else {
                Image(
                    painter = painterResource(R.drawable.ic_bar_chart_new),
                    modifier = Modifier.size(96.dp),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = Color.Black)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = if(nextClickCount == 0){"Stay on top of your game with always accessible information to run your business."} else {
                    "Monitor your sales and activity with a robust reporting engine built right into the app."
                },
                textAlign = TextAlign.Center,
                color = Color.Black,
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 32.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp),
                onClick = { navController.navigate("home_screen") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = YellowMellow,
                    contentColor = Color.Black
                ),
                border = BorderStroke(1.dp, color = Color.Black)
            ) {
                Text(text = "Skip")
            }
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp),
                onClick = { if(nextClickCount == 1) {
                    navController.navigate("home_screen")
                } else {
                    nextClickCount += 1
                }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = YellowMellow,
                    contentColor = Color.Black
                ),
                border = BorderStroke(1.dp, color = Color.Black)
            ) {
                Text(text = "Next")
            }
        }
    }
}