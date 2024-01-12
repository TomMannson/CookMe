package com.tommannson.familycooking.infrastructure

import com.tommannson.familycooking.infrastructure.cameraPicker.CameraPicker
import com.tommannson.familycooking.infrastructure.cameraPicker.CameraPickerImpl
import com.tommannson.familycooking.infrastructure.textRecognition.ImageToTextDecoderImpl
import com.tommannson.familycooking.infrastructure.textRecognition.TextRecognizer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class InfrastructureModule {

    @Binds
    internal abstract fun cameraPicker(impl: CameraPickerImpl): CameraPicker

    @Binds
    internal abstract fun textRecognizer(impl: ImageToTextDecoderImpl): TextRecognizer

}