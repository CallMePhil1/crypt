package com.github.callmephil1.crypt.ui.compose.qrscan

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.callmephil1.crypt.ui.compose.CryptScaffold
import org.koin.androidx.compose.koinViewModel
import java.util.concurrent.Executors

@Composable
fun RequestCameraPermissions(onPermissionGranted: () -> Unit) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted)
            onPermissionGranted()
        else
            Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }
}

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    processImage: (ImageProxy) -> Unit
) {
    val context = LocalContext.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = remember { PreviewView(context) }
    val cameraExecutor = ContextCompat.getMainExecutor(context)
    var hasPermission: Boolean by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED)
    }

    DisposableEffect(Unit) {
        onDispose {
            cameraProviderFuture.get()?.unbindAll()
        }
    }

    if (hasPermission) {
        AndroidView(
            factory = {
                previewView.setBackgroundColor(Color.Transparent.toArgb())
                previewView.scaleType = PreviewView.ScaleType.FIT_CENTER
                previewView.apply {
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            },
            modifier = modifier,
            update = { _ ->
                cameraProviderFuture.addListener({
                    val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                    // Select the rear-facing camera
                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                    val imageAnalysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()

                    imageAnalysis.setAnalyzer(
                        Executors.newSingleThreadExecutor(),
                        processImage
                    )

                    try {
                        // Unbind previous use cases before rebinding
                        cameraProvider.unbindAll()
                        // Bind the camera to the lifecycle
                        cameraProvider.bindToLifecycle(
                            context as LifecycleOwner,
                            cameraSelector,
                            preview,
                            imageAnalysis
                        )
                    } catch (exc: Exception) {
                        Log.e("CameraPreview", "Use case binding failed", exc)
                    }
                }, cameraExecutor)
            }
        )
    } else {
        RequestCameraPermissions {
            hasPermission = true
        }
    }
}

@Composable
fun QrScanScreen(
    viewModel: QrScanViewModel = koinViewModel(),
    onDismissClicked: () -> Unit = {},
    onNavToNewEntry: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val navToNewEntry by viewModel.navToNewEntry.collectAsStateWithLifecycle()

    LaunchedEffect(navToNewEntry) {
        if (navToNewEntry) {
            onNavToNewEntry()
        }
    }

    CryptScaffold {
        Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            CameraPreview(
                processImage = viewModel::processBarcode,
                modifier = Modifier.fillMaxHeight()
            )

            TextButton(
                onClick = {
                    viewModel.onDismissClicked()
                    onDismissClicked()
                },
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.primaryContainer),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
                    .height(60.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    text = "Dismiss",
                    fontSize = 24.sp,
                    color = contentColorFor(MaterialTheme.colorScheme.secondaryContainer)
                )
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview
@Composable
fun QRScreenPreview() {
    Column {
//        QrScanScreen {
//
//        }
    }
}