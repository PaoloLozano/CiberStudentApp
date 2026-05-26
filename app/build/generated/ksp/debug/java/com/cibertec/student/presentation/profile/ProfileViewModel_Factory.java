package com.cibertec.student.presentation.profile;

import com.cibertec.student.domain.repository.AuthRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
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
public final class ProfileViewModel_Factory implements Factory<ProfileViewModel> {
  private final Provider<AuthRepository> authRepositoryProvider;

  public ProfileViewModel_Factory(Provider<AuthRepository> authRepositoryProvider) {
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public ProfileViewModel get() {
    return newInstance(authRepositoryProvider.get());
  }

  public static ProfileViewModel_Factory create(
      javax.inject.Provider<AuthRepository> authRepositoryProvider) {
    return new ProfileViewModel_Factory(Providers.asDaggerProvider(authRepositoryProvider));
  }

  public static ProfileViewModel_Factory create(Provider<AuthRepository> authRepositoryProvider) {
    return new ProfileViewModel_Factory(authRepositoryProvider);
  }

  public static ProfileViewModel newInstance(AuthRepository authRepository) {
    return new ProfileViewModel(authRepository);
  }
}
