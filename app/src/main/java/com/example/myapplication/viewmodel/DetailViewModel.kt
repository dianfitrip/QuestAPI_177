package com.example.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.modeldata.DataSiswa
import com.example.myapplication.repository.RepositoryDataSiswa
import com.example.myapplication.uicontroller.route.DestinasiDetail
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


sealed interface StatusUIDetail {
    data class Success(val satusiswa: DataSiswa) : StatusUIDetail
    object Error : StatusUIDetail
    object Loading : StatusUIDetail
}

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryDataSiswa: RepositoryDataSiswa
) : ViewModel() {

    private val idSiswa: Int = checkNotNull(savedStateHandle[DestinasiDetail.itemIdArg])
    var statusUIDetail: StatusUIDetail by mutableStateOf(StatusUIDetail.Loading)
        private set

    init {
        getSatuSiswa()
    }
    fun getSatuSiswa() {
        viewModelScope.launch {
            statusUIDetail = StatusUIDetail.Loading
            statusUIDetail = try {
                StatusUIDetail.Success(satusiswa = repositoryDataSiswa.getSatuSiswa(idSiswa))
            } catch (e: IOException) {
                StatusUIDetail.Error
            } catch (e: HttpException) {
                StatusUIDetail.Error
            }
        }
    }
    suspend fun hapusSatuSiswa() {
        val resp: Response<Void> = repositoryDataSiswa.hapusSatuSiswa(idSiswa)

        if (resp.isSuccessful) {
            println("Sukses Hapus Data : ${resp.message()}")
        } else {
            println("Gagal Hapus Data : ${resp.errorBody()}")
        }
    }
}