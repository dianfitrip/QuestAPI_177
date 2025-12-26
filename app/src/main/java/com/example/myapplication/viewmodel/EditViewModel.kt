package com.example.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.myapplication.modeldata.DetailSiswa
import com.example.myapplication.modeldata.UIStateSiswa
import com.example.myapplication.modeldata.toDataSiswa
import com.example.myapplication.modeldata.toUIStateSiswa
import com.example.myapplication.repository.RepositoryDataSiswa
import com.example.myapplication.uicontroller.route.DestinasiDetail
import retrofit2.Response
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch



class EditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryDataSiswa: RepositoryDataSiswa
) : ViewModel() {
    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    private val idSiswa: Int = checkNotNull(savedStateHandle[DestinasiDetail.itemIdArg])

    init {
        viewModelScope.launch {
            try {
                val dataSiswa = repositoryDataSiswa.getSatuSiswa(idSiswa)
                uiStateSiswa = dataSiswa.toUIStateSiswa(true)
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error saat ambil data siswa: ${e.message}")
            }
        }
    }
    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa = UIStateSiswa(
            detailSiswa = detailSiswa,
            isEntryValid = validasiInput(detailSiswa)
        )
    }
    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa): Boolean {
        return with(uiState) {
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }
    suspend fun editSatuSiswa() {
        if (validasiInput(uiStateSiswa.detailSiswa)) {
            val call: Response<Void> = repositoryDataSiswa.editSatuSiswa(
                idSiswa,
                uiStateSiswa.detailSiswa.toDataSiswa()
            )
            if (call.isSuccessful) {
                println("Update Sukses : ${call.message()}")
            } else {
                println("Update Error : ${call.errorBody()}")
            }
        }
    }

}