package com.osg.practice04sharedelemetcompose

import android.content.Intent
import android.os.Bundle
import android.transition.Visibility
import android.util.Log
import android.view.Gravity
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.osg.practice04sharedelemetcompose.intent.MainIntent
import com.osg.practice04sharedelemetcompose.state.MainState
import com.osg.practice04sharedelemetcompose.ui.Practice04SharedElemetComposeTheme
import com.osg.practice04sharedelemetcompose.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Practice04SharedElemetComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    RootScreen(
                            mainViewModel.state,
                            { toNextScreen() },
                            { toNextActivity() }
                    )
                }
            }
        }
    }

    private fun toNextScreen() {
        mainViewModel.viewModelScope.launch {
            mainViewModel.intent.send(MainIntent.SecondScreen)
            Log.d("asdf", mainViewModel.state.value.toString())
        }
    }
    private fun toNextActivity() {
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
    }
}

@Composable
fun RootScreen(
        state: LiveData<MainState>,
        toNextScreen: () -> Unit,
        toNextActivity: () -> Unit
) {
    Column {
        Text("베이스 화면")
        val currentState = state.observeAsState("")
        when(currentState.value) {
            is MainState.FirstScreen -> {
                Screen01(toNextScreen = toNextScreen, toNextActivity = toNextActivity, users = users)
            }
//            is MainState.SecondScreen -> {
//                Screen02(Image(imageResource(id = it.img)))
//            }
        }
    }

}

@Composable
fun Screen01(toNextScreen: () -> Unit, toNextActivity: () -> Unit, users: List<User>) {
    val expand: MutableState<Boolean> = remember { mutableStateOf(false) }
    Column(Modifier.animateContentSize()) {
        Text("스크린01")
        if(!expand.value) {
            TestList(expand = expand, toNextScreen = toNextScreen, users )
        } else {
            Screen02(image = users[0].img, name = users[0].name)
        }
    }
}

@Composable
fun Screen02(image: Int, name: String) {
    Column(Modifier.fillMaxSize()) {
        Text(name)
        Image(imageResource(id = image), Modifier.fillMaxWidth())
    }
}

@Composable
fun SharedElement(
    expand: MutableState<Boolean>
) {
    Card {
        Column(Modifier
            .animateContentSize()
            .clickable(onClick = { expand.value = !expand.value })
        ) {
            if(expand.value) {
                Image(
                    imageResource(id = R.drawable.img_temp),
                    Modifier
                        .width(300.dp)
                        .height(300.dp)
                )    
            } else  {
                Image(
                    imageResource(id = R.drawable.img_temp),
                    Modifier
                        .width(200.dp)
                        .height(200.dp)
                )
            }
        }
    }
}

@Composable
fun TestList(expand: MutableState<Boolean>, toNextScreen: () -> Unit, users: List<User>) {
    Column(Modifier.animateContentSize()) {
        LazyColumn() {
            items(users) {
                Row(
                    Modifier
                        .clickable(onClick =  { expand.value = !expand.value } )
                ) {
                    Image(
                        imageResource(id = it.img),
                        Modifier.width(50.dp)
                            .height(50.dp)
                    )
                    Text(it.name)
                }
            }
        }
    }
}

data class User(@DrawableRes val img: Int, val name: String)

val users = listOf(
    User(R.drawable.img_temp, "고양이"),
    User(R.drawable.img_temp, "고라니"),
    User(R.drawable.img_temp, "멍멍이"),
    User(R.drawable.img_temp, "삐약이"),
    User(R.drawable.img_temp, "음메에"),
    User(R.drawable.img_temp, "살쾡이"),
    User(R.drawable.img_temp, "하늘소"),
    User(R.drawable.img_temp, "수달"),
    User(R.drawable.img_temp, "팽이"),
    User(R.drawable.img_temp, "어찌고")
)

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
//    Practice04SharedElemetComposeTheme {
//        RootScreen(mainViewModel.state)
//    }
}
