package com.example.gotam_project.ui


import MainScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gotam_project.data.repository.PetRepositoryImpl
import com.example.gotam_project.data.room.AppDatabase
import com.example.gotam_project.domain.usecase.GetPetUsecase
import com.example.gotam_project.domain.usecase.SetWalkTime
import com.example.gotam_project.ui.screens.main.MainViewModel
import com.example.gotam_project.ui.screens.main.MainViewModelFactory
import com.example.gotam_project.ui.theme.GoTamProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // база данных и репозиторий
        val petDatabase = AppDatabase.getDatabase(this)
        val petRepository = PetRepositoryImpl(petDatabase.petDao())

        // usecase
        val getPetUsecase = GetPetUsecase(petRepository)
        val setWalkTime = SetWalkTime(petRepository)

        // передаем фабрику в setContent
        setContent {
            GoTamProjectTheme {
                val viewModel: MainViewModel = viewModel(
                    factory = MainViewModelFactory(getPetUsecase, setWalkTime)
                )
                MainScreen(viewModel = viewModel)
            }
        }
    }
}