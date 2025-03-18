import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gotam_project.R
import com.example.gotam_project.ui.screens.main.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val pet by viewModel.pet.collectAsState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(150.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        // картинка питомца
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Pet Image",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 16.dp)
        )

        // имя
        Text(text = "Pet Name: ${pet?.name ?: "No pet data available"}")

        Box(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                viewModel.setWalkTime(1000L) // заглушка времени
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text(text = "Гулять")
        }
    }
}