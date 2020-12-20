package com.osg.practice04sharedelemetcompose.state

sealed class MainState {
    object FirstScreen: MainState()
    object SecondScreen: MainState()
    data class Error(val error: String) : MainState()
}