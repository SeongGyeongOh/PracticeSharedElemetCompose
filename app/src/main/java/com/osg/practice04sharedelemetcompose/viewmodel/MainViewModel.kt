package com.osg.practice04sharedelemetcompose.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osg.practice04sharedelemetcompose.intent.MainIntent
import com.osg.practice04sharedelemetcompose.state.MainState
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

@ActivityScoped
class MainViewModel@ViewModelInject constructor() : ViewModel() {
    val intent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _state = MutableLiveData<MainState>()
    val state: LiveData<MainState> get() = _state

    private val _expandState = MutableLiveData<Boolean>()
    val expandState: LiveData<Boolean> get() = _expandState

    init {
        handleIntent()
    }

    private fun handleIntent() {
        _state.value = MainState.FirstScreen
        viewModelScope.launch {
            intent.consumeAsFlow().collect {
                when(it) {
                    is MainIntent.SecondScreen -> openSecondScreen()
                }
            }
        }
    }

    private fun openSecondScreen() {
        viewModelScope.launch {
            _state.value = try {
                MainState.SecondScreen
            }catch (e: Exception) {
                MainState.Error(e.localizedMessage.toString())
            }
        }
    }
}