package com.osg.practice04sharedelemetcompose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import com.osg.practice04sharedelemetcompose.ui.Practice04SharedElemetComposeTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Practice04SharedElemetComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    RootScreen()
                }
            }
        }
    }
}

enum class State {
    SCREEN01, SCREEN02
}

@Composable
fun RootScreen() {
    Column {
        Text("베이스 화면")
        Screen01()
    }
}

@Composable
fun Screen01() {
    Column {
        Text("스크린01")
        val img = Image(imageResource(id = R.drawable.img_temp))
    }
}

@Composable
fun Screen02() {
    Column() {
        Text("스크린02")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Practice04SharedElemetComposeTheme {
        RootScreen()
    }
}