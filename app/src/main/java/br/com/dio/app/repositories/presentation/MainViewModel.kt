package br.com.dio.app.repositories.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dio.app.repositories.domain.ListUserRepositoriesUserCase
import br.com.dio.app.repositories.model.Repo
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
// add na mao
import kotlinx.coroutines.flow.collect


class MainViewModel(
    private val listUserRepositoriesUserCase: ListUserRepositoriesUserCase
) : ViewModel() {

    private val _repos = MutableLiveData<State>()
    val repos: LiveData<State> = _repos


    fun getRepoList(user: String) {
        viewModelScope.launch {
            listUserRepositoriesUserCase(user)
                .onStart {
                    _repos.postValue(State.Loading)
                }
                .catch {
                    _repos.postValue(State.Error(it))
                }
                .collect {
                    _repos.postValue(State.Sucess(it))
                }
        }
    }

    sealed class State {
        object Loading : State()
        data class Sucess(val list: List<Repo>) : State()
        data class Error(val error: Throwable) : State()
    }
}

