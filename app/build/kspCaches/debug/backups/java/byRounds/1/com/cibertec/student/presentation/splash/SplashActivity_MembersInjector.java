package com.cibertec.student.presentation.splash;

import com.cibertec.student.domain.repository.AuthRepository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;

@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class SplashActivity_MembersInjector implements MembersInjector<SplashActivity> {
  private final Provider<AuthRepository> authRepositoryProvider;

  public SplashActivity_MembersInjector(Provider<AuthRepository> authRepositoryProvider) {
    this.authRepositoryProvider = authRepositoryProvider;
  }

  public static MembersInjector<SplashActivity> create(
      Provider<AuthRepository> authRepositoryProvider) {
    return new SplashActivity_MembersInjector(authRepositoryProvider);
  }

  public static MembersInjector<SplashActivity> create(
      javax.inject.Provider<AuthRepository> authRepositoryProvider) {
    return new SplashActivity_MembersInjector(Providers.asDaggerProvider(authRepositoryProvider));
  }

  @Override
  public void injectMembers(SplashActivity instance) {
    injectAuthRepository(instance, authRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.cibertec.student.presentation.splash.SplashActivity.authRepository")
  public static void injectAuthRepository(SplashActivity instance, AuthRepository authRepository) {
    instance.authRepository = authRepository;
  }
}
