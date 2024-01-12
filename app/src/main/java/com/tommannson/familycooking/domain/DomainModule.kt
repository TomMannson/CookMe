package com.tommannson.familycooking.domain

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DomainModule {

    @Binds
    internal abstract fun loadImage(impl: DefaultLoadImageUseCase): LoadImageUseCase
    @Binds
    internal abstract fun textRecognitionUseCase(impl: DefaultTextRecognitionUseCase): TextRecognitionUseCase
}