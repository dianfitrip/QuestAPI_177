package com.example.myapplication.view



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailSiswaScreen(
    navigateToEditItem: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    Scaffold(
        topBar = {
            SiswaTopAppBar(
                title = stringResource(DestinasiDetail.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            val uiState = viewModel.statusUIDetail // Sudah disesuaikan (UIDetail)
            FloatingActionButton(
                onClick = {
                    when(uiState){
                        is StatusUIDetail.Success ->
                            navigateToEditItem(uiState.satusiswa.id)
                        else ->{}
                    }
                },
    )
}