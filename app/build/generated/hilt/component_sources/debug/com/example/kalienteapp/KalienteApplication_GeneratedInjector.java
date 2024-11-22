package com.example.kalienteapp;

import dagger.hilt.InstallIn;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.internal.GeneratedEntryPoint;

@OriginatingElement(
    topLevelClass = KalienteApplication.class
)
@GeneratedEntryPoint
@InstallIn(SingletonComponent.class)
public interface KalienteApplication_GeneratedInjector {
  void injectKalienteApplication(KalienteApplication kalienteApplication);
}
