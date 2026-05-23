package com.cibertec.student.presentation.schedule;

import com.cibertec.student.domain.repository.AuthRepository;
import com.cibertec.student.domain.repository.CourseRepository;
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
public final class ScheduleViewModel_Factory implements Factory<ScheduleViewModel> {
  private final Provider<AuthRepository> authRepositoryProvider;

  private final Provider<CourseRepository> courseRepositoryProvider;

  public ScheduleViewModel_Factory(Provider<AuthRepository> authRepositoryProvider,
      Provider<CourseRepository> courseRepositoryProvider) {
    this.authRepositoryProvider = authRepositoryProvider;
    this.courseRepositoryProvider = courseRepositoryProvider;
  }

  @Override
  public ScheduleViewModel get() {
    return newInstance(authRepositoryProvider.get(), courseRepositoryProvider.get());
  }

  public static ScheduleViewModel_Factory create(
      javax.inject.Provider<AuthRepository> authRepositoryProvider,
      javax.inject.Provider<CourseRepository> courseRepositoryProvider) {
    return new ScheduleViewModel_Factory(Providers.asDaggerProvider(authRepositoryProvider), Providers.asDaggerProvider(courseRepositoryProvider));
  }

  public static ScheduleViewModel_Factory create(Provider<AuthRepository> authRepositoryProvider,
      Provider<CourseRepository> courseRepositoryProvider) {
    return new ScheduleViewModel_Factory(authRepositoryProvider, courseRepositoryProvider);
  }

  public static ScheduleViewModel newInstance(AuthRepository authRepository,
      CourseRepository courseRepository) {
    return new ScheduleViewModel(authRepository, courseRepository);
  }
}
