package com.example.simpleviewmodel.ui.viewmodel

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.simpleviewmodel.model.ListGender
import com.example.simpleviewmodel.ui.view.DetailMahasiswaView
import com.example.simpleviewmodel.ui.view.FormMahasiswaView
import java.text.Normalizer.Form

enum class Halaman{
    Form,
    Data
}

@Composable
fun Navigasi(
    modifier: Modifier = Modifier,
    viewModel: MahasiswaViewModel = viewModel(),
    navHost: NavHostController = rememberNavController()
){
    Scaffold { isipadding ->
        val uiState by viewModel.dataModel.collectAsState()
        NavHost(
            modifier = Modifier.padding(isipadding),
            navController = navHost,
            startDestination = Halaman.Form.name
        ){
            composable(route = Halaman.Form.name){
                val konteks = LocalContext.current
                FormMahasiswaView(
                    listGender = ListGender.listGender.map { isi ->
                        konteks.resources.getString(isi)
                    },
                    onSubmitClick = {
                        viewModel.saveDataMhs(it)
                        navHost.navigate(Halaman.Data.name)
                    }
                )
            }
            composable(route = Halaman.Data.name){
                DetailMahasiswaView(
                    dataMhs = uiState,
                    onBackButton = {
                        navHost.popBackStack()
                    }
                )
            }
        }
    }
}