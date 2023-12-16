package com.example.internetcafeapplication.view.store


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.internetcafeapplication.R
import com.example.internetcafeapplication.model.Machine
import com.example.internetcafeapplication.model.MachineDto
import com.example.internetcafeapplication.viewmodel.StoreViewModel


// Assuming this would be your data source for the LazyColumn
//val storeList = listOf(
//    MainComputerData(1,"Alien",10,8),
//    MainComputerData(2,"HP",10,7),
//    MainComputerData(3,"MAC",10,6)
//)

@Composable
fun DetailComputerList(viewModel: StoreViewModel?){
    //var  storeList: MutableList<MainComputerData> = viewModel.list.value
    var lists: MachineDto = viewModel?.detailList?.value ?: MachineDto()
    var detailList: List<Machine>  = listOf()
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Available", "Occupied")

    when(selectedTabIndex){
        1 ->{
            detailList = lists.activated
        }
        0 ->{
            detailList = lists.notActivated
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Transparent,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = Color.Blue
                )
            },
            divider = {}
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }

        LazyColumn{
            items(detailList) { item ->
                DCItem(item)

            }
        }
    }

    viewModel?.detailList?.value.let{
        when(it){
             null ->{
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

            }
        }

    }
}

@Composable
fun DCItem(machine: Machine, modifier: Modifier = Modifier){
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .clip(RoundedCornerShape(15.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_computer_24),
                contentDescription = "Computer Image",
                modifier = Modifier
                    .size(100.dp)
            )

            Text(
                text = "${machine.name}",
                style = TextStyle(color = Color.Black) // Replace with your desired style
                // Add other Text properties as needed
            )


        }
    }


}



@Preview(showBackground = true)
@Composable
fun DCItemPreview() {
    DetailComputerList(null)
    //DCItem(Machine())
}
