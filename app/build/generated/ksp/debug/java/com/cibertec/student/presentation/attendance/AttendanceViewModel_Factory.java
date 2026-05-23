package com.cibertec.student.presentation.attendance;

import com.cibertec.student.domain.repository.AttendanceRepository;
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
public final class AttendanceViewModel_Factory implements Factory<AttendanceViewModel> {
  private final Provider<AuthRepository> authRepositoryProvider;

  private final Provider<AttendanceRepository> attendanceRepositoryProvider;

  public AttendanceViewModel_Factory(Provider<AuthRepository> authRepositoryProvider,
      Provider<AttendanceRepository> attendanceRepositoryProvider) {
    this.authRepositoryProvider = authRepositoryProvider;
    this.attendanceRepositoryProvider = attendanceRepositoryProvider;
  }

  @Override
  public AttendanceViewModel get() {
    return newInstance(authRepositoryProvider.get(), attendanceRepositoryProvider.get());
  }

  public static AttendanceViewModel_Factory create(
      javax.inject.Provider<AuthRepository> authRepositoryProvider,
      javax.inject.Provider<AttendanceRepository> attendanceRepositoryProvider) {
    return new AttendanceViewModel_Factory(Providers.asDaggerProvider(authRepositoryProvider), Providers.asDaggerProvider(attendanceRepositoryProvider));
  }

  public static AttendanceViewModel_Factory create(Provider<AuthRepository> authRepositoryProvider,
      Provider<AttendanceRepository> attendanceRepositoryProvider) {
    return new AttendanceViewModel_Factory(authRepositoryProvider, attendanceRepositoryProvider);
  }

  public static AttendanceViewModel newInstance(AuthRepository authRepository,
      AttendanceRepository attendanceRepository) {
    return new AttendanceViewModel(authRepository, attendanceRepository);
  }
}
