package com.cibertec.student.presentation.home;

import com.cibertec.student.domain.repository.AuthRepository;
import com.cibertec.student.domain.repository.CourseRepository;
import com.cibertec.student.domain.repository.TaskRepository;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<AuthRepository> authRepositoryProvider;

  private final Provider<CourseRepository> courseRepositoryProvider;

  private final Provider<TaskRepository> taskRepositoryProvider;

  public HomeViewModel_Factory(Provider<AuthRepository> authRepositoryProvider,
      Provider<CourseRepository> courseRepositoryProvider,
      Provider<TaskRepository> taskRepositoryProvider) {
    this.authRepositoryProvider = authRepositoryProvider;
    this.courseRepositoryProvider = courseRepositoryProvider;
    this.taskRepositoryProvider = taskRepositoryProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(authRepositoryProvider.get(), courseRepositoryProvider.get(), taskRepositoryProvider.get());
  }

  public static HomeViewModel_Factory create(
      javax.inject.Provider<AuthRepository> authRepositoryProvider,
      javax.inject.Provider<CourseRepository> courseRepositoryProvider,
      javax.inject.Provider<TaskRepository> taskRepositoryProvider) {
    return new HomeViewModel_Factory(Providers.asDaggerProvider(authRepositoryProvider), Providers.asDaggerProvider(courseRepositoryProvider), Providers.asDaggerProvider(taskRepositoryProvider));
  }

  public static HomeViewModel_Factory create(Provider<AuthRepository> authRepositoryProvider,
      Provider<CourseRepository> courseRepositoryProvider,
      Provider<TaskRepository> taskRepositoryProvider) {
    return new HomeViewModel_Factory(authRepositoryProvider, courseRepositoryProvider, taskRepositoryProvider);
  }

  public static HomeViewModel newInstance(AuthRepository authRepository,
      CourseRepository courseRepository, TaskRepository taskRepository) {
    return new HomeViewModel(authRepository, courseRepository, taskRepository);
  }
}
