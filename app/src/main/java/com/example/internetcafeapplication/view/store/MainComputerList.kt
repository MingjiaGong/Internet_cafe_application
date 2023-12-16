package com.example.internetcafeapplication.view.store


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.internetcafeapplication.R
import com.example.internetcafeapplication.model.MachineInfo
import com.example.internetcafeapplication.viewmodel.StoreViewModel


// Assuming this would be your data source for the LazyColumn
//val storeList = listOf(
//    MainComputerData(1,"Alien",10,8),
//    MainComputerData(2,"HP",10,7),
//    MainComputerData(3,"MAC",10,6)
//)

@Composable
fun TotalComputerList(viewModel: StoreViewModel,navHostController: NavHostController?){
    var  storeList: List<MachineInfo> = viewModel.mainlist.value?.machines ?: listOf()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)){
        items(storeList){ item->
            TCItem(item,navHostController,viewModel)

        }
    }

    viewModel.mainlist.value.let{
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
fun TCItem(machineInfo: MachineInfo,navHostController: NavHostController?,viewModel: StoreViewModel?){
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(5.dp)
            .clickable {
                viewModel?.showDetail(machineInfo.type)
                navHostController?.navigate("storeDetail")
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .clip(RoundedCornerShape(15.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {

            Text(
                text = "${machineInfo.type} PCs \nAvailable \n${machineInfo.activeCount}/${machineInfo.total}",
                style = TextStyle(color = Color.Black) // Replace with your desired style
                // Add other Text properties as needed
            )

            Image(
                painter = painterResource(id = R.drawable.baseline_computer_24),
                contentDescription = "Computer Image",
                modifier = Modifier
                    .size(130.dp)
            )
        }
    }


}



@Preview(showBackground = true)
@Composable
fun TCItemPreview() {
    TCItem(
        machineInfo= MachineInfo(),null,null)
}
